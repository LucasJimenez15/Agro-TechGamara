package com.example.agrotechgamara.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.*;
import java.util.List;
import com.example.agrotechgamara.data.repository.ControlRepository; // Importamos el Repo

/* Agrupa: Actividades, Empleado, Pagos, y sus tablas intermedias. Uso: Gestión de personal, asignación de tareas diarias y nómina. Ahora delega toda la lógica de datos al ControlRepository. */

public class ControlViewModel extends AndroidViewModel {

    // Única referencia necesaria: El Repositorio
    private ControlRepository repository;

    private LiveData<List<Empleado>> allEmpleados;
    private LiveData<List<Actividades>> allActividades;
    private LiveData<List<Pagos>> allPagos;

    public ControlViewModel(@NonNull Application application) {
        super(application);

        // 1. Obtenemos la instancia de la DB
        AppDatabase db = AppDatabase.getDatabase(application);

        // 2. Inicializamos el repositorio
        repository = new ControlRepository(db);

        // 3. Conectamos los LiveData directamente desde el repositorio
        allEmpleados = repository.getAllEmpleados();
        allActividades = repository.getAllActividades();
        allPagos = repository.getAllPagos();
    }

    // --- LECTURA ---
    public LiveData<List<Empleado>> getEmpleados() { return allEmpleados; }
    public LiveData<List<Actividades>> getActividades() { return allActividades; }
    public LiveData<List<Pagos>> getHistorialPagos() { return allPagos; }

    // --- ESCRITURA ---
    // El ViewModel solo pasa los objetos al repositorio.
    // Ya no maneja 'Executors' ni hilos aquí.

    public void insertEmpleado(Empleado empleado) {
        repository.insertEmpleado(empleado);
    }

    public void insertActividad(Actividades actividad) {
        repository.insertActividad(actividad);
    }

    // Métodos que implican relaciones (Tablas intermedias)
    public void asignarEmpleadoAActividad(int idEmpleado, int idActividad) {
        repository.asignarEmpleadoAActividad(idEmpleado, idActividad);
    }

    public void registrarPago(Pagos pago, int idEmpleado) {
        repository.registrarPago(pago, idEmpleado);
    }
}