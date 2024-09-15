package com.project.ebank.service;

import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.Customer;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    BankAccount saveCurrentBankAccount(double initialBalance, double overDraft,Long customerID ) throws CustomerNotFoundException;
    BankAccount saveSavingBankAccount(double initialBalance, double interestRate,Long customerID ) throws CustomerNotFoundException;
    List<Customer> listCustomer();

    List<BankAccount> listBankAccounts();

    BankAccount getBankAccount(String id) throws BankAccountNotFoundException;
    void debit(String accountID, double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountID, double amount,String description) throws BankAccountNotFoundException;
    void transfer(String accountIDSource, String accountIDDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

}
