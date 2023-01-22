package com.example.tamagotchi;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Satiety extends Stats implements Parcelable {
    public Satiety(){
        super();
    }

    public Satiety(int i){
        super(i);
    }

    public static final Creator<Satiety> CREATOR = new Creator<Satiety>() {
        @Override
        public Satiety createFromParcel(Parcel in) {
            int state = in.readInt();
            return new Satiety(state);
        }

        @Override
        public Satiety[] newArray(int size) {
            return new Satiety[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(super.getState());
    }
}
