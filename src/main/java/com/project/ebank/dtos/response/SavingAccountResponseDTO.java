package com.project.ebank.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class SavingAccountResponseDTO extends BankAccountResponseDTO {

    private double interestRate;


}
