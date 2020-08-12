package com.example.escoba2020;

import android.widget.ImageView;

import androidx.annotation.NonNull;

public class Card {
    private Integer palo;
    private Integer valor;
    private Boolean sel = false;
    private Boolean flip = false;
    private ImageView imagen;

    public Card(){
        palo = 0;
        valor = 0;
    }

    public Card(Integer palo, Integer valor) {
        this.palo = palo;
        this.valor = valor;
    }

    public Card(Integer palo, Integer valor, Boolean flip) {
        this.palo = palo;
        this.valor = valor;
        this.flip = flip;
    }

    public Card(Integer palo, Integer valor, Boolean flip, ImageView imagen) {
        this.palo = palo;
        this.valor = valor;
        this.flip = flip;
        this.imagen = imagen;
    }

    public Integer getPalo() {
        return palo;
    }

    public String getPaloString(){
        switch(palo){
            case 1: return "Oros";
            case 2: return "Copas";
            case 3: return "Espadas";
            case 4: return "Bastos";
            default: return "????";
        }
    }

    public void setPalo(Integer palo) {
        this.palo = palo;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Boolean getSel() {
        return sel;
    }

    public void setSel(Boolean sel) {
        this.sel = sel;
    }

    public Boolean getFlip() {
        return flip;
    }

    public void setFlip(Boolean flip) {
        this.flip = flip;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    @NonNull
    @Override
    public String toString() {
        return valor + " de " + getPaloString();
    }
}
