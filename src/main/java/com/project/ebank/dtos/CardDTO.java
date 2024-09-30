package com.project.ebank.dtos;

import com.project.ebank.entities.BankAccount;
import com.project.ebank.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CardDTO {
    private String id;
    private Date expirationDate;
    private CardType type;
}

