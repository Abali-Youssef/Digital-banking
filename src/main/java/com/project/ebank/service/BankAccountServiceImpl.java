package com.project.ebank.service;

import com.project.ebank.dtos.*;
import com.project.ebank.entities.*;
import com.project.ebank.enums.AccountStatus;
import com.project.ebank.enums.OperationType;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.mappers.CardMapper;
import com.project.ebank.mappers.CurrentBankAccountMapper;
import com.project.ebank.mappers.CustomerMapper;
import com.project.ebank.mappers.SavingBankAccountMapper;
import com.project.ebank.repositories.BankAccountRepository;
import com.project.ebank.repositories.CardRepository;
import com.project.ebank.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{


    CustomerRepository customerRepository;
    BankAccountRepository bankAccountRepository;
    CardRepository cardRepository;
    BankAccountOperationService bankAccountOperationService;
    CurrentBankAccountMapper currentBankAccountMapper;
    SavingBankAccountMapper savingBankAccountMapper;
    CustomerMapper customerMapper;
    CardMapper cardMapper;
    @Override
    public CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer == null){
            throw new CustomerNotFoundException("customer not found");
        }
        CurrentAccount bankAccount=new CurrentAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setOverDraft(overDraft);
        CurrentAccount savedAccount = bankAccountRepository.save(bankAccount);
        return currentBankAccountMapper.fromBankAccount(savedAccount);
    }

    @Override
    public SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerID) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer == null){
            throw new CustomerNotFoundException("customer not found");
        }
        SavingAccount bankAccount=new SavingAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setInterestRate(interestRate);
        SavingAccount savedAccount = bankAccountRepository.save(bankAccount);
        return savingBankAccountMapper.fromBankAccount(savedAccount);
    }





    @Override
    public List<BankAccountDTO> listBankAccounts() {
        return bankAccountRepository.findAll().stream().map(bankAccount -> {

            if(bankAccount instanceof SavingAccount){
                SavingAccountDTO savingAccountDTO=savingBankAccountMapper.fromBankAccount((SavingAccount) bankAccount);
                savingAccountDTO.setCustomerDTO(customerMapper.fromCustomer(bankAccount.getCustomer()) );
                savingAccountDTO.setCardDTO(bankAccount.getCards().stream().map(card ->cardMapper.fromCard(card)).collect(Collectors.toList()));
                savingAccountDTO.setType("SAVING_ACCOUNT");
                return savingAccountDTO;
            }else{
                CurrentAccountDTO currentAccountDTO=currentBankAccountMapper.fromBankAccount((CurrentAccount) bankAccount);
                currentAccountDTO.setCustomerDTO(customerMapper.fromCustomer(bankAccount.getCustomer()));
                currentAccountDTO.setCardDTO(bankAccount.getCards().stream().map(card ->cardMapper.fromCard(card)).collect(Collectors.toList()));
                currentAccountDTO.setType("CURRENT_ACCOUNT");
                return currentAccountDTO;
            }
        }).collect(Collectors.toList());
    }
    @Override
    public BankAccountDTO getBankAccount(String id) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(id).orElseThrow(()-> new BankAccountNotFoundException("bank account not found"));
        if (bankAccount instanceof CurrentAccount){
            CurrentAccountDTO currentAccountDTO=currentBankAccountMapper.fromBankAccount((CurrentAccount) bankAccount);
            currentAccountDTO.setCustomerDTO(customerMapper.fromCustomer(bankAccount.getCustomer()));
            currentAccountDTO.setCardDTO(bankAccount.getCards().stream().map(card ->cardMapper.fromCard(card)).collect(Collectors.toList()));
            currentAccountDTO.setType("CURRENT_ACCOUNT");
            return currentAccountDTO;
        }
        SavingAccountDTO savingAccountDTO=savingBankAccountMapper.fromBankAccount((SavingAccount)  bankAccount);
        savingAccountDTO.setCustomerDTO(customerMapper.fromCustomer(bankAccount.getCustomer()) );
        savingAccountDTO.setCardDTO(bankAccount.getCards().stream().map(card ->cardMapper.fromCard(card)).collect(Collectors.toList()));
        savingAccountDTO.setType("SAVING_ACCOUNT");
        return savingAccountDTO;
    }
    @Override
    public void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountID).orElseThrow(()-> new BankAccountNotFoundException("bank account not found"));

        if (bankAccount.getBalance() < amount){
            throw new BalanceNotSufficientException("balance not sufficient");
        }
        bankAccountOperationService.saveOperation(OperationType.DEBIT,amount,description,new Date(),bankAccount);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountID, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountID).orElseThrow(()-> new BankAccountNotFoundException("bank account not found"));

        bankAccountOperationService.saveOperation(OperationType.CREDIT,amount,description,new Date(),bankAccount);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }
    @Override
    public void transfer(String accountIDSource, String accountIDDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIDSource,amount,"Transfer to "+accountIDDestination);
        credit(accountIDDestination,amount,"Transfert from "+accountIDSource);
    }
    @Override
    public BankAccountDTO attachCard(String id, CardDTO cardDTO) throws BankAccountNotFoundException {
        Card card = cardMapper.fromCardDTO(cardDTO);
        card.setBankAccount(bankAccountRepository.findById(id).orElseThrow());
        cardRepository.save(card);
        return getBankAccount(id);
    }
    @Override
    public void deleteBankAccount(String accountId){
        bankAccountRepository.deleteById(accountId);
    }
    @Override
    public BankAccountDTO editBankAccountStatus(String id, AccountStatus accountStatus) throws BankAccountNotFoundException {
        BankAccount  bankAccount=bankAccountRepository.findById(id).orElseThrow(()->new BankAccountNotFoundException("account not found"));;
        bankAccount.setStatus(accountStatus);
        bankAccountRepository.save(bankAccount);
        return getBankAccount(id);
    }
}
