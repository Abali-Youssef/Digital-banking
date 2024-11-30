package com.project.ebank.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CurrentAccountResponseDTO extends BankAccountResponseDTO {
    private double overDraft;


}
