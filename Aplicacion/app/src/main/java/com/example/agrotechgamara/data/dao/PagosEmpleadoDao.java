package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.agrotechgamara.data.model.Pagos;
import com.example.agrotechgamara.data.model.PagosEmpleado;

import java.util.List;

@Dao
public interface PagosEmpleadoDao {
    @Insert
    void insertPagoEmpleado(PagosEmpleado relacion);

    // Obtener todos los pagos a dar a un empleado específico
    // Seleccionamos la tabla 'pagos', unimos con la tabla intermedia, y filtramos por empleado.
    @Query("SELECT P.* FROM pagos P " +
            "INNER JOIN pagosEmpleado PE ON P.idPago = PE.idPago " +
            "WHERE PE.idEmpleado = :empleadoId")
    LiveData<List<Pagos>> getPagosPorEmpleado(int empleadoId);
}