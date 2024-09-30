package com.project.ebank.service;

import com.project.ebank.dtos.CardDTO;
import com.project.ebank.entities.Card;
import com.project.ebank.enums.CardType;
import com.project.ebank.mappers.CardMapper;
import com.project.ebank.repositories.CardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
    CardRepository cardRepository;
    CardMapper cardMapper;
    @Override
    public List<CardDTO> allCardsByAccount(String accountId) {
        return  cardRepository.findByBankAccountId(accountId).stream().map(card -> cardMapper.fromCard(card)).collect(Collectors.toList());
    }
    @Override
    public CardDTO saveCard(CardDTO cardDTO) {
        Card card= Card.builder()
                .id(UUID.randomUUID().toString())
                .expirationDate(cardDTO.getExpirationDate())
                .type(cardDTO.getType())
                .build();
        return cardMapper.fromCard( cardRepository.save(card));

    }

    @Override
    public CardDTO updateCard(CardDTO cardDTO, String id) {
        Card card=cardRepository.findById(id).orElseThrow();
        card.setType(cardDTO.getType());
        card.setExpirationDate(cardDTO.getExpirationDate());
        return cardMapper.fromCard(cardRepository.save(card)) ;
    }

    @Override
    public void deleteCard(String id) {
        cardRepository.deleteById(id);
    }
}
