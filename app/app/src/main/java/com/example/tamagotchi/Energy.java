package com.example.tamagotchi;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Energy extends Stats implements Parcelable {

    public Energy(){
        super();
    }

    public Energy(int i){
        super(i);
    }

    public static final Creator<Energy> CREATOR = new Creator<Energy>() {
        @Override
        public Energy createFromParcel(Parcel in) {
            int state = in.readInt();
            return new Energy(state);
        }

        @Override
        public Energy[] newArray(int size) {
            return new Energy[size];
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
