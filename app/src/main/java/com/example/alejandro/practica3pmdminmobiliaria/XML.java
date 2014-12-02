package com.example.alejandro.practica3pmdminmobiliaria;


import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alejandro on 02/12/2014.
 */
public class XML {

    public static void crear(Context contexto, ArrayList<Inmueble> lista){
        try{
            FileOutputStream fosxml = new FileOutputStream(new File(contexto.getExternalFilesDir(null), "inmuebles.xml"));

            XmlSerializer docxml= Xml.newSerializer();
            docxml.setOutput(fosxml, "UTF-8");
            docxml.startDocument(null, Boolean.valueOf(true));
            docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            docxml.startTag(null, "inmobiliaria");
            for(int i = 0; i<lista.size();i++){
                docxml.startTag(null, "inmueble");
                    docxml.attribute(null, "id", String.valueOf(lista.get(i).getId()));
                    docxml.attribute(null, "precio", String.valueOf(lista.get(i).getPrecio()));
                    docxml.attribute(null, "localidad", lista.get(i).getLocalidad());
                    docxml.attribute(null, "direccion", lista.get(i).getDireccion());
                    docxml.attribute(null, "tipo", lista.get(i).getTipo());
                docxml.endTag(null, "inmueble");
            }
            docxml.endTag(null, "inmobiliaria");
            docxml.endDocument();
            docxml.flush();
            fosxml.close();
        }catch(Exception e){
            System.out.println("ERROR AL ESCRIBIR");
        }
    }

    public static void eliminar(Context con, ArrayList<Inmueble> lista , Inmueble in){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).equals(in)){
                lista.remove(i);
                crear(con, lista);
                Collections.sort(lista);
                break;
            }
        }

    }

    public static void modificar(Context con, ArrayList<Inmueble> lista, Inmueble inmuebleNuevo, Inmueble inmuebleAntiguo){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).equals(inmuebleAntiguo)){
                lista.remove(i);
                lista.add(inmuebleNuevo);
                crear(con, lista);
                Collections.sort(lista);
                break;
            }
        }
    }

    public static ArrayList<Inmueble> leer(Context contexto){
        ArrayList<Inmueble> datos = new ArrayList<Inmueble>();
        try{
            XmlPullParser lectorxml= Xml.newPullParser();
            lectorxml.setInput(new FileInputStream(new File(contexto.getExternalFilesDir(null), "inmuebles.xml")), "utf-8");
            int evento = lectorxml.getEventType();
            while(evento!=XmlPullParser.END_DOCUMENT){
                if(evento==XmlPullParser.START_TAG){
                    String etiqueta = lectorxml.getName();
                    if(etiqueta.compareTo("inmueble")==0){
                        Log.v("adasd","");
                        datos.add(new Inmueble(Integer.parseInt(lectorxml.getAttributeValue(null, "id")),
                                Double.valueOf(lectorxml.getAttributeValue(null, "precio")),
                                lectorxml.getAttributeValue(null, "localidad"),
                                lectorxml.getAttributeValue(null, "direccion"),
                                lectorxml.getAttributeValue(null, "tipo")
                        ));
                    }
                }
                evento = lectorxml.next();
            }

        }catch (Exception e) {
            System.out.println("Error al leer");
        }
        if(datos.size()!=0){
            return datos;
        }else{
            return new ArrayList<Inmueble>();
        }
    }

}
