package com.project.ebank.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDTO {
    String accountId;
    double balance;
    private String AccountType;
    private List<BankAccountOperationDTO> bankAccountOperationDTO;
    private int currentPage;
    private int totalPage;
    private int pageSize;
}
