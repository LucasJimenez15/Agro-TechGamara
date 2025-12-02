package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "pagos")
public class Pagos {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idPago")
    private int idPago;

    @ColumnInfo(name = "fechaPago")
    private Date fechaPago;

    @ColumnInfo(name = "horasTrabajadas")
    private Float horasTrabajadas;

    // Getters & Setters
    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

    public Float getHorasTrabajadas() { return horasTrabajadas; }
    public void setHorasTrabajadas(Float horasTrabajadas) { this.horasTrabajadas = horasTrabajadas; }
}
