package com.example.alejandro.practica3pmdminmobiliaria;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class FragmentoFotos extends Fragment {

    private View v;

    private ArrayList<Bitmap> arrayFotos;

    public FragmentoFotos(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fragmento_fotos, container, false);
        return v;
    }

    public void primeraFoto(ArrayList<Bitmap> arrayFotos,int pos){

        ImageView iv = (ImageView)v.findViewById(R.id.imageView);
        if(arrayFotos.size()==0){
            Drawable myDrawable = getResources().getDrawable(R.drawable.foto);
            iv.setImageDrawable(myDrawable);
        }else{
        iv.setImageBitmap(arrayFotos.get(pos));
        }
    }
    public void fotoSiguiente(ArrayList<Bitmap> arrayFotos,int pos){
            ImageView iv = (ImageView)v.findViewById(R.id.imageView);
            iv.setImageBitmap(arrayFotos.get(pos));
    }
    public void fotoAnterior(ArrayList<Bitmap> arrayFotos,int pos){
            ImageView iv = (ImageView)v.findViewById(R.id.imageView);
            iv.setImageBitmap(arrayFotos.get(pos));
    }


    public ArrayList<Bitmap> insertarFotos(ArrayList<Bitmap> arrayFotos,int posicion,ArrayList<Inmueble> datos,File cfotos){

        File carpetaFotos = cfotos;
        String[] archivosCarpetaFotos = carpetaFotos.list();
        arrayFotos=new ArrayList<Bitmap>();

        Inmueble in=datos.get(posicion);
        String id=""+in.getId();
        Bitmap bm;
        for (int i=0;i<archivosCarpetaFotos.length;i++){
            if (archivosCarpetaFotos[i].indexOf("inmueble_"+id) != -1){
               // Log.v("archivo",archivosCarpetaFotos[i]);
                bm = BitmapFactory.decodeFile(carpetaFotos.getAbsolutePath() + "/" + archivosCarpetaFotos[i]);
                //Log.v("bir",""+bm);
                arrayFotos.add(bm);
            }
        }
        return arrayFotos;
    }


}
