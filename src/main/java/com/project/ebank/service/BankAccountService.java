package com.project.ebank.service;

import com.project.ebank.dtos.*;
import com.project.ebank.enums.AccountStatus;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID ) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerID ) throws CustomerNotFoundException;
    List<BankAccountDTO> listBankAccounts();
    BankAccountDTO getBankAccount(String id) throws BankAccountNotFoundException;
    void debit(String accountID, double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountID, double amount,String description) throws BankAccountNotFoundException;
    void transfer(String accountIDSource, String accountIDDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
BankAccountDTO attachCard(String id, CardDTO cardDTO) throws BankAccountNotFoundException;
    void deleteBankAccount(String accountId);
    BankAccountDTO editBankAccountStatus(String id, AccountStatus accountStatus) throws BankAccountNotFoundException;


}
