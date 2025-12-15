package com.example.agrotechgamara.data.repository;

import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.dao.UbicacionDao;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Ubicacion;
import com.example.agrotechgamara.data.networkapis.GoogleMapsApiService;

public class UbicacionRepository {

    private UbicacionDao ubicacionDao;
    private GoogleMapsApiService mapsService;

    // Constructor
    public UbicacionRepository(AppDatabase db, GoogleMapsApiService mapsService) {
        this.ubicacionDao = db.ubicacionDao();
        this.mapsService = mapsService;
    }

    // --- LECTURA (UI) ---
    public LiveData<Ubicacion> getUbicacionById(int id) {
        return ubicacionDao.getUbicacionById(id); // Este devuelve LiveData para la pantalla
    }

    // --- ESCRITURA (Async) ---
    public void insertUbicacion(Ubicacion ubicacion) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                ubicacionDao.insertUbicacion(ubicacion)
        );
    }

    // --- LÓGICA MIXTA (DB + API) ---
    public void fetchAdvancedDetails(int idLote, Callback<String> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // A. Obtenemos la ubicación de forma SÍNCRONA (Directa, sin LiveData)
            // Si usamos el métodoque devuelve LiveData aquí, dará NULL.
            Ubicacion ubicacion = ubicacionDao.getUbicacionByIdSync(idLote);

            if (ubicacion != null) {
                try {
                    // B. Validación de seguridad por si el servicio es null
                    if (mapsService != null) {
                        String advancedInfo = mapsService.getLoteDetails(ubicacion.getLatitud(), ubicacion.getLongitud());
                        callback.onSuccess(advancedInfo);
                    } else {
                        callback.onError("El servicio de mapas no está configurado.");
                    }
                } catch (Exception e) {
                    callback.onError("Error de red: " + e.getMessage());
                }
            } else {
                callback.onError("Ubicación local no encontrada para el ID: " + idLote);
            }
        });
    }

    // Interfaz para comunicar resultados al ViewModel
    public interface Callback<T> {
        void onSuccess(T result);
        void onError(String error);
    }
}