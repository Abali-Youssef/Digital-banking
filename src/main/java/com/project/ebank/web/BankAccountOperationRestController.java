package com.project.ebank.web;

import com.project.ebank.dtos.AccountHistoryDTO;
import com.project.ebank.dtos.BankAccountOperationDTO;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.service.BankAccountOperationService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class BankAccountOperationRestController {
    BankAccountOperationService bankAccountOperationService;
    @GetMapping("/bank-accounts/{accountId}/page-operations")
    AccountHistoryDTO getAccountHistory(@PathVariable String accountId , @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountOperationService.getAccountHistory(accountId,page,size);
    }
    @GetMapping("/account-operation/{id}")
    public BankAccountOperationDTO getBankAccountOperation(@PathVariable Long id){
        return bankAccountOperationService.getBankAccountOperation(id);
    }

}
