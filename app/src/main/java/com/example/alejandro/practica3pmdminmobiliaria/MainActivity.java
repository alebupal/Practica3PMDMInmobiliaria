package com.example.alejandro.practica3pmdminmobiliaria;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends Activity {

    private ArrayList<Inmueble> inmuebles;
    private ArrayList<Inmueble> datos = new ArrayList<Inmueble>();
    private AdaptadorArrayList ad;
    private final int ANADIR = 0;
    private String tipoNuevo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_anadir) {
            anadir();
        } else if (id == R.id.action_fecha) {
            //ordenarFecha();
        } else if (id == R.id.action_genero) {
           // ordenarGenero();
        } else if (id == R.id.action_nombre) {
           // ordernarNombre();
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        if (id == R.id.action_borrar) {
           borrar(index);
        } else if (id == R.id.action_editar) {
           editar(index);
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ANADIR) {
            int id;
            Double precio;
            String localidad, direccion, tipo;
            Bundle dsc = data.getExtras();

            id = dsc.getInt("id");
            precio = dsc.getDouble("precio");
            localidad = dsc.getString("localidad");
            direccion = dsc.getString("direccion");
            tipo = dsc.getString("tipo");

            datos.add(new Inmueble(id, precio,localidad,direccion,tipo));

            crearXML();
            visualizarInmuebles();
            ad.notifyDataSetChanged();
            tostada(getString(R.string.mensaje_anadir));
        }
    }


    /****************************************************/
    /*                                                  */
    /*               auxiliares                         */
    /*                                                  */
    /****************************************************/

    private void initComponents() {
        datos = new ArrayList<Inmueble>();
        leerXML();
    }

    private void tostada(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    public void visualizarInmuebles() {
        ad = new AdaptadorArrayList(this, R.layout.lista_detalle, datos);
        final ListView ls = (ListView) findViewById(R.id.listView);
        ls.setAdapter(ad);
        registerForContextMenu(ls);
    }
    /*public boolean comprueba(String titulo) {
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i).getTitulo().equalsIgnoreCase(titulo) == true) {
                return false;
            }
        }
        return true;
    }*/

    /****************************************************/
    /*                                                  */
    /*               metodos click                      */
    /*                                                  */
    /****************************************************/

    private void anadir() {
        Intent i = new Intent(this, Anadir.class);
        Bundle b = new Bundle();
        b.putParcelableArrayList("arraylist", datos);
        i.putExtras(b);
        startActivityForResult(i, ANADIR);
    }

    private boolean borrar(final int pos) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.tituloBorrar));
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                borrarXML(pos);
                Collections.sort(datos);
                tostada(getString(R.string.mensaje_eliminar));
                visualizarInmuebles();
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
        return true;
    }

    private boolean editar(final int index) {




        final int id;
        final Double precio;
        final String localidad, direccion, tipo;

        id = datos.get(index).getId();
        precio = datos.get(index).getPrecio();
        localidad = datos.get(index).getLocalidad();
        direccion = datos.get(index).getDireccion();
        tipo = datos.get(index).getTipo();

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.titulo_editar);

        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.editar, null);
        alert.setView(vista);

        final EditText etEditarPrecio, etEditarLocalidad, etEditarDireccion;
        final Spinner etEditarTipo;
        etEditarPrecio = (EditText) vista.findViewById(R.id.etEditarPrecio);
        etEditarLocalidad = (EditText) vista.findViewById(R.id.etEditarLocalidad);
        etEditarDireccion = (EditText) vista.findViewById(R.id.etEditarDireccion);

        etEditarTipo = (Spinner)vista.findViewById(R.id.spinnerEditarTipo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tipoInmueble, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etEditarTipo.setAdapter(adapter);


        etEditarPrecio.setText(precio+"");
        etEditarLocalidad.setText(localidad);
        etEditarDireccion.setText(direccion);
        Log.v("tipo",tipo);
        if (tipo.equals("Piso")) {
            etEditarTipo.setSelection(0);
        } else if (tipo.equals("Casa")) {
            etEditarTipo.setSelection(1);
        }
        etEditarTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                tipoNuevo= parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (etEditarPrecio.getText().toString().equals("") == true || etEditarLocalidad.getText().toString().equals("") == true || etEditarDireccion.getText().toString().equals("") == true) {
                    tostada(getString(R.string.vacio));
                }else {
                    Inmueble inmuebleAntiguo = new Inmueble(id,precio,localidad,direccion,tipo);
                    Inmueble inmuebleNuevo = new Inmueble(id,Double.parseDouble(etEditarPrecio.getText().toString()), etEditarLocalidad.getText().toString(), etEditarDireccion.getText().toString(),tipoNuevo);
                    Collections.sort(datos);
                    editarXML(inmuebleNuevo,inmuebleAntiguo);
                    ad.notifyDataSetChanged();
                    tostada(getString(R.string.mensaje_editar));
                    visualizarInmuebles();
                }

            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
        return true;
    }
    private boolean settings() {
        return true;
    }



    public void leerXML(){
        XML xml = new XML();
        datos = xml.leer(getApplicationContext());
        visualizarInmuebles();
    }
    public void crearXML(){
        XML xml = new XML();
        xml.crear(getApplicationContext(), datos);
        datos = xml.leer(getApplicationContext());
    }
    public void borrarXML(int pos){

        int id;
        Double precio;
        String localidad, direccion, tipo;

        id = datos.get(pos).getId();
        precio = datos.get(pos).getPrecio();
        localidad = datos.get(pos).getLocalidad();
        direccion = datos.get(pos).getDireccion();
        tipo = datos.get(pos).getTipo();

        Inmueble inmu = new Inmueble(id, precio, localidad,direccion,tipo);

        XML xml = new XML();
        xml.eliminar(getApplicationContext(), datos, inmu);

    }
    public void editarXML(Inmueble inmuebleNuevo, Inmueble inmuebleAntiguo){
        XML cxml = new XML();
        cxml.modificar(getApplicationContext(), datos, inmuebleNuevo, inmuebleAntiguo);
    }





}
