package com.project.ebank.mappers;


import com.project.ebank.dtos.request.CardRequestDTO;
import com.project.ebank.dtos.response.CardResponseDTO;
import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.Card;
import com.project.ebank.entities.CurrentAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Mapper(componentModel = "spring")
public interface CardMapper {
    Card fromCardDTO(CardRequestDTO cardRequestDTO);
    @Mapping(source = "bankAccount.id", target = "accountId")
    CardResponseDTO fromCard(Card card);
}
