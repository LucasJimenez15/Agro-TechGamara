package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "empleado")
public class Empleado {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idEmpleado")
    private int idEmpleado;

    @ColumnInfo(name = "nomEmpleado")
    private String nomEmpleado;

    // Getters & Setters
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNomEmpleado() { return nomEmpleado; }
    public void setNomEmpleado(String nomEmpleado) { this.nomEmpleado = nomEmpleado; }
}
