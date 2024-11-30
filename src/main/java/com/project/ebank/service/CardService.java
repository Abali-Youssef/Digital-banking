package com.project.ebank.service;

import com.project.ebank.dtos.request.CardRequestDTO;
import com.project.ebank.dtos.response.CardResponseDTO;
import com.project.ebank.dtos.response.lists.CardResponseDTOList;
import com.project.ebank.exceptions.BankAccountNotFoundException;

import java.util.List;

public interface CardService {


    CardResponseDTOList allCardsByAccount(String accountId, int page, int size) throws BankAccountNotFoundException;

    CardResponseDTO saveCard(CardRequestDTO cardRequestDTO) throws BankAccountNotFoundException;
    CardResponseDTO updateCard(CardRequestDTO cardDTO,String id);

    void deleteCards(List<String> cardsIds);

    CardResponseDTOList allCards(int page, int size);
}
