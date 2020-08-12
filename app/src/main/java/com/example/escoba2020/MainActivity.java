package com.example.escoba2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TinyDB tinydb = new TinyDB(getApplicationContext());
        boolean disableContinue = tinydb.getBoolean("disableContinue");
        if(disableContinue){
            ((Button)findViewById(R.id.continuarButton)).setEnabled(false);
        }else{
            ((Button)findViewById(R.id.continuarButton)).setEnabled(true);
        }
    }

    public void startGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("continue", false);
        startActivity(intent);
    }

    public void continueGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("continue", true);
        startActivity(intent);
    }

    public void startStats(View view){
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    public void flipCardMain(View view){
        int startRot = 0;
        int endRot = -180;



        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotationY", startRot, endRot);
        anim.setDuration(1000);
        anim.start();

    }
}
