package com.example.johanaanesen.imt3673_lab03;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Ball ball;

    private SensorManager sensorManager;
    private Sensor sensor;

    private Vibrator vibrator;

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        this.ball.accX = -(sensorEvent.values[0] / 6);
        this.ball.accY = sensorEvent.values[1] / 6;

        this.ball.velX = this.ball.velX + this.ball.accX;
        this.ball.velY = this.ball.velY + this.ball.accY;

        //speed limiting for better response
        if (this.ball.velX > 15) {
            this.ball.velX = 15;
        }
        if (this.ball.velX < -15) {
            this.ball.velX = -15;
        }
        if (this.ball.velY > 15) {
            this.ball.velY = 15;
        }
        if (this.ball.velY < -15) {
            this.ball.velY = -15;
        }

        this.ball.posX = this.ball.posX + this.ball.velX;
        this.ball.posY = this.ball.posY + this.ball.velY;

        if (this.ball.posX < 0) {
            this.ball.posX = 0;
            this.ball.velX = -this.ball.velX * 0.8f;
            collision();
        }

        if (this.ball.posX > this.ball.maxPosX) {
            this.ball.posX = this.ball.maxPosX;
            this.ball.velX = -this.ball.velX * 0.8f;
            collision();
        }

        if (this.ball.posY < 0) {
            this.ball.posY = 0;
            this.ball.velY = -this.ball.velY * 0.8f;
            collision();
        }

        if (this.ball.posY > this.ball.maxPosY) {
            this.ball.posY = this.ball.maxPosY;
            this.ball.velY = -this.ball.velY * 0.8f;
            collision();
        }

        this.ball.ball.setX(this.ball.posX);
        this.ball.ball.setY(this.ball.posY);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void init() {

        FrameLayout frameLayoutBalance = (FrameLayout) findViewById(R.id.framey);
        frameLayoutBalance.setBackgroundColor(ContextCompat.getColor(this, R.color.black));

        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int offset = dpToPx(30);

        this.ball = new Ball(findViewById(R.id.ball), size.x - offset, size.y - getNavHeight() - dpToPx(18));


        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        this.mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.pop);
    }

    public void collision() {
        this.mediaPlayer.start();
        this.vibrator.vibrate(100);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public int getNavHeight() {
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
