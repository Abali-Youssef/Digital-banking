package com.project.ebank.service;

import com.project.ebank.dtos.CardDTO;
import com.project.ebank.entities.Card;
import com.project.ebank.enums.CardType;

import java.util.Date;
import java.util.List;

public interface CardService {

    List<CardDTO> allCardsByAccount(String accountId);

    CardDTO saveCard(CardDTO cardDTO);
    CardDTO updateCard(CardDTO cardDTO,String id);
    void deleteCard(String id);
}
