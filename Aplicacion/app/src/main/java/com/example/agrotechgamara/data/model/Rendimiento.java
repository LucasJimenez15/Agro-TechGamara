package com.example.agrotechgamara.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "rendimiento")
public class Rendimiento {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idRendimiento")
    private int idRendimiento;

    @ColumnInfo(name = "montoInsumos")
    private Float montoInsumos;

    @ColumnInfo(name = "montoLabores")
    private Float montoLabores;

    @ColumnInfo(name = "montoTractores")
    private Float montoTractores;

    @ColumnInfo(name = "montoSiembra")
    private Float montoSiembra;

    @ColumnInfo(name = "montoIngresos")
    private Float montoIngresos;

    @ColumnInfo(name = "totalGastado")
    private Float totalGastado;

    @ColumnInfo(name = "montoRentabilidad")
    private Float montoRentabilidad;

    @ColumnInfo(name = "cantBulbosTotal")
    private Float cantBulbosTotal;

    @ColumnInfo(name = "cantBulbosPorHect")
    private Float cantBulbosPorHect;

    // Getters & Setters
    public int getIdRendimiento() { return idRendimiento; }
    public void setIdRendimiento(int idRendimiento) { this.idRendimiento = idRendimiento; }

    public Float getMontoInsumos() { return montoInsumos; }
    public void setMontoInsumos(Float montoInsumos) { this.montoInsumos = montoInsumos; }

    public Float getMontoLabores() { return montoLabores; }
    public void setMontoLabores(Float montoLabores) { this.montoLabores = montoLabores; }

    public Float getMontoTractores() { return montoTractores; }
    public void setMontoTractores(Float montoTractores) { this.montoTractores = montoTractores; }

    public Float getMontoSiembra() { return montoSiembra; }
    public void setMontoSiembra(Float montoSiembra) { this.montoSiembra = montoSiembra; }

    public Float getMontoIngresos() { return montoIngresos; }
    public void setMontoIngresos(Float montoIngresos) { this.montoIngresos = montoIngresos; }

    public Float getTotalGastado() { return totalGastado; }
    public void setTotalGastado(Float totalGastado) { this.totalGastado = totalGastado; }

    public Float getMontoRentabilidad() { return montoRentabilidad; }
    public void setMontoRentabilidad(Float montoRentabilidad) { this.montoRentabilidad = montoRentabilidad; }

    public Float getCantBulbosTotal() { return cantBulbosTotal; }
    public void setCantBulbosTotal(Float cantBulbosTotal) { this.cantBulbosTotal = cantBulbosTotal; }

    public Float getCantBulbosPorHect() { return cantBulbosPorHect; }
    public void setCantBulbosPorHect(Float cantBulbosPorHect) { this.cantBulbosPorHect = cantBulbosPorHect; }
}
