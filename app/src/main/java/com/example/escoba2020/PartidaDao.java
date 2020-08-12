package com.example.escoba2020;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PartidaDao {
    @Query("SELECT * FROM partida")
    List<Partida> getAll();

    @Query("SELECT * FROM partida WHERE uid IN (:userIds)")
    List<Partida> loadAllByIds(int[] userIds);

    @Insert
    void insert(Partida partida);

    @Delete
    void delete(Partida partida);
}