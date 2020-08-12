package com.example.escoba2020;

public class SavedCard {
    private Integer palo;
    private Integer valor;

    public SavedCard(Card card) {
        this.palo = card.getPalo();
        this.valor = card.getValor();
    }

    public Card convertToCard(){
        return new Card(palo, valor, true);
    }
}
