package com.project.ebank.web;

import com.project.ebank.dtos.request.*;
import com.project.ebank.dtos.response.BankAccountResponseDTO;
import com.project.ebank.dtos.response.lists.BankAccountResponseDTOList;
import com.project.ebank.exceptions.BalanceNotSufficientException;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CustomerNotFoundException;
import com.project.ebank.service.BankAccountService;
import com.project.ebank.validation.account.ValidAccountStatus;
import jakarta.validation.Valid;
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
    public BankAccountResponseDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/bank-accounts/customer/{cin}")
    public List<BankAccountResponseDTO> getBankAccountByCin(@PathVariable String cin) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccountByCustomerCin(cin);
    }
    @PostMapping("/bank-accounts/saving")
    public BankAccountResponseDTO saveSavingBankAccount(@RequestBody @Valid SavingAccountRequestDTO savingAccountDTO) throws CustomerNotFoundException {
       return bankAccountService.saveSavingBankAccount(savingAccountDTO.getBalance(),savingAccountDTO.getInterestRate(),savingAccountDTO.getCustomerId());
    }
    @PutMapping("/bank-accounts/{accountId}")
    public BankAccountResponseDTO updateBankAccount(@PathVariable String accountId, @RequestBody  String status) throws CustomerNotFoundException {
return bankAccountService.updateBankAccount(accountId,status);    }
    @PostMapping("/bank-accounts/current")
    public BankAccountResponseDTO saveCurrentBankAccount(@RequestBody @Valid CurrentAccountRequestDTO currentAccountDTO) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentBankAccount(currentAccountDTO.getBalance(),currentAccountDTO.getOverDraft(),currentAccountDTO.getCustomerId());    }

    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/bank-accounts")
    public BankAccountResponseDTOList allBankAccount(@RequestParam(name = "cin",defaultValue = "") String cin, @RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "5") int size){
        return bankAccountService.listBankAccounts(cin,page-1, size);
    }
    @PutMapping("/debit/{bankAccountId}")
    public void debit(@PathVariable String bankAccountId,@RequestBody @Valid BankAccountOperationRequestDTO bankAccountOperationDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
         bankAccountService.debit(bankAccountId,bankAccountOperationDTO.getAmount(),bankAccountOperationDTO.getDescription());
    }
    @PutMapping("/credit/{bankAccountId}")
    public void credit(@PathVariable String bankAccountId,@RequestBody @Valid BankAccountOperationRequestDTO bankAccountOperationDTO) throws BankAccountNotFoundException {
        bankAccountService.credit(bankAccountId,bankAccountOperationDTO.getAmount(),bankAccountOperationDTO.getDescription());
    }
    @PutMapping("/transfer")
    public void transfer(@RequestBody @Valid TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        log.info(transferRequestDTO.getSourceAccountId()+transferRequestDTO.getDestinationAccountId()+transferRequestDTO.getOperation().getAmount());
        bankAccountService.transfer(transferRequestDTO.getSourceAccountId(),transferRequestDTO.getDestinationAccountId(),transferRequestDTO.getOperation().getAmount());
    }

    @DeleteMapping("/bank-accounts")
    public void deleteBankAccount(@RequestBody List<String> accountIds){
        bankAccountService.deleteBankAccounts(accountIds);
    }
}
