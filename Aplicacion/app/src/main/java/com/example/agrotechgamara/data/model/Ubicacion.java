package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ubicacion")
public class Ubicacion {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idUbicacion")
    private int idUbicacion;

    @ColumnInfo(name = "latitud")
    private float latitud;

    @ColumnInfo(name = "longitud")
    private float longitud;

    // Getters & Setters
    public int getIdUbicacion() { return idUbicacion; }
    public void setIdUbicacion(int idUbicacion) { this.idUbicacion = idUbicacion; }

    public float getLatitud() { return latitud; }
    public void setLatitud(float latitud) { this.latitud = latitud; }

    public float getLongitud() { return longitud; }
    public void setLongitud(float longitud) { this.longitud = longitud; }
}
