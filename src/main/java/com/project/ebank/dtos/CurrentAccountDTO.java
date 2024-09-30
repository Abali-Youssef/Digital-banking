package com.project.ebank.dtos;

import com.project.ebank.entities.BankAccount;
import com.project.ebank.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class CurrentAccountDTO extends BankAccountDTO {
    private double overDraft;


}
