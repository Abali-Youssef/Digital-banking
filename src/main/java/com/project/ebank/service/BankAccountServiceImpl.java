package com.project.ebank.service;

import com.project.ebank.dtos.request.BankAccountRequestDTO;
import com.project.ebank.dtos.request.CurrentAccountRequestDTO;
import com.project.ebank.dtos.request.SavingAccountRequestDTO;
import com.project.ebank.dtos.response.BankAccountResponseDTO;
import com.project.ebank.dtos.response.CurrentAccountResponseDTO;
import com.project.ebank.dtos.response.SavingAccountResponseDTO;
import com.project.ebank.dtos.response.lists.BankAccountResponseDTOList;
import com.project.ebank.entities.*;
import com.project.ebank.enums.AccountStatus;
import com.project.ebank.enums.OperationType;
import com.project.ebank.exceptions.*;
import com.project.ebank.mappers.CardMapper;
import com.project.ebank.mappers.CurrentBankAccountMapper;
import com.project.ebank.mappers.CustomerMapper;
import com.project.ebank.mappers.SavingBankAccountMapper;
import com.project.ebank.repositories.BankAccountRepository;
import com.project.ebank.repositories.CardRepository;
import com.project.ebank.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@Data
public class BankAccountServiceImpl implements BankAccountService{

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private CardRepository cardRepository;
    private BankAccountOperationService bankAccountOperationService;
    private CurrentBankAccountMapper currentBankAccountMapper;
    private SavingBankAccountMapper savingBankAccountMapper;
    private CustomerMapper customerMapper;
    private CardMapper cardMapper;
    @Override
    public CurrentAccountResponseDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException {
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
    public SavingAccountResponseDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerID) throws CustomerNotFoundException {
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
    public BankAccountResponseDTO updateBankAccount(String accountId,  String status) throws CustomerNotFoundException {
        BankAccount account= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank account not found"));
        account.setStatus(AccountStatus.valueOf(status));
        if(account instanceof SavingAccount) savingBankAccountMapper.fromBankAccount((SavingAccount) account);
        return currentBankAccountMapper.fromBankAccount((CurrentAccount) bankAccountRepository.save(account));

    }



    @Override
    public BankAccountResponseDTOList listBankAccounts(String customerId,int page, int size) {
        if(!customerId.isEmpty() && customerId != null){
            Page<BankAccount> bankAccounts=bankAccountRepository.findByCustomerCin(customerId,PageRequest.of(page,size));
            if(bankAccounts.isEmpty()){
                throw new BankAccountNotFoundException("No bank account for this CIN");
            }
            List<BankAccountResponseDTO> bankAccountResponseDTOS=bankAccounts.getContent().stream().map(bankAccount -> {

                if(bankAccount instanceof SavingAccount){
                    SavingAccountResponseDTO savingAccountResponseDTO=savingBankAccountMapper.fromBankAccount((SavingAccount) bankAccount);
                    savingAccountResponseDTO.setCustomer(customerMapper.fromCustomer(bankAccount.getCustomer()) );
                    savingAccountResponseDTO.setCards(bankAccount.getCards().stream().map(card ->cardMapper.fromCard(card)).collect(Collectors.toList()));
                    savingAccountResponseDTO.setType("SAVING_ACCOUNT");
                    return savingAccountResponseDTO;
                }else{
                    CurrentAccountResponseDTO currentAccountDTO=currentBankAccountMapper.fromBankAccount((CurrentAccount) bankAccount);
                    currentAccountDTO.setCustomer(customerMapper.fromCustomer(bankAccount.getCustomer()));
                    currentAccountDTO.setCards(bankAccount.getCards().stream().map(card ->cardMapper.fromCard(card)).collect(Collectors.toList()));
                    currentAccountDTO.setType("CURRENT_ACCOUNT");
                    return currentAccountDTO;
                }
            }).collect(Collectors.toList());
            return new BankAccountResponseDTOList(bankAccountResponseDTOS,page+1,bankAccounts.getTotalPages(),bankAccounts.getTotalElements(),size);
        }
        Page<BankAccount> bankAccounts=bankAccountRepository.findAll(PageRequest.of(page,size));
        List<BankAccountResponseDTO> bankAccountResponseDTOS=bankAccounts.getContent().stream().map(bankAccount -> {

            if(bankAccount instanceof SavingAccount){
                SavingAccountResponseDTO savingAccountResponseDTO=savingBankAccountMapper.fromBankAccount((SavingAccount) bankAccount);
                savingAccountResponseDTO.setCustomer(customerMapper.fromCustomer(bankAccount.getCustomer()) );
                savingAccountResponseDTO.setCards(bankAccount.getCards().stream().map(card ->cardMapper.fromCard(card)).collect(Collectors.toList()));
                savingAccountResponseDTO.setType("SAVING_ACCOUNT");
                return savingAccountResponseDTO;
            }else{
                CurrentAccountResponseDTO currentAccountDTO=currentBankAccountMapper.fromBankAccount((CurrentAccount) bankAccount);
                currentAccountDTO.setCustomer(customerMapper.fromCustomer(bankAccount.getCustomer()));
                currentAccountDTO.setCards(bankAccount.getCards().stream().map(card ->cardMapper.fromCard(card)).collect(Collectors.toList()));
                currentAccountDTO.setType("CURRENT_ACCOUNT");
                return currentAccountDTO;
            }
        }).collect(Collectors.toList());
        return new BankAccountResponseDTOList(bankAccountResponseDTOS,page+1,bankAccounts.getTotalPages(),bankAccounts.getTotalElements(),size);

    }
    @Override
    public BankAccountResponseDTO getBankAccount(String id) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(id).orElseThrow(()-> new BankAccountNotFoundException("bank account not found"));
        if (bankAccount instanceof CurrentAccount){
            CurrentAccountResponseDTO currentAccountDTO=currentBankAccountMapper.fromBankAccount((CurrentAccount) bankAccount);
            currentAccountDTO.setCustomer(customerMapper.fromCustomer(bankAccount.getCustomer()));
            currentAccountDTO.setCards(bankAccount.getCards().stream().map(card ->cardMapper.fromCard(card)).collect(Collectors.toList()));
            currentAccountDTO.setType("CURRENT_ACCOUNT");
            return currentAccountDTO;
        }
        SavingAccountResponseDTO savingAccountDTO=savingBankAccountMapper.fromBankAccount((SavingAccount)  bankAccount);
        savingAccountDTO.setCustomer(customerMapper.fromCustomer(bankAccount.getCustomer()) );
        savingAccountDTO.setCards(bankAccount.getCards().stream().map(card ->cardMapper.fromCard(card)).collect(Collectors.toList()));
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
    public BankAccountResponseDTO attachCard(String accountId, String cardId) throws BankAccountNotFoundException {
        Card card = cardRepository.findById(cardId).orElseThrow(()->{
            return new CardNotFoundException("card not found");
        });
        card.setBankAccount(bankAccountRepository.findById(accountId).orElseThrow(()->{
            return new BankAccountNotFoundException("Bank account not found");
        }));
        cardRepository.save(card);
        return getBankAccount(accountId);
    }
    @Override
    public void deleteBankAccounts(List<String> accountIds){
        accountIds.forEach(acc ->{
            BankAccount bankAccount=bankAccountRepository.findById(acc).orElseThrow(()-> new BankAccountNotFoundException("bank account with id="+acc+" not found"));
            if(!bankAccount.getBankAccountOperations().isEmpty()){
                throw new NonDeletabeleRessourceException("Bank account with id="+acc+" is related, try to delete all operations");
            }
            if(!bankAccount.getCards().isEmpty()){
                throw new NonDeletabeleRessourceException("Bank account with id="+acc+" is related to card");
            }
        });
        bankAccountRepository.deleteAllById(accountIds);
    }
    @Override
    public BankAccountResponseDTO editBankAccountStatus(String id, AccountStatus accountStatus) throws BankAccountNotFoundException {
        BankAccount  bankAccount=bankAccountRepository.findById(id).orElseThrow(()->new BankAccountNotFoundException("account not found"));;
        bankAccount.setStatus(accountStatus);
        bankAccountRepository.save(bankAccount);
        return getBankAccount(id);
    }

    @Override
    public List<BankAccountResponseDTO> getBankAccountByCustomerCin(String cin) {
        List<BankAccountResponseDTO> bankaccounts = bankAccountRepository.findByCustomerCin(cin).stream().map(bankAccount ->{
            if (bankAccount instanceof CurrentAccount){
                CurrentAccountResponseDTO currentAccountDTO=currentBankAccountMapper.fromBankAccount((CurrentAccount) bankAccount);
                return currentAccountDTO;
            }
            SavingAccountResponseDTO savingAccountDTO=savingBankAccountMapper.fromBankAccount((SavingAccount)  bankAccount);
            return savingAccountDTO;
        }).collect(Collectors.toList());
if(bankaccounts==null || bankaccounts.size()==0){
    throw new BankAccountNotFoundException("No bank account found for this CIN");
}
        return bankaccounts;
    }
}
