package com.example.agrotechgamara.ui.viewmodel;

/*Responsabilidad Única: La lógica de agendar o borrar (que son operaciones de escritura) ahora residen en el Repositorio, que es su lugar correcto. Si en el futuro agregas la función de "Sincronizar Recordatorios con Google Calendar API", solo tienes que modificar RecordatorioRepository.java.*/
/*Ahora tu ViewModel es extremadamente limpio. Solo actúa como intermediario entre la Vista (Activity/Fragment) y el Repositorio.*/

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.repository.RecordatorioRepository; // Importamos el Repo
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Recordatorio;
import java.util.List;

/* Resumen del cambio
Repository: Se encarga del trabajo sucio (hilos, fechas, base de datos).
ViewModel: Se queda solo con la lógica de presentación.
Beneficio: Si mañana quieres cambiar cómo se guardan los recordatorios (ej. enviarlos a Google Calendar), solo se modifica el RecordatorioRepository, y el ViewModel ni se entera.*/

public class RecordatorioViewModel extends AndroidViewModel {

    private RecordatorioRepository repository;

    // Cacheamos el LiveData para no pedirlo múltiples veces
    private LiveData<List<Recordatorio>> recordatoriosPendientes;

    public RecordatorioViewModel(@NonNull Application application) {
        super(application);

        // 1. Instanciamos DB y Repositorio
        AppDatabase db = AppDatabase.getDatabase(application);
        repository = new RecordatorioRepository(db);

        // 2. Conectamos los datos al iniciar
        recordatoriosPendientes = repository.getRecordatoriosPendientes();
    }

    // --- GETTERS (La UI observa esto) ---
    public LiveData<List<Recordatorio>> getRecordatoriosPendientes() {
        return recordatoriosPendientes;
    }

    // --- ACCIONES (La UI llama a esto) ---
    public void agendar(Recordatorio recordatorio) {
        repository.agendar(recordatorio);
    }

    public void borrar(Recordatorio recordatorio) {
        repository.borrar(recordatorio);
    }
}