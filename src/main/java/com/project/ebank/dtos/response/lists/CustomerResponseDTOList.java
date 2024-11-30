package com.project.ebank.dtos.response.lists;

import com.project.ebank.dtos.response.CustomerResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerResponseDTOList {
    List<CustomerResponseDTO> customers;
    private int currentPage;
    private int totalPage;
    private Long totalElements;
    private int pageSize;
}
