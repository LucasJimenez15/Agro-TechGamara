package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "lotes",
        foreignKeys = @ForeignKey(
                entity = Ubicacion.class,
                parentColumns = "idUbicacion",
                childColumns = "ubiLote",
                onDelete = ForeignKey.SET_NULL
        ),
        indices = {@Index("idUbicacion")}
)
public class Lote {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idLote")
    private int idLote;

    @ColumnInfo(name = "nomLote")
    private String nomLote;

    @ColumnInfo(name = "ubiLote")
    private String ubiLote;

    @ColumnInfo(name = "cantHectLote")
    private Integer cantHectLote;

    // Getters & Setters
    public int getIdLote() { return idLote; }
    public void setIdLote(int idLote) { this.idLote = idLote; }

    public String getNomLote() { return nomLote; }
    public void setNomLote(String nomLote) { this.nomLote = nomLote; }

    public String getUbiLote() { return ubiLote; }
    public void setUbiLote(String ubiLote) { this.ubiLote = ubiLote; }

    public Integer getCantHectLote() { return cantHectLote; }
    public void setCantHectLote(Integer cantHectLote) { this.cantHectLote = cantHectLote; }

}
