package com.example.agrotechgamara.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.agrotechgamara.data.dao.AgricultorDao;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Agricultor;

public class AgricultorViewModel extends AndroidViewModel {

    private AgricultorDao agricultorDao;

    public AgricultorViewModel(@NonNull Application application) {
        super(application);
        agricultorDao = AppDatabase.getDatabase(application).agricultorDao();
    }

    public void registrarAgricultor(Agricultor agricultor) {
        AppDatabase.databaseWriteExecutor.execute(() -> agricultorDao.insertAgricultor(agricultor));
    }

    public void actualizarPerfil(Agricultor agricultor) {
        AppDatabase.databaseWriteExecutor.execute(() -> agricultorDao.updateAgricultor(agricultor));
    }

    // Para login (debe manejarse con cuidado en hilo secundario)
    // En MVVM real, el resultado se pasaría a un LiveData<EstadoLogin>
    public void buscarPorEmail(String email) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Agricultor a = agricultorDao.getAgricultorByEmail(email).getValue();
            // Aquí podrías postear el valor a un MutableLiveData<Agricultor> usuarioActual;
        });
    }
}