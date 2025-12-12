package com.example.agrotechgamara.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.*;
import com.example.agrotechgamara.data.dao.UbicacionDao;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.*;
import com.example.agrotechgamara.data.networkapis.GoogleMapsApiService;
import com.example.agrotechgamara.data.repository.UbicacionRepository;

/*Uso: Generalmente se usará cuando estés capturando coordenadas GPS o seleccionando puntos en el mapa*/

public class UbicacionViewModel extends AndroidViewModel {

    private UbicacionDao ubicacionDao;
    private UbicacionRepository repository;

    public UbicacionViewModel(@NonNull Application application) {
        super(application);
        ubicacionDao = AppDatabase.getDatabase(application).ubicacionDao();

        AppDatabase db = AppDatabase.getDatabase(application);
        // Creamos una instancia simulada del servicio (o Retrofit real)
        // GoogleMapsApiService apiService = new GoogleMapsApiServiceSimulated();
        GoogleMapsApiService apiService = null;
        repository = new UbicacionRepository(db.ubicacionDao(), apiService);

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


    //MAPAS
    // LiveData para notificar a la UI sobre la info avanzada de Google Maps
    public MutableLiveData<String> detalleMapaAvanzado = new MutableLiveData<>();

    // El Fragment llama a este método
    public void solicitarDetalleAvanzado(int idLote) {
        repository.fetchAdvancedDetails(idLote, new UbicacionRepository.Callback<String>() {
            @Override
            public void onSuccess(String result) {
                // El Repositorio nos devuelve el resultado en el hilo secundario.
                // ¡IMPORTANTE! Debemos pasarlo al hilo principal (UI) con postValue
                detalleMapaAvanzado.postValue("Detalle de Google Maps: " + result);
            }

            @Override
            public void onError(String error) {
                detalleMapaAvanzado.postValue("Error: " + error);
            }
        });
    }

}