package com.project.ebank.mappers;

import com.project.ebank.dtos.CardDTO;
import com.project.ebank.dtos.CurrentAccountDTO;
import com.project.ebank.entities.BankAccount;
import com.project.ebank.entities.Card;
import com.project.ebank.entities.CurrentAccount;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Mapper(componentModel = "spring")
public interface CardMapper {
    Card fromCardDTO(CardDTO cardDTO);
    CardDTO fromCard(Card card);
}
