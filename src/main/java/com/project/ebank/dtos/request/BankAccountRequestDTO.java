package com.project.ebank.dtos.request;


import com.project.ebank.enums.AccountStatus;
import com.project.ebank.validation.account.ValidAccountStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BankAccountRequestDTO {
    @PositiveOrZero(message = "Balance less than zero")
    private double balance;

    @ValidAccountStatus
    @Pattern(regexp = "^[A-Z]+$", message = "Status must contain only letters with no spaces")
    private String status;
    @NotNull(message = "Customer is is required")
    private Long customerId;
    //private List<CardRequestDTO> cardDTO;

}
