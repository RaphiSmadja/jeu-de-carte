package com.natsystem.bataille.initializer;

import com.natsystem.bataille.model.Card;
import com.natsystem.bataille.repository.CardRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class CardInitializer {

    private final CardRepository cardRepository;

    public CardInitializer(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PostConstruct
    public void init() {
        if (cardRepository.count() == 0) {
            String[] suits = {"trèfle", "carreau", "coeur", "pique"};
            String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Reine", "Roi", "As"};

            for (String suit : suits) {
                for (String value : values) {
                    cardRepository.save(new Card(value, suit));
                }
            }
            System.out.println("Jeu de cartes initialisé !");
        }
    }
}
