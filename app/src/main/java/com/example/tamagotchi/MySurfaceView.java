package com.example.tamagotchi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class MySurfaceView extends SurfaceView implements Runnable{

    private Thread gameThread;
    private SurfaceHolder surfaceHolder;
    private RectF rectF;
    private Paint paint;
    private Paint background;
    private Paint text;
    private Paint bricksPaint;
    private RectF backgroundF;
    private Canvas canvas;
    private Ball ball = null;
    private Bricks bricks = null;
    private int WIDTH;
    private int HEIGHT;
    private int WIDTH_RACKET;
    private final int RADIUS;
    public float x;
    public float y;
    private int ballSpeed;
    public boolean pause = false;
    public boolean isRunning = false;
    public boolean gameLoop = true;
    /*private RectF [] bricks = {
            new RectF((WIDTH / 2) - ((WIDTH / 6) * 3), 0, (WIDTH / 2) - ((WIDTH / 6) * 3) + (WIDTH / 6), 100),
            new RectF((WIDTH / 2) - ((WIDTH / 6) * 2), 0, (WIDTH / 2) - ((WIDTH / 6) * 2) + (WIDTH / 6), 100),
            new RectF((WIDTH / 2) - ((WIDTH / 6) * 1), 0, (WIDTH / 2) - ((WIDTH / 6) * 1) + (WIDTH / 6), 100),
            //new RectF((WIDTH / 2) + ((WIDTH / 6) * 1), 0, (WIDTH / 2) + ((WIDTH / 6) * 1) + (WIDTH / 6), 100),
            //new RectF((WIDTH / 2) + ((WIDTH / 6) * 2), 0, (WIDTH / 2) + ((WIDTH / 6) * 2) + (WIDTH / 6), 100),
            //new RectF((WIDTH / 2) + ((WIDTH / 6) * 3), 0, (WIDTH / 2) + ((WIDTH / 6) * 3) + (WIDTH / 6), 100)
    };*/
    //private ArrayList<RectF> bricks;

    public MySurfaceView(Context context, int ballSpeed) {
        super(context);
        surfaceHolder = getHolder();
        setZOrderOnTop(true);
        setBackgroundColor(Color.WHITE);
        RADIUS = 50;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        rectF = new RectF();
        background = new Paint(Paint.ANTI_ALIAS_FLAG);
        background.setStyle(Paint.Style.STROKE);
        background.setColor(Color.BLACK);
        background.setStrokeWidth(20);
        bricksPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bricksPaint.setStyle(Paint.Style.STROKE);
        bricksPaint.setStrokeWidth(10);
        bricksPaint.setColor(Color.BLACK);
        backgroundF = new RectF();
        text = new Paint(Paint.ANTI_ALIAS_FLAG);
        text.setColor(Color.RED);
        text.setTextSize(60);
        this.ballSpeed = ballSpeed;
        /*for (int i = 1; i < 4; i++){
            bricks.add(new RectF((WIDTH / 2) - ((WIDTH / 6) * i), 0, (WIDTH / 2) - ((WIDTH / 6) * i) + (WIDTH / 6), 100));
        }
        for (int i = 1; i < 4; i++){
            bricks.add(new RectF((WIDTH / 2) + ((WIDTH / 6) * i), 0, (WIDTH / 2) + ((WIDTH / 6) * i) + (WIDTH / 6), 100));
        }*/
    }

    @Override
    public void run() {
        game();
    }
    public void stop (){
        gameLoop = false;
    }
    public void start (){
        gameThread = new Thread(this::run);
        gameThread.start();
    }
    public boolean isActive (){
        if (gameThread == null){
            return false;
        }
        else {
            return gameThread.isAlive();
        }
    }

    public synchronized void game(){
        while (gameLoop) {
            try {
                if (!isRunning) {
                    WIDTH = this.getWidth();
                    HEIGHT = this.getHeight();
                    WIDTH_RACKET = this.getWidth() / 5;
                    rectF.set(WIDTH / 2 - WIDTH_RACKET / 2, HEIGHT - 100, WIDTH / 2 + WIDTH_RACKET / 2, HEIGHT - 50);
                    bricks = new Bricks(WIDTH, HEIGHT, RADIUS);
                    ball = new Ball(WIDTH, HEIGHT, RADIUS, ballSpeed, bricks);
                    if (new Random().nextBoolean()){
                        ball.ballXspeed *= -1;
                    }
                    if (new Random().nextBoolean()){
                        ball.ballYspeed *= -1;
                    }
                } else {
                    rectF.set(x - WIDTH_RACKET / 2, HEIGHT - 100, x + WIDTH_RACKET / 2, HEIGHT - 50);
                    if (!ball.update(x)){
                        canvas = surfaceHolder.lockCanvas();
                        canvas.drawColor(Color.WHITE);
                        backgroundF.set(10, 10, WIDTH - 10, HEIGHT - 10);
                        canvas.drawRect(backgroundF, background);
                        canvas.drawText("Game Over", WIDTH / 2 - 140, HEIGHT / 2 - 50 , text);
                        canvas.drawText("Score: " + ball.getScore(), WIDTH / 2 - 110, HEIGHT / 2 + 50, text);
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        gameLoop = false;
                        break;
                    }
                }
                canvas = surfaceHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                backgroundF.set(10, 10, WIDTH - 10, HEIGHT - 10);
                canvas.drawRect(backgroundF, background);
                canvas.drawRoundRect(rectF, 10, 10, paint);
                canvas.drawOval(ball, paint);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    bricks.bricks.stream().filter(x -> x.getStatus() == 1).forEach(x -> canvas.drawRect(x, bricksPaint));
                }
                /*for (int i = 0; i < bricks.lenth; i++) {
                    if (!bricks.getBrick(i).status) continue;
                    canvas.drawRect(bricks.getBrick(i), bricksPaint);
                }*/
                //canvas.drawText("Score: " + ball.getScore() + "bricks.update = " + bricks.update(ball.getX(), ball.getY()), 150, 150, text);
                canvas.drawText("ballX = " + ball.getX() + " ballY = " + ball.getY(), 150, 150, text);
                canvas.drawText("brick = " + bricks.getBrickNow(ball.getX(), ball.getY()), 150, 200, text);
                canvas.drawText("ball.centerX = " + ball.getCenterX(), 150, 250, text);
                canvas.drawText("ball.centerY = " + ball.getCenterY(), 150, 300, text);
                surfaceHolder.unlockCanvasAndPost(canvas);
                while (pause){
                    canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.WHITE);
                    backgroundF.set(10, 10, WIDTH - 10, HEIGHT - 10);
                    canvas.drawRect(backgroundF, background);
                    canvas.drawRoundRect(rectF, 10, 10, paint);
                    canvas.drawOval(ball, paint);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        bricks.bricks.stream().filter(x -> x.getStatus() == 1).forEach(x -> canvas.drawRect(x, bricksPaint));
                    }
                    /*for (int i = 0; i < bricks.lenth; i++) {
                        if(!bricks.getBrick(i).status) continue;
                        canvas.drawRect(bricks.getBrick(i), bricksPaint);
                    }*/
                    //canvas.drawText("Score: " + ball.getScore(), 150, 150, text);
                    //canvas.drawText("Pause ", WIDTH / 2 - 75, HEIGHT / 2, text);
                    canvas.drawText("ballX = " + ball.getX() + " ballY = " + ball.getY(), 150, 150, text);
                    canvas.drawText("brick = " + bricks.getBrickNow(ball.getX(), ball.getY()), 150, 200, text);
                    canvas.drawText("ball.centerX = " + ball.getCenterX(), 150, 250, text);
                    canvas.drawText("ball.centerY = " + ball.getCenterY(), 150, 300, text);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }catch (Exception ex){
                //Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            try {
                gameThread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public int getScore(){
        return ball.getScore();
    }
}
