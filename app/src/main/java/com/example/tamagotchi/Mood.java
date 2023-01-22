package com.example.tamagotchi;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Mood extends Stats implements Parcelable {

    public Mood(){
        super();
    }

    public Mood(int i){
        super(i);
    }

    public static final Creator<Mood> CREATOR = new Creator<Mood>() {
        @Override
        public Mood createFromParcel(Parcel in) {
            int state = in.readInt();
            return new Mood(state);
        }

        @Override
        public Mood[] newArray(int size) {
            return new Mood[size];
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
