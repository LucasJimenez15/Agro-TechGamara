package com.example.agrotechgamara.data.repository;

import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.dao.*;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.*;
import java.util.List;

/*Definición: El Repositorio es una clase que decide si los datos deben obtenerse de la Base de Datos Local (Room) o de un Servicio en Línea (API).
¿Por qué lo necesitamos? Aísla al ViewModel: El ViewModel no sabe ni le importa si la información viene de la DB o de Internet. Él solo le pide el dato al Repositorio.
Manejo de Lógica Compleja: El Repositorio es el lugar perfecto para poner la lógica de "Guardar en DB y luego subir a la nube" (sincronización).*/

/*Ejemplo: Uso del Repositorio con API y DB
ViewModel pide: repository.getTiempo(lote)

Repository mira: Si tiene el dato en Room y es de hace menos de 1 hora, lo devuelve (rápido). Si no, llama a la API: apiService.getTiempo(lote.lat, lote.lon)
Guarda la respuesta de la API en Room. Devuelve el dato desde Room. El Repositorio es el único lugar en tu código que sabe cómo manejar esa complejidad.

Al igual que con los ViewModels, los Repositorios deben crearse pensando en "Conjuntos de Datos" o "Funcionalidades", no en tablas individuales. Se crean cuando tengamos un conjunto de datos relacionados que suelen consultarse o modificarse juntos.

Este repositorio manejatodo lo referente a la tierra y producción. maneja los DAOs: LoteDao, SembradoDao, CampanaDao, IncidenciaDao, RendimientoDao.
Por qué se crea uno solo? Porque tenerlos en un solo lugar facilita las transacciones (ej: "Borrar un lote y sus sembrados en cascada" o otras operaciones que contengan todos estos tipos de datos juntos).*/


public class ProduccionRepository {

    // Declaramos TODOS los DAOs de la sección Producción
    private LoteDao loteDao;
    private SembradoDao sembradoDao;
    private CampañaDao campañaDao;
    private RendimientoDao rendimientoDao;
    private IncidenciaDao incidenciaDao;

    // Se podría tener un ApiService si el Lote, Sembrado o Campaña se sincronizan en línea
    // private ApiService apiService;

    // El repositorio recibe acceso a VARIAS tablas
    public ProduccionRepository(AppDatabase db) {
        this.loteDao = db.loteDao();
        this.sembradoDao = db.sembradoDao();
        this.campañaDao = db.campañaDao();
        this.rendimientoDao = db.rendimientoDao();
        this.incidenciaDao = db.incidenciaDao();
        // apiService = RetrofitClient.getService();
    }

    // --- MÉTODOS DE LOTES ---
    public LiveData<List<Lote>> getAllLotes() {
        return loteDao.getAllLotes();
        // Lógica de decisión si tendriamos que obtener los datos en internet:
        // 1. Si está conectado a internet, sincronizar primero.
        // 2. Luego devolver los datos locales que son la fuente.
    }

    public void insertLote(Lote lote) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            loteDao.insertLote(lote);
            // 2. Subir a la API: apiService.uploadLote(lote);
        });
    }

    // --- METODOS DE CAMPAÑAS ---
    public LiveData<List<Campaña>> getAllCampañas() {
        return campañaDao.getAllcampañas();
    }
    public void insertCampaña(Campaña campaña) {
        AppDatabase.databaseWriteExecutor.execute(() -> campañaDao.insertCampaña(campaña));
    }


    // --- MÉTODOS DE SEMBRADOS ---
    // observar cómo este repositorio maneja otra tabla sin problemas
    public LiveData<List<Sembrado>> getSembradosPorLote(int idLote) {
        return sembradoDao.getSembradosByLote(idLote);
    }

    // --- TRANSACCIÓN COMPLEJA (la ventaja de agrupar todos estas entidades en un repositorio) ---
    // Puedes crear métodos que toquen varias tablas a la vez
    public void registrarNuevaSiembra(Lote lote, Sembrado sembrado) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Lógica compleja: Quizás actualizar el estado del lote
            // y luego insertar el sembrado.
            // loteDao.updateEstado(lote.getIdLote(), "OCUPADO");
            sembradoDao.insertSembrado(sembrado);
        });
    }

    public void insertSembrado(Sembrado sembrado) {
        AppDatabase.databaseWriteExecutor.execute(() -> sembradoDao.insertSembrado(sembrado));
    }


    // --- METODOS DE RENDIMIENTO ---
    public LiveData<Float> getTotalGastado() {
        return rendimientoDao.getTotalGastadoGlobal();
    }
    public void insertRendimiento(Rendimiento rendimiento) {
        AppDatabase.databaseWriteExecutor.execute(() -> rendimientoDao.insertRendimiento(rendimiento));
    }

    // --- METODOS DE INCIDENCIAS ---
    public LiveData<List<Incidencia>> getAllIncidencias() {
        return incidenciaDao.getAllIncidencias();
    }
    public void insertIncidencia(Incidencia incidencia) {
        AppDatabase.databaseWriteExecutor.execute(() -> incidenciaDao.insertIncidencia(incidencia));
    }

    /*Mantenibilidad: Si mañana decides que al insertar un lote por ejemplo también quieres enviarlo a la nube, solo cambias el código en ProduccionRepository.java y No tienes que tocar nada del ProduccionViewModel ni en las actividades.*/
    
}
