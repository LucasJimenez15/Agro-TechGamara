package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.agrotechgamara.data.model.Sembrado;

import java.util.List;
import java.util.Date;

@Dao
public interface SembradoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSembrado(Sembrado sembrado);

    @Update
    void updateSembrado(Sembrado sembrado);

    // Obtiene todo lo sembrado en un Lote específico
    @Query("SELECT * FROM sembrado WHERE idLote = :idLote")
    LiveData<List<Sembrado>> getSembradosByLote(int idLote);

    // Obtiene todo lo sembrado en una campaña (ej. todo lo de 2024)
    @Query("SELECT * FROM sembrado WHERE idCampaña = :idCampana")
    LiveData<List<Sembrado>> getSembradosByCampana(int idCampana);

    // FILTRO DE FECHAS: Gracias a los Converters, se puede pasar objetos Date.
    // Room los convierte a números y compara el rango.
    @Query("SELECT * FROM sembrado WHERE fechaSiembra BETWEEN :desde AND :hasta")
    LiveData<List<Sembrado>> getSembradosPorFecha(Date desde, Date hasta);
}