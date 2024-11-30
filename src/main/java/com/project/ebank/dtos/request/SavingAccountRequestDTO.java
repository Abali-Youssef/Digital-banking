package com.project.ebank.dtos.request;


import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class SavingAccountRequestDTO extends BankAccountRequestDTO {
@Positive(message = "interest rate should be positive")
    private double interestRate;


}
