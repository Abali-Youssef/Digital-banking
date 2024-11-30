package com.project.ebank.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface AuthService {
    ResponseEntity<Map<String, String>> authenticate(String grantType,
                                                     String email,
                                                     String password,
                                                     boolean withRefreshToken,
                                                     String refreshToken);
}
