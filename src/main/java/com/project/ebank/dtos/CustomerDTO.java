package com.project.ebank.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ebank.entities.BankAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;

}
