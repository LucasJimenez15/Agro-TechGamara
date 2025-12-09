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

import java.util.List;

/*Agrupa: Lote, Campaña, Sembrado, Rendimiento, Incidencia. Uso: Ideal para la pantalla principal del mapa, detalle
de lotes y registro de producción.*/

public class ProduccionViewModel extends AndroidViewModel {

    // DAOs necesarios
    private LoteDao loteDao;
    private CampañaDao campañaDao;
    private SembradoDao sembradoDao;
    private RendimientoDao rendimientoDao;
    private IncidenciaDao incidenciaDao;

    // LiveData básicos
    private LiveData<List<Lote>> allLotes;
    private LiveData<List<Campaña>> allcampañas;
    private LiveData<List<Incidencia>> allIncidencias;

    // Filtros (Para ver sembrados de un lote específico)
    private MutableLiveData<Integer> filtroLoteId = new MutableLiveData<>();
    private LiveData<List<Sembrado>> sembradosDelLote;

    public ProduccionViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        loteDao = db.loteDao();
        campañaDao = db.campañaDao();
        sembradoDao = db.sembradoDao();
        rendimientoDao = db.rendimientoDao();
        incidenciaDao = db.incidenciaDao();

        // Inicializamos listas generales
        allLotes = loteDao.getAllLotes();
        allcampañas = campañaDao.getAllcampañas();
        allIncidencias = incidenciaDao.getAllIncidencias(); // Asumiendo que devuelve LiveData

        // Configuración del filtro: Si cambiamos el ID, se actualiza la lista de sembrados
        sembradosDelLote = Transformations.switchMap(filtroLoteId, id ->
                sembradoDao.getSembradosByLote(id)
        );
    }
    
    // --- GETTERS (Lectura) ---
    public LiveData<List<Lote>> getAllLotes() { return allLotes; }
    public LiveData<List<Campaña>> getAllcampañas() { return allcampañas; }
    public LiveData<List<Incidencia>> getAllIncidencias() { return allIncidencias; }

    // Para filtrar sembrados
    public void setLoteSeleccionado(int idLote) { filtroLoteId.setValue(idLote); }
    public LiveData<List<Sembrado>> getSembradosFiltrados() { return sembradosDelLote; }

    // Para obtener totales financieros (Rendimiento)
    public LiveData<Float> getTotalGastado() { return rendimientoDao.getTotalGastadoGlobal(); }

    // --- INSERTS (Escritura en segundo plano) ---
    public void insertLote(Lote lote) {
        AppDatabase.databaseWriteExecutor.execute(() -> loteDao.insertLote(lote));
    }

    public void insertcampaña(Campaña campaña) {
        AppDatabase.databaseWriteExecutor.execute(() -> campañaDao.insertCampaña(campaña));
    }

    public void insertSembrado(Sembrado sembrado) {
        AppDatabase.databaseWriteExecutor.execute(() -> sembradoDao.insertSembrado(sembrado));
    }

    public void insertRendimiento(Rendimiento rendimiento) {
        AppDatabase.databaseWriteExecutor.execute(() -> rendimientoDao.insertRendimiento(rendimiento));
    }

    public void insertIncidencia(Incidencia incidencia) {
        AppDatabase.databaseWriteExecutor.execute(() -> incidenciaDao.insertIncidencia(incidencia));
    }
}