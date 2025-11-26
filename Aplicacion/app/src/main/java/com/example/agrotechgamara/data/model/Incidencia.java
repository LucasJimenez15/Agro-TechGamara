package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "incidencia",
        foreignKeys = @ForeignKey(entity = Recordatorio.class, parentColumns = "idRecordatorio", childColumns = "idRecordatorio", onDelete = ForeignKey.SET_NULL),
        indices = {@Index("idRecordatorio")}
)
public class Incidencia {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idIncidencia")
    private int idIncidencia;

    @ColumnInfo(name = "nomIncidencia")
    private String nomIncidencia;

    @ColumnInfo(name = "descIncidencia")
    private String descIncidencia;

    @ColumnInfo(name = "fotosIncidencia", typeAffinity = ColumnInfo.BLOB)
    private byte[] fotosIncidencia;

    @ColumnInfo(name = "idRecordatorio")
    private int idRecordatorio;

    // Getters & Setters
    public int getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(int idIncidencia) { this.idIncidencia = idIncidencia; }

    public String getNomIncidencia() { return nomIncidencia; }
    public void setNomIncidencia(String nomIncidencia) { this.nomIncidencia = nomIncidencia; }

    public String getDescIncidencia() { return descIncidencia; }
    public void setDescIncidencia(String descIncidencia) { this.descIncidencia = descIncidencia; }

    public byte[] getFotosIncidencia() { return fotosIncidencia; }
    public void setFotosIncidencia(byte[] fotosIncidencia) { this.fotosIncidencia = fotosIncidencia; }

    public int getIdRecordatorio() { return idRecordatorio; }
    public void setIdRecordatorio(int idRecordatorio) { this.idRecordatorio = idRecordatorio; }
}
