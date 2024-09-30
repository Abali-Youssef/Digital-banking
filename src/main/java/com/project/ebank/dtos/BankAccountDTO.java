package com.project.ebank.dtos;

import com.project.ebank.entities.Card;
import com.project.ebank.enums.AccountStatus;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BankAccountDTO {

    private String id;
    private double balance;
    private Date createdAt;
    private String type;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private List<CardDTO> cardDTO;

}
