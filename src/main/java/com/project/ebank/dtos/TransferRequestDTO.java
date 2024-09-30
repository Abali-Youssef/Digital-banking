package com.project.ebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequestDTO {
    private BankAccountDTO sourceAccount;
    private BankAccountDTO destinationAccount;
    private BankAccountOperationDTO operation;


}
