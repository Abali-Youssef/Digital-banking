package com.project.ebank.web;


import com.project.ebank.dtos.response.AccountHistoryDTO;
import com.project.ebank.dtos.response.BankAccountOperationResponseDTO;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.service.BankAccountOperationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class BankAccountOperationRestController {
    BankAccountOperationService bankAccountOperationService;
    @GetMapping("/bank-accounts/{accountId}/page-operations")
    AccountHistoryDTO getAccountHistory(@PathVariable String accountId , @RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountOperationService.getAccountHistory(accountId,page-1,size);
    }
    @GetMapping("/account-operation/{id}")
    public BankAccountOperationResponseDTO getBankAccountOperation(@PathVariable Long id){
        return bankAccountOperationService.getBankAccountOperation(id);
    }

}
