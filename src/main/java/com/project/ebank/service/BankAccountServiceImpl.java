package com.project.ebank.service;

import com.project.ebank.entities.*;
import com.project.ebank.enums.AccountStatus;
import com.project.ebank.enums.OperationType;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.repositories.BankAccountOperationRepository;
import com.project.ebank.repositories.BankAccountRepository;
import com.project.ebank.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
CustomerRepository customerRepository;
BankAccountRepository bankAccountRepository;
BankAccountOperationRepository bankAccountOperationRepository;
    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("saving new customer ...");
        Customer savedCustomer = customerRepository.save(customer);
        log.info("customer saved ");
        return savedCustomer;
    }

    @Override
    public BankAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer == null){
            throw new CustomerNotFoundException("customer not found");
        }
        BankAccount bankAccount=new CurrentAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setStatus(AccountStatus.CREATED);
        ((CurrentAccount) bankAccount).setOverDraft(overDraft);
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return savedAccount;
    }

    @Override
    public BankAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerID) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer == null){
            throw new CustomerNotFoundException("customer not found");
        }
        BankAccount bankAccount=new SavingAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setStatus(AccountStatus.CREATED);
        ((SavingAccount) bankAccount).setInterestRate(interestRate);
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return savedAccount;
    }



    @Override
    public List<Customer> listCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public List<BankAccount> listBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String id) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(id).orElseThrow(()-> new BankAccountNotFoundException("bank account not found"));

        return bankAccount;
    }

    @Override
    public void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=this.getBankAccount(accountID);
        if (bankAccount.getBalance() < amount){
            throw new BalanceNotSufficientException("balance not sufficient");
        }
        BankAccountOperation bankAccountOperation=new BankAccountOperation();
        bankAccountOperation.setOperationType(OperationType.DEBIT);
        bankAccountOperation.setAmount(amount);
        bankAccountOperation.setDescription(description);
        bankAccountOperation.setOperationDate(new Date());
        bankAccountOperation.setBankAccount(bankAccount);
        bankAccountOperationRepository.save(bankAccountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountID, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=this.getBankAccount(accountID);

        BankAccountOperation bankAccountOperation=new BankAccountOperation();
        bankAccountOperation.setOperationType(OperationType.CREADIT);
        bankAccountOperation.setAmount(amount);
        bankAccountOperation.setDescription(description);
        bankAccountOperation.setOperationDate(new Date());
        bankAccountOperation.setBankAccount(bankAccount);
        bankAccountOperationRepository.save(bankAccountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIDSource, String accountIDDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIDSource,amount,"Transfer to "+accountIDDestination);
        credit(accountIDDestination,amount,"Transfert from "+accountIDSource);
    }
}
