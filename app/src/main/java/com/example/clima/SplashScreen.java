package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    private String texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Bundle b= getIntent().getExtras();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2500);
    }
}