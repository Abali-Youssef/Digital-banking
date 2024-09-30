package com.project.ebank.mappers;

import com.project.ebank.dtos.BankAccountOperationDTO;
import com.project.ebank.entities.BankAccountOperation;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface BankAccountOperationMapper {
BankAccountOperation fromBankAccountOperationDTO(BankAccountOperationDTO bankAccountOperationDTO);
BankAccountOperationDTO fromBankAccountOperation(BankAccountOperation bankAccountOperation);
}
