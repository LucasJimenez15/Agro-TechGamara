package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.agrotechgamara.data.model.Empleado;
import java.util.List;

@Dao
public interface EmpleadoDao {

    @Insert
    void insertEmpleado(Empleado empleado);

    @Update
    void updateEmpleado(Empleado empleado);

    @Query("SELECT * FROM empleado")
    LiveData<List<Empleado>> getAllEmpleados();
}