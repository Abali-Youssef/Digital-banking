package com.project.ebank.dtos.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CurrentAccountRequestDTO extends BankAccountRequestDTO {
    @PositiveOrZero(message =" OverDraft should be greater than zero")
    private double overDraft;


}
