package com.project.ebank.dtos.response.lists;

import com.project.ebank.dtos.response.BankAccountResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data @AllArgsConstructor @NoArgsConstructor
public class BankAccountResponseDTOList {
    List<BankAccountResponseDTO> bankAccounts;
    private int currentPage;
    private int totalPage;
    private Long totalElements;
    private int pageSize;
}
