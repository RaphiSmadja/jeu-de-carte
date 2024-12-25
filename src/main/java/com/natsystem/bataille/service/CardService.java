package com.natsystem.bataille.service;

import com.natsystem.bataille.model.Card;
import com.natsystem.bataille.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private List<Card> deck;


    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void loadDeck() {
        deck = cardRepository.findAll();
    }

    public List<Card> getAllCards(String suit) {
        if (suit != null) {
            return cardRepository.findBySuit(suit);
        }
        return deck;
    }

    public List<Card> shuffleDeck() {
        Collections.shuffle(deck);
        return deck;
    }

    public Card getFirstCard() {
        return deck.isEmpty() ? null : deck.get(0);
    }

    public List<Card> drawTwoCards() {
        Collections.shuffle(deck);
        return deck.subList(0, Math.min(2, deck.size()));
    }

    public List<Card> filterCardsBySuit(String suit) {
        return cardRepository.findBySuit(suit);
    }

}
