package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.agrotechgamara.data.model.Incidencia;
import java.util.List;

@Dao
public interface IncidenciaDao {
    @Insert
    void insertIncidencia(Incidencia incidencia);

    @Query("SELECT * FROM incidencia ORDER BY idIncidencia DESC")
    LiveData<List<Incidencia>> getAllIncidencias();

    // Búsqueda de texto: Busca cualquier incidencia que contenga la palabra clave en su nombre
    @Query("SELECT * FROM incidencia WHERE nomIncidencia LIKE '%' || :busqueda || '%'")
    LiveData<List<Incidencia>> buscarIncidencia(String busqueda);
}