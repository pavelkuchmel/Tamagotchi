package com.example.tamagotchi;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.GregorianCalendar;

public class Timer extends Thread{

    private TextView time;

    public Timer(TextView textView){
        time = textView;
    }

    private void textChange(){
        time.setText(new GregorianCalendar().getTime().toString());
    }

    @Override
    public void run() {
        textChange();
    }

}
