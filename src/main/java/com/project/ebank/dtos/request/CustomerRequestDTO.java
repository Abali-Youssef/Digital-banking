package com.project.ebank.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerRequestDTO {
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only letters and spaces")
    private String name;
    @NotBlank(message = "CIN is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "CIN must contain no spaces")
    private String cin;
    @NotBlank(message = "Email is required")
    @Email(message = "The provided value isn't an email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

}
