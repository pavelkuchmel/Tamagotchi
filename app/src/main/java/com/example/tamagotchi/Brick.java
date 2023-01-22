package com.example.tamagotchi;

import android.graphics.RectF;

public class Brick extends RectF {

    boolean status;

    public Brick(int left, int top, int right, int bottom){
        super((float) left, (float) top, (float) right, (float) bottom);
        status = true;
    }

    public int getStatus(){
        int result = 0;
        if (status){
            result = 1;
        }
        return result;
    }

}
