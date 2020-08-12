package com.example.escoba2020;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "partida")
public class Partida {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "puntosPlayer")
    public int puntosPlayer;

    @ColumnInfo(name = "puntosRival")
    public int puntosRival;

    public Partida(int puntosPlayer, int puntosRival) {
        this.puntosPlayer = puntosPlayer;
        this.puntosRival = puntosRival;
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

    public int getPuntosRival() {
        return puntosRival;
    }

    public void setPuntosRival(int puntosRival) {
        this.puntosRival = puntosRival;
    }
}
