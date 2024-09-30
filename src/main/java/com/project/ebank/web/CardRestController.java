package com.project.ebank.web;

import com.project.ebank.dtos.CardDTO;
import com.project.ebank.entities.Card;
import com.project.ebank.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class CardRestController {
    CardService cardService;
    @GetMapping("/cards/{accountId}")
    public List<CardDTO> allCardsByAccount(@PathVariable String accountId){
        return cardService.allCardsByAccount(accountId);
    }
    @PostMapping("/cards")
    public CardDTO saveCard(@RequestBody CardDTO cardDTO){
        return cardService.saveCard(cardDTO);
    }
    @PutMapping("/cards/update/{id}")
    public CardDTO saveCard(@RequestBody CardDTO cardDTO,@PathVariable String id){
        return cardService.updateCard(cardDTO,id);
    }
    @DeleteMapping("/cards/delete/{id}")
    public void deleteCard(String id){
        cardService.deleteCard(id);
    }
}
