package com.project.ebank.mappers;

import com.project.ebank.dtos.CurrentAccountDTO;
import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.CurrentAccount;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface CurrentBankAccountMapper {
    CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO);
    CurrentAccountDTO fromBankAccount(BankAccount bankAccount);
}
