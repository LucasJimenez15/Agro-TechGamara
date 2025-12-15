package com.example.agrotechgamara.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Ubicacion;
import com.example.agrotechgamara.data.networkapis.GoogleMapsApiService;
import com.example.agrotechgamara.data.repository.UbicacionRepository;

public class UbicacionViewModel extends AndroidViewModel {

    private UbicacionRepository repository;

    // LiveData para la respuesta de la API
    private MutableLiveData<String> detalleMapaAvanzado = new MutableLiveData<>();

    public UbicacionViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);

        // Aquí deberíamos inicializar el cliente Retrofit real.
        // Por ahora lo dejamos en null o simulado, pero el Repo ya maneja el null check.
        GoogleMapsApiService apiService = null;

        // Inicializamos el Repositorio
        repository = new UbicacionRepository(db, apiService);
    }

    // --- ACCIONES BÁSICAS ---

    public void insertUbicacion(Ubicacion ubicacion) {
        repository.insertUbicacion(ubicacion);
    }

    public LiveData<Ubicacion> getUbicacion(int id) {
        return repository.getUbicacionById(id);
    }

    // --- ACCIONES DE API (MAPAS) ---

    public LiveData<String> getDetalleMapaAvanzado() {
        return detalleMapaAvanzado;
    }

    public void solicitarDetalleAvanzado(int idLote) {
        // Llamamos al repositorio y esperamos el Callback
        repository.fetchAdvancedDetails(idLote, new UbicacionRepository.Callback<String>() {
            @Override
            public void onSuccess(String result) {
                // PostValue es necesario porque venimos de un hilo secundario
                detalleMapaAvanzado.postValue("Detalle de Google Maps: " + result);
            }

            @Override
            public void onError(String error) {
                detalleMapaAvanzado.postValue("Error: " + error);
            }
        });
    }
}