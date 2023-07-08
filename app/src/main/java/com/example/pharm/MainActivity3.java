package com.example.pharm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity implements SensorEventListener {

    ImageButton menu;
    ImageButton shop;
    LinearLayout menuwidg;
    TextView myaccount;
    TextView myorder;
    TextView logout;
    ImageButton arrow;
    WebView promot;
    SensorManager sensorManager = null;
    Sensor mAcc;
    TextView count ;
    int steps =0;
    ImageButton cart;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        menu = findViewById(R.id.imageButton);
        menuwidg = findViewById(R.id.menu);
        myaccount = findViewById(R.id.myaccount);
        myorder = findViewById(R.id.myorder);
        logout = findViewById(R.id.logout);
        arrow = findViewById(R.id.imageButton4);
        promot = findViewById(R.id.webview);
        count = findViewById(R.id.textView2);
        shop = findViewById(R.id.imageButton2);
        cart = findViewById(R.id.imageButton3);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x_acceleration = event.values[0];
                float y_acceleration = event.values[1];
                float z_acceleration = event.values[2];
                double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
                double MagnitudeDelta = Magnitude;

                if (event.values[0] > 6){
                    steps++;
                }
                count.setText(""+steps);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener,mAcc,SensorManager.SENSOR_DELAY_NORMAL);
        count.setText(""+steps);
        promot.loadUrl("https://www.medicalnewstoday.com/");
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuwidg.setVisibility(View.VISIBLE);
                myaccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity3.this,MainActivity5.class);
                        startActivity(i);
                    }
                });

            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuwidg.setVisibility(View.INVISIBLE);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity3.this , MainActivity6.class);
                startActivity(i);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity3.this, cart.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}