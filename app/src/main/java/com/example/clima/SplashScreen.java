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
        texto=b.getString("nombre");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(SplashScreen.this,Clima.class);
                Bundle b = new Bundle();
                b.putString("nombre", texto);
                intent.putExtras(b);
                startActivityForResult(intent, 1);
                finish();
            }
        },2500);
    }
}