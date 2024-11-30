package com.project.ebank.mappers;
import com.project.ebank.dtos.request.BankAccountOperationRequestDTO;
import com.project.ebank.dtos.response.BankAccountOperationResponseDTO;
import com.project.ebank.entities.BankAccountOperation;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface BankAccountOperationMapper {
BankAccountOperation fromBankAccountOperationDTO(BankAccountOperationRequestDTO bankAccountOperationRequestDTO);
BankAccountOperationResponseDTO fromBankAccountOperation(BankAccountOperation bankAccountOperation);
}
