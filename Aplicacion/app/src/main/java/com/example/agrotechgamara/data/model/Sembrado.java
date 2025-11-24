package com.example.agrotechgamara.data.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(
        tableName = "sembrado"
//Luego probar agregar la clave foranea y si andatodo la dejo
//       ,
//        foreignKeys = @ForeignKey(
//                entity = Campaña.class,
//                parentColumns = "idCampaña",
//                childColumns = "idCampaña",
//                onDelete = ForeignKey.CASCADE
//        )
)
public class Sembrado {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idSembrado")
    private int idSembrado;

    @ColumnInfo(name = "fechaSiembra")
    private String fechaSiembra;
    //Guardás la fecha como texto (Room no tiene tipo date)

    @ColumnInfo(name = "nomSiembra")
    private String nomSiembra;
    /*👉 Room NO permite especificar longitud, porque usa el tipo TEXT de SQLite. SQLite ignora VARCHAR(30) o VARCHAR(200).
    Para Room, simplemente es: private String nomSiembra; Si querés limitar caracteres se hace con validaciones, no con la base.*/

    @ColumnInfo(name = "fechaRiego")
    private String fechaRiego;

    @ColumnInfo(name = "fechaCosecha")
    private String fechaCosecha;

    @ColumnInfo(name = "AgroquimicosSiembra")
    private String agroquimicosSiembra;

    @ColumnInfo(name = "descCampo")
    private String descCampo;

    @ColumnInfo(name = "fotosCampo",typeAffinity = ColumnInfo.BLOB)
    private byte[] fotosCampo;

    @ColumnInfo(name = "idCampaña")
    private int idCampaña;

    @ColumnInfo(name = "idLote")
    private int idLote;

    @ColumnInfo(name = "idRendimiento")
    private int idRendimiento;

    // --- GETTERS & SETTERS --- //

    public int getIdSembrado() { return idSembrado; }
    public void setIdSembrado(int idSembrado) { this.idSembrado = idSembrado; }

    public String getFechaSiembra() { return fechaSiembra; }
    public void setFechaSiembra(String fechaSiembra) { this.fechaSiembra = fechaSiembra; }

    public String getNomSiembra() { return nomSiembra; }
    public void setNomSiembra(String nomSiembra) { this.nomSiembra = nomSiembra; }

    public String getFechaRiego() { return fechaRiego; }
    public void setFechaRiego(String fechaRiego) { this.fechaRiego = fechaRiego; }

    public String getFechaCosecha() { return fechaCosecha; }
    public void setFechaCosecha(String fechaCosecha) { this.fechaCosecha = fechaCosecha; }

    public String getAgroquimicosSiembra() { return agroquimicosSiembra; }
    public void setAgroquimicosSiembra(String agroquimicosSiembra) { this.agroquimicosSiembra = agroquimicosSiembra; }

    public String getDescCampo() { return descCampo; }
    public void setDescCampo(String descCampo) { this.descCampo = descCampo; }

    public byte[] getFotosCampo() { return fotosCampo; }
    public void setFotosCampo(byte[] fotosCampo) { this.fotosCampo = fotosCampo; }

    public int getIdCampaña() { return idCampaña; }
    public void setIdCampaña(int idCampaña) { this.idCampaña = idCampaña; }

    public int getIdLote() { return idLote; }
    public void setIdLote(int idLote) { this.idLote = idLote; }

    public int getIdRendimiento() { return idRendimiento; }
    public void setIdRendimiento(int idRendimiento) { this.idRendimiento = idRendimiento; }
}
