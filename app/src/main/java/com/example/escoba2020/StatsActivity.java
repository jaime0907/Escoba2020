package com.example.escoba2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class StatsActivity extends AppCompatActivity {

    AppDatabase db;
    JuegoDao juegoDao;
    PartidaDao partidaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        db =  AppDatabase.getDatabase(getApplicationContext());
        juegoDao = db.juegoDao();
        partidaDao = db.partidaDao();

        makeStats();
    }







    public void makeStats(){

        //new InsertAsyncTask(dao).execute(user);

        ExecutorService es = Executors.newFixedThreadPool(10);

        Callable<List<Juego>> task = new Callable(){
            @Override
            public List<Juego> call() {
                return juegoDao.getAll();
            }
        };

        Callable<List<Partida>> task2 = new Callable(){
            @Override
            public List<Partida> call() {
                return partidaDao.getAll();
            }
        };

        Future<List<Juego>> futureListJuego = es.submit(task);
        Future<List<Partida>> futureListPartida = es.submit(task2);

        int partidasGanadas = 0;
        int partidasJugadas = 0;
        String winrate = "0";
        int juegosJugados = 0;
        String mediaPuntos = "0";
        int escobasTotales = 0;
        int maxPuntos = 0;
        int maxEscobas = 0;
        int maxSietes = 0;
        int maxOros = 0;
        int maxCartas = 0;
        String percent7oros = "0";

        int puntosTotales = 0;
        int total7oros = 0;

        try {
            for(Juego j : futureListJuego.get()){
                juegosJugados++;
                puntosTotales += j.puntosPlayer;
                if(j.puntosPlayer > maxPuntos) maxPuntos = j.puntosPlayer;
                if(j.orosPlayer > maxOros) maxOros = j.orosPlayer;
                if(j.sietesPlayer > maxSietes) maxSietes = j.sietesPlayer;
                if(j.cartasPlayer > maxCartas) maxCartas = j.cartasPlayer;
                if(j.escobasPlayer > maxEscobas) maxEscobas = j.escobasPlayer;
                escobasTotales += j.escobasPlayer;
                total7oros += j.sieteOrosPlayer;
            }
            for(Partida p : futureListPartida.get()){
                if(p.puntosPlayer > p.puntosRival) partidasGanadas++;
                partidasJugadas++;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#");
        percent7oros = decimalFormat.format((float)total7oros * 100 / (float)juegosJugados);
        mediaPuntos = decimalFormat.format((float)puntosTotales / (float)juegosJugados);
        winrate = decimalFormat.format((float)partidasGanadas * 100 / (float)partidasJugadas);


        ((TextView)findViewById(R.id.statPartidasGanadas)).setText(String.valueOf(partidasGanadas));
        ((TextView)findViewById(R.id.statPartidasJugadas)).setText(String.valueOf(partidasJugadas));
        ((TextView)findViewById(R.id.statWinrate)).setText(winrate + "%");
        ((TextView)findViewById(R.id.statJuegosJugados)).setText(String.valueOf(juegosJugados));
        ((TextView)findViewById(R.id.statMediaPuntos)).setText(mediaPuntos);
        ((TextView)findViewById(R.id.statEscobasTotales)).setText(String.valueOf(escobasTotales));
        ((TextView)findViewById(R.id.statMaxPuntos)).setText(String.valueOf(maxPuntos));
        ((TextView)findViewById(R.id.statMaxEscobas)).setText(String.valueOf(maxEscobas));
        ((TextView)findViewById(R.id.statMaxSietes)).setText(String.valueOf(maxSietes));
        ((TextView)findViewById(R.id.statMaxOros)).setText(String.valueOf(maxOros));
        ((TextView)findViewById(R.id.statMaxCartas)).setText(String.valueOf(maxCartas));
        ((TextView)findViewById(R.id.stat7oros)).setText(percent7oros + "%");

    }

    private class InsertAsyncTask extends AsyncTask<Juego, Void, Void> {
        JuegoDao mDao;

        public InsertAsyncTask(JuegoDao mDao){
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Juego... juegos) {
            mDao.insert(juegos[0]);
            return null;
        }
    }
}