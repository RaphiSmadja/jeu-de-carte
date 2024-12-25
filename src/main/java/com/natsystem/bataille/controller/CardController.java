package com.natsystem.bataille.controller;

import com.natsystem.bataille.dto.CardDTO;
import com.natsystem.bataille.service.CardService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostConstruct
    public void init() {
        logger.info("Initialisation du deck de cartes...");
        cardService.loadDeck();
        logger.info("Deck initialisé avec succès.");
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards(@RequestParam(required = false) String suit) {
        logger.info("Récupération des cartes. Suit param: {}", suit);
        List<CardDTO> cards = cardService.getAllCards(suit);
        if (cards.isEmpty()) {
            logger.warn("Aucune carte trouvée.");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/shuffle")
    public ResponseEntity<List<CardDTO>> shuffleDeck() {
        logger.info("Mélange des cartes...");
        List<CardDTO> shuffledDeck = cardService.shuffleDeck();
        logger.info("Deck mélangé avec succès.");
        return ResponseEntity.ok(shuffledDeck);
    }

    @GetMapping("/first")
    public ResponseEntity<CardDTO> getFirstCard() {
        logger.info("Récupération de la première carte du deck.");
        CardDTO firstCard = cardService.getFirstCard();
        if (firstCard == null) {
            logger.warn("Aucune carte disponible dans le deck.");
            return ResponseEntity.noContent().build();
        }
        logger.info("Première carte récupérée : {}", firstCard);
        return ResponseEntity.ok(firstCard);
    }

    @GetMapping("/draw/2")
    public ResponseEntity<List<CardDTO>> drawTwoCards() {
        logger.info("Tirage de deux cartes aléatoires...");
        List<CardDTO> cards = cardService.drawTwoCards();
        if (cards.isEmpty()) {
            logger.warn("Impossible de tirer deux cartes : le deck est vide.");
            return ResponseEntity.noContent().build();
        }
        logger.info("Deux cartes tirées : {}", cards);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CardDTO>> filterCardsBySuit(@RequestParam String suit) {
        logger.info("Filtrage des cartes par couleur : {}", suit);
        List<CardDTO> filteredCards = cardService.filterCardsBySuit(suit);
        if (filteredCards.isEmpty()) {
            logger.warn("Aucune carte trouvée pour la couleur : {}", suit);
            return ResponseEntity.noContent().build();
        }
        logger.info("{} cartes trouvées pour la couleur {}", filteredCards.size(), suit);
        return ResponseEntity.ok(filteredCards);
    }
}
