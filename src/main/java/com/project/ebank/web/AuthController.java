package com.project.ebank.web;


import com.project.ebank.dtos.request.AuthRequestDTO;
import com.project.ebank.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AuthController {
private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody AuthRequestDTO authRequestDTO){
        return authService.authenticate( authRequestDTO.getGrantType(),authRequestDTO.getEmail(),authRequestDTO.getPassword(),authRequestDTO.isWithRefreshToken(),authRequestDTO.getRefreshToken());
    }

}
