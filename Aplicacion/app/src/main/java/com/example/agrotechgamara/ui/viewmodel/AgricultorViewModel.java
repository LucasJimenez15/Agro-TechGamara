package com.example.agrotechgamara.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.agrotechgamara.data.database.AppDatabase;
import com.example.agrotechgamara.data.model.Agricultor;
import com.example.agrotechgamara.data.repository.AgricultorRepository;

public class AgricultorViewModel extends AndroidViewModel {

    // 1. EL REPOSITORIO: Nuestro único punto de acceso a los datos (DB o Nube)
    private AgricultorRepository repository;

    // 2. EL "GATILLO" (Trigger):
    // Imagina esto como una caja de búsqueda vacía.
    // La UI (Fragment) escribirá el email aquí.
    // Es 'Mutable' porque nosotros (la UI) podemos cambiar su valor.
    private MutableLiveData<String> filtroEmail = new MutableLiveData<>();

    // 3. EL "RESULTADO" (Output):
    // La UI observará esta variable. Cuando el 'Gatillo' cambie,
    // esta variable se actualizará sola con el agricultor encontrado.
    private LiveData<Agricultor> usuarioResult;

    public AgricultorViewModel(@NonNull Application application) {
        super(application);

        // Inicializamos la DB y el Repositorio
        AppDatabase db = AppDatabase.getDatabase(application);
        repository = new AgricultorRepository(db);

        // 4. LA MAGIA REACTIVA (SwitchMap):
        // Aquí conectamos el GATILLO con el RESULTADO.
        // Se lee así: "Cada vez que 'filtroEmail' cambie, ejecuta 'repository.getAgricultorByEmail'
        // y pon el resultado dentro de 'usuarioResult'".
        usuarioResult = Transformations.switchMap(filtroEmail, email ->
                repository.getAgricultorByEmail(email)
        );
    }

    // --- MÉTODOS DE ACCIÓN (La UI los llama) ---

    // Método para GUARDAR un nuevo usuario
    public void registrarAgricultor(Agricultor agricultor) {
        // Delegamos al repositorio (él se encarga de los hilos/background)
        repository.registrarAgricultor(agricultor);
    }

    public void actualizarPerfil(Agricultor agricultor) {
        repository.actualizarPerfil(agricultor);
    }

    // --- MÉTODOS PARA EL LOGIN / BÚSQUEDA ---

    // Paso A: La UI llama a esto cuando el usuario escribe el email y da click en "Buscar"
    public void buscarPorEmail(String email) {
        filtroEmail.setValue(email); // Al hacer esto, el SwitchMap (arriba) se dispara automáticamente.
    }

    // Paso B: La UI observa esto para ver qué encontró el SwitchMap
    public LiveData<Agricultor> getResultadoBusqueda() {
        return usuarioResult;
    }
}