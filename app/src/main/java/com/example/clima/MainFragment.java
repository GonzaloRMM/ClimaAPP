package com.example.clima;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private ListView lv1;
    private TextView nombre;
    private Button volver;
    private String nombre_txt;
    private String lon, lat="";

    String sTitle;

    ArrayList<String>datos=new ArrayList<String>();
    ArrayList<ArrayList<String>>arrayb=new ArrayList<ArrayList<String>>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        lv1=(ListView)view.findViewById(R.id.lv);
        sTitle=getArguments().getString("city");
        volver=(Button)view.findViewById(R.id.volver_b);
        datos=getArguments().getStringArrayList("datos");
        Log.i("fragment",datos+"");



        //arrayb.add(datos);
        //crearAdapter(lv1,arrayb);
        ArrayList<String>datosOrdenados=new ArrayList<String>();
        float num=0;
        for(int i=0;i<datos.size();i++){
            //Log.i("dato introducido",datos.get(i));d
            datosOrdenados.add(datos.get(i));
            num++;
            if(i>35){
                datos.clear();
                num=1;
            }
            else if(num%5==0.0){
                Log.i("datos ordendaos",datosOrdenados+"");
                arrayb.add(datosOrdenados);
                datosOrdenados.clear();
                //Log.i("arrayb",arrayb+"");
                Log.i("i",i+"");
            }
        }

        crearAdapter(lv1,arrayb);


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayb.clear();
                System.exit(0);
            }
        });
        return view;
    }
    public void crearAdapter(ListView lv, ArrayList<ArrayList<String>> datos){
        Adaptardor adapter = new Adaptardor(getActivity(),datos);
        lv.setAdapter(adapter);
    }
}