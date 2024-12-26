package com.natsystem.bataille.controller;

import com.natsystem.bataille.dto.CardDTO;
import com.natsystem.bataille.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        logger.info("Initializing the deck of cards...");
        cardService.initializeDeck();
        logger.info("Deck initialized successfully.");
    }

    @Operation(summary = "Get All Cards", description = "Retrieve the full deck of 52 cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cards returned successfully"),
            @ApiResponse(responseCode = "204", description = "No cards available"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameter")
    })
    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards() {
        logger.info("Retrieving all cards");
        List<CardDTO> cards = cardService.getAllCards();
        if (cards.isEmpty()) {
            logger.warn("No cards found.");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cards);
    }

    @Operation(summary = "Shuffle deck", description = "Shuffle the entire deck of cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deck shuffled successfully")
    })
    @PostMapping("/shuffle")
    public ResponseEntity<List<CardDTO>> shuffleDeck() {
        logger.info("Shuffling the deck...");
        List<CardDTO> shuffledDeck = cardService.shuffleDeck();
        logger.info("Deck shuffled successfully.");
        return ResponseEntity.ok(shuffledDeck);
    }

    @Operation(summary = "Get first card of deck", description = "Draw the first card from the deck")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "First card returned successfully"),
            @ApiResponse(responseCode = "204", description = "No card available in the deck")
    })
    @GetMapping("/first")
    public ResponseEntity<CardDTO> getFirstCard() {
        logger.info("Retrieving the first card from the deck.");
        CardDTO firstCard = cardService.getFirstCard();
        if (firstCard == null) {
            logger.warn("No card available in the deck.");
            return ResponseEntity.noContent().build();
        }
        logger.info("First card retrieved: {}", firstCard);
        return ResponseEntity.ok(firstCard);
    }

    @Operation(summary = "Get 2 cards of deck", description = "Draw 2 random cards from the deck")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "2 cards returned successfully"),
            @ApiResponse(responseCode = "204", description = "Not enough cards available in the deck")
    })
    @GetMapping("/draw/2")
    public ResponseEntity<List<CardDTO>> drawTwoCards() {
        logger.info("Drawing two random cards...");
        List<CardDTO> cards = cardService.drawTwoCards();
        if (cards.isEmpty()) {
            logger.warn("Cannot draw two cards: the deck is empty.");
            return ResponseEntity.noContent().build();
        }
        logger.info("Two cards drawn: {}", cards);
        return ResponseEntity.ok(cards);
    }

    @Operation(summary = "Get a list of cards by suit", description = "Filter the deck by suit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards filtered by suit returned successfully"),
            @ApiResponse(responseCode = "204", description = "No cards found for the specified suit")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<CardDTO>> filterCards(
            @RequestParam(required = false) String suit,
            @RequestParam(required = false) String value) {
        logger.info("Filtering cards. Suit: {}, Value: {}", suit, value);
        List<CardDTO> filteredCards = cardService.filterCards(suit, value);

        if (filteredCards.isEmpty()) {
            logger.warn("No cards found for the given criteria. Suit: {}, Value: {}", suit, value);
            return ResponseEntity.noContent().build();
        }
        logger.info("{} cards found for the given criteria. Suit: {}, Value: {}", filteredCards.size(), suit, value);
        return ResponseEntity.ok(filteredCards);
    }

}
