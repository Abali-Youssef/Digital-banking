package com.project.ebank.web;

import com.project.ebank.dtos.*;
import com.project.ebank.entities.BankAccount;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.service.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@Slf4j
public class BankAccountRestController {
    BankAccountService bankAccountService;

    @GetMapping("/bank-accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @PostMapping("/bank-accounts/saving")
    public BankAccountDTO saveSavingBankAccount(@RequestBody SavingAccountDTO savingAccountDTO) throws CustomerNotFoundException {
       return bankAccountService.saveSavingBankAccount(savingAccountDTO.getBalance(),savingAccountDTO.getInterestRate(),savingAccountDTO.getCustomerDTO().getId());
    }
    @PostMapping("/bank-accounts/current")
    public BankAccountDTO saveCurrentBankAccount(@RequestBody CurrentAccountDTO  currentAccountDTO) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentBankAccount(currentAccountDTO.getBalance(),currentAccountDTO.getOverDraft(),currentAccountDTO.getCustomerDTO().getId());    }
    @GetMapping("bank-accounts")
    public List<BankAccountDTO> allBankAccount(){
        return bankAccountService.listBankAccounts();
    }
    @PutMapping("/debit/{bankAccountId}")
    public void debit(@PathVariable String bankAccountId,@RequestBody BankAccountOperationDTO bankAccountOperationDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
         bankAccountService.debit(bankAccountId,bankAccountOperationDTO.getAmount(),bankAccountOperationDTO.getDescription());
    }
    @PutMapping("/credit/{bankAccountId}")
    public void credit(@PathVariable String bankAccountId,@RequestBody BankAccountOperationDTO bankAccountOperationDTO) throws BankAccountNotFoundException {
        bankAccountService.credit(bankAccountId,bankAccountOperationDTO.getAmount(),bankAccountOperationDTO.getDescription());
    }
    @PutMapping("/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        log.info(transferRequestDTO.getSourceAccount().getId()+transferRequestDTO.getDestinationAccount().getId()+transferRequestDTO.getOperation().getAmount());
        bankAccountService.transfer(transferRequestDTO.getSourceAccount().getId(),transferRequestDTO.getDestinationAccount().getId(),transferRequestDTO.getOperation().getAmount());
    }
    @DeleteMapping("/bank-accounts")
    public void deleteBankAccount(@PathVariable String accountId){
        bankAccountService.deleteBankAccount(accountId);
    }
}
