package com.example.agrotechgamara.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.agrotechgamara.data.model.Recordatorio;
import java.util.List;
import java.util.Date;

@Dao
public interface RecordatorioDao {

    @Insert
    long insertRecordatorio(Recordatorio recordatorio);

    @Delete
    void deleteRecordatorio(Recordatorio recordatorio);

    // Obtener recordatorios futuros (cuya fecha de activación sea mayor a 'ahora')
    @Query("SELECT * FROM recordatorio WHERE fechaActivacion >= :ahora ORDER BY fechaActivacion ASC")
    LiveData<List<Recordatorio>> getRecordatoriosPendientes(Date ahora);
}