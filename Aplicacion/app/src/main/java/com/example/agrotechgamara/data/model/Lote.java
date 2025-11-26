package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
      tableName = "lotes"//,
//        foreignKeys = @ForeignKey(
//                entity = Ubicacion.class,
//                parentColumns = "idUbicacion",
//                childColumns = "ubiLote",
//                onDelete = ForeignKey.SET_NULL
//        ),
//        indices = {@Index("idUbicacion")}
)

public class Lote {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idLote")
    private int idLote;

    @ColumnInfo(name = "nomLote")
    private String nomLote;

    @ColumnInfo(name = "ubiLote")
    private int ubiLote;

    @ColumnInfo(name = "cantHectLote")
    private int cantHectLote;

    // Getters & Setters
    public int getIdLote() { return idLote; }
    public void setIdLote(int idLote) { this.idLote = idLote; }

    public String getNomLote() { return nomLote; }
    public void setNomLote(String nomLote) { this.nomLote = nomLote; }

    public int getUbiLote() { return ubiLote; }
    public void setUbiLote(int ubiLote) { this.ubiLote = ubiLote; }

    public int getCantHectLote() { return cantHectLote; }
    public void setCantHectLote(int cantHectLote) { this.cantHectLote = cantHectLote; }

}
