package com.example.escoba2020;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

public class CurrentPartida {

    public int totalPlayer;

    public int totalRival;

    public int escobasPlayer;

    public int escobasRival;

    public ArrayList<Card> listPlayerCards;

    public ArrayList<Card> listRivalCards;

    public ArrayList<Card> listCenterCards;

    public ArrayList<Card> pilaPlayerCards;

    public ArrayList<Card> pilaRivalCards;

    public ArrayList<Card> baraja;

    public int numCarta;

    public boolean empiezaPlayer;

    public boolean lastPlayByPlayer;

    public CurrentPartida(int totalPlayer, int totalRival, int escobasPlayer, int escobasRival, ArrayList<Card> listPlayerCards, ArrayList<Card> listRivalCards, ArrayList<Card> listCenterCards, ArrayList<Card> pilaPlayerCards, ArrayList<Card> pilaRivalCards, ArrayList<Card> baraja, int numCarta, boolean empiezaPlayer, boolean lastPlayByPlayer) {

        this.totalPlayer = totalPlayer;
        this.totalRival = totalRival;
        this.escobasPlayer = escobasPlayer;
        this.escobasRival = escobasRival;
        this.listPlayerCards = listPlayerCards;
        this.listRivalCards = listRivalCards;
        this.listCenterCards = listCenterCards;
        this.pilaPlayerCards = pilaPlayerCards;
        this.pilaRivalCards = pilaRivalCards;
        this.baraja = baraja;
        this.numCarta = numCarta;
        this.empiezaPlayer = empiezaPlayer;
        this.lastPlayByPlayer = lastPlayByPlayer;
    }

    public int getTotalPlayer() {
        return totalPlayer;
    }

    public int getTotalRival() {
        return totalRival;
    }

    public int getEscobasPlayer() {
        return escobasPlayer;
    }

    public int getEscobasRival() {
        return escobasRival;
    }

    public ArrayList<Card> getListPlayerCards() {
        return listPlayerCards;
    }

    public ArrayList<Card> getListRivalCards() {
        return listRivalCards;
    }

    public ArrayList<Card> getListCenterCards() {
        return listCenterCards;
    }

    public ArrayList<Card> getPilaPlayerCards() {
        return pilaPlayerCards;
    }

    public ArrayList<Card> getPilaRivalCards() {
        return pilaRivalCards;
    }

    public ArrayList<Card> getBaraja() {
        return baraja;
    }

    public int getNumCarta() {
        return numCarta;
    }

    public boolean isEmpiezaPlayer() {
        return empiezaPlayer;
    }

    public boolean isLastPlayByPlayer() {
        return lastPlayByPlayer;
    }


    public void setTotalPlayer(int totalPlayer) {
        this.totalPlayer = totalPlayer;
    }

    public void setTotalRival(int totalRival) {
        this.totalRival = totalRival;
    }

    public void setEscobasPlayer(int escobasPlayer) {
        this.escobasPlayer = escobasPlayer;
    }

    public void setEscobasRival(int escobasRival) {
        this.escobasRival = escobasRival;
    }

    public void setListPlayerCards(ArrayList<Card> listPlayerCards) {
        this.listPlayerCards = listPlayerCards;
    }

    public void setListRivalCards(ArrayList<Card> listRivalCards) {
        this.listRivalCards = listRivalCards;
    }

    public void setListCenterCards(ArrayList<Card> listCenterCards) {
        this.listCenterCards = listCenterCards;
    }

    public void setPilaPlayerCards(ArrayList<Card> pilaPlayerCards) {
        this.pilaPlayerCards = pilaPlayerCards;
    }

    public void setPilaRivalCards(ArrayList<Card> pilaRivalCards) {
        this.pilaRivalCards = pilaRivalCards;
    }

    public void setBaraja(ArrayList<Card> baraja) {
        this.baraja = baraja;
    }

    public void setNumCarta(int numCarta) {
        this.numCarta = numCarta;
    }

    public void setEmpiezaPlayer(boolean empiezaPlayer) {
        this.empiezaPlayer = empiezaPlayer;
    }

    public void setLastPlayByPlayer(boolean lastPlayByPlayer) {
        this.lastPlayByPlayer = lastPlayByPlayer;
    }
}
