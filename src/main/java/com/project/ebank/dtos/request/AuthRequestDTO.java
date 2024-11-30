package com.project.ebank.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthRequestDTO {
    @NotBlank(message = "GrantType is required")
    private String grantType;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Refreshtoken is required")
    private boolean withRefreshToken;
    private String refreshToken;
}

