package com.project.ebank.service;


import com.project.ebank.dtos.response.AccountHistoryDTO;
import com.project.ebank.dtos.response.BankAccountOperationResponseDTO;
import com.project.ebank.entities.BankAccount;
import com.project.ebank.enums.OperationType;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.BankAccountOperationNotFound;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface BankAccountOperationService {
    //List<BankAccountOperationResponseDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
    BankAccountOperationResponseDTO saveOperation(OperationType operationType, double amount, String description, Date operationDate, BankAccount bankAccount);
    void deleteBankAccountOperation(Long id) throws BankAccountOperationNotFound;
    BankAccountOperationResponseDTO getBankAccountOperation(Long id);

}
