package com.example.agrotechgamara.data.repository;

import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.dao.LoteDao;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Lote;
import java.util.List;

/*Definición: El Repositorio es una clase que decide si los datos deben obtenerse de la Base de Datos Local (Room) o de un Servicio en Línea (API).
¿Por qué lo necesitamos? Aísla al ViewModel: El ViewModel no sabe ni le importa si la información viene de la DB o de Internet. Él solo le pide el dato al Repositorio.
Manejo de Lógica Compleja: El Repositorio es el lugar perfecto para poner la lógica de "Guardar en DB y luego subir a la nube" (sincronización).*/

/*Ejemplo: Uso del Repositorio con API y DB
ViewModel pide: repository.getTiempo(lote)

Repository mira: Si tiene el dato en Room y es de hace menos de 1 hora, lo devuelve (rápido). Si no, llama a la API: apiService.getTiempo(lote.lat, lote.lon)
Guarda la respuesta de la API en Room. Devuelve el dato desde Room. El Repositorio es el único lugar en tu código que sabe cómo manejar esa complejidad.*/

public class LoteRepository {

    private LoteDao loteDao;
    // Podrías tener un ApiService si los Lotes se sincronizan en línea
    // private ApiService apiService;

    public LoteRepository(LoteDao loteDao) {
        this.loteDao = loteDao;
        // apiService = RetrofitClient.getService();
    }

    // 1. EL REPOSITORIO ES QUIEN HACE EL TRABAJO PESADO
    public LiveData<List<Lote>> getAllLotes() {
        // Lógica de decisión:
        // 1. Si está conectado a internet, sincronizar primero.
        // 2. Luego devolver los datos locales que son la fuente.
        return loteDao.getAllLotes();
    }

    public void insertLote(Lote lote) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            loteDao.insertLote(lote);
            // 2. Subir a la API: apiService.uploadLote(lote);
        });
    }
}