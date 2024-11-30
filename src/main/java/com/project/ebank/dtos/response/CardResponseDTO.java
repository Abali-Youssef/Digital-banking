package com.project.ebank.dtos.response;

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
public class CardResponseDTO {
    private String id;
    private Date expirationDate;
    private CardType type;
    private String accountId;

}

