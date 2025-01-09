package com.project.ebank.mappers;

import com.project.ebank.dtos.request.CurrentAccountRequestDTO;
import com.project.ebank.dtos.response.CurrentAccountResponseDTO;
import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.CurrentAccount;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface CurrentBankAccountMapper {
    BankAccount fromCurrentAccountDTO(CurrentAccountRequestDTO currentAccountRequestDTO);
    CurrentAccountResponseDTO fromBankAccount(CurrentAccount bankAccount);
}
