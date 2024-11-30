package com.project.ebank.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerResponseDTO {

    private Long id;
    private String name;
    private String cin;
    private String email;
    private String password;


}
