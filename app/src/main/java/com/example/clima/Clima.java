package com.example.clima;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class Clima extends AppCompatActivity {

    private ListView lv1;
    private TextView nombre;
    private Button volver;
    private String nombre_txt;
    ArrayList<ArrayList<String>> arrayB = new ArrayList<>();
    ArrayList<String>datos=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clima);

        lv1=(ListView)findViewById(R.id.lv_1);
        nombre=(TextView)findViewById(R.id.Nombre);
        volver=(Button)findViewById(R.id.volver_b);
        Bundle b= getIntent().getExtras();
        nombre_txt=b.getString("nombre");

        recogerDatos();
        asignarNombre(nombre);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayB.clear();
                finish();
            }
        });
    }
    private void asignarNombre(TextView nombre) {
        nombre.setText(nombre_txt.toUpperCase());
    }

    public void crearAdapter(ListView lv, ArrayList<ArrayList<String>> datos){
        Adaptardor adapter = new Adaptardor(this,datos);
        lv.setAdapter(adapter);
    }

    public void recogerDatos(){
        RequestQueue queue= Volley.newRequestQueue(Clima.this);
        String url="https://api.openweathermap.org/data/2.5/weather?q="+nombre_txt+"&appid=b6bf6be4dfd8477c040c44a4b99e6ec7";

        JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String cod=response.getString("cod");
                            if(cod.equals("404")){

                            }else{
                                JSONObject cityInfo = response.getJSONObject("main");
                                String tiempoActual ="Temperatura: " + (cityInfo.getInt("temp") - 273) + "ºC";
                                String tiempoMax="Temp Max: " + (cityInfo.getInt("temp_max") - 273) + "ºC";
                                String tiempoMin="Temp Min: " + (cityInfo.getInt("temp_min") - 273) + "ºC";
                                JSONArray weatherArray=response.getJSONArray("weather");
                                JSONObject array=weatherArray.getJSONObject(0);
                                String weather=array.getString("main");
                                datos.add(weather);
                                datos.add(tiempoActual);
                                datos.add(tiempoMax);
                                datos.add(tiempoMin);
                                arrayB.add(datos);
                                crearAdapter(lv1,arrayB);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error","error");
                datos.add("ERROR");
                datos.add("404");
                datos.add("CIUDAD");
                datos.add("ERRONEA");
                arrayB.add(datos);
                crearAdapter(lv1,arrayB);
            }
        });
        queue.add(jor);
    }
}