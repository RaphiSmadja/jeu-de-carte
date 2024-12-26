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

    public void loadDeck() {
        List<Card> all = cardRepository.findAll();
        deck = cardMapper.toDTOList(cardRepository.findAll());
    }

    public List<CardDTO> getAllCards(String suit) {
        if (suit != null) {
            return cardMapper.toDTOList(cardRepository.findBySuit(suit));
        }
        return deck;
    }

    public List<CardDTO> shuffleDeck() {
        System.out.println(deck);
        Collections.shuffle(deck);
        return deck;
    }

    public CardDTO getFirstCard() {
        return deck.isEmpty() ? null : deck.get(0);
    }

    public List<CardDTO> drawTwoCards() {
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
