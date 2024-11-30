package com.project.ebank.dtos.response;


import com.project.ebank.enums.AccountStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BankAccountResponseDTO {

    private String id;
    private double balance;
    private Date createdAt;
    private String type;
    private AccountStatus status;
    private CustomerResponseDTO customer;
    private List<CardResponseDTO> cards;
}
