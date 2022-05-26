package com.example.flappybirdbasic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {

    private Bird bird;
    private Handler handler;
    private Runnable r;
    private ArrayList<Tuberias> arrTuberias;
    private int sumaTuberia, distancia;
    private int score;
    private boolean iniciar = false;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        score = 0;
        iniciar = false;
        initBird();
        initTuberia();
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }
//Posicionar tuberias
    private void initTuberia() {
        sumaTuberia = 6;
        distancia = 300*Constrants.SCREEN_HEIGHT/1920;
        arrTuberias = new ArrayList<>();
        for (int i = 0; i < sumaTuberia; i++) {
            if(i < sumaTuberia/2) {
                this.arrTuberias.add(new Tuberias(Constrants.SCREEN_WIDTH+i*((Constrants.SCREEN_WIDTH+200*Constrants.SCREEN_WIDTH/1080)/(sumaTuberia/2)),
                            0, 200*Constrants.SCREEN_WIDTH/1080, Constrants.SCREEN_HEIGHT/2));
                this.arrTuberias.get(this.arrTuberias.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe2));
                this.arrTuberias.get(this.arrTuberias.size()-1).aleatorioY();
            }else {
                this.arrTuberias.add(new Tuberias(this.arrTuberias.get(i-sumaTuberia/2).getX(), this.arrTuberias.get(i-sumaTuberia/2).getY()
                +this.arrTuberias.get(i-sumaTuberia/2).getHeight() + this.distancia, 200*Constrants.SCREEN_WIDTH/1080, Constrants.SCREEN_HEIGHT/2));
                this.arrTuberias.get(this.arrTuberias.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe1));
            }
        }
    }

    private void initBird() {
        bird = new Bird();
        bird.setWidth(100*Constrants.SCREEN_WIDTH/1080);
        bird.setHeight(100*Constrants.SCREEN_HEIGHT/1920);
        bird.setX(100*Constrants.SCREEN_WIDTH/1080);
        bird.setY(Constrants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird2));
        bird.setArrBms(arrBms);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (iniciar) {
            bird.draw(canvas);
            for (int i = 0; i < sumaTuberia; i++) {
                if(bird.getRect().intersect(arrTuberias.get(i).getRect()) || bird.getY()-bird.getHeight() < 0 || bird.getY() > Constrants.SCREEN_HEIGHT) {
                    Tuberias.velocidad = 0;
                    MainActivity.txtScoreFin.setText(MainActivity.txtScore.getText());
                    MainActivity.rlFin.setVisibility(VISIBLE);
                }
                if (this.bird.getX()+this.bird.getWidth() > arrTuberias.get(i).getX() + arrTuberias.get(i).getWidth()/2
                        && this.bird.getX()+this.bird.getWidth() < -arrTuberias.get(i).getX() + arrTuberias.get(i).getWidth()/2 + Tuberias.velocidad
                        && i < sumaTuberia/2) {
                    score++;
                    MainActivity.txtScore.setText(""+score);
                }
                if (this.arrTuberias.get(i).getX() < -arrTuberias.get(i).getWidth()) {
                    this.arrTuberias.get(i).setX(Constrants.SCREEN_WIDTH);
                    if (i < sumaTuberia/2) {
                        arrTuberias.get(i).aleatorioY();
                    }else {
                        arrTuberias.get(i).setY(this.arrTuberias.get(i-sumaTuberia/2).getY()
                                +this.arrTuberias.get(i-sumaTuberia/2).getHeight() + this.distancia);
                    }
                }
                this.arrTuberias.get(i).draw(canvas);
            }
        }else {
            if (bird.getY() > Constrants.SCREEN_HEIGHT/2) {
                bird.setDrop(-15*Constrants.SCREEN_HEIGHT/1920);
            }
            bird.draw(canvas);
        }
        handler.postDelayed(r, 10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bird.setDrop(-15);
        }
        return true;
    }

    public boolean isIniciar() {
        return iniciar;
    }

    public void setIniciar(boolean iniciar) {
        this.iniciar = iniciar;
    }

    public void reset() {
        MainActivity.txtScore.setText("0");
        score = 0;
        initTuberia();
        initBird();
    }


}
