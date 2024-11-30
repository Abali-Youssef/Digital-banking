package com.project.ebank.service;

import com.project.ebank.dtos.request.CardRequestDTO;
import com.project.ebank.dtos.response.CardResponseDTO;
import com.project.ebank.dtos.response.lists.CardResponseDTOList;
import com.project.ebank.entities.Card;
import com.project.ebank.enums.CardType;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.exceptions.CardNotFoundException;
import com.project.ebank.mappers.CardMapper;
import com.project.ebank.repositories.BankAccountRepository;
import com.project.ebank.repositories.CardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
    private CardRepository cardRepository;
    private CardMapper cardMapper;
    private BankAccountRepository bankAccountRepository;
    @Override
    public CardResponseDTOList allCardsByAccount(String accountId, int page, int size) throws BankAccountNotFoundException {
//        System.out.println("account id est ====>  "+accountId);
        if(accountId.equals("all")){
            return allCards(page,size);
        }

        bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank account not found"));
        Page<Card> cards= cardRepository.findByBankAccountId(accountId, PageRequest.of(page,size));
        List<CardResponseDTO> cardResponseDTOs=cards.stream().map(card -> cardMapper.fromCard(card)).collect(Collectors.toList());
        CardResponseDTOList cardResponseDTOList = new CardResponseDTOList();
        cardResponseDTOList.setCards(cardResponseDTOs);
        cardResponseDTOList.setPageSize(size);
        cardResponseDTOList.setCurrentPage(page+1);
        cardResponseDTOList.setTotalPage(cards.getTotalPages());
        cardResponseDTOList.setTotalElements(cards.getTotalElements());
        return cardResponseDTOList;
    }
    @Override
    public CardResponseDTO saveCard(CardRequestDTO cardDTO) throws BankAccountNotFoundException {
        Card card= Card.builder()
                .id(UUID.randomUUID().toString())
                .expirationDate(cardDTO.getExpirationDate())
                .bankAccount(bankAccountRepository.findById(cardDTO.getAccountId()).orElseThrow(()->new BankAccountNotFoundException("account not found")) )
                .type(CardType.valueOf(cardDTO.getType()))
                .build();
        return cardMapper.fromCard( cardRepository.save(card));

    }

    @Override
    public CardResponseDTO updateCard(CardRequestDTO cardDTO, String id) {
        Card card=cardRepository.findById(id).orElseThrow(()->new CardNotFoundException("Card not found"));
        card.setType(CardType.valueOf(cardDTO.getType()));
        card.setExpirationDate(cardDTO.getExpirationDate());
        return cardMapper.fromCard(cardRepository.save(card)) ;
    }

    @Override
    public void deleteCards(List<String> cardsIds){
        cardsIds.forEach(c ->{
            cardRepository.findById(c).orElseThrow(()->new CardNotFoundException("Customer with Id" + c + " not found"));
        });
        cardRepository.deleteAllById(cardsIds);
    }


    @Override
    public CardResponseDTOList allCards(int page, int size) {
        Page<Card> cards= cardRepository.findAll( PageRequest.of(page,size));
        List<CardResponseDTO> cardResponseDTOs=cards.stream().map(card -> cardMapper.fromCard(card)).collect(Collectors.toList());
        CardResponseDTOList cardResponseDTOList = new CardResponseDTOList();
        cardResponseDTOList.setCards(cardResponseDTOs);
        cardResponseDTOList.setPageSize(size);
        cardResponseDTOList.setCurrentPage(page+1);
        cardResponseDTOList.setTotalPage(cards.getTotalPages());
        return cardResponseDTOList;
    }


}
