package com.example.agrotechgamara.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Agricultor;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.agrotechgamara.data.repository.AgricultorRepository; // Importamos el Repo

/*Aquí hacemos un cambio importante. En lugar de ejecutar una búsqueda manual en un hilo secundario (que es difícil de devolver a la UI), usamos un patrón Reactivo.
Tenemos un "Trigger" (filtroEmail). Cuando seteamos el email, el LiveData de resultado (usuarioResult) se actualiza automáticamente gracias al Repositorio.*/

public class AgricultorViewModel extends AndroidViewModel {

    private AgricultorRepository repository;

    // Lógica para el Login / Búsqueda
    // 1. El "gatillo": Aquí ponemos el email que queremos buscar
    private MutableLiveData<String> filtroEmail = new MutableLiveData<>();

    // 2. El "resultado": Observa el gatillo y pide los datos al repositorio
    private LiveData<Agricultor> usuarioResult;

    public AgricultorViewModel(@NonNull Application application) {
        super(application);

        // Inicializamos Repositorio
        AppDatabase db = AppDatabase.getDatabase(application);
        repository = new AgricultorRepository(db);

        // Configuración Reactiva:
        // Cada vez que 'filtroEmail' cambie, se llama a repository.getAgricultorByEmail()
        usuarioResult = Transformations.switchMap(filtroEmail, email ->
                repository.getAgricultorByEmail(email)
        );
    }

    // --- ACCIONES (La UI llama a esto) ---

    public void registrarAgricultor(Agricultor agricultor) {
        repository.registrarAgricultor(agricultor);
    }

    public void actualizarPerfil(Agricultor agricultor) {
        repository.actualizarPerfil(agricultor);
    }

    // LOGIN: Paso 1 - La Vista llama a esto con el email escrito en el EditText
    public void buscarPorEmail(String email) {
        filtroEmail.setValue(email);
    }

    // LOGIN: Paso 2 - La Vista observa esto para saber si encontró al usuario
    public LiveData<Agricultor> getResultadoBusqueda() {
        return usuarioResult;
    }
}

/*¿Cómo usar este Login en tu Activity?
Con esta estructura mejorada, tu código en la pantalla de Login (LoginActivity) sería así de limpio:
En LoginActivity.java

// 1. Observar el resultado
agricultorViewModel.getResultadoBusqueda().observe(this, agricultor -> {
    if (agricultor != null) {
        // ¡Usuario encontrado! Ir a la pantalla principal
        iniciarSesion(agricultor);
    } else {
        // Usuario no existe en la DB
        mostrarError("Email no registrado");
    }
});

// 2. Cuando el usuario presiona el botón "Ingresar"
botonLogin.setOnClickListener(v -> {
    String email = inputEmail.getText().toString();
    agricultorViewModel.buscarPorEmail(email); // Esto dispara la observación de arriba
});*/
