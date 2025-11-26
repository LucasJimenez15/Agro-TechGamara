package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "actividades")
public class Actividades {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idActividad")
    private int idActividad;

    @ColumnInfo(name = "nombActividad")
    private String nombActividad;

    @ColumnInfo(name = "descActividad")
    private String descActividad;

    @ColumnInfo(name = "fechaActividad")
    private String fechaActividad;

    @ColumnInfo(name = "fotosActividad", typeAffinity = ColumnInfo.BLOB)
    private byte[] fotosActividad;

    @ColumnInfo(name = "idRecordatorio")
    private Integer idRecordatorio;

    // Getters & Setters
    public int getIdActividad() { return idActividad; }
    public void setIdActividad(int idActividad) { this.idActividad = idActividad; }

    public String getNombActividad() { return nombActividad; }
    public void setNombActividad(String nombActividad) { this.nombActividad = nombActividad; }

    public String getDescActividad() { return descActividad; }
    public void setDescActividad(String descActividad) { this.descActividad = descActividad; }

    public String getFechaActividad() { return fechaActividad; }
    public void setFechaActividad(String fechaActividad) { this.fechaActividad = fechaActividad; }

    public byte[] getFotosActividad() { return fotosActividad; }
    public void setFotosActividad(byte[] fotosActividad) { this.fotosActividad = fotosActividad; }

    public Integer getIdRecordatorio() { return idRecordatorio; }
    public void setIdRecordatorio(Integer idRecordatorio) { this.idRecordatorio = idRecordatorio; }
}
