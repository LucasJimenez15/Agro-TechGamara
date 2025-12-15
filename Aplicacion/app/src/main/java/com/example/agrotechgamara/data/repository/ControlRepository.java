package com.example.agrotechgamara.data.repository;

import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.dao.*;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.*;
import java.util.List;

public class ControlRepository {

    // Declaramos los DAOs necesarios para la sección de Control/RRHH
    private ActividadesDao actividadesDao;
    private EmpleadoDao empleadoDao;
    private ActividadEmpleadoDao actividadEmpleadoDao;
    private PagosDao pagosDao;
    private PagosEmpleadoDao pagosEmpleadoDao;

    // Constructor: Inicializa todos los DAOs desde la DB
    public ControlRepository(AppDatabase db) {
        this.actividadesDao = db.actividadesDao();
        this.empleadoDao = db.empleadoDao();
        this.actividadEmpleadoDao = db.actividadEmpleadoDao();
        this.pagosDao = db.pagosDao();
        this.pagosEmpleadoDao = db.pagosEmpleadoDao();
    }

    // --- LECTURA (Getters) ---
    public LiveData<List<Empleado>> getAllEmpleados() {
        return empleadoDao.getAllEmpleados();
    }

    public LiveData<List<Actividades>> getAllActividades() {
        return actividadesDao.getAllActividades();
    }

    public LiveData<List<Pagos>> getAllPagos() {
        return pagosDao.getAllPagos();
    }

    // --- ESCRITURA BÁSICA ---
    public void insertEmpleado(Empleado empleado) {
        AppDatabase.databaseWriteExecutor.execute(() -> empleadoDao.insertEmpleado(empleado));
    }

    public void insertActividad(Actividades actividad) {
        AppDatabase.databaseWriteExecutor.execute(() -> actividadesDao.insertActividad(actividad));
    }

    // --- ESCRITURA RELACIONAL (Lógica de Negocio) ---

    // 1. Asignar Tarea: Vincula un empleado con una actividad
    public void asignarEmpleadoAActividad(int idEmpleado, int idActividad) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            ActividadEmpleado relacion = new ActividadEmpleado();
            // Asumiendo que usas Setters o campos públicos
            relacion.setIdEmpleado(idEmpleado);
            relacion.setIdActividad(idActividad);
            actividadEmpleadoDao.asignarEmpleado(relacion);
        });
    }

    // 2. Registrar Pago: Inserta el pago Y crea el vínculo en una sola transacción lógica
    public void registrarPago(Pagos pago, int idEmpleado) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // A. Insertamos el pago y obtenemos el ID autogenerado
            long nuevoIdPago = pagosDao.insertPago(pago);

            // B. Creamos el registro en la tabla intermedia
            PagosEmpleado relacion = new PagosEmpleado();
            relacion.setIdPago((int) nuevoIdPago);
            relacion.setIdEmpleado(idEmpleado);

            // C. Guardamos la relación
            pagosEmpleadoDao.insertPagoEmpleado(relacion);
        });
    }
}