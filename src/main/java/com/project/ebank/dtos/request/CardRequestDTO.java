package com.project.ebank.dtos.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.ebank.enums.CardType;
import com.project.ebank.validation.card.ValidCardType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CardRequestDTO {

    @Future(message = "Card expiration date should be a future date") @NotNull(message = "Expiration date is required")
    private Date expirationDate;


    @ValidCardType
    @NotNull(message = "Card type is required")
    @Pattern(regexp = "^[A-Z_]+$", message = "Value must contain only uppercase letters and underscores.")
    private String type;

    @NotBlank(message = "Account is required")
    private String accountId;
}

