package com.example.johanaanesen.imt3673_lab03;

import android.view.View;
import android.widget.ImageView;

public class Ball {
    public ImageView ball;

    public float posX, posY, accX, accY, velX, velY, maxPosX, maxPosY;

    public Ball(View Ball, float maxX, float maxY) {
        this.maxPosX = maxX;
        this.maxPosY = maxY;

        this.accX = 0;
        this.accY = 0;
        this.velX = 0;
        this.velY = 0;
        this.posX = this.maxPosX / 2;
        this.posY = this.maxPosY / 2;

        this.ball = (ImageView) Ball;
        this.ball.setX(this.posX);
        this.ball.setY(this.posY);
    }
}
