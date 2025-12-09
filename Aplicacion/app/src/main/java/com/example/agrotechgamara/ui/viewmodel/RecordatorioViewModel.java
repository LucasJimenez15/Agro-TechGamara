package com.example.agrotechgamara.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.dao.RecordatorioDao;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Recordatorio;
import java.util.Date;
import java.util.List;

public class RecordatorioViewModel extends AndroidViewModel {

    private RecordatorioDao recordatorioDao;

    public RecordatorioViewModel(@NonNull Application application) {
        super(application);
        recordatorioDao = AppDatabase.getDatabase(application).recordatorioDao();
    }

    public LiveData<List<Recordatorio>> getRecordatoriosPendientes() {
        // 'new Date()' obtiene la fecha/hora actual
        return recordatorioDao.getRecordatoriosPendientes(new Date()); // Asumiendo que modificaste el DAO para devolver LiveData
    }

    public void agendar(Recordatorio recordatorio) {
        AppDatabase.databaseWriteExecutor.execute(() -> recordatorioDao.insertRecordatorio(recordatorio));
    }

    public void borrar(Recordatorio recordatorio) {
        AppDatabase.databaseWriteExecutor.execute(() -> recordatorioDao.deleteRecordatorio(recordatorio));
    }
}