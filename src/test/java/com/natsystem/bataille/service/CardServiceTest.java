package com.natsystem.bataille.service;

import com.natsystem.bataille.dto.CardDTO;
import com.natsystem.bataille.mapper.CardMapper;
import com.natsystem.bataille.model.Card;
import com.natsystem.bataille.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardService cardService;

    private List<Card> mockCards;
    private List<CardDTO> mockCardDTOs;

    @BeforeEach
    public void setUp() {
        mockCards = Arrays.asList(
                new Card(1L, "Ace", "Hearts"),
                new Card(2L, "King", "Spades")
        );

        mockCardDTOs = Arrays.asList(
                new CardDTO(1L, "Ace", "Hearts"),
                new CardDTO(2L, "King", "Spades")
        );
    }

    @Test
    public void testGetAllCards() {
        when(cardRepository.findAll()).thenReturn(mockCards);
        when(cardMapper.toDTOList(mockCards)).thenReturn(mockCardDTOs);

        List<CardDTO> result = cardService.getAllCards();

        assertEquals(2, result.size());
        assertEquals("Ace", result.get(0).getValue());
        assertEquals("Hearts", result.get(0).getSuit());

        verify(cardRepository, times(1)).findAll();
        verify(cardMapper, times(1)).toDTOList(mockCards);
    }

    @Test
    public void testInitializeDeck() {
        when(cardRepository.findAll()).thenReturn(mockCards);
        when(cardMapper.toDTOList(mockCards)).thenReturn(mockCardDTOs);

        cardService.initializeDeck();

        List<CardDTO> result = cardService.getDeck();
        assertEquals(2, result.size());
        assertEquals("Ace", result.get(0).getValue());
        verify(cardRepository, times(1)).findAll();
        verify(cardMapper, times(1)).toDTOList(mockCards);
    }

    @Test
    public void testGetDeckWhenNotInitialized() {
        Exception exception = assertThrows(IllegalStateException.class, () -> cardService.getDeck());
        assertEquals("Deck not initialized. Call initializeDeck() first.", exception.getMessage());
    }

    @Test
    public void testShuffleDeck() {
        when(cardRepository.findAll()).thenReturn(mockCards);
        when(cardMapper.toDTOList(mockCards)).thenReturn(mockCardDTOs);

        cardService.initializeDeck();
        List<CardDTO> shuffledDeck = cardService.shuffleDeck();

        assertEquals(2, shuffledDeck.size());
        verify(cardMapper, atLeastOnce()).toDTOList(mockCards);
    }

    @Test
    public void testShuffleDeckWhenNotInitialized() {
        Exception exception = assertThrows(IllegalStateException.class, () -> cardService.shuffleDeck());
        assertEquals("Deck not initialized. Call initializeDeck() first.", exception.getMessage());
    }

    @Test
    public void testGetFirstCard() {
        when(cardRepository.findAll()).thenReturn(mockCards);
        when(cardMapper.toDTOList(mockCards)).thenReturn(mockCardDTOs);

        cardService.initializeDeck();
        CardDTO firstCard = cardService.getFirstCard();

        assertNotNull(firstCard);
        assertEquals("Ace", firstCard.getValue());
        assertEquals("Hearts", firstCard.getSuit());
    }

    @Test
    public void testGetFirstCardWhenDeckIsEmpty() {
        when(cardRepository.findAll()).thenReturn(Collections.emptyList());

        cardService.initializeDeck();
        CardDTO firstCard = cardService.getFirstCard();

        assertNull(firstCard);
    }

    @Test
    public void testDrawTwoCards() {
        when(cardRepository.findAll()).thenReturn(mockCards);
        when(cardMapper.toDTOList(mockCards)).thenReturn(mockCardDTOs);

        cardService.initializeDeck();
        List<CardDTO> twoCards = cardService.drawTwoCards();

        assertEquals(2, twoCards.size());
    }

    @Test
    public void testDrawTwoCardsWhenDeckIsEmpty() {
        when(cardRepository.findAll()).thenReturn(Collections.emptyList());

        cardService.initializeDeck();
        List<CardDTO> result = cardService.drawTwoCards();

        assertEquals(0, result.size());
    }

    @Test
    public void testFilterCardsBySuitAndValue() {
        List<Card> filteredCards = Collections.singletonList(new Card(1L, "Ace", "Hearts"));
        List<CardDTO> filteredCardDTOs = Collections.singletonList(new CardDTO(1L, "Ace", "Hearts"));

        when(cardRepository.findBySuitAndValue("Hearts", "Ace")).thenReturn(filteredCards);
        when(cardMapper.toDTOList(filteredCards)).thenReturn(filteredCardDTOs);

        List<CardDTO> result = cardService.filterCards("Hearts", "Ace");

        assertEquals(1, result.size());
        assertEquals("Ace", result.get(0).getValue());
        assertEquals("Hearts", result.get(0).getSuit());

        verify(cardRepository, times(1)).findBySuitAndValue("Hearts", "Ace");
        verify(cardMapper, times(1)).toDTOList(filteredCards);
    }

    @Test
    public void testFilterCardsBySuit() {
        List<Card> filteredCards = Collections.singletonList(new Card(1L, "Ace", "Hearts"));
        List<CardDTO> filteredCardDTOs = Collections.singletonList(new CardDTO(1L, "Ace", "Hearts"));

        when(cardRepository.findBySuit("Hearts")).thenReturn(filteredCards);
        when(cardMapper.toDTOList(filteredCards)).thenReturn(filteredCardDTOs);

        List<CardDTO> result = cardService.filterCards("Hearts", null);

        assertEquals(1, result.size());
        assertEquals("Ace", result.get(0).getValue());
        assertEquals("Hearts", result.get(0).getSuit());

        verify(cardRepository, times(1)).findBySuit("Hearts");
        verify(cardMapper, times(1)).toDTOList(filteredCards);
    }

    @Test
    public void testFilterCardsByValue() {
        List<Card> filteredCards = Collections.singletonList(new Card(1L, "Ace", "Hearts"));
        List<CardDTO> filteredCardDTOs = Collections.singletonList(new CardDTO(1L, "Ace", "Hearts"));

        when(cardRepository.findByValue("Ace")).thenReturn(filteredCards);
        when(cardMapper.toDTOList(filteredCards)).thenReturn(filteredCardDTOs);

        List<CardDTO> result = cardService.filterCards(null, "Ace");

        assertEquals(1, result.size());
        assertEquals("Ace", result.get(0).getValue());
        assertEquals("Hearts", result.get(0).getSuit());

        verify(cardRepository, times(1)).findByValue("Ace");
        verify(cardMapper, times(1)).toDTOList(filteredCards);
    }
}
