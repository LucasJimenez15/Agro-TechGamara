package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.agrotechgamara.data.model.ActividadEmpleado;

import java.util.List;

@Dao
public interface ActividadEmpleadoDao {

    // Asigna un empleado a una actividad
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void asignarEmpleado(ActividadEmpleado relacion);

    // Elimina la asignación
    @Delete
    void eliminarAsignacion(ActividadEmpleado relacion);

    // Obtener IDs de empleados que participaron en una actividad X
    @Query("SELECT idEmpleado FROM actividadEmpleado WHERE idActividad = :idActividad")
    LiveData<List<Integer>> getEmpleadosEnActividad(int idActividad);
}