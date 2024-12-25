package com.natsystem.bataille.controller;

import com.natsystem.bataille.model.Card;
import com.natsystem.bataille.repository.CardRepository;
import com.natsystem.bataille.service.CardService;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    private List<Card> deck;

    @PostConstruct
    public void init() {
        cardService.loadDeck();
    }

    @GetMapping
    public List<Card> getAllCards(@RequestParam(required = false) String suit) {
        return cardService.getAllCards(suit);
    }

    @PostMapping("/shuffle")
    public List<Card> shuffleDeck() {
        return cardService.shuffleDeck();
    }

    @GetMapping("/first")
    public Card getFirstCard() {
        return cardService.getFirstCard();
    }

    @GetMapping("/draw/2")
    public List<Card> drawTwoCards() {
        return cardService.drawTwoCards();
    }

    @GetMapping("/filter")
    public List<Card> filterCardsBySuit(@RequestParam String suit) {
        return cardService.filterCardsBySuit(suit);
    }
}
