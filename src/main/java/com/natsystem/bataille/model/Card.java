package com.natsystem.bataille.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards")
@Data
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;
    private String suit;

    public Card() {
    }
    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }
}
