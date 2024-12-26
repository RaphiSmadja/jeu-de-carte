package com.natsystem.bataille.dto;

public class CardDTO {
    private Long id;
    private String value;
    private String suit;

    public CardDTO(Long id, String value, String suit) {
        this.id = id;
        this.value = value;
        this.suit = suit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }
}
