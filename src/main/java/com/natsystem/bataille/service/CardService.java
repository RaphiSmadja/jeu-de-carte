package com.natsystem.bataille.service;

import com.natsystem.bataille.dto.CardDTO;
import com.natsystem.bataille.mapper.CardMapper;
import com.natsystem.bataille.model.Card;
import com.natsystem.bataille.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private List<CardDTO> deck;

    public CardService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    public List<CardDTO> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return cardMapper.toDTOList(cards);
    }

    public void initializeDeck() {
        deck = cardMapper.toDTOList(cardRepository.findAll());
    }

    public List<CardDTO> getDeck() {
        if (deck == null) {
            throw new IllegalStateException("Deck not initialized. Call initializeDeck() first.");
        }
        return deck;
    }

    public List<CardDTO> shuffleDeck() {
        if (deck == null) {
            throw new IllegalStateException("Deck not initialized. Call initializeDeck() first.");
        }
        Collections.shuffle(deck);
        return deck;
    }

    public CardDTO getFirstCard() {
        if (deck == null || deck.isEmpty()) {
            return null;
        }
        return deck.get(0);
    }

    public List<CardDTO> drawTwoCards() {
        if (deck == null || deck.isEmpty()) {
            return Collections.emptyList();
        }
        Collections.shuffle(deck);
        return deck.subList(0, Math.min(2, deck.size()));
    }

    public List<CardDTO> filterCards(String suit, String value) {
        List<Card> cards;

        if (suit != null && value != null) {
            cards = cardRepository.findBySuitAndValue(suit, value);
        } else if (suit != null) {
            cards = cardRepository.findBySuit(suit);
        } else if (value != null) {
            cards = cardRepository.findByValue(value);
        } else {
            cards = cardRepository.findAll();
        }

        return cardMapper.toDTOList(cards);
    }
}
