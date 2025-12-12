package com.example.agrotechgamara.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.agrotechgamara.data.dao.*;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.*;
import com.example.agrotechgamara.data.repository.ProduccionRepository;

import java.util.List;

/*Agrupa: Lote, Campaña, Sembrado, Rendimiento, Incidencia. Uso: Ideal para la pantalla principal del mapa, detalle
de lotes y registro de producción.*/

/* 🛑 El Problema: Evitar la "Sobrecarga de la Pantalla" En la aplicación, una sola pantalla (ej., la que muestra toda la info de los lotes, campaña, rendimiento, incidencias, es decir la pantalla de detalles de lote) necesita datos de múltiples tablas a la vez (Lotes, Sembrado, Campaña, Incidencia). Nos enfocamos únicamente en el porqué de la agrupación de tus ViewModels asi de esta forma, con un énfasis en la eficiencia de la programación.
* Si creamos un ViewModel por tabla, obligamos a la pantalla (Activity/Fragment) a hacer un trabajo innecesario y riesgoso: coordinar, unir y filtrar manualmente los datos de 5 fuentes diferentes. Esto se llama Acoplamiento Rígido y hace que tu código sea difícil de mantener y propenso a errores.

✅ La Solución: ViewModels agrupados por TAREA o FUNCIÓN
La mejor práctica de Android (MVVM) es crear ViewModels que sirvan a una tarea completa de la aplicación, como un "plato listo para servir" a la pantalla. haciendo el trabajo pesado de la base de datos por adelantado. La Estructura de ViewModels (4 Clases en lugar de 13).

1. ProduccionViewModel (El gerente de Producción)
Función: Entregar todos los datos necesarios para saber qué está pasando en la tierra.
Resuelve la pregunta: ¿Cuál es el estado actual de este lote?
Agrupa: Lotes, Campaña, Sembrado, Rendimiento, Incidencia.
Beneficio: Tu pantalla solo le pide a este ViewModel la información del Lote 5, y él automáticamente le entrega: su nombre, sus siembras pasadas, sus costos y sus problemas (plagas). Un solo punto de contacto.

2. ControlViewModel (El Administrador - Personal y Finanzas)
Función: Entregar todos los datos necesarios para la gestión de gente y dinero.
Resuelve la pregunta: ¿Quién hizo la Actividad X y cuánto se le pagó?
Agrupa: Actividades, Empleado, Pagos y sus tablas intermedias.
Beneficio: Cuando registras un pago, este ViewModel se encarga de que se guarde el pago y, al mismo tiempo, se vincule al empleado correcto en la tabla intermedia (PagosEmpleado). Automatización de transacciones complejas.

3. ViewModels Utilitarios (Ubicacion, Recordatorio, Agricultor)
Función: Tareas muy específicas que solo se llaman en momentos puntuales (ej. al abrir el perfil o al grabar un lugar en el GPS).
Beneficio: No sobrecargan a los ViewModels principales.

🔑 Conclusión: Al agrupar los ViewModels de esta forma, lograremos que el código de pantalla sea mínimo y limpio. La Activity solo da la orden (ej., "muestra el lote 5") y el ViewModel se encarga de la compleja coordinación interna de la base de datos.*/

public class ProduccionViewModel extends AndroidViewModel {

    // Ahora solo necesitamos el Repositorio, no los DAOs individualmente
    private ProduccionRepository repository;

    // LiveData básicos
    private LiveData<List<Lote>> allLotes;
    private LiveData<List<Campaña>> allCampañas;
    private LiveData<List<Incidencia>> allIncidencias;

    // Filtros (Para ver sembrados de un lote específico)
    private MutableLiveData<Integer> filtroLoteId = new MutableLiveData<>();
    private LiveData<List<Sembrado>> sembradosDelLote;

    public ProduccionViewModel(@NonNull Application application) {
        super(application);

        // 1. Instanciamos la base de datos
        AppDatabase db = AppDatabase.getDatabase(application);

        // 2. Inicializamos el Repositorio pasándole la DB
        repository = new ProduccionRepository(db);

        // 3. Obtenemos los datos a través del Repositorio
        allLotes = repository.getAllLotes();
        allCampañas = repository.getAllCampañas();
        allIncidencias = repository.getAllIncidencias();

        // 4. Configuración del filtro llamando al repo
        sembradosDelLote = Transformations.switchMap(filtroLoteId, id ->
                repository.getSembradosPorLote(id)
        );
    }

    // --- GETTERS (Lectura) ---
    public LiveData<List<Lote>> getAllLotes() { return allLotes; }
    public LiveData<List<Campaña>> getAllCampañas() { return allCampañas; }
    public LiveData<List<Incidencia>> getAllIncidencias() { return allIncidencias; }

    // Filtros
    public void setLoteSeleccionado(int idLote) { filtroLoteId.setValue(idLote); }
    public LiveData<List<Sembrado>> getSembradosFiltrados() { return sembradosDelLote; }

    // Totales
    public LiveData<Float> getTotalGastado() { return repository.getTotalGastado(); }

    // --- INSERTS (Escritura) ---
    // debemos fijarnos que aquí ya NO usamos 'AppDatabase.databaseWriteExecutor.execute'
    // porque esa responsabilidad ahora está encapsulada DENTRO del Repositorio.

    public void insertLote(Lote lote) {
        repository.insertLote(lote);
    }

    public void insertCampaña(Campaña campaña) {
        repository.insertCampaña(campaña);
    }

    public void insertSembrado(Sembrado sembrado) {
        repository.insertSembrado(sembrado);
    }

    // Ejemplo de uso de la transacción compleja que creamos en el Repo
    public void registrarNuevaSiembra(Lote lote, Sembrado sembrado) {
        repository.registrarNuevaSiembra(lote, sembrado);
    }

    public void insertRendimiento(Rendimiento rendimiento) {
        repository.insertRendimiento(rendimiento);
    }

    public void insertIncidencia(Incidencia incidencia) {
        repository.insertIncidencia(incidencia);
    }
}