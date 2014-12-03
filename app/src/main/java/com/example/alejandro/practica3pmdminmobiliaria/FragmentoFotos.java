package com.example.alejandro.practica3pmdminmobiliaria;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentoFotos extends Fragment {

    private View v;

    public FragmentoFotos(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fragmento_fotos, container, false);
        return v;
    }

    public void setTexto(String s){
        TextView tv = (TextView)v.findViewById(R.id.tvFragmentoFotos);
        tv.setText(s);
    }


}
