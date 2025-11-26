package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "actividadEmpleado",
        primaryKeys = {"idEmpleado", "idActividad"}//,
//        foreignKeys = {
//                @ForeignKey(entity = Empleado.class, parentColumns = "idEmpleado", childColumns = "idEmpleado", onDelete = ForeignKey.CASCADE),
//                @ForeignKey(entity = Actividades.class, parentColumns = "idActividad", childColumns = "idActividad", onDelete = ForeignKey.CASCADE)
//        },
//        indices = {@Index("idEmpleado"), @Index("idActividad")}
)
public class ActividadEmpleado {

    @ColumnInfo(name = "idEmpleado")
    private int idEmpleado;

    @ColumnInfo(name = "idActividad")
    private int idActividad;

    // Getters & Setters
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public int getIdActividad() { return idActividad; }
    public void setIdActividad(int idActividad) { this.idActividad = idActividad; }
}
