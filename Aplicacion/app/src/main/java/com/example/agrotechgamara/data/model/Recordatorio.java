package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recordatorio")
public class Recordatorio {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idRecordatorio")
    private int idRecordatorio;

    @ColumnInfo(name = "fechaActivacion")
    private String fechaActivacion; // timestamp guardado como String

    @ColumnInfo(name = "descRecordatorio")
    private String descRecordatorio;

    // Getters & Setters
    public int getIdRecordatorio() { return idRecordatorio; }
    public void setIdRecordatorio(int idRecordatorio) { this.idRecordatorio = idRecordatorio; }

    public String getFechaActivacion() { return fechaActivacion; }
    public void setFechaActivacion(String fechaActivacion) { this.fechaActivacion = fechaActivacion; }

    public String getDescRecordatorio() { return descRecordatorio; }
    public void setDescRecordatorio(String descRecordatorio) { this.descRecordatorio = descRecordatorio; }
}
