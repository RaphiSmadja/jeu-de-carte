package com.natsystem.bataille.controller;

import com.natsystem.bataille.dto.CardDTO;
import com.natsystem.bataille.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardControllerTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    private List<CardDTO> mockCardDTOs;

    @BeforeEach
    public void setUp() {
        mockCardDTOs = Arrays.asList(
                new CardDTO(1L, "Ace", "Hearts"),
                new CardDTO(2L, "King", "Spades")
        );
    }

    @Test
    public void testGetAllCards() {
        when(cardService.getAllCards()).thenReturn(mockCardDTOs);

        ResponseEntity<List<CardDTO>> response = cardController.getAllCards();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(cardService, times(1)).getAllCards();
    }

    @Test
    public void testGetAllCardsEmpty() {
        when(cardService.getAllCards()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CardDTO>> response = cardController.getAllCards();

        assertEquals(204, response.getStatusCodeValue());
        verify(cardService, times(1)).getAllCards();
    }

    @Test
    public void testShuffleDeck() {
        when(cardService.shuffleDeck()).thenReturn(mockCardDTOs);

        ResponseEntity<List<CardDTO>> response = cardController.shuffleDeck();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(cardService, times(1)).shuffleDeck();
    }

    @Test
    public void testGetFirstCard() {
        CardDTO firstCard = new CardDTO(1L, "Ace", "Hearts");
        when(cardService.getFirstCard()).thenReturn(firstCard);

        ResponseEntity<CardDTO> response = cardController.getFirstCard();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ace", response.getBody().getValue());
        assertEquals("Hearts", response.getBody().getSuit());
        verify(cardService, times(1)).getFirstCard();
    }

    @Test
    public void testGetFirstCardWhenDeckIsEmpty() {
        when(cardService.getFirstCard()).thenReturn(null);

        ResponseEntity<CardDTO> response = cardController.getFirstCard();

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(cardService, times(1)).getFirstCard();
    }

    @Test
    public void testDrawTwoCards() {
        when(cardService.drawTwoCards()).thenReturn(mockCardDTOs);

        ResponseEntity<List<CardDTO>> response = cardController.drawTwoCards();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(cardService, times(1)).drawTwoCards();
    }

    @Test
    public void testDrawTwoCardsWhenDeckIsEmpty() {
        when(cardService.drawTwoCards()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CardDTO>> response = cardController.drawTwoCards();

        assertEquals(204, response.getStatusCodeValue());
        verify(cardService, times(1)).drawTwoCards();
    }

    @Test
    public void testFilterCardsBySuitAndValue() {
        when(cardService.filterCards("Hearts", "Ace")).thenReturn(
                Collections.singletonList(new CardDTO(1L, "Ace", "Hearts"))
        );

        ResponseEntity<List<CardDTO>> response = cardController.filterCards("Hearts", "Ace");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Ace", response.getBody().get(0).getValue());
        assertEquals("Hearts", response.getBody().get(0).getSuit());
        verify(cardService, times(1)).filterCards("Hearts", "Ace");
    }

    @Test
    public void testFilterCardsNoResults() {
        when(cardService.filterCards("Spades", "King")).thenReturn(Collections.emptyList());

        ResponseEntity<List<CardDTO>> response = cardController.filterCards("Spades", "King");

        assertEquals(204, response.getStatusCodeValue());
        verify(cardService, times(1)).filterCards("Spades", "King");
    }

    @Test
    public void testFilterCardsNoParameters() {
        when(cardService.filterCards(null, null)).thenReturn(mockCardDTOs);

        ResponseEntity<List<CardDTO>> response = cardController.filterCards(null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(cardService, times(1)).filterCards(null, null);
    }
}
