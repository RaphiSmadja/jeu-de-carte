package com.natsystem.bataille.initializer;

import com.natsystem.bataille.model.Card;
import com.natsystem.bataille.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardInitializerTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardInitializer cardInitializer;

    @Test
    public void testInitDeck() {
        when(cardRepository.count()).thenReturn(0L);

        cardInitializer.init();

        verify(cardRepository, times(52)).save(any(Card.class));
    }

    @Test
    public void testInitDeckAlreadyInitialized() {
        when(cardRepository.count()).thenReturn(52L);

        cardInitializer.init();

        verify(cardRepository, never()).save(any(Card.class));
    }
}
