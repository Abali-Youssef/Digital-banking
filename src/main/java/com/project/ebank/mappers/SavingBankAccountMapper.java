package com.project.ebank.mappers;

import com.project.ebank.dtos.CurrentAccountDTO;
import com.project.ebank.dtos.SavingAccountDTO;
import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.CurrentAccount;
import com.project.ebank.entities.SavingAccount;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface SavingBankAccountMapper {
    SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO);
    SavingAccountDTO fromBankAccount(SavingAccount savingAccount);
}
