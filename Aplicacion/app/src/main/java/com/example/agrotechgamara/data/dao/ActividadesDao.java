package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.agrotechgamara.data.model.Actividades;
import java.util.List;

@Dao
public interface ActividadesDao {

    @Insert
    long insertActividad(Actividades actividad); // Devuelve ID

    @Query("SELECT * FROM actividades")
    LiveData<List<Actividades>> getAllActividades();

    @Query("SELECT * FROM actividades WHERE idActividad = :id")
    LiveData<Actividades> getActividadById(int id);
}