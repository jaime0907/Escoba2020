package com.example.escoba2020;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JuegoDao {
    @Query("SELECT * FROM juego")
    List<Juego> getAll();

    @Query("SELECT * FROM juego WHERE uid IN (:userIds)")
    List<Juego> loadAllByIds(int[] userIds);

    @Insert
    void insert(Juego juego);

    @Delete
    void delete(Juego juego);
}