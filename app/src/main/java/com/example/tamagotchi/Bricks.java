package com.example.tamagotchi;

import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Bricks {
    private int WIDTH;
    private int HEIGHT;
    private final int RADIUS;
    volatile List<Brick> bricks;
    //private Rect[] bricks;
    public final int lenth;

    public Bricks(int width, int height, int radius) {
        WIDTH = width;
        HEIGHT = height;
        RADIUS = radius;
        bricks = new ArrayList<>(Arrays.asList(new Brick((WIDTH / 2) - ((WIDTH / 6) * 3), 0, (WIDTH / 2) - ((WIDTH / 6) * 3) + (WIDTH / 6), HEIGHT / 20),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 2), 0, (WIDTH / 2) - ((WIDTH / 6) * 2) + (WIDTH / 6), HEIGHT / 20),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 1), 0, (WIDTH / 2) - ((WIDTH / 6) * 1) + (WIDTH / 6), HEIGHT / 20),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 1), 0, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 1) + (WIDTH / 6), HEIGHT / 20),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 2), 0, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 2) + (WIDTH / 6), HEIGHT / 20),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 3), 0, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 3) + (WIDTH / 6), HEIGHT / 20),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 3), HEIGHT / 20, (WIDTH / 2) - ((WIDTH / 6) * 3) + (WIDTH / 6), (HEIGHT / 20) * 2),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 2), HEIGHT / 20, (WIDTH / 2) - ((WIDTH / 6) * 2) + (WIDTH / 6), (HEIGHT / 20) * 2),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 1), HEIGHT / 20, (WIDTH / 2) - ((WIDTH / 6) * 1) + (WIDTH / 6), (HEIGHT / 20) * 2),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 1), HEIGHT / 20, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 1) + (WIDTH / 6), (HEIGHT / 20) * 2),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 2), HEIGHT / 20, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 2) + (WIDTH / 6), (HEIGHT / 20) * 2),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 3), HEIGHT / 20, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 3) + (WIDTH / 6), (HEIGHT / 20) * 2),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 3), (HEIGHT / 20) * 2, (WIDTH / 2) - ((WIDTH / 6) * 3) + (WIDTH / 6), (HEIGHT / 20) * 3),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 2), (HEIGHT / 20) * 2, (WIDTH / 2) - ((WIDTH / 6) * 2) + (WIDTH / 6), (HEIGHT / 20) * 3),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 1), (HEIGHT / 20) * 2, (WIDTH / 2) - ((WIDTH / 6) * 1) + (WIDTH / 6), (HEIGHT / 20) * 3),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 1), (HEIGHT / 20) * 2, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 1) + (WIDTH / 6), (HEIGHT / 20) * 3),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 2), (HEIGHT / 20) * 2, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 2) + (WIDTH / 6), (HEIGHT / 20) * 3),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 3), (HEIGHT / 20) * 2, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 3) + (WIDTH / 6), (HEIGHT / 20) * 3),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 3), (HEIGHT / 20) * 3, (WIDTH / 2) - ((WIDTH / 6) * 3) + (WIDTH / 6), (HEIGHT / 20) * 4),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 2), (HEIGHT / 20) * 3, (WIDTH / 2) - ((WIDTH / 6) * 2) + (WIDTH / 6), (HEIGHT / 20) * 4),
                new Brick((WIDTH / 2) - ((WIDTH / 6) * 1), (HEIGHT / 20) * 3, (WIDTH / 2) - ((WIDTH / 6) * 1) + (WIDTH / 6), (HEIGHT / 20) * 4),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 1), (HEIGHT / 20) * 3, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 1) + (WIDTH / 6), (HEIGHT / 20) * 4),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 2), (HEIGHT / 20) * 3, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 2) + (WIDTH / 6), (HEIGHT / 20) * 4),
                new Brick((WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 3), (HEIGHT / 20) * 3, (WIDTH / 2) - (WIDTH / 6) + ((WIDTH / 6) * 3) + (WIDTH / 6), (HEIGHT / 20) * 4)));
        lenth = bricks.size();
    }

    public Brick getBrick(int number){
        return bricks.get(number);
    }

    public boolean takeLeftRight(int x, int y){
        for (int i = 0; i < bricks.size(); i++){
            if (y >= bricks.get(i).top && y <= bricks.get(i).bottom) {
                if (x >= bricks.get(i).left - RADIUS / 2 && x <= bricks.get(i).right + RADIUS / 2) {
                    if (bricks.get(i).status) {
                        int finalI = i;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                bricks.get(finalI).status = false;
                            }
                        }).start();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean takeTopBottom(int x, int y){
        for (int i = 0; i < bricks.size(); i++){
            if (x >= bricks.get(i).left && x <= bricks.get(i).right) {
                if (y <= bricks.get(i).bottom + RADIUS / 2 && y >= bricks.get(i).top) {
                    if (bricks.get(i).status) {
                        int finalI = i;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                bricks.get(finalI).status = false;
                            }
                        }).start();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getBrickNow(int x, int y){
        for (int i = 0; i < bricks.size(); i++){
            if (x >= bricks.get(i).left && x <= bricks.get(i).right){
                return i + 1;
            }
        }
        return 0;
    }

    public void remove(int x, int y){
        for (int i = 0; i < bricks.size(); i++){
            if (x >= bricks.get(i).left + RADIUS / 2 && x <= bricks.get(i).right + RADIUS / 2) {
                if (y <= bricks.get(i).bottom + RADIUS / 2) {
                    if (bricks.get(i).status) {
                        bricks.get(i).status = false;
                    }
                }
            }
        }
    }
}