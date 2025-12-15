package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.agrotechgamara.data.model.Ubicacion;

@Dao
public interface UbicacionDao {

    // Devuelve un 'long', que es el ID de la fila recién insertada.
    // Esto es útil para asignarlo inmediatamente al Lote.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUbicacion(Ubicacion ubicacion);

    @Update
    void updateUbicacion(Ubicacion ubicacion);

    @Query("SELECT * FROM ubicacion WHERE idUbicacion = :id")
    LiveData<Ubicacion> getUbicacionById(int id);

    //El que usa el Repositorio internamente (Sin LiveData)
    @Query("SELECT * FROM ubicacion WHERE idUbicacion = :id")
    Ubicacion getUbicacionByIdSync(int id);

}