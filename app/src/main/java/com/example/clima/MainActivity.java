package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Intent myIntent;
    private Button boton;
    private EditText caja;
    private ArrayList<String>ciudades=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton=(Button)findViewById(R.id.boton1);
        caja=(EditText)findViewById(R.id.caja1);
        myIntent = new Intent(this, com.example.clima.SplashScreen.class);

        rellenarArray();
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String caja_txt=caja.getText().toString();
                Bundle b = new Bundle();
                b.putString("nombre", caja_txt);
                myIntent.putExtras(b);
                startActivityForResult(myIntent, 1);
                caja.setText("");
                /*
                for(int j=0;j<ciudades.size();j++){
                    if(caja_txt.equals(ciudades.get(j))){
                        Bundle b = new Bundle();
                        b.putString("nombre", caja_txt);
                        myIntent.putExtras(b);
                        startActivityForResult(myIntent, 1);
                    }else{
                        caja.setText("");
                    }
                }

                 */
            }
        });
    }

    private void rellenarArray() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ciudades);
        for(int i=0;i<ta.length();i++){
            ciudades.add(ta.getString(i));
        }
    }
}