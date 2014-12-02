package com.example.alejandro.practica3pmdminmobiliaria;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alejandro on 02/12/2014.
 */
public class Inmueble implements Comparable<Inmueble>,Parcelable {

    private int id;
    private double precio;
    private String localidad, direccion, tipo;


    public Inmueble(int id, double precio, String localidad, String direccion, String tipo) {
        this.id = id;
        this.precio = precio;
        this.localidad = localidad;
        this.direccion = direccion;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Inmueble.class != o.getClass()) return false;

        Inmueble inmueble = (Inmueble) o;

        if (id != inmueble.id) return false;
        if (Double.compare(inmueble.precio, precio) != 0) return false;
        if (direccion != null ? !direccion.equals(inmueble.direccion) : inmueble.direccion != null)
            return false;
        if (localidad != null ? !localidad.equals(inmueble.localidad) : inmueble.localidad != null)
            return false;
        if (tipo != null ? !tipo.equals(inmueble.tipo) : inmueble.tipo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(precio);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (localidad != null ? localidad.hashCode() : 0);
        result = 31 * result + (direccion != null ? direccion.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "id=" + id +
                ", precio=" + precio +
                ", localidad='" + localidad + '\'' +
                ", direccion='" + direccion + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }

    @Override
    public int compareTo(Inmueble another) {
        return this.getLocalidad().compareTo(another.getLocalidad());
    }

    // ==================== Parcelable ====================
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.id);
        parcel.writeDouble(this.precio);
        parcel.writeString(this.localidad);
        parcel.writeString(this.direccion);
        parcel.writeString(this.tipo);
    }

    private Inmueble(Parcel in) {
        this.id = in.readInt();
        this.precio = in.readDouble();
        this.localidad = in.readString();
        this.direccion = in.readString();
        this.tipo = in.readString();
    }

    public static final Parcelable.Creator<Inmueble> CREATOR = new Parcelable.Creator<Inmueble>() {
        public Inmueble createFromParcel(Parcel in) {
            return new Inmueble(in);
        }

        public Inmueble[] newArray(int size) {
            return new Inmueble[size];
        }
    };


}
