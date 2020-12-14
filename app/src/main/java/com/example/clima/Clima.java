package com.example.clima;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
//import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Clima extends AppCompatActivity {
/*
     class clima extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url =new URL(strings[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
//                connection.connect();

                InputStream is=connection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);

                int data=isr.read();
                String content="";
                char ch;
                while (data!=0){
                    ch=(char)data;
                    content=content + ch;
                    data=isr.read();
                }
//                Log.i("Content",content);
                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
 */
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

/*
        clima weather=new clima();
        try{
            String content=weather.execute("http://api.openweathermap.org/data/2.5/weather?q=London&appid=b6bf6be4dfd8477c040c44a4b99e6ec7").get();
            Log.i("contentData",content);
        }catch (Exception e){
            e.printStackTrace();
        }
 */
        asignarNombre(nombre);
        crearAdapter(lv1,recogerDatos());
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public ArrayList<ArrayList<String>> recogerDatos(){
/*
        datos.add("clima");
        datos.add("temMax");
        datos.add("temMin");
        arrayB.add(datos);
 */
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="http://api.openweathermap.org/data/2.5/weather?q=Granada&appid=b6bf6be4dfd8477c040c44a4b99e6ec7";
        JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array=response.getJSONArray("weather");
                            String tiempo="";
                            for(int i=0;i<array.length();i++){
                                JSONObject object=(JSONObject)array.get(i);
                                tiempo+=object.getString("main");

                                Log.i("ID", "ID: " + object.getString("id"));
                                Log.i("MAIN", "MAIN: " + object.getString("main"));
                            }

                            JSONObject main_object= response.getJSONObject("main");
                            String tempMax=main_object.getDouble("temp_max")+"";
                            String tempMin=main_object.getDouble("temp_min")+"";

                            datos.add(tiempo);
                            datos.add(tempMax);
                            datos.add(tempMin);
                            arrayB.add(datos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jor);
        return arrayB;
    }
}