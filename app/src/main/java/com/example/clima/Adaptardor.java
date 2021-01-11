package com.example.clima;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Adaptardor extends ArrayAdapter {
    private Activity context;
    private ArrayList<ArrayList<String>> datos;

    public Adaptardor(Activity context, ArrayList<ArrayList<String>> datos){
        super(context, R.layout.list_view, datos);
        this.context = context;
        this.datos = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View item = inflater.inflate(R.layout.list_view,null);
        long date = System.currentTimeMillis();

        SimpleDateFormat actualDate = new SimpleDateFormat("MMM dd");
        String dateString = actualDate.format(date);
 
        ((TextView) item.findViewById(R.id.fecha)).setText("Fecha: "+datos.get(position).get(0));
        ((TextView) item.findViewById(R.id.temperatura)).setText(datos.get(position).get(2));
        ((TextView) item.findViewById(R.id.temperaturaMax)).setText(datos.get(position).get(3));
        ((TextView) item.findViewById(R.id.temperaturaMinima)).setText(datos.get(position).get(4));
        switch (datos.get(position).get(1)){
            case "Clouds":
                ((ImageView)item.findViewById(R.id.icono)).setImageResource(R.drawable.nublado);
                break;
            case "Clear":
                ((ImageView)item.findViewById(R.id.icono)).setImageResource(R.drawable.soleado);
                break;
            case "Snow":
                ((ImageView)item.findViewById(R.id.icono)).setImageResource(R.drawable.nieve);
                break;
            case "Rain":
                ((ImageView)item.findViewById(R.id.icono)).setImageResource(R.drawable.lluvia);
                break;
        }
        return item;
    }
}
