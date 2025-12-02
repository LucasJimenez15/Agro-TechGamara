package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.agrotechgamara.data.model.Lote;
import java.util.List;

@Dao
public interface LoteDao {

    // Inserta un lote. Si el ID ya existe, lo reemplaza.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLote(Lote lote);

    @Update
    void updateLote(Lote lote);

    @Delete
    void deleteLote(Lote lote);

    // Envolvemos el retorno en LiveData
    @Query("SELECT * FROM lotes")
    LiveData<List<Lote>> getAllLotes();

    @Query("SELECT * FROM lotes WHERE idLote = :id")
    LiveData<Lote> getLoteById(int id);
}