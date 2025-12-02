package com.example.agrotechgamara.data.dao;

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

    // Selecciona todos los lotes
    @Query("SELECT * FROM lotes")
    List<Lote> getAllLotes();

    // Busca un lote específico por su ID
    @Query("SELECT * FROM lotes WHERE idLote = :id")
    Lote getLoteById(int id);
}