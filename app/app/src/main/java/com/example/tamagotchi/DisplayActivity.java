package com.example.tamagotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import org.w3c.dom.Text;

import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class DisplayActivity extends AppCompatActivity {

    private int doublePressBack;
    TextView tvTest2;
    Tamagotchi tamagotchi;
    Timer timer;

    private final String PRESS_BACK_AGAIN = "Нажмите назад еще раз...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        timer = new Timer();

        ImageView animGlasses = findViewById(R.id.iv_test2);
        animGlasses.setBackgroundResource(R.drawable.glasses_animation);
        AnimationDrawable frameAnimationGlass = (AnimationDrawable) animGlasses.getBackground();
        frameAnimationGlass.start();

        Bundle arguments = getIntent().getExtras();
        tamagotchi = null;
        if (arguments != null) {
            tamagotchi = arguments.getParcelable("Test");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tamagotchi.changeStats();
                                tvTest2.setText(new GregorianCalendar().getTime().toString() + '|' + tamagotchi.getState());
                                tvTest1.setText("Name: " + tamagotchi.getName() + "\nSatiety: " + tamagotchi.getSatiety() + "\nEnergy: " + tamagotchi.getEnergy() + "\nMood: " + tamagotchi.getMood() + "\nBornDate: " + tamagotchi.getBornDate().getTime());
                            }catch (Exception e){
                                Toast.makeText(DisplayActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            //tvTest2.setText(new GregorianCalendar().getTime().toString() + '|' + tamagotchi.getState());
                        }
                    });
                }
            }, 0, 1000);
        }
    }

    @Override
    public void onBackPressed() {
        if (doublePressBack == 1) {
            Intent data = new Intent();
            data.putExtra("TestResult", tamagotchi);
            setResult(RESULT_OK, data);
            timer.cancel();
            finish();
        }
        if (doublePressBack != 1) {
            Toast.makeText(DisplayActivity.this, PRESS_BACK_AGAIN, Toast.LENGTH_SHORT).show();
            doublePressBack++;
        }
    }
}