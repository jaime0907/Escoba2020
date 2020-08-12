package com.example.escoba2020;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Juego.class, Partida.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JuegoDao juegoDao();
    public abstract PartidaDao partidaDao();

    private static volatile AppDatabase dbInstance;

    static final Migration MIGRATION = new Migration(4,5){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };
    static AppDatabase getDatabase(final Context context){
        if(dbInstance == null){
            synchronized (AppDatabase.class){
                if(dbInstance == null){
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_db")
                            .addMigrations(MIGRATION)
                            //.fallbackToDestructiveMigration() //Solo si queremos cargarnos toda la BBDD!!
                            .build();
                }
            }
        }
        return dbInstance;
    }

}