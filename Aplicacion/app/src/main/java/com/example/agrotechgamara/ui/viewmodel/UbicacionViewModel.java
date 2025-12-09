package com.example.agrotechgamara.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.dao.UbicacionDao;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.*;

/*Uso: Generalmente se usará cuando estés capturando coordenadas GPS o seleccionando puntos en el mapa*/

public class UbicacionViewModel extends AndroidViewModel {

    private UbicacionDao ubicacionDao;

    public UbicacionViewModel(@NonNull Application application) {
        super(application);
        ubicacionDao = AppDatabase.getDatabase(application).ubicacionDao();
    }

    // Insertar ubicación y obtener el ID (útil para asignarlo a un Lote inmediatamente)
    // Nota: Como devuelve 'long', esto suele manejarse con Callbacks o RxJava,
    // pero aquí usamos un método simple void para guardar.
    public void insertUbicacion(Ubicacion ubicacion) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            ubicacionDao.insertUbicacion(ubicacion);
        });
    }

    public LiveData<Ubicacion> getUbicacion(int id) {
        return ubicacionDao.getUbicacionById(id);
    }
}