package com.example.agrotechgamara.ui.fragments;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agrotechgamara.R;
import com.example.agrotechgamara.data.model.Agricultor;
import com.example.agrotechgamara.ui.viewmodel.AgricultorViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrarseFragment extends Fragment {

    // Componentes de la UI
    private EditText registrarseContraseñaText, verifCorreoText, verifCodigoText, registrarseCorreoText, registrarseNombreText;
    private Button registroBtn;
    private TextView registroBtnInciarSesion;

    // Nuestro ViewModel
    private AgricultorViewModel agricultorViewModel;

    public RegistrarseFragment() {
        // Required empty public constructor
    }

    public static RegistrarseFragment newInstance() {
        RegistrarseFragment fragment = new RegistrarseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_registrarse, container, false);
        // Inicializar el ExecutorService solo una vez
        init(root);
        return root;
    }

    private void init(View root) {
        /*Este codigo hace que la barra de control del celular no se quede por encima de la pantalla o fragmento que estamos mostrando y lo sobre ponga*/
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            int bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            v.setPadding(0, 0, 0, bottom);
            return insets;
        });

        registrarseContraseñaText = root.findViewById(R.id.registrarseContraseñaText);
        verifCorreoText = root.findViewById(R.id.verifCorreoText);
        verifCodigoText = root.findViewById(R.id.verifCodigoText);
        registrarseCorreoText = root.findViewById(R.id.registrarseCorreoText);
        registrarseNombreText = root.findViewById(R.id.registrarseNombreText);
        registroBtn = root.findViewById(R.id.registroBtn);
        registroBtnInciarSesion = root.findViewById(R.id.registroBtnInciarSesion);

        /*Inicializar el ViewModel correctamente Usamos 'requireActivity()' si queremos compartir el VM con otros fragments, o 'this' si es solo para este.*/
        agricultorViewModel = new ViewModelProvider(this).get(AgricultorViewModel.class);

        // 4. Configurar el botón de REGISTRO
        registroBtn.setOnClickListener(v -> {
            realizarRegistro();
        });

        // 5. (Opcional) Configurar botón para ir a Login
        registroBtnInciarSesion.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_registrarse_to_login);
        });

    }

    private void realizarRegistro() {
        // A. Obtener datos de los EditText
        String nombre = registrarseNombreText.getText().toString().trim();
        String correo = registrarseCorreoText.getText().toString().trim();
        String pass = registrarseContraseñaText.getText().toString().trim();

        // B. Validaciones básicas
        if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // C. Crear el objeto Agricultor
        Agricultor nuevoAgricultor = new Agricultor();
        nuevoAgricultor.setNomAgricultor(nombre);
        nuevoAgricultor.setEmailAgricultor(correo);
        nuevoAgricultor.setContaAgricultor(pass); // En una app real, ¡encripta la contraseña!

        // D. Guardar usando el ViewModel
        agricultorViewModel.registrarAgricultor(nuevoAgricultor);

        Toast.makeText(getContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();

        // E. Navegar a la siguiente pantalla (Home o Login)
        NavHostFragment.findNavController(this).navigate(R.id.action_registrarse_to_login);
    }

    /*¿Cómo usaría la parte de "Búsqueda Reactiva" en este Fragmento de Registro?
Si quisieras verificar si el correo ya existe antes de registrarlo, podrías usar la lógica que explicamos en la Parte 1 dentro del métodoinit:*/


    /*// Dentro de init()
// Observamos si el usuario ya existe
agricultorViewModel.getResultadoBusqueda().observe(getViewLifecycleOwner(), agricultorEncontrado -> {
    if (agricultorEncontrado != null) {
        // Si entra aquí, significa que el email YA EXISTE en la base de datos
        registrarseCorreoText.setError("Este correo ya está registrado");
        registroBtn.setEnabled(false); // Desactivamos el botón
    } else {
        // El correo está libre
        registrarseCorreoText.setError(null);
        registroBtn.setEnabled(true);
    }
});

// Listener para cuando el usuario termina de escribir el correo (Focus change)
registrarseCorreoText.setOnFocusChangeListener((v, hasFocus) -> {
    if (!hasFocus) { // Cuando pierde el foco (terminó de escribir)
        String email = registrarseCorreoText.getText().toString();
        // Disparamos el Gatillo para verificar
        agricultorViewModel.buscarPorEmail(email);
    }
});*/


}