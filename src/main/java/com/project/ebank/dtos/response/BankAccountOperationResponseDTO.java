package com.project.ebank.dtos.response;

import com.project.ebank.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class BankAccountOperationResponseDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    private OperationType operationType;

}
