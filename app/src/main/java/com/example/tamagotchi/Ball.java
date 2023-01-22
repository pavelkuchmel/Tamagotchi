package com.example.tamagotchi;

import android.graphics.RectF;

import java.util.Timer;

public class Ball extends RectF {
    private int score;
    private final int WIDTH;
    private final int WIDTH_RACKET;
    private final int HEIGHT;
    private final int RADIUS;
    public int ballXspeed;
    public int ballYspeed;
    private int ballXposs;
    private int ballYposs;
    private boolean check;
    private boolean ballXspeedX2;
    private Bricks bricks;

    public Ball(int width, int height, int radius, int ballSpeed, Bricks bricks) {
        super(width / 2 - radius / 2, height / 2 - radius / 2, width / 2 + radius / 2, height / 2 + radius / 2);
        WIDTH = width;
        WIDTH_RACKET = width / 5;
        HEIGHT = height;
        RADIUS = radius / 2;
        this.bricks = bricks;
        ballXspeed = ballSpeed;
        ballYspeed = ballSpeed;
        ballXposs = width / 2;
        ballYposs = height / 2;
        score = 0;
        check = true;
        ballXspeedX2 = false;
    }
    public boolean checkBallXspeedX2(boolean take){
        if (ballXspeedX2 && take) {
            if (ballXspeed > 0) ballXspeed -= 2;
            else ballXspeed += 2;
            ballXspeedX2 = false;
        }
        return take;
    }

    public boolean update(float x) {
        if (ballYposs < HEIGHT - 50 - RADIUS){
            if ((ballXposs < (RADIUS + 20) || ballXposs > WIDTH - 20 - RADIUS) && (ballYposs > (HEIGHT - 100 - RADIUS) && ballYposs < (HEIGHT - 100 - RADIUS + 50) && ballXposs >= (x - WIDTH_RACKET / 2) && ballXposs <= (x + WIDTH_RACKET / 2))) {
                ballXspeed *= -1;
                ballXspeed *= -1;
                check = false;
            } //возомжно надо будет удалить
            if (ballXposs < (20 + RADIUS) || ballXposs > (WIDTH - 20 - RADIUS) || checkBallXspeedX2(bricks.takeLeftRight(ballXposs, ballYposs))/*bricks.takeLeftRight(ballXposs, ballYposs)*/) {
                ballXspeed *= -1;
                check = true;
            }
            if (ballYposs < (20 + RADIUS) || checkBallXspeedX2(bricks.takeTopBottom(ballXposs, ballYposs))/*bricks.takeTopBottom(ballXposs, ballYposs)*/) {
                ballYspeed *= -1;
                check = true;
            }
            /*if (check && ballYposs > (HEIGHT - 100 - RADIUS) && ballYposs < (HEIGHT - 100 - RADIUS + 50) && ballXposs >= (x - WIDTH_RACKET / 2) && ballXposs <= x) {
                ballYspeed *= -1;
                if (ballYspeed < 0) {
                    ballYspeed--;
                }
                else {
                    ballYspeed++;
                }
                if (ballXspeed < 0){
                    ballXspeed--;
                }
                if (ballXspeed > 0){
                    ballXspeed++;
                    ballXspeed *= -1;
                }
                score++;
                check = false;
            }
            if (check && ballYposs > (HEIGHT - 100 - RADIUS) && ballYposs < (HEIGHT - 100 - RADIUS + 50) && ballXposs > x && ballXposs <= (x + WIDTH_RACKET / 2)) {
                ballYspeed *= -1;
                if (ballYspeed < 0) {
                    ballYspeed--;
                }
                else {
                    ballYspeed++;
                }
                if (ballXspeed > 0){
                    ballXspeed++;
                }
                if (ballXspeed < 0){
                    ballXspeed--;
                    ballXspeed *= -1;
                }
                score++;
                check = false;
            }*/
            //начало попытки изменения скорости Х
            if (check && ballYposs > (HEIGHT - 100 - RADIUS) && ballYposs < (HEIGHT - 100 - RADIUS + 50) && ballXposs >= (x - (WIDTH_RACKET / 4)) && ballXposs <= x) {
                ballYspeed *= -1;
                if (ballYspeed < 0) {
                    ballYspeed--;
                }
                else {
                    ballYspeed++;
                }
                if (ballXspeed < 0){
                    ballXspeed--;
                }
                if (ballXspeed > 0){
                    ballXspeed++;
                    ballXspeed *= -1;
                }
                score++;
                check = false;
            }
            if (check && ballYposs > (HEIGHT - 100 - RADIUS) && ballYposs < (HEIGHT - 100 - RADIUS + 50) && ballXposs > x && ballXposs <= (x + (WIDTH_RACKET / 4))) {
                ballYspeed *= -1;
                if (ballYspeed < 0) {
                    ballYspeed--;
                }
                else {
                    ballYspeed++;
                }
                if (ballXspeed > 0){
                    ballXspeed ++;
                }
                if (ballXspeed < 0){
                    ballXspeed --;
                    ballXspeed *= -1;
                }
                score++;
                check = false;
            }
            if (check && ballYposs > (HEIGHT - 100 - RADIUS) && ballYposs < (HEIGHT - 100 - RADIUS + 50) && ballXposs >= (x - WIDTH_RACKET / 2) && ballXposs < x - (WIDTH_RACKET / 4)) {
                ballYspeed *= -1;
                if (ballYspeed < 0) {
                    ballYspeed--;
                }
                else {
                    ballYspeed++;
                }
                if (ballXspeed < 0){
                    ballXspeed --;
                    ballXspeed -= 2;
                    ballXspeedX2 = true;
                }
                if (ballXspeed > 0){
                    ballXspeed ++;
                    ballXspeed += 2;
                    ballXspeed *= -1;
                    ballXspeedX2 = true;
                }
                score++;
                check = false;
            }
            if (check && ballYposs > (HEIGHT - 100 - RADIUS) && ballYposs < (HEIGHT - 100 - RADIUS + 50) && ballXposs > (x + (WIDTH_RACKET / 4)) && ballXposs <= (x + WIDTH_RACKET / 2)) {
                ballYspeed *= -1;
                if (ballYspeed < 0) {
                    ballYspeed--;
                }
                else {
                    ballYspeed++;
                }
                if (ballXspeed > 0){
                    ballXspeed ++;
                    ballXspeed += 2;
                    ballXspeedX2 = true;
                }
                if (ballXspeed < 0){
                    ballXspeed --;
                    ballXspeed -= 2;
                    ballXspeed *= -1;
                    ballXspeedX2 = true;
                }
                score++;
                check = false;
            }
            //конце попытки изменения скорости Х
        }
        if (ballYposs >= HEIGHT - 10 - RADIUS) {
            return false;
        }

        super.set(ballXposs - RADIUS, ballYposs - RADIUS, ballXposs + RADIUS, ballYposs + RADIUS);
        ballXposs += ballXspeed;
        ballYposs += ballYspeed;

        return true;
    }

    public int getScore() {
        return score;
    }

    public int getX(){
        return ballXposs;
    }

    public int getY(){
        return ballYposs;
    }

    public float getCenterX(){
        return super.centerX();
    }

    public float getCenterY(){
        return super.centerY();
    }

}