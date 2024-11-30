package com.project.ebank.service;

import com.project.ebank.dtos.request.BankAccountRequestDTO;
import com.project.ebank.dtos.request.CardRequestDTO;
import com.project.ebank.dtos.request.CurrentAccountRequestDTO;
import com.project.ebank.dtos.request.SavingAccountRequestDTO;
import com.project.ebank.dtos.response.BankAccountResponseDTO;
import com.project.ebank.dtos.response.CurrentAccountResponseDTO;
import com.project.ebank.dtos.response.SavingAccountResponseDTO;
import com.project.ebank.dtos.response.lists.BankAccountResponseDTOList;
import com.project.ebank.enums.AccountStatus;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CurrentAccountResponseDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID ) throws CustomerNotFoundException;
    SavingAccountResponseDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerID ) throws CustomerNotFoundException;
    BankAccountResponseDTO updateBankAccount(String accountId,  String status) throws CustomerNotFoundException;
    BankAccountResponseDTOList listBankAccounts(String cin,int page, int size);
    BankAccountResponseDTO getBankAccount(String id) throws BankAccountNotFoundException;
    void debit(String accountID, double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountID, double amount,String description) throws BankAccountNotFoundException;
    void transfer(String accountIDSource, String accountIDDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    BankAccountResponseDTO attachCard(String accountId, String cardId) throws BankAccountNotFoundException;

    void deleteBankAccounts(List<String> accountIds);
    BankAccountResponseDTO editBankAccountStatus(String id, AccountStatus accountStatus) throws BankAccountNotFoundException;


    List<BankAccountResponseDTO> getBankAccountByCustomerCin(String cin);
}
