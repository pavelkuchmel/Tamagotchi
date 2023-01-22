package com.example.tamagotchi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button btnOne;
    private Button btnTwo;
    private Button btnThree;
    private Button btnFour;
    TextView tvTest2;
    Tamagotchi tamagotchi;
    Timer timer;

    private final String PRESS_BACK_AGAIN = "Нажмите назад еще раз...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent intent = result.getData();
                            int score = intent.getIntExtra("ScorePong", 0);
                            if (tamagotchi.getMood() < 100){
                                if (tamagotchi.getMood() + score == 100){
                                    tamagotchi.setMood(100);
                                }
                                else {
                                    tamagotchi.setMood(tamagotchi.getMood() + score);
                                }
                            }
                            //btnTwo.setText(score+"");
                        }
                        else{
                            //textView.setText("Ошибка доступа");
                        }
                    }
                });

        btnOne = findViewById(R.id.btn_One);
        btnTwo = findViewById(R.id.btn_Two);
        btnThree = findViewById(R.id.btn_Three);
        btnFour = findViewById(R.id.btn_Four);

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

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayActivity.this, GamePong.class);
                mStartForResult.launch(intent);
            }
        });
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