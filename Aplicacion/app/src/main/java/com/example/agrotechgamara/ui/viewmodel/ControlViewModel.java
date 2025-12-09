package com.example.agrotechgamara.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.dao.*;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.*;
import java.util.List;

/*Agrupa: Actividades, Empleado, Pagos, y sus tablas intermedias. Uso: Gestión de personal, asignación de tareas diarias y nómina.*/

public class ControlViewModel extends AndroidViewModel {

    private ActividadesDao actividadesDao;
    private EmpleadoDao empleadoDao;
    private ActividadEmpleadoDao actividadEmpleadoDao;
    private PagosDao pagosDao;
    private PagosEmpleadoDao pagosEmpleadoDao;

    private LiveData<List<Empleado>> allEmpleados;
    private LiveData<List<Actividades>> allActividades;
    private LiveData<List<Pagos>> allPagos;

    public ControlViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        actividadesDao = db.actividadesDao();
        empleadoDao = db.empleadoDao();
        actividadEmpleadoDao = db.actividadEmpleadoDao();
        pagosDao = db.pagosDao();
        pagosEmpleadoDao = db.pagosEmpleadoDao();

        allEmpleados = empleadoDao.getAllEmpleados(); // Asumiendo LiveData en DAO
        allActividades = actividadesDao.getAllActividades(); // Asumiendo LiveData en DAO
        allPagos = pagosDao.getAllPagos(); // Asumiendo LiveData en DAO
    }

    // --- LECTURA ---
    public LiveData<List<Empleado>> getEmpleados() { return allEmpleados; }
    public LiveData<List<Actividades>> getActividades() { return allActividades; }
    public LiveData<List<Pagos>> getHistorialPagos() { return allPagos; }

    // --- ESCRITURA BASICA ---
    public void insertEmpleado(Empleado empleado) {
        AppDatabase.databaseWriteExecutor.execute(() -> empleadoDao.insertEmpleado(empleado));
    }

    public void insertActividad(Actividades actividad) {
        AppDatabase.databaseWriteExecutor.execute(() -> actividadesDao.insertActividad(actividad));
    }

    // --- ESCRITURA RELACIONAL (TABLAS INTERMEDIAS) ---

    // 1. Asignar un empleado a una actividad
    public void asignarEmpleadoAActividad(int idEmpleado, int idActividad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            ActividadEmpleado relacion = new ActividadEmpleado();
            relacion.setIdEmpleado(idEmpleado);
            relacion.setIdActividad(idActividad);
            actividadEmpleadoDao.asignarEmpleado(relacion);
        });
    }

    // 2. Registrar un pago y vincularlo al empleado
    public void registrarPago(Pagos pago, int idEmpleado) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            long nuevoIdPago = pagosDao.insertPago(pago); // Insertamos pago

            PagosEmpleado relacion = new PagosEmpleado(); // Creamos vínculo
            relacion.setIdPago((int) nuevoIdPago);
            relacion.setIdEmpleado(idEmpleado);
            pagosEmpleadoDao.insertPagoEmpleado(relacion);
        });
    }
}