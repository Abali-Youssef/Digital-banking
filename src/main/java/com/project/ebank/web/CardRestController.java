package com.project.ebank.web;

import com.project.ebank.dtos.request.CardRequestDTO;
import com.project.ebank.dtos.response.CardResponseDTO;
import com.project.ebank.dtos.response.lists.CardResponseDTOList;
import com.project.ebank.entities.Card;
import com.project.ebank.exceptions.BankAccountNotFoundException;
import com.project.ebank.service.CardService;
import jakarta.validation.Valid;
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
    public CardResponseDTOList allCardsByAccount(@PathVariable String accountId, @RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return cardService.allCardsByAccount(accountId,page-1,size);
    }
//    @GetMapping("/cards")
//    public CardResponseDTOList allCards( @RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
//        return cardService.allCards(page-1,size);
//    }
    @PostMapping("/cards")
    public CardResponseDTO saveCard(@Valid @RequestBody CardRequestDTO cardDTO) throws BankAccountNotFoundException {
        CardResponseDTO cardResponseDTO = cardService.saveCard(cardDTO);
        return cardResponseDTO;
    }
    @PutMapping("/cards/update/{id}")
    public CardResponseDTO updateCard(@RequestBody @Valid CardRequestDTO cardDTO,@PathVariable String id){
        return cardService.updateCard(cardDTO,id);
    }
    @DeleteMapping("/cards/delete")
    public void deleteCard(@RequestBody List<String> cadIds){
        cardService.deleteCards(cadIds);
    }
}
