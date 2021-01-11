package com.example.clima;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class Clima extends AppCompatActivity {

    private ListView lv1;
    private TextView nombre;
    private Button volver;
    private String nombre_txt;
    private String lon, lat="";


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
            String url="https://api.openweathermap.org/data/2.5/weather?q="+nombre_txt+"&appid=c5e8291c1d20ea05cb1bd81589023f00";

            JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String cod=response.getString("cod");
                                if(cod.equals("404")){

                                }else{
                                    JSONObject coord = response.getJSONObject("coord");
                                    lon =(coord.getDouble("lon") + "");
                                    lat=(coord.getDouble("lat") + "");

                                    RequestQueue queue2= Volley.newRequestQueue(Clima.this);
                                    String url2="https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&exclude=hourly&appid=ef6268039205afa83791429e9d07bd50";

                                    JsonObjectRequest jor2=new JsonObjectRequest(Request.Method.GET, url2,null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response2) {
                                                    try {
                                                        JSONArray weather = response2.getJSONArray("daily");
                                                        for(int i=0;i<7;i++){
                                                            JSONObject array = weather.getJSONObject(i);
                                                            String dt=array.getInt("dt")+"";
                                                            JSONArray weatherArray=array.getJSONArray("weather");
                                                            JSONObject arrayWeather=weatherArray.getJSONObject(0);
                                                            String descripcion=arrayWeather.getString("main");
                                                            JSONObject temp=array.getJSONObject("temp");
                                                            String day="Temp: " + (temp.getInt("day") - 273) + "ºC";
                                                            String tempMax="Temp Max: " + (temp.getInt("max") - 273) + "ºC";
                                                            String tempMin="Temp Min: " + (temp.getInt("min") - 273) + "ºC";

                                                            datos.add(dt);
                                                            datos.add(descripcion);
                                                            datos.add(day);
                                                            datos.add(tempMax);
                                                            datos.add(tempMin);
                                                            arrayB.add(datos);
                                                        }
                                                        crearAdapter(lv1,arrayB);

                            /*
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
                            */
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
                                    queue.add(jor2);

                                    /*
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
                                     */
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