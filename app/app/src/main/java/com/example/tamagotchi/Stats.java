package com.example.tamagotchi;

public abstract class Stats {
    private int state;

    public Stats(){
        state = 100;
    }

    public Stats(int i){
        state = i;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "state " + state;
    }
}
