package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
          tableName = "actividades"//,
//        foreignKeys = @ForeignKey(entity = Recordatorio.class,parentColumns = "idRecordatorio",childColumns = "idRecordatorio",onDelete = ForeignKey.SET_NULL)
)

public class Actividades {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idActividad")
    private int idActividad;

    @ColumnInfo(name = "nombActividad")
    private String nombActividad;

    @ColumnInfo(name = "descActividad")
    private String descActividad;

    @ColumnInfo(name = "fechaActividad")
    private Date fechaActividad;

    @ColumnInfo(name = "fotosActividad", typeAffinity = ColumnInfo.BLOB)
    private byte[] fotosActividad;

    @ColumnInfo(name = "idRecordatorio")
    private int idRecordatorio;

    // Getters & Setters
    public int getIdActividad() { return idActividad; }
    public void setIdActividad(int idActividad) { this.idActividad = idActividad; }

    public String getNombActividad() { return nombActividad; }
    public void setNombActividad(String nombActividad) { this.nombActividad = nombActividad; }

    public String getDescActividad() { return descActividad; }
    public void setDescActividad(String descActividad) { this.descActividad = descActividad; }

    public Date getFechaActividad() { return fechaActividad; }
    public void setFechaActividad(Date fechaActividad) { this.fechaActividad = fechaActividad; }

    public byte[] getFotosActividad() { return fotosActividad; }
    public void setFotosActividad(byte[] fotosActividad) { this.fotosActividad = fotosActividad; }

    public int getIdRecordatorio() { return idRecordatorio; }
    public void setIdRecordatorio(int idRecordatorio) { this.idRecordatorio = idRecordatorio; }
}
