package com.project.ebank.dtos.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequestDTO {
    @NotBlank(message = "Source account is required")
    private String sourceAccountId;
    @NotBlank(message = "Destination account is required")
    private String destinationAccountId;
    @Valid
    private BankAccountOperationRequestDTO operation;
}
