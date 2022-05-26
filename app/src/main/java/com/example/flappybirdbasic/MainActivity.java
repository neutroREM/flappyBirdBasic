package com.example.flappybirdbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static TextView txtScore, txtScoreFin;
    public static RelativeLayout rlFin;
    private GameView gv;
    public static Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constrants.SCREEN_HEIGHT = dm.heightPixels;
        Constrants.SCREEN_WIDTH = dm.widthPixels;
        setContentView(R.layout.activity_main);
        txtScore = findViewById(R.id.txtScore);
        txtScoreFin = findViewById(R.id.txtScoreFin);
        btnIniciar = findViewById(R.id.btnIniciar);
        rlFin = findViewById(R.id.rlFin);
        gv = findViewById(R.id.gv);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gv.setIniciar(true);
                txtScore.setVisibility(View.VISIBLE);
                btnIniciar.setVisibility(View.INVISIBLE);
            }
        });

        rlFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIniciar.setVisibility(View.VISIBLE);
                rlFin.setVisibility(View.INVISIBLE);
                gv.setIniciar(false);
                gv.reset();
            }
        });
    }
}