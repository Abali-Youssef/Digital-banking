package com.project.ebank.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private String AccountType;
    private List<BankAccountOperationResponseDTO> bankAccountOperations;
    private int currentPage;
    private int totalPage;
    private Long totalElements;
    private int pageSize;
}
