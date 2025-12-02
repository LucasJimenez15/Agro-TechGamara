package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.agrotechgamara.data.model.Rendimiento;

@Dao
public interface RendimientoDao {

    @Insert
    void insertRendimiento(Rendimiento rendimiento);

    @Update
    void updateRendimiento(Rendimiento rendimiento);

    // Consulta analítica: Suma todo lo gastado en total en la DB
    @Query("SELECT SUM(totalGastado) FROM rendimiento")
    LiveData<Float> getTotalGastadoGlobal();

    // Consulta para obtener el rendimiento asociado a un ID específico
    @Query("SELECT * FROM rendimiento WHERE idRendimiento = :id")
    LiveData<Rendimiento> getRendimientoById(int id);
}