package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.agrotechgamara.data.model.Pagos;

import java.util.List;
import java.util.Date;

@Dao
public interface PagosDao {
    @Insert
    long insertPago(Pagos pago); // Retorna ID para vincularlo luego

    @Query("SELECT * FROM pagos ORDER BY fechaPago DESC")
    LiveData<List<Pagos>> getAllPagos();

    // Obtener pagos realizados en un rango de fechas
    @Query("SELECT * FROM pagos WHERE fechaPago BETWEEN :start AND :end")
    LiveData<List<Pagos>> getPagosInDateRange(Date start, Date end);
}