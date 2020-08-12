package com.example.escoba2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class GameActivity extends AppCompatActivity {
    int duration = 1000;
    ArrayList<Card> baraja = new ArrayList<>();
    ArrayList<Card> listPlayerCards = new ArrayList<>();
    ArrayList<Card> listRivalCards = new ArrayList<>();
    ArrayList<Card> pilaPlayerCards = new ArrayList<>();
    ArrayList<Card> pilaRivalCards = new ArrayList<>();
    ArrayList<Card> listCenterCards = new ArrayList<>();

    boolean empiezaPlayer = true;

    int totalPlayer = 0;
    int totalRival = 0;

    int escobasPlayer = 0;
    int escobasRival = 0;

    String gameState = "Stop";

    boolean lastPlayByPlayer = false;

    Integer numCarta = 0;

    //81dp y 126dp para una pantalla 534ppp (Pixel XL)
    int widthCard = 283;
    int heightCard = 441;

    Handler handler = new Handler();

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        db =  AppDatabase.getDatabase(getApplicationContext());

        boolean esContinue = getIntent().getBooleanExtra("continue", false);

        if(esContinue){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    continueGame();
                }
            }, 500);
        }else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startGame();
                }
            }, 500);
        }

    }


    public void continueGame(){
        TinyDB tinydb = new TinyDB(getApplicationContext());

        numCarta = tinydb.getInt("numCarta");
        totalRival = tinydb.getInt("totalRival");
        totalPlayer = tinydb.getInt("totalPlayer");
        escobasPlayer = tinydb.getInt("escobasPlayer");
        escobasRival = tinydb.getInt("escobasRival");
        lastPlayByPlayer = tinydb.getBoolean("lastPlayByPlayer");
        boolean finish = tinydb.getBoolean("finish");
        ArrayList<Card> listPlayerCards1 = convertListSavedToCard(tinydb.getListCard("listPlayerCards"));
        ArrayList<Card> listRivalCards1 = convertListSavedToCard(tinydb.getListCard("listRivalCards"));
        ArrayList<Card> pilaPlayerCards1 = convertListSavedToCard(tinydb.getListCard("pilaPlayerCards"));
        ArrayList<Card> pilaRivalCards1 = convertListSavedToCard(tinydb.getListCard("pilaRivalCards"));
        ArrayList<Card> listCenterCards1 = convertListSavedToCard(tinydb.getListCard("listCenterCards"));
        baraja = convertListSavedToCard(tinydb.getListCard("baraja"));


        final ImageView carta1 = findViewById(R.id.player1);
        widthCard = carta1.getWidth();
        heightCard = carta1.getHeight();

        makeCartasBaraja();

        for(Card c : baraja){
            boolean nextCard = false;

            for (int i = 0; i < listPlayerCards1.size(); i++) {
                Card cx = listPlayerCards1.get(i);
                if (c.getPalo().equals(cx.getPalo()) && c.getValor().equals(cx.getValor())) {
                    listPlayerCards.add(c);
                    nextCard = true;
                    break;
                }
            }
            if(nextCard) continue;

            for (int i = 0; i < listRivalCards1.size(); i++) {
                Card cx = listRivalCards1.get(i);
                if (c.getPalo().equals(cx.getPalo()) && c.getValor().equals(cx.getValor())) {
                    listRivalCards.add(c);
                    nextCard = true;
                    break;
                }
            }
            if(nextCard) continue;

            for (int i = 0; i < listCenterCards1.size(); i++) {
                Card cx = listCenterCards1.get(i);
                if (c.getPalo().equals(cx.getPalo()) && c.getValor().equals(cx.getValor())) {
                    listCenterCards.add(c);
                    nextCard = true;
                    break;
                }
            }
            if(nextCard) continue;

            for (int i = 0; i < pilaRivalCards1.size(); i++) {
                Card cx = pilaRivalCards1.get(i);
                if (c.getPalo().equals(cx.getPalo()) && c.getValor().equals(cx.getValor())) {
                    pilaRivalCards.add(c);
                    nextCard = true;
                    break;
                }
            }
            if(nextCard) continue;

            for (int i = 0; i < pilaPlayerCards1.size(); i++) {
                Card cx = pilaPlayerCards1.get(i);
                if (c.getPalo().equals(cx.getPalo()) && c.getValor().equals(cx.getValor())) {
                    pilaPlayerCards.add(c);
                    nextCard = true;
                    break;
                }
            }
        }




        int i = 1;
        float x = 0;
        float y = 0;
        ImageView playerImage = null;
        ImageView rivalImage = null;
        ImageView pilaPlayer = findViewById(R.id.pilaPlayer);
        ImageView pilaRival = findViewById(R.id.pilaRival);

        i = 1;
        for(Card card : listRivalCards){
            switch(i){
                case 1:
                    rivalImage = findViewById(R.id.rival1);
                    break;
                case 2:
                    rivalImage = findViewById(R.id.rival2);
                    break;
                case 3:
                    rivalImage = findViewById(R.id.rival3);
                    break;
            }
            moveCard(card, rivalImage.getX(), rivalImage.getY(), false, false, duration);
            i++;
        }

        i = 1;
        for(Card card : listPlayerCards){
            switch(i){
                case 1:
                    moveCard(card, findViewById(R.id.player1).getX(), findViewById(R.id.player1).getY(), false, true, duration);
                    break;
                case 2:
                    moveCard(card, findViewById(R.id.player2).getX(), findViewById(R.id.player2).getY(), false, true, duration);
                    break;
                case 3:
                    moveCard(card, findViewById(R.id.player3).getX(), findViewById(R.id.player3).getY(), false, true, duration);
                    break;
                default:

            }
            card.getImagen().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPlayerCard(v);
                }
            });
            i++;
        }



        for(Card card : pilaPlayerCards){
            moveCard(card, pilaPlayer.getX(), pilaPlayer.getY(), true, false, duration);
        }
        for(Card card : pilaRivalCards){
            moveCard(card, pilaRival.getX(), pilaRival.getY(), true, false, duration);
        }
        redrawCenter(new Card(), true, true);

        if(finish){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    calcularResultados(true);
                }
            },200);
        }

    }


    public void startGame(){
        numCarta = 0;
        escobasPlayer = 0;
        escobasRival = 0;
        lastPlayByPlayer = false;
        listPlayerCards.clear();
        listRivalCards.clear();
        pilaPlayerCards.clear();
        pilaRivalCards.clear();
        listCenterCards.clear();
        baraja.clear();

        ImageView carta1 = findViewById(R.id.player1);
        widthCard = carta1.getWidth();
        heightCard = carta1.getHeight();

        for(int i = 1; i <= 4; i++){
            for(int j = 1; j <= 10; j++){
                baraja.add(new Card(i,j, true));
            }
        }
        Collections.shuffle(baraja);
        makeCartasBaraja();

        reparteBaza();
        reparteCentro();
        int extraTime = 0;
        if(suman15(listCenterCards)){
            extraTime = duration;
            final View pila;
            if(empiezaPlayer){
                escobasPlayer++;
                pila = findViewById(R.id.pilaPlayer);
            }else{
                escobasRival++;
                pila = findViewById(R.id.pilaRival);
            }
            makeAnnouncement(R.string.escobaMano);
            final ArrayList<Card> auxListCards = new ArrayList<>();
            for(Card card : listCenterCards){
                if(empiezaPlayer){
                    pilaPlayerCards.add(card);
                }else{
                    pilaRivalCards.add(card);
                }
                auxListCards.add(card);
            }


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(Card card : auxListCards){
                        moveCard(card, pila.getX(), pila.getY(), true, true, duration);
                    }
                }
            }, duration);
            listCenterCards.clear();
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!empiezaPlayer){
                    rivalTurn();
                }
            }
        }, duration*12/10 + extraTime);

        gameState = "Playing";
    }

    public void startAgain(View view){
        empiezaPlayer = !empiezaPlayer;
        if(view != null) view.setVisibility(View.GONE);
        if(totalPlayer >= 21 && totalRival >= 21){
            if(totalPlayer > totalRival){
                winPlayer();
                return;
            }
            if(totalPlayer < totalRival){
                winRival();
                return;
            }
        }else {
            if (totalPlayer >= 21) {
                winPlayer();
                return;
            }
            if (totalRival >= 21) {
                winRival();
                return;
            }
        }

        ImageView barajaImage = findViewById(R.id.baraja);
        int x = 0;
        int y = 0;
        for(Card card : baraja){
            moveCard(card, barajaImage.getX() + x, barajaImage.getY() + y, false, false, duration/2);
            x++;
            y++;
        }


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ConstraintLayout game_layout = findViewById(R.id.game_layout);
                for(Card card : baraja){
                    game_layout.removeView(card.getImagen());
                }
                startGame();
            }
        }, 500);
    }

    public void startFull(View view){
        view.setVisibility(View.GONE);
        totalPlayer = 0;
        totalRival = 0;

        startAgain(null);
    }


    public void reparteCard(ImageView slotImage, Card card, Boolean isPlayerCard) {
        moveCard(card, slotImage.getX(), slotImage.getY(), false, isPlayerCard, duration);
        if (isPlayerCard) {
            card.getImagen().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPlayerCard(v);
                }
            });
            listPlayerCards.add(card);
        }else{
            listRivalCards.add(card);
        }
    }

    public void reparteCentro(){
        for(int i = 0; i < 4; i++) {
            addCardToCenter(baraja.get(numCarta), false, false);
            numCarta++;
        }
    }

    public void reparteBaza(){
        reparteCard((ImageView) findViewById(R.id.player1), baraja.get(numCarta), true);
        numCarta++;
        reparteCard((ImageView) findViewById(R.id.player2), baraja.get(numCarta), true);
        numCarta++;
        reparteCard((ImageView) findViewById(R.id.player3), baraja.get(numCarta), true);
        numCarta++;

        reparteCard((ImageView) findViewById(R.id.rival1), baraja.get(numCarta), false);
        numCarta++;
        reparteCard((ImageView) findViewById(R.id.rival2), baraja.get(numCarta), false);
        numCarta++;
        reparteCard((ImageView) findViewById(R.id.rival3), baraja.get(numCarta), false);
        numCarta++;
    }


    public int getCardImageId(Card card){
        String mDrawableName = "card_" + card.getPalo().toString() + "_" + card.getValor().toString();
        if(!card.getSel()) {
            return getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
        }else {
            return getResources().getIdentifier(mDrawableName + "_sel", "drawable", getPackageName());
        }
    }

    public void genCardImage(Card card, ImageView slot, ConstraintLayout layout){
        ImageView newCardImage = new ImageView(getApplicationContext());
        if(card.getFlip()){
            newCardImage.setImageResource(R.drawable.card_back);
        }else{
            newCardImage.setImageResource(getCardImageId(card));
        }

        newCardImage.setId(View.generateViewId());

        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(widthCard,heightCard);
        newCardImage.setLayoutParams(lp);
        newCardImage.setX(slot.getX());
        newCardImage.setY(slot.getY());
        layout.addView(newCardImage);
        card.setImagen(newCardImage);
    }

    public void makeCartasBaraja(){
        ConstraintLayout layout = findViewById(R.id.game_layout);
        ImageView barajaImage = (ImageView)findViewById(R.id.baraja);
        float x = barajaImage.getX();
        float y = barajaImage.getY();
        Collections.reverse(baraja);
        for(Card card : baraja){
            x++;
            y++;
            ImageView newCardImage = new ImageView(getApplicationContext());
            if (card.getFlip()) {
                newCardImage.setImageResource(R.drawable.card_back);
            } else {
                newCardImage.setImageResource(getCardImageId(card));
            }

            newCardImage.setId(View.generateViewId());
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(widthCard, heightCard);
            newCardImage.setLayoutParams(lp);
            newCardImage.setX(x);
            newCardImage.setY(y);
            newCardImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            layout.addView(newCardImage);
            card.setImagen(newCardImage);
        }
        Collections.reverse(baraja);
    }

    public void selectCentreCard(View view){
        Card card = null;
        for(Card cardx : listCenterCards){
            if(cardx.getImagen() == (ImageView)view){
                card = cardx;
            }
        }
        if(card == null){
            return;
        }
        highlightCard(card);
    }

    public void selectPlayerCard(View view){
        Card card = null;
        for(Card cardx : listPlayerCards){
            if(cardx.getImagen() == (ImageView)view){
                card = cardx;
            }
            if(cardx.getSel()){
                highlightCard(cardx);
            }

        }
        if(card == null){
            return;
        }
        highlightCard(card);
    }

    public void highlightCard(Card card){
        card.setSel(!card.getSel());
        card.getImagen().setImageResource(getCardImageId(card));
    }

    public void playerPlay(View view){
        ArrayList<Card> listPlay = new ArrayList<>();

        Card cardPlayed = null;
        for(Card card : listPlayerCards) {
            if(card.getSel()){
                listPlay.add(card);
                cardPlayed = card;
            }
        }
        for(Card card : listCenterCards) {
            if(card.getSel()){
                listPlay.add(card);
            }
        }
        if(suman15(listPlay) && cardPlayed != null) {
            makePlay(listPlay, cardPlayed, true);
            if(listPlayerCards.isEmpty() && listRivalCards.isEmpty()){
                if(numCarta >= 40){

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishGame();
                        }
                    }, duration);
                }else{

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reparteBaza();
                        }
                    }, duration);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rivalTurn();
                        }
                    }, duration + duration*12/10);
                }
            }else {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rivalTurn();
                    }
                }, duration);
            }
        }

    }

    public void makePlay(ArrayList<Card> listPlay, Card cardPlayed, boolean isPlayer){
        ImageView pila;
        lastPlayByPlayer = isPlayer;
        if(isPlayer){
            pila = (ImageView)findViewById(R.id.pilaPlayer);
        }else{
            pila = (ImageView)findViewById(R.id.pilaRival);
        }
        for(Card card : listPlay){
            if(isPlayer) highlightCard(card);

            if(card.equals(cardPlayed)){
                if(isPlayer){
                    moveCard(card, pila.getX(), pila.getY(), true, true, duration);
                    listPlayerCards.remove(listPlayerCards.indexOf(card));
                }else{
                    moveCard(card, pila.getX(), pila.getY(), true, true, true);
                    listRivalCards.remove(listRivalCards.indexOf(card));
                }
            }else{
                moveCard(card, pila.getX(), pila.getY(), true, true, duration);
                listCenterCards.remove(listCenterCards.indexOf(card));
            }
            if(isPlayer){
                pilaPlayerCards.add(card);
            }else{
                pilaRivalCards.add(card);
            }
            card.getImagen().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        if(listCenterCards.isEmpty()){
            if(isPlayer){
                escobasPlayer++;
            }else {
                escobasRival++;
            }
            makeAnnouncement(R.string.escoba);
        }
        redrawCenter(cardPlayed, false, false);
    }

    public void moveCard(Card card, float x, float y, Boolean toPila, Boolean withFlip, int duration){

        ConstraintLayout layout = findViewById(R.id.game_layout);
        ImageView oldCardImage = card.getImagen();
        genCardImage(card, card.getImagen(), layout);
        layout.removeView(oldCardImage);
        card.getImagen().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });

        if(toPila){
            x +=  Math.random()*10;
            y +=  Math.random()*10;
        }
        ObjectAnimator animX = ObjectAnimator.ofFloat(card.getImagen(), "x", x);
        ObjectAnimator animY = ObjectAnimator.ofFloat(card.getImagen(), "y", y);
        ObjectAnimator animRot = ObjectAnimator.ofFloat(card.getImagen(), "rotation", 0, (float)(Math.random()-0.5)*10);
        AnimatorSet animSetXY = new AnimatorSet();
        if(toPila){
            animSetXY.playTogether(animX, animY, animRot);
        }else{
            animSetXY.playTogether(animX, animY);
        }
        animSetXY.setDuration(duration);
        animSetXY.start();
        if(withFlip) {
            flipCard(card, duration);
        }
    }

    public void moveCard(Card card, float x, float y, Boolean toPila, Boolean withFlip, boolean specialFlip) {
        if(specialFlip){
            moveCard(card, x, y, toPila, false, duration);
            flipCard(card, duration/4);

            final Card card2 = card;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    flipCard(card2, duration / 2);
                }
            }, duration/2);
        }else{
            moveCard(card, x, y, toPila, withFlip, duration);
        }
    }

    public void flipCard(Card card, int duration){
        final Card cardCopy = card;
        int startRot = 0;
        int endRot = -180;

        if(card.getFlip()){
            startRot = -180;
            endRot = 0;
        }


        ObjectAnimator anim = ObjectAnimator.ofFloat(card.getImagen(), "rotationY", startRot, endRot);
        anim.setDuration(duration);
        anim.start();
        card.getImagen().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(cardCopy.getFlip()){
                    cardCopy.getImagen().setImageResource(getCardImageId(cardCopy));
                }else{
                    cardCopy.getImagen().setImageResource(R.drawable.card_back);
                }
                cardCopy.setFlip(!cardCopy.getFlip());

            }
        }, duration/2);

    }

    public void redrawCenter(Card card, boolean withFlip, boolean allFlip){
        float x;
        float y;
        View centro = findViewById(R.id.centro);
        boolean fullRow = false;
        for(int i = 0; i < listCenterCards.size(); i++){
            if(i % 5 == 0){
                fullRow = listCenterCards.size() - i >= 5;
            }
            Card c = listCenterCards.get(i);
            if(fullRow){
                x = centro.getX() + ((i % 5) - 5f/2f + 0.5f)*widthCard;
            }else{
                x = centro.getX() + ((i % 5) - (listCenterCards.size() - (i/5)*5)/2f + 0.5f)*widthCard;
            }

            y = centro.getY() + ((i / 5) - ((listCenterCards.size() - 1)/5 + 1)/2f + 0.5f)*heightCard;;
            moveCard(c, x, y, false, (withFlip && card.equals(c)) || allFlip, duration);
            listCenterCards.get(i).getImagen().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCentreCard(v);
                }
            });
        }
    }

    public void addCardToCenter(Card card, boolean isPlayer, boolean isRival){
        listCenterCards.add(card);
        if(isPlayer) {
            listPlayerCards.remove(listPlayerCards.indexOf(card));
        }
        if(isRival){
            listRivalCards.remove(listRivalCards.indexOf(card));
        }
        redrawCenter(card, !isPlayer, false);
    }

    public void throwCard(View view){
        Card throwedCard = null;
        for(Card card : listCenterCards){
            if(card.getSel()){
                highlightCard(card);
            }
        }
        for(Card card : listPlayerCards) {
            if(card.getSel()){
                highlightCard(card);
                addCardToCenter(card, true, false);
                throwedCard = card;
                break;
            }
        }
        if(throwedCard == null) return;
        int extraDuration = 0;
        if(checkCaptureCards(throwedCard)){
            extraDuration = duration;
        }
        if(listPlayerCards.isEmpty() && listRivalCards.isEmpty()){
            if(numCarta >=  40){

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishGame();
                    }
                }, duration + extraDuration);
            }else {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reparteBaza();
                    }
                }, duration + extraDuration);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rivalTurn();
                    }
                }, duration + duration*12/10 + extraDuration);
            }
        }else{

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rivalTurn();
                }
            }, duration + extraDuration);
        }

    }

    public boolean checkCaptureCards(final Card card){
        final ImageView pila = findViewById(R.id.pilaRival);
        ArrayList<Card> auxListCenter = new ArrayList<>(listCenterCards);
        auxListCenter.remove(auxListCenter.indexOf(card));
        ArrayList<Card> fakeRivalCards = new ArrayList<>();
        fakeRivalCards.add(card);
        ArrayList<Pair<ArrayList<Card>, Card>> allPlays = calculateAllPlays(auxListCenter, fakeRivalCards);
        final Pair<ArrayList<Card>, Card> bestPlay = pickBestPlay(allPlays);
        if(bestPlay == null) return false;
        for(Card c : bestPlay.first){
            try {
                listCenterCards.remove(listCenterCards.indexOf(c));
            }catch(ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
                for(Card c2 : bestPlay.first) {
                    Log.d("List bestPlay -->", c2.toString());
                }
                Log.d("Card -->", c.toString());
            }

            pilaRivalCards.add(c);
        }
        if(listCenterCards.isEmpty()){
            escobasRival++;
            makeAnnouncement(R.string.captureEscoba);
        }else{
            makeAnnouncement(R.string.captureCards);
        }
        lastPlayByPlayer = false;


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(Card c : bestPlay.first){
                    moveCard(c, pila.getX(), pila.getY(), true, true, duration);
                }
            }
        }, duration);
        return true;
    }

    public void makeAnnouncement(int idString){
        final ConstraintLayout game_layout = findViewById(R.id.game_layout);
        final View fondoOri = findViewById(R.id.escobaBackground);
        final TextView textoOri = findViewById(R.id.escobaText);

        final View fondo = new View(getApplicationContext());
        fondo.setId(View.generateViewId());
        fondo.setLayoutParams(fondoOri.getLayoutParams());
        fondo.setBackgroundColor(Color.parseColor("#FF000000"));


        final TextView texto = new TextView(getApplicationContext());
        texto.setId(View.generateViewId());
        texto.setLayoutParams(textoOri.getLayoutParams());
        texto.setTextColor(Color.parseColor("#FFAA00"));
        texto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
        texto.setTypeface(null, Typeface.BOLD);
        texto.setTextAlignment(textoOri.getTextAlignment());
        texto.setText(idString);

        fondo.setVisibility(View.VISIBLE);
        texto.setVisibility(View.VISIBLE);

        fondo.setAlpha(1f);
        texto.setAlpha(1f);

        game_layout.addView(fondo);
        game_layout.addView(texto);

        ObjectAnimator animFondo = ObjectAnimator.ofFloat(fondo, View.ALPHA, 0, 0.68f);

        ObjectAnimator animTextX = ObjectAnimator.ofFloat(texto, "scaleX", 10f, 1f);
        ObjectAnimator animTextY = ObjectAnimator.ofFloat(texto, "scaleY", 10f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animFondo, animTextX, animTextY);
        set.setDuration(duration/2);
        set.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animFondo = ObjectAnimator.ofFloat(fondo, View.ALPHA, 0.68f, 0f);

                ObjectAnimator animText = ObjectAnimator.ofFloat(texto, View.ALPHA, 1f, 0f);

                AnimatorSet set = new AnimatorSet();
                set.playTogether(animFondo, animText);
                set.setDuration(duration/2);
                set.start();
            }
        }, duration);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                game_layout.removeView(texto);
                game_layout.removeView(fondo);
            }
        }, duration*3/2);
    }



    public void rivalTurn(){
        ArrayList<Pair<ArrayList<Card>, Card>> allPlays = calculateAllPlays(listCenterCards, listRivalCards);

        Pair<ArrayList<Card>, Card> bestPlay = pickBestPlay(allPlays);
        if(bestPlay == null){
            Card cardToThrow = pickBestThrow();
            addCardToCenter(cardToThrow, false, true);
        }else{
            if(suman15(bestPlay.first) && bestPlay.second != null) {
                makePlay(bestPlay.first, bestPlay.second, false);
            }
        }
        guardarPartida(false);
        if(listPlayerCards.isEmpty() && listRivalCards.isEmpty()){

            if(numCarta >= 40){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishGame();
                    }
                }, duration);
            }else{
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reparteBaza();
                        guardarPartida(false);
                    }
                }, duration);
            }
        }
    }

    public ArrayList<Pair<ArrayList<Card>, Card>> calculateAllPlays(ArrayList<Card> listCenterCards, ArrayList<Card> listRivalCards){
        ArrayList<Pair<ArrayList<Card>, Card>> allPlays = new ArrayList<>();

        for(Card rivalCard : listRivalCards){
            ArrayList<Card> currentPlay = new ArrayList<>();
            currentPlay.add(rivalCard);
            recursivePlays(listCenterCards, 0, allPlays, rivalCard, currentPlay);
        }
        return allPlays;
    }

    public void recursivePlays(ArrayList<Card> listCenterCards, int iteration, ArrayList<Pair<ArrayList<Card>, Card>> allPlays, Card rivalCard, ArrayList<Card> currentPlay){
        if(iteration >= listCenterCards.size()) return;
        for(int i = iteration; i < listCenterCards.size(); i++){
            ArrayList<Card> copiaCurrentPlay = new ArrayList<>(currentPlay);
            copiaCurrentPlay.add(listCenterCards.get(i));
            allPlays.add(new Pair<>(copiaCurrentPlay, rivalCard));
            recursivePlays(listCenterCards, i+1, allPlays, rivalCard, copiaCurrentPlay);
        }
    }

    public Pair<ArrayList<Card>, Card> pickBestPlay(ArrayList<Pair<ArrayList<Card>, Card>> allPlays){
        Pair<ArrayList<Card>, Card> bestPlay = null;
        boolean firstPlay = true;
        for(Pair<ArrayList<Card>, Card> pair : allPlays){
            if(suman15(pair.first)) {
                if (firstPlay) {
                    bestPlay = pair;
                    firstPlay = false;
                } else {
                    if (comparePlays(pair, bestPlay)) {
                        bestPlay = pair;
                    }
                }
            }
        }
        return bestPlay;
    }

    //Return true si contender es mejor que king
    public boolean comparePlays(Pair<ArrayList<Card>, Card> contenderPair, Pair<ArrayList<Card>, Card> kingPair){
        ArrayList<Card> contender = contenderPair.first;
        ArrayList<Card> king = kingPair.first;
        if(esEscoba(contender) && !esEscoba(king)) return true;
        if(!esEscoba(contender) && esEscoba(king)) return false;
        if(hasSieteOros(contender) && !hasSieteOros(king)) return true;
        if(!hasSieteOros(contender) && hasSieteOros(king)) return false;
        if(numberOfSietes(contender) > numberOfSietes(king)) return true;
        if(numberOfSietes(contender) < numberOfSietes(king)) return false;
        if(numberOfOros(contender) > numberOfOros(king)) return true;
        if(numberOfOros(contender) < numberOfOros(king)) return false;
        if(contender.size() > king.size()) return true;
        if(contender.size() < king.size()) return false;
        return contenderPair.second.getPalo() != 1 && kingPair.second.getPalo() == 1;
    }

    public boolean esEscoba(ArrayList<Card> list){
        return list.size() - 1 == listCenterCards.size();
    }

    public boolean hasSieteOros(ArrayList<Card> list){
        for(Card card : list){
            if(card.getValor() == 7 && card.getPalo() == 1) return true;
        }
        return false;
    }

    public int numberOfSietes(ArrayList<Card> list){
        int num = 0;
        for(Card card : list){
            if(card.getValor() == 7) num++;
        }
        return num;
    }

    public int numberOfOros(ArrayList<Card> list){
        int num = 0;
        for(Card card : list){
            if(card.getPalo() == 1) num++;
        }
        return num;
    }

    public boolean suman15(ArrayList<Card> list){
        int num = 0;
        for(Card card : list){
            num += card.getValor();
        }
        return num == 15;
    }

    public int sumaValores(ArrayList<Card> list){
        int num = 0;
        for(Card card : list){
            num += card.getValor();
        }
        return num;
    }

    public Card pickBestThrow(){
        Card best = null;
        boolean first = true;
        for(Card card : listRivalCards){
            if(first){
                best = card;
                first = false;
            }else{
                if(compareCardsThrow(card, best)){
                    best = card;
                }
            }
        }
        return best;
    }

    public boolean compareCardsThrow(Card contender, Card king){
        if(sumaValores(listCenterCards) < 5){
            if(contender.getValor() + sumaValores(listCenterCards) < 5 && contender.getValor() > king.getValor()) return true;
            if(contender.getValor() + sumaValores(listCenterCards) < 5 && contender.getValor() < king.getValor()) return false;
        }

        if(contender.getValor() != 7 && king.getValor() == 7) return true;
        if(contender.getValor() == 7 && king.getValor() != 7) return false;
        if(contender.getValor() != 8 && king.getValor() == 8) return true;
        if(contender.getValor() == 8 && king.getValor() != 8) return false;

        if(sumaValores(listCenterCards) < 15){
            if(contender.getValor() + sumaValores(listCenterCards) > 15 && king.getValor() + sumaValores(listCenterCards) < 15) return true;
            if(contender.getValor() + sumaValores(listCenterCards) < 15 && king.getValor() + sumaValores(listCenterCards) > 15) return false;
        }

        return contender.getPalo() != 1 && king.getPalo() == 1;
    }

    public void finishGame(){
        ImageView pila;
        if(lastPlayByPlayer){
            pila = findViewById(R.id.pilaPlayer);
        }else{
            pila = findViewById(R.id.pilaRival);
        }
        for(Card card : listCenterCards){
            if(lastPlayByPlayer){
                pilaPlayerCards.add(card);
            }else{
                pilaRivalCards.add(card);
            }
            moveCard(card, pila.getX(), pila.getY(), true, true, duration);
        }
        listCenterCards.clear();
        guardarPartida(true);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                calcularResultados(false);
            }
        }, 1000);

    }


    public void calcularResultados(boolean esContinue){

        int puntosPlayer = escobasPlayer;
        int puntosRival = escobasRival;


        //Siete de oros
        int sieteOrosPlayer = 0;
        int sieteOrosRival = 0;

        //Sietes
        final int sietesPlayer = numberOfSietes(pilaPlayerCards);
        final int sietesRival = numberOfSietes(pilaRivalCards);


        //Oros
        final int orosPlayer = numberOfOros(pilaPlayerCards);
        final int orosRival = numberOfOros(pilaRivalCards);


        //Cartas
        final int cartasPlayer = pilaPlayerCards.size();
        final int cartasRival = pilaRivalCards.size();

        if(hasSieteOros(pilaPlayerCards)){
            sieteOrosPlayer = 1;
            puntosPlayer++;
        }else{
            sieteOrosRival = 1;
            puntosRival++;
        }


        if(sietesPlayer > sietesRival){
            puntosPlayer++;
        }
        if(sietesRival > sietesPlayer){
            puntosRival++;
        }


        if(orosPlayer > orosRival){
            puntosPlayer++;
        }
        if(orosRival > orosPlayer){
            puntosRival++;
        }


        if(cartasPlayer > cartasRival){
            puntosPlayer++;
        }
        if(cartasRival > cartasPlayer){
            puntosRival++;
        }

        //Total
        totalPlayer += puntosPlayer;
        totalRival += puntosRival;

        rellenarTablaPuntos(puntosPlayer, puntosRival, sieteOrosPlayer, sieteOrosRival, sietesPlayer, sietesRival, orosPlayer, orosRival, cartasPlayer, cartasRival);

        //El fallo está al ejecutar esto cada vez que continueGame()
        if(!esContinue){
            final int finalPuntosPlayer = puntosPlayer;
            final int finalSieteOrosPlayer = sieteOrosPlayer;
            final int finalPuntosRival = puntosRival;
            final int finalSieteOrosRival = sieteOrosRival;

            ExecutorService es = Executors.newFixedThreadPool(10);
            final JuegoDao juegoDao;
            juegoDao = db.juegoDao();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Juego juego = new Juego(finalPuntosPlayer, escobasPlayer, finalSieteOrosPlayer, sietesPlayer, orosPlayer, cartasPlayer, finalPuntosRival, escobasRival, finalSieteOrosRival, sietesRival, orosRival, cartasRival);
                    juegoDao.insert(juego);
                }
            };
            es.submit(task);
        }
    }

    public void rellenarTablaPuntos(int puntosPlayer, int puntosRival, int sieteOrosPlayer, int sieteOrosRival, int sietesPlayer, int sietesRival, int orosPlayer, int orosRival, int cartasPlayer, int cartasRival){
        (findViewById(R.id.cardResultados)).setVisibility(View.VISIBLE);


        //Escobas
        ((TextView)findViewById(R.id.escobasPlayer)).setText(String.valueOf(escobasPlayer));
        ((TextView)findViewById(R.id.escobasRival)).setText(String.valueOf(escobasRival));


        TextView sieteOrosPlayerText = (TextView)findViewById(R.id.sieteOrosPlayer);
        TextView sieteOrosRivalText = (TextView)findViewById(R.id.sieteOrosRival);
        sieteOrosPlayerText.setTextColor(Color.parseColor("#FFFFFF"));
        sieteOrosRivalText.setTextColor(Color.parseColor("#FFFFFF"));

        if(hasSieteOros(pilaPlayerCards)){
            sieteOrosPlayerText.setTextColor(Color.parseColor("#FFAA00"));
        }else{
            sieteOrosRivalText.setTextColor(Color.parseColor("#FFAA00"));
        }
        sieteOrosPlayerText.setText(String.valueOf(sieteOrosPlayer));
        sieteOrosRivalText.setText(String.valueOf(sieteOrosRival));


        TextView sietesPlayerText = (TextView)findViewById(R.id.sietesPlayer);
        TextView sietesRivalText = (TextView)findViewById(R.id.sietesRival);
        sietesPlayerText.setTextColor(Color.parseColor("#FFFFFF"));
        sietesRivalText.setTextColor(Color.parseColor("#FFFFFF"));

        if(sietesPlayer > sietesRival){
            sietesPlayerText.setTextColor(Color.parseColor("#FFAA00"));
        }
        if(sietesRival > sietesPlayer){
            sietesRivalText.setTextColor(Color.parseColor("#FFAA00"));
        }
        sietesPlayerText.setText(String.valueOf(sietesPlayer));
        sietesRivalText.setText(String.valueOf(sietesRival));


        TextView orosPlayerText = (TextView)findViewById(R.id.orosPlayer);
        TextView orosRivalText = (TextView)findViewById(R.id.orosRival);
        orosPlayerText.setTextColor(Color.parseColor("#FFFFFF"));
        orosRivalText.setTextColor(Color.parseColor("#FFFFFF"));

        if(orosPlayer > orosRival){
            orosPlayerText.setTextColor(Color.parseColor("#FFAA00"));
        }
        if(orosRival > orosPlayer){
            orosRivalText.setTextColor(Color.parseColor("#FFAA00"));
        }
        orosPlayerText.setText(String.valueOf(orosPlayer));
        orosRivalText.setText(String.valueOf(orosRival));

        TextView cartasPlayerText = (TextView)findViewById(R.id.numCartasPlayer);
        TextView cartasRivalText = (TextView)findViewById(R.id.numCartasRival);
        cartasPlayerText.setTextColor(Color.parseColor("#FFFFFF"));
        cartasRivalText.setTextColor(Color.parseColor("#FFFFFF"));

        if(cartasPlayer > cartasRival){
            cartasPlayerText.setTextColor(Color.parseColor("#FFAA00"));
        }
        if(cartasRival > cartasPlayer){
            cartasRivalText.setTextColor(Color.parseColor("#FFAA00"));
        }
        cartasPlayerText.setText(String.valueOf(cartasPlayer));
        cartasRivalText.setText(String.valueOf(cartasRival));

        //Puntos
        TextView puntosPlayerText = (TextView)findViewById(R.id.pointsPlayer);
        TextView puntosRivalText = (TextView)findViewById(R.id.pointsRival);

        puntosPlayerText.setText(String.valueOf(puntosPlayer));
        puntosRivalText.setText(String.valueOf(puntosRival));

        ((TextView)findViewById(R.id.totalPlayer)).setText(String.valueOf(totalPlayer));
        ((TextView)findViewById(R.id.totalRival)).setText(String.valueOf(totalRival));
    }

    public void winPlayer(){


        ((ImageView)findViewById(R.id.trophy)).setImageResource(R.drawable.card_1_1);
        ((TextView)findViewById(R.id.ganadorText)).setText(R.string.youWin);
        ((TextView)findViewById(R.id.marcadorFinal)).setText(totalPlayer + "-" + totalRival);

        (findViewById(R.id.cardGanador)).setVisibility(View.VISIBLE);
        writePartidaIntoDB();
        TinyDB tinydb = new TinyDB(getApplicationContext());
        tinydb.putBoolean("disableContinue", true);
    }

    public void winRival(){
        TableLayout tablaGanador = (TableLayout)findViewById(R.id.tablaGanador);

        ((ImageView)findViewById(R.id.trophy)).setImageResource(R.drawable.card_back);
        ((TextView)findViewById(R.id.ganadorText)).setText(R.string.youLose);
        ((TextView)findViewById(R.id.marcadorFinal)).setText(totalPlayer + "-" + totalRival);

        (findViewById(R.id.cardGanador)).setVisibility(View.VISIBLE);
        writePartidaIntoDB();
        TinyDB tinydb = new TinyDB(getApplicationContext());
        tinydb.putBoolean("disableContinue", true);
    }

    public void writePartidaIntoDB(){
        ExecutorService es = Executors.newFixedThreadPool(10);
        AppDatabase db;
        final PartidaDao partidaDao;
        db =  AppDatabase.getDatabase(getApplicationContext());
        partidaDao = db.partidaDao();
        Runnable task = new Runnable(){
            @Override
            public void run() {
                Partida partida = new Partida(totalPlayer, totalRival);
                partidaDao.insert(partida);
            }
        };
        es.submit(task);
    }

    public void guardarPartida(boolean finish){
        TinyDB tinydb = new TinyDB(getApplicationContext());

        tinydb.putInt("numCarta", numCarta);
        tinydb.putInt("escobasPlayer", escobasPlayer);
        tinydb.putInt("escobasRival", escobasRival);
        tinydb.putInt("totalPlayer", totalPlayer);
        tinydb.putInt("totalRival", totalRival);
        tinydb.putBoolean("lastPlayByPlayer", lastPlayByPlayer);
        tinydb.putListCard("listPlayerCards", convertListCardToSaved(listPlayerCards));
        tinydb.putListCard("listRivalCards", convertListCardToSaved(listRivalCards));
        tinydb.putListCard("pilaPlayerCards", convertListCardToSaved(pilaPlayerCards));
        tinydb.putListCard("pilaRivalCards", convertListCardToSaved(pilaRivalCards));
        tinydb.putListCard("listCenterCards", convertListCardToSaved(listCenterCards));
        tinydb.putListCard("baraja", convertListCardToSaved(baraja));
        tinydb.putBoolean("finish", finish);
        tinydb.putBoolean("disableContinue", false);
    }

    public ArrayList<SavedCard> convertListCardToSaved(ArrayList<Card> list){
        ArrayList<SavedCard> savedList = new ArrayList<SavedCard>();
        for(Card c : list){
            savedList.add(new SavedCard(c));
        }
        return savedList;
    }

    public ArrayList<Card> convertListSavedToCard(ArrayList<SavedCard> list){
        ArrayList<Card> cardList = new ArrayList<Card>();
        for(SavedCard c : list){
            cardList.add(c.convertToCard());
        }
        return cardList;
    }
}
