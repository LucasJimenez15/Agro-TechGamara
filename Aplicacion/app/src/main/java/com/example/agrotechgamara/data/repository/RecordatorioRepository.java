package com.example.agrotechgamara.data.repository;

import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.dao.RecordatorioDao;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Recordatorio;
import java.util.Date;
import java.util.List;

/*Este repositorio encapsula la lógica de acceso a datos. Fíjate que aquí es donde decidimos usar new Date() y donde manejamos los hilos en segundo plano.*/

public class RecordatorioRepository {

    private RecordatorioDao recordatorioDao;

    // Constructor: Inicializa el DAO
    public RecordatorioRepository(AppDatabase db) {
        this.recordatorioDao = db.recordatorioDao();
    }

    // --- LECTURA ---
    // El Repositorio se encarga de proveer la fecha actual para el filtro
    public LiveData<List<Recordatorio>> getRecordatoriosPendientes() {
        return recordatorioDao.getRecordatoriosPendientes(new Date());
    }

    // --- ESCRITURA (Manejo de Hilos) ---
    public void agendar(Recordatorio recordatorio) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                recordatorioDao.insertRecordatorio(recordatorio)
        );
    }

    public void borrar(Recordatorio recordatorio) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                recordatorioDao.deleteRecordatorio(recordatorio)
        );
    }
}