package com.project.ebank.dtos.request;

import com.project.ebank.enums.OperationType;
import com.project.ebank.validation.operation.ValidOperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class BankAccountOperationRequestDTO {
    @Positive(message = "Amount should be greater that zero")
    private double amount;
    @NotNull(message = "Description is required")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "description must contain only letters and spaces")
    private String description;
//    @ValidOperationType
//    @Pattern(regexp = "^[a-zA-Z]+$", message = "Type must contain only letters with no spaces")
//    private String operationType;

}
