package com.natsystem.bataille.service;

import com.natsystem.bataille.dto.CardDTO;
import com.natsystem.bataille.mapper.CardMapper;
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
        deck = cardMapper.toDTOList(cardRepository.findAll());
    }

    public List<CardDTO> getAllCards(String suit) {
        if (suit != null) {
            return cardMapper.toDTOList(cardRepository.findBySuit(suit));
        }
        return deck;
    }

    public List<CardDTO> shuffleDeck() {
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

    public List<CardDTO> filterCardsBySuit(String suit) {
        return cardMapper.toDTOList(cardRepository.findBySuit(suit));
    }

}
