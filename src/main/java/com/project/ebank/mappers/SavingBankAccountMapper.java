package com.project.ebank.mappers;


import com.project.ebank.dtos.request.SavingAccountRequestDTO;
import com.project.ebank.dtos.response.SavingAccountResponseDTO;
import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.CurrentAccount;
import com.project.ebank.entities.SavingAccount;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface SavingBankAccountMapper {
    BankAccount fromSavingAccountDTO(SavingAccountRequestDTO savingAccountRequestDTO);
    SavingAccountResponseDTO fromBankAccount(SavingAccount savingAccount);
}
