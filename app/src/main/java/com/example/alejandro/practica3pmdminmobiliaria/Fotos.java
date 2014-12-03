package com.example.alejandro.practica3pmdminmobiliaria;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class Fotos extends Activity {

    private int id;
    private int posicion=0;
    private int IDACTIVIDADFOTO=2;
    private ArrayList<Bitmap> arrayFotos;
    String nombrefoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        id = getIntent().getExtras().getInt("id");
        Log.v("id", id+"");
        final FragmentoFotos ffotos = (FragmentoFotos)getFragmentManager().findFragmentById(R.id.fragmentoFotos);
        arrayFotos=new ArrayList<Bitmap>();
        arrayFotos=insertarFotos(arrayFotos);
        ffotos.setTexto(id+"");
        ffotos.primeraFoto(arrayFotos, 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fotos, menu);
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id",id);
        finish();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //s=savedInstanceState.getString("eres");
        id=savedInstanceState.getInt("id");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.anadirFoto) {
            foto();
        }

        return super.onOptionsItemSelected(item);
    }

    public void foto(){
        Intent i = new Intent ("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(i, IDACTIVIDADFOTO);
    }

    @Override
    public void onActivityResult(int pet, int res, Intent i) {
        if (res == RESULT_OK && pet == IDACTIVIDADFOTO) {
            Bitmap foto = (Bitmap)i.getExtras().get("data");
            FileOutputStream salida;
            try {
                String[] fecha=getFecha().split("-");
                nombrefoto="inmueble_"+id+"_"+fecha[0]+"_"+fecha[1]+"_"+fecha[2]+"_"+fecha[3]+"_"+fecha[4]+"_"+fecha[5];
                salida = new FileOutputStream(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/"+nombrefoto+".jpg");
                foto.compress(Bitmap.CompressFormat.JPEG, 90, salida);
            } catch (FileNotFoundException e) {
            }
        }
    }


    private String getFecha(){

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String formatteDate = df.format(date);
        return formatteDate;

    }

    public ArrayList<Bitmap> insertarFotos(ArrayList<Bitmap> arrayFotos){
        File carpetaFotos = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
        String[] archivosCarpetaFotos = carpetaFotos.list();
        Bitmap bm;
        for (int i=0;i<archivosCarpetaFotos.length;i++){
            if (archivosCarpetaFotos[i].indexOf("inmueble_"+id) != -1){
                bm = BitmapFactory.decodeFile(carpetaFotos.getAbsolutePath() + "/" + archivosCarpetaFotos[i]);
                arrayFotos.add(bm);
            }
        }
        return arrayFotos;
    }

    public void siguiente(View v){
        final FragmentoFotos ffotos = (FragmentoFotos)getFragmentManager().findFragmentById(R.id.fragmentoFotos);
        arrayFotos=new ArrayList<Bitmap>();
        arrayFotos=insertarFotos(arrayFotos);
        ffotos.setTexto(id+"");
        posicion++;
        Log.v("siguiente","boton");
        if(arrayFotos.size()==0){

        }else {
            if (posicion > arrayFotos.size() - 1) {
                posicion = arrayFotos.size() - 1;
                Log.v("posicion1", posicion + "");
                Log.v("tamaño1", arrayFotos.size() + "");
                ffotos.fotoSiguiente(arrayFotos, posicion);
            } else {
                Log.v("posicion2", posicion + "");
                Log.v("tamaño2", arrayFotos.size() + "");
                ffotos.fotoSiguiente(arrayFotos, posicion);
            }
        }
    }

   public void anterior(View v){
       final FragmentoFotos ffotos = (FragmentoFotos)getFragmentManager().findFragmentById(R.id.fragmentoFotos);
       arrayFotos=new ArrayList<Bitmap>();
       arrayFotos=insertarFotos(arrayFotos);
       ffotos.setTexto(id+"");
       posicion--;
       Log.v("boton","anterior");
       if(arrayFotos.size()==0){

       }else {
           if (posicion < 0) {
               posicion = 0;
               Log.v("posicion1", posicion + "");
               Log.v("tamaño1", arrayFotos.size() + "");
               ffotos.fotoSiguiente(arrayFotos, posicion);
           } else {
               Log.v("posicion2", posicion + "");
               Log.v("tamaño2", arrayFotos.size() + "");
               ffotos.fotoAnterior(arrayFotos, posicion);
           }
       }
    }
}
