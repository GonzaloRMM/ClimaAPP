package com.example.clima;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        String dateString = sdf.format(date);

        ((TextView) item.findViewById(R.id.fecha)).setText("Fecha: "+dateString);
        ((TextView) item.findViewById(R.id.imagen)).setText("Tiempo: "+datos.get(0).get(0));
        ((TextView) item.findViewById(R.id.temperaturaMax)).setText("Temp Max: "+datos.get(0).get(1));
        ((TextView) item.findViewById(R.id.temperaturaMinima)).setText("Temp Min: "+datos.get(0).get(2));
        return item;
    }
}
