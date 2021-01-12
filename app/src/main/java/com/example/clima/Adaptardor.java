package com.example.clima;

import android.app.Activity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Adaptardor extends ArrayAdapter {
    private Activity context;
    private ArrayList<ArrayList<String>> datos;
    int fecha,contador=0;
    int descripcion=1;
    int temp=2;
    int tempMax=3;
    int tempMin=4;

    public Adaptardor(Activity context, ArrayList<ArrayList<String>> datos){
        super(context, R.layout.list_view, datos);
        this.context = context;
        this.datos = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View item = inflater.inflate(R.layout.list_view,null);


            long UTC_TIMEZONE=Long.parseLong(datos.get(0).get(fecha));
            String OUTPUT_DATE_FORMATE="dd/MM/yyyy";

            fecha=fecha+5;
        if(fecha>=35){
            fecha=0;
        }

            switch (datos.get(0).get(descripcion)){
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
        descripcion=descripcion+5;
        if(descripcion>=31){
            descripcion=1;
        }

            ((TextView) item.findViewById(R.id.fecha)).setText("Fecha: "+getDateFromUTCTimestamp(UTC_TIMEZONE,OUTPUT_DATE_FORMATE));
            ((TextView) item.findViewById(R.id.temperatura)).setText(datos.get(0).get(temp));
        temp=temp+5;
        if(temp>=32){
            temp=2;
        }
            ((TextView) item.findViewById(R.id.temperaturaMax)).setText(datos.get(0).get(tempMax));
        tempMax=tempMax+5;
        if(tempMax>=33){
            tempMax=3;
        }
            ((TextView) item.findViewById(R.id.temperaturaMinima)).setText(datos.get(0).get(tempMin));
        tempMin=tempMin+5;
        if(tempMin>=34){
            tempMin=4;
        }

        return item;
    }

    private String getDateFromUTCTimestamp(long utc_timezone, String output_date_formate) {
        String date = null;
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            cal.setTimeInMillis(utc_timezone * 1000L);
            date = DateFormat.format(output_date_formate, cal.getTimeInMillis()).toString();

            SimpleDateFormat formatter = new SimpleDateFormat(output_date_formate);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(date);

            SimpleDateFormat dateFormatter = new SimpleDateFormat(output_date_formate);
            dateFormatter.setTimeZone(TimeZone.getDefault());
            date = dateFormatter.format(value);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
