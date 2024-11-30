package com.project.ebank.service;


import com.project.ebank.dtos.response.AccountHistoryDTO;
import com.project.ebank.dtos.response.BankAccountOperationResponseDTO;
import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.BankAccountOperation;
import com.project.ebank.enums.OperationType;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.BankAccountOperationNotFound;
import com.project.ebank.mappers.BankAccountOperationMapper;
import com.project.ebank.repositories.BankAccountOperationRepository;
import com.project.ebank.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class BankAccountOperationServiceImpl implements BankAccountOperationService {
    BankAccountOperationRepository bankAccountOperationRepository;
    BankAccountRepository  bankAccountRepository;
    BankAccountOperationMapper bankAccountOperationMapper;



//    @Override
//    public List<BankAccountOperationResponseDTO> accountHistory(String accountId){
//        List<BankAccountOperation>  bankAccountOperationList= bankAccountOperationRepository.findByBankAccountId(accountId);
//        return bankAccountOperationList.stream().map(bankAccountOperation -> bankAccountOperationMapper.fromBankAccountOperation(bankAccountOperation)).collect(Collectors.toList());
//    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Bank account not found"));

        Page<BankAccountOperation> bankAccountOperationList= bankAccountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page,size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<BankAccountOperationResponseDTO> bankAccountOperationDTOS=bankAccountOperationList.stream().map(accountHistoryDto -> bankAccountOperationMapper.fromBankAccountOperation(accountHistoryDto)).collect(Collectors.toList());
        accountHistoryDTO.setBankAccountOperations(bankAccountOperationDTOS);
        accountHistoryDTO.setAccountId(accountId);
        accountHistoryDTO.setAccountType(bankAccount.getClass().getSimpleName());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page+1);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPage(bankAccountOperationList.getTotalPages());
        accountHistoryDTO.setTotalElements(bankAccountOperationList.getTotalElements());
        return accountHistoryDTO;
    }

    @Override
    public BankAccountOperationResponseDTO saveOperation(OperationType operationType, double amount, String description, Date operationDate, BankAccount bankAccount) {
        BankAccountOperation bankAccountOperation=new BankAccountOperation();
        bankAccountOperation.setOperationType(operationType);
        bankAccountOperation.setAmount(amount);
        bankAccountOperation.setDescription(description);
        bankAccountOperation.setOperationDate(new Date());
        bankAccountOperation.setBankAccount(bankAccount);
        return bankAccountOperationMapper.fromBankAccountOperation(bankAccountOperationRepository.save(bankAccountOperation));
    }
    @Override
    public void deleteBankAccountOperation(Long id) throws BankAccountOperationNotFound {
        BankAccountOperation bankAccountOperation = bankAccountOperationRepository.findById(id)
                .orElseThrow(() -> new BankAccountOperationNotFound("operation with id= " + id + " not found"));

        BankAccount bankAccount=bankAccountOperation.getBankAccount();
        if(bankAccountOperation.getOperationType()==OperationType.DEBIT){
            bankAccount.setBalance(bankAccountOperation.getAmount()+bankAccount.getBalance());
        }else{
            bankAccount.setBalance(bankAccountOperation.getAmount()-bankAccount.getBalance());
        }
        bankAccountRepository.save(bankAccount);
        bankAccountOperationRepository.deleteById(id);
    }

    @Override
    public BankAccountOperationResponseDTO getBankAccountOperation(Long id) {
        return bankAccountOperationMapper.fromBankAccountOperation(bankAccountOperationRepository.findById(id).orElseThrow(() -> new BankAccountOperationNotFound("operation with id= " + id + " not found")));
    }
}
