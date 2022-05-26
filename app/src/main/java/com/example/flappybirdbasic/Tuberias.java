package com.example.flappybirdbasic;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Tuberias extends BaseObject {

    public static int velocidad;


    public Tuberias(float x, float y, int width, int height) {
        super(x, y , width, height);
        velocidad = 10 * Constrants.SCREEN_WIDTH/1000;
    }

    public void draw(Canvas canvas) {
        this.x-=velocidad;
        canvas.drawBitmap(this.bm, this.x, this.y, null);
    }

    public void aleatorioY() {
        Random r = new Random();
        this.y = r.nextInt((0+this.height/4)+1)-this.height/4;
    }

    @Override
    public void setBm(Bitmap bm) {
        this.bm = Bitmap.createScaledBitmap(bm, width, height, true);
    }
}
