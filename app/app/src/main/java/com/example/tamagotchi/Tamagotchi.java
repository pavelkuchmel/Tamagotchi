package com.example.tamagotchi;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.GenericArrayType;
import java.util.*;

public class Tamagotchi implements Parcelable {

    private String name;
    private Satiety satiety;
    private Mood mood;
    private Energy energy;
    private GregorianCalendar bornDate;
    private GregorianCalendar saveDate;
    private int interval = 600000;

    public Tamagotchi(String name, Satiety satiety, Mood mood, Energy energy, GregorianCalendar bornDate, GregorianCalendar saveDate) {
        this.name = name;
        this.satiety = satiety;
        this.mood = mood;
        this.energy = energy;
        this.bornDate = bornDate;
        this.saveDate = saveDate;
    }

    public Tamagotchi(String name){
        this.name = name;
        energy = new Energy(80);
        mood = new Mood(76);
        satiety = new Satiety(87);
        bornDate = new GregorianCalendar();
        saveDate = new GregorianCalendar();
    }

    public void changeStats(){
        GregorianCalendar current = new GregorianCalendar();
        //int diff = (int) ((current.getTimeInMillis() - saveDate.getTimeInMillis()) / 600000);
        int diff = (int) ((current.getTimeInMillis() - saveDate.getTimeInMillis()) / 10000);
        if (diff != 0) saveDate = current;
        System.out.println("saveDate = " + saveDate.getTime());
        System.out.println("current = " + current.getTime());
        System.out.println("diff = " + diff);
        satiety.setState(satiety.getState() - diff);
        energy.setState(energy.getState() - diff);
        mood.setState(mood.getState() - diff);
        //saveDate = current;
    }


    public int getState(){
        List<Stats> arrayStats = new ArrayList<>(Arrays.asList(satiety, mood, energy));

        Collections.sort(arrayStats, new Comparator<Stats>() {
            @Override
            public int compare(Stats stats2, Stats stats1) {
                return Integer.compare(stats2.getState(), stats1.getState());
            }
        });

        /*if (arrayStats.get(0).getState() <= 0) return 666;
        else {
            if (arrayStats.get(0).getClass().toString().equals(satiety.getClass().toString()))
                return (100 + arrayStats.get(0).getState() / 10);
            else {
                if (arrayStats.get(0).getClass().toString().equals(energy.getClass().toString()))
                    return (200 + arrayStats.get(0).getState() / 10);
                else {
                        return (300 + arrayStats.get(0).getState() / 10);
                }
            }
        }*/

        int result = 0;

        switch (arrayStats.get(0).getClass().toString()){
            case "class com.example.tamagotchi.Satiety":
                result = 100 + arrayStats.get(0).getState() / 10;
                break;
            case "class com.example.tamagotchi.Mood":
                result = 200 + arrayStats.get(0).getState() / 10;
                break;
            case "class com.example.tamagotchi.Energy":
                result = 300 + arrayStats.get(0).getState() / 10;
                break;
        }

        return result;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public GregorianCalendar getBornDate() {
        return bornDate;
    }

    public GregorianCalendar getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(GregorianCalendar saveDate) {
        this.saveDate = saveDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSatiety() {
        return satiety.getState();
    }

    public void setSatiety(int satiety) {
        this.satiety.setState(satiety);
    }

    public int getMood() {
        return mood.getState();
    }

    public void setMood(int mood) {
        this.mood.setState(mood);
    }

    public int getEnergy() {
        return energy.getState();
    }

    public void setEnergy(int energy) {
        this.energy.setState(energy);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeValue(satiety);
        parcel.writeValue(mood);
        parcel.writeValue(energy);
        parcel.writeValue(bornDate);
        parcel.writeValue(saveDate);
    }

    public static final Creator<Tamagotchi> CREATOR = new Creator<Tamagotchi>() {
        @Override
        public Tamagotchi createFromParcel(Parcel parcel) {
            String name = parcel.readString();
            Satiety satiety = (Satiety) parcel.readValue(Satiety.class.getClassLoader());
            Mood mood = (Mood) parcel.readValue(Mood.class.getClassLoader());
            Energy energy = (Energy) parcel.readValue(Energy.class.getClassLoader());
            GregorianCalendar bornDate = (GregorianCalendar) parcel.readValue(GregorianCalendar.class.getClassLoader());
            GregorianCalendar saveDate = (GregorianCalendar) parcel.readValue(GregorianCalendar.class.getClassLoader());
            return new Tamagotchi(name, satiety, mood, energy, bornDate, saveDate);
        }

        @Override
        public Tamagotchi[] newArray(int i) {
            return new Tamagotchi[0];
        }
    };
}
