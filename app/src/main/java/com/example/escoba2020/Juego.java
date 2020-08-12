package com.example.escoba2020;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "juego")
public class Juego {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "puntosPlayer")
    public int puntosPlayer;

    @ColumnInfo(name = "escobasPlayer")
    public int escobasPlayer;

    @ColumnInfo(name = "sieteOrosPlayer")
    public int sieteOrosPlayer;

    @ColumnInfo(name = "sietesPlayer")
    public int sietesPlayer;

    @ColumnInfo(name = "orosPlayer")
    public int orosPlayer;

    @ColumnInfo(name = "cartasPlayer")
    public int cartasPlayer;

    @ColumnInfo(name = "puntosRival")
    public int puntosRival;

    @ColumnInfo(name = "escobasRival")
    public int escobasRival;

    @ColumnInfo(name = "sieteOrosRival")
    public int sieteOrosRival;

    @ColumnInfo(name = "sietesRival")
    public int sietesRival;

    @ColumnInfo(name = "orosRival")
    public int orosRival;

    @ColumnInfo(name = "cartasRival")
    public int cartasRival;

    public Juego(int puntosPlayer, int escobasPlayer, int sieteOrosPlayer, int sietesPlayer, int orosPlayer, int cartasPlayer, int puntosRival, int escobasRival, int sieteOrosRival, int sietesRival, int orosRival, int cartasRival) {
        this.puntosPlayer = puntosPlayer;
        this.escobasPlayer = escobasPlayer;
        this.sieteOrosPlayer = sieteOrosPlayer;
        this.sietesPlayer = sietesPlayer;
        this.orosPlayer = orosPlayer;
        this.cartasPlayer = cartasPlayer;
        this.puntosRival = puntosRival;
        this.escobasRival = escobasRival;
        this.sieteOrosRival = sieteOrosRival;
        this.sietesRival = sietesRival;
        this.orosRival = orosRival;
        this.cartasRival = cartasRival;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPuntosPlayer() {
        return puntosPlayer;
    }

    public void setPuntosPlayer(int puntosPlayer) {
        this.puntosPlayer = puntosPlayer;
    }

    public int getEscobasPlayer() {
        return escobasPlayer;
    }

    public void setEscobasPlayer(int escobasPlayer) {
        this.escobasPlayer = escobasPlayer;
    }

    public int getSieteOrosPlayer() {
        return sieteOrosPlayer;
    }

    public void setSieteOrosPlayer(int sieteOrosPlayer) {
        this.sieteOrosPlayer = sieteOrosPlayer;
    }

    public int getSietesPlayer() {
        return sietesPlayer;
    }

    public void setSietesPlayer(int sietesPlayer) {
        this.sietesPlayer = sietesPlayer;
    }

    public int getOrosPlayer() {
        return orosPlayer;
    }

    public void setOrosPlayer(int orosPlayer) {
        this.orosPlayer = orosPlayer;
    }

    public int getCartasPlayer() {
        return cartasPlayer;
    }

    public void setCartasPlayer(int cartasPlayer) {
        this.cartasPlayer = cartasPlayer;
    }

    public int getPuntosRival() {
        return puntosRival;
    }

    public void setPuntosRival(int puntosRival) {
        this.puntosRival = puntosRival;
    }

    public int getEscobasRival() {
        return escobasRival;
    }

    public void setEscobasRival(int escobasRival) {
        this.escobasRival = escobasRival;
    }

    public int getSieteOrosRival() {
        return sieteOrosRival;
    }

    public void setSieteOrosRival(int sieteOrosRival) {
        this.sieteOrosRival = sieteOrosRival;
    }

    public int getSietesRival() {
        return sietesRival;
    }

    public void setSietesRival(int sietesRival) {
        this.sietesRival = sietesRival;
    }

    public int getOrosRival() {
        return orosRival;
    }

    public void setOrosRival(int orosRival) {
        this.orosRival = orosRival;
    }

    public int getCartasRival() {
        return cartasRival;
    }

    public void setCartasRival(int cartasRival) {
        this.cartasRival = cartasRival;
    }
}