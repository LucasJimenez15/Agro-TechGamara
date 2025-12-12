package com.example.agrotechgamara.data.repository;
import androidx.lifecycle.LiveData;

import com.example.agrotechgamara.data.dao.*;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Ubicacion;
import com.example.agrotechgamara.data.networkapis.GoogleMapsApiService;

// data/repository/UbicacionRepository.java
public class UbicacionRepository {

    private UbicacionDao ubicacionDao;
    private GoogleMapsApiService mapsService; // Nuestro servicio de mapas

    public UbicacionRepository(UbicacionDao ubicacionDao, GoogleMapsApiService mapsService) {
        this.ubicacionDao = ubicacionDao;
        this.mapsService = mapsService; // Inyección de dependencia
    }

    // 1. OBTENER DATOS SIMPLES (DESDE LA DB LOCAL)
    public LiveData<Ubicacion> getUbicacionById(int id) {
        return ubicacionDao.getUbicacionById(id);
    }

    // 2. OBTENER DATOS AVANZADOS (DESDE LA DB Y LA API DE GOOGLE MAPS)
    // Nota: Esto debe correr en un hilo secundario (Executor)
    public void fetchAdvancedDetails(int idLote, Callback<String> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // A. Primero, obtenemos la información base de nuestro DB local
            Ubicacion ubicacion = ubicacionDao.getUbicacionById(idLote).getValue();

            if (ubicacion != null) {
                // B. Luego, usamos la información local para llamar a la API externa
                String advancedInfo = mapsService.getLoteDetails(ubicacion.getLatitud(), ubicacion.getLongitud());

                // C. Enviamos el resultado a quien nos llamó (el ViewModel)
                callback.onSuccess(advancedInfo);
            } else {
                callback.onError("Ubicación no encontrada.");
            }
        });
    }

    // Interfaz genérica para manejar el resultado asíncrono
    public interface Callback<T> {
        void onSuccess(T result);
        void onError(String error);
    }

}