package com.example.tamagotchi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class GamePong extends AppCompatActivity  implements View.OnTouchListener{

    public float x = 0;
    public float y = 0;
    private int ballSpeed = 1;
    private TextView tvView;
    private TextView tvMySurfaceIsActive;
    private TextView tvShowScore;
    private Button btnLeft;
    private Button btnRight;
    private Button btnStart;
    private Button btnSetting;
    private Button btnTestMysurfaceViewIsActive;
    private EditText edBallSpeed;
    private Button btnApply;
    private MySurfaceView mySurfaceView;
    private int exitCount;
    private GregorianCalendar exitControl;
    private int score;
    ConstraintLayout gameTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_pong);
        gameTable = findViewById(R.id.showPField);
        //gameTable.addView(mySurfaceView = new MySurfaceView(this));
        btnLeft = findViewById(R.id.btn_Pleft);
        btnLeft.setVisibility(View.GONE);
        btnRight = findViewById(R.id.btn_Pright);
        btnRight.setVisibility(View.GONE);
        btnStart = findViewById(R.id.btn_Pstart);
        btnSetting = findViewById(R.id.btn_Psetting);
        btnApply = findViewById(R.id.btn_Papply);
        btnTestMysurfaceViewIsActive = findViewById(R.id.btn_PtestMysurfaceViewIsActive);
        edBallSpeed = findViewById(R.id.ed_PballSpeed);
        tvView = findViewById(R.id.tv_Pview);
        tvMySurfaceIsActive = findViewById(R.id.tv_PmySurfaceIsActive);
        tvShowScore = findViewById(R.id.tv_PshowScore);
        exitCount = 0;
        score = 0;
        tvView.setOnTouchListener(this);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStart.setVisibility(View.GONE);
                btnSetting.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.VISIBLE);
                if (mySurfaceView != null) {
                    gameTable.removeAllViews();
                }
                //try {
                gameTable.addView(mySurfaceView = new MySurfaceView(GamePong.this, ballSpeed));
                mySurfaceView.start();
                /*}catch (Exception e){
                    System.out.println(e.getMessage());
                }*/
                //mySurfaceView.start();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStart.setVisibility(View.GONE);
                btnSetting.setVisibility(View.GONE);
                tvMySurfaceIsActive.setVisibility(View.GONE);
                btnTestMysurfaceViewIsActive.setVisibility(View.GONE);
                edBallSpeed.setVisibility(View.VISIBLE);
                btnApply.setVisibility(View.VISIBLE);
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (Integer.parseInt(edBallSpeed.getText().toString()) > 0 && Integer.parseInt(edBallSpeed.getText().toString()) < 21) {
                        ballSpeed = Integer.parseInt(edBallSpeed.getText().toString());
                        btnStart.setVisibility(View.VISIBLE);
                        btnSetting.setVisibility(View.VISIBLE);
                        edBallSpeed.setVisibility(View.GONE);
                        btnApply.setVisibility(View.GONE);
                        tvMySurfaceIsActive.setVisibility(View.VISIBLE);
                        btnTestMysurfaceViewIsActive.setVisibility(View.VISIBLE);
                    }
                    else {
                        Toast.makeText(GamePong.this, "Number is out of border (1 - 20)", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(GamePong.this, "You must enter only numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mySurfaceView != null) {
                    if (!mySurfaceView.isActive()){
                        gameTable.removeAllViews();
                        gameTable.addView(mySurfaceView = new MySurfaceView(GamePong.this, ballSpeed));
                        mySurfaceView.start();
                    }
                    else {
                        mySurfaceView.stop();
                        gameTable.removeAllViews();
                        gameTable.addView(mySurfaceView = new MySurfaceView(GamePong.this, ballSpeed));
                        mySurfaceView.start();
                    }
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mySurfaceView.pause) {
                    mySurfaceView.pause = true;
                    btnRight.setText("Сontinue");
                }
                else {
                    mySurfaceView.pause = false;
                    btnRight.setText("Pause");
                }
                //btnRight.setText(""+mySurfaceView.isActive());
            }
        });

        btnTestMysurfaceViewIsActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mySurfaceView == null) {
                    tvMySurfaceIsActive.setText("mySurfaceView == null ");
                }
                else {
                    tvMySurfaceIsActive.setText(mySurfaceView.gameLoop + "");
                }
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean up = false;
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            x = event.getX();
            y = event.getY();

            up = true;
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE){
            x = event.getX();
            y = event.getY();
            up = true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
            tvView.setText("Swipe here");
            up = false;

        }

        if (up) {
            if (mySurfaceView != null){
                mySurfaceView.isRunning = true;
                mySurfaceView.x = x;
            }
            tvView.setText("x = " + x/* + " y = " + y*/);
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if (mySurfaceView == null){
            /*if (exitCount == 0){
                Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                exitCount++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (exitCount != 0) exitCount = 0;
                    }
                }).start();
            }
            else {
                this.onDestroy();
            }*/
            Intent data = new Intent();
            data.putExtra("ScorePong", score);
            setResult(RESULT_OK, data);
            finish();
            //onDestroy();
        }
        else {
            if (mySurfaceView.isActive()) {
                if (exitCount == 0) {
                    Toast.makeText(GamePong.this, "Game is running. Press back again to exit", Toast.LENGTH_SHORT).show();
                    exitCount++;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (exitCount != 0) exitCount = 0;
                        }
                    }).start();
                } else {
                    exitGame();
                }
            } else {
                exitGame();
            }
        }
    }

    public void exitGame(){
        if (score == 0) {
            score = mySurfaceView.getScore();
        }
        else {
            score += mySurfaceView.getScore();
        }
        tvShowScore.setText(score + "");
        gameTable.removeView(mySurfaceView);
        btnStart.setVisibility(View.VISIBLE);
        btnSetting.setVisibility(View.VISIBLE);
        mySurfaceView.stop();
        mySurfaceView = null;
        exitCount = 0;
    }
}