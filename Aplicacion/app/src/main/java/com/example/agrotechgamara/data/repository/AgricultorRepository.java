package com.example.agrotechgamara.data.repository;

import androidx.lifecycle.LiveData;
import com.example.agrotechgamara.data.dao.AgricultorDao;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Agricultor;

/*Este caso es interesante porque incluye una lógica de Login/Búsqueda, la cual suele manejarse de forma Reactiva (usando Transformations) para que sea segura y eficiente.*/

public class AgricultorRepository {

    private AgricultorDao agricultorDao;

    // Constructor que inicializa el DAO
    public AgricultorRepository(AppDatabase db) {
        this.agricultorDao = db.agricultorDao();
    }

    // --- ESCRITURA (Async) ---
    public void registrarAgricultor(Agricultor agricultor) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                agricultorDao.insertAgricultor(agricultor)
        );
    }

    public void actualizarPerfil(Agricultor agricultor) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                agricultorDao.updateAgricultor(agricultor)
        );
    }

    /*Este repositorio encapsula el acceso a la tabla de usuarios. He mejorado el métodode buscar por email para que devuelva un LiveData, lo que permite a la UI reaccionar cuando se encuentra al usuario.*/

    // --- LECTURA (Login) ---
    // Devuelve un LiveData. Si el usuario existe, el LiveData emitirá el objeto.
    // Si no existe, podría emitir null (dependiendo de tu DAO).
    public LiveData<Agricultor> getAgricultorByEmail(String email) {
        return agricultorDao.getAgricultorByEmail(email);
    }
}