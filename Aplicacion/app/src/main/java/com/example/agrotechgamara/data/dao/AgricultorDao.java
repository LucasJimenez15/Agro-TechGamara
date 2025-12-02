package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.agrotechgamara.data.model.Agricultor;

@Dao
public interface AgricultorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAgricultor(Agricultor agricultor);

    @Update
    void updateAgricultor(Agricultor agricultor);

    // Útil para un Login o perfil: busca por email
    @Query("SELECT * FROM agricultor WHERE emailAgricultor = :email LIMIT 1")
    LiveData<Agricultor> getAgricultorByEmail(String email);
}