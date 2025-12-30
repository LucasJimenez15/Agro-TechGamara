package com.example.agrotechgamara.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agrotechgamara.R;
import com.example.agrotechgamara.data.model.Agricultor;
import com.example.agrotechgamara.ui.viewmodel.AgricultorViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*PARA VALIDAR LOS CAMPOS SE PUEDE USAR LA LIBRERIA AWESOMEVALIDATTION QUE LE HICE UN FORK EN GITHUB
 * ES MUY UTIL YA QUE TIENE LAS VALIDACIONES NECESARIAS PARA LOS CAMPOS, EN ESTE CASO LO HICE CON IFS Y CODIGO BASICO
 * CON LA LIBRERIA PATTERNS DE JAVA*/

public class RegistrarseFragment extends Fragment {

    // Componentes de la UI
    private EditText registrarseContraseñaText, verifCorreoText, verifCodigoText, registrarseCorreoText, registrarseNombreText;
    private Button registroBtn;
    private TextView registroBtnInciarSesion;
    private ImageView volverIconoRegistrarse,btnEnviarCodigo;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db; // Para el envío de correos
    private String codigoGenerado = null;
    private AgricultorViewModel agricultorViewModel; // Nuestro ViewModel
    private String emailParaVerificar = ""; // Guardamos el email al que enviamos el código

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
        /*Este codigo hace que la barra de control del celular no se quede por encima de la pantalla o fragmento
        que estamos mostrando y lo sobre ponga*/
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
        volverIconoRegistrarse = root.findViewById(R.id.volverIconoRegistrarse);
        btnEnviarCodigo = root.findViewById(R.id.btnEnviarCodigo);

        /*Aqui se inicializa una instancia de firebase auth y de firebase firestore para poder almacenar el usuario, correo o el inicio de sesion
        por asi decirlo del usuario*/
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /*Inicializar el ViewModel correctamente Usamos 'requireActivity()'
        si queremos compartir el VM con otros fragments, o 'this' si es solo para este.*/
        agricultorViewModel = new ViewModelProvider(this).get(AgricultorViewModel.class);

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
        });

        // 4. Configurar el botón de REGISTRO
        registroBtn.setOnClickListener(v -> {
            realizarRegistro();
        });

        // 5. ir a Login
        registroBtnInciarSesion.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_registrarse_to_login);
        });

        // 6. Volver
        volverIconoRegistrarse.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_registrarse_to_login);
        });

        // 7. verificacion
        // Lógica del botón de verificacion (Solo envía el código)
        btnEnviarCodigo.setOnClickListener(v -> {
            String email = verifCorreoText.getText().toString().trim();

            if (!email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailParaVerificar = email; // Guardamos el email destino
                prepararVerificacion(email);
            } else {
                verifCorreoText.setError("Ingresa un email válido para recibir el código");
            }
        });

    }


    private void realizarRegistro() {
        // A. Obtener datos de los EditText
        String nombre = registrarseNombreText.getText().toString().trim();
        String correo = registrarseCorreoText.getText().toString().trim();
        String pass = registrarseContraseñaText.getText().toString().trim();
        String codigoVerif = verifCodigoText.getText().toString().trim();
        String correoVerif = verifCorreoText.getText().toString().trim();

        // B. Validaciones básicas
        if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty() || codigoVerif.isEmpty()) {
            Toast.makeText(getContext(), "Por favor debe de completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. VALIDACIÓN DE EMAIL
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            registrarseCorreoText.setError("Por favor, ingresa un correo electrónico válido");
            registrarseCorreoText.requestFocus(); // Pone el cursor en el error
            return;
        }

        // 2. VALIDACION DE CONTRASEÑA PARA SU LONGITUD (Opcional pero recomendado)
        if (pass.length() < 6) {
            registrarseContraseñaText.setError("La contraseña debe tener al menos 6 caracteres");
            registrarseContraseñaText.requestFocus(); // Pone el cursor en el error
            return;
        }


        // 3. VALIDACIÓN DE EMAIL PARA OBTENER CODIGO
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correoVerif).matches()) {
            Toast.makeText(getContext(), "Por favor, ingrese un correo valido para obtener el codigo", Toast.LENGTH_SHORT).show();
            return;
        }

        // 4. VERIFICACION DEL CODIGO INGRESADO
        if (codigoVerif.equals(codigoGenerado)) {
            // EL CÓDIGO ES CORRECTO -> Crear usuario en Firebase Auth y local
            registrarEnFirebaseYLocal(nombre, correo, pass);
        } else {
            verifCodigoText.setError("El código ingresado es incorrecto");
            verifCodigoText.requestFocus();
        }
    }

    private void registrarEnFirebaseYLocal(String nombre, String correo, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Paso A: Guardar en base de datos local
                        Agricultor nuevo = new Agricultor();
                        nuevo.setNomAgricultor(nombre);
                        nuevo.setEmailAgricultor(correo);
                        nuevo.setContaAgricultor(pass);
                        agricultorViewModel.registrarAgricultor(nuevo);

                        Toast.makeText(getContext(), "¡Bienvenido, " + nombre + "!", Toast.LENGTH_SHORT).show();
                        irALogin();
                    } else {
                        // Error de Firebase (ej. correo ya en uso en la nube)
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        dameToastdeerror(errorCode);
                    }
                });
    }

    private void prepararVerificacion(String email) {
        // Generar código aleatorio
        codigoGenerado = String.valueOf((int) (Math.random() * 900000) + 100000);

        Map<String, Object> mail = new HashMap<>();
        mail.put("to", email);

        Map<String, Object> message = new HashMap<>();
        message.put("subject", "Tu código de verificación de AgroTech");
        message.put("html", "<h1>Bienvenido</h1><p>Tu código es: <b>" + codigoGenerado + "</b></p>");
        mail.put("message", message);

        // IMPORTANTE: Se usa 'db' (Firestore), no firebaseAuth
        db.collection("mail").add(mail)
                .addOnSuccessListener(ref -> Toast.makeText(getContext(), "Código enviado a " + email, Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al enviar correo", Toast.LENGTH_SHORT).show());
    }

    private void irALogin() {
        NavHostFragment.findNavController(this).navigate(R.id.action_registrarse_to_login);
    }


    public void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(getContext(), "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(getContext(), "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(getContext(), "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(getContext(), "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                registrarseCorreoText.setError("La dirección de correo electrónico está mal formateada.");
                registrarseCorreoText.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(getContext(), "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                registrarseContraseñaText.setError("la contraseña es incorrecta ");
                registrarseContraseñaText.requestFocus();
                registrarseContraseñaText.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(getContext(), "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(getContext(), "Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(getContext(), "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(getContext(), "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                registrarseCorreoText.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                registrarseCorreoText.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(getContext(), "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(getContext(), "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(getContext(), "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(getContext(), "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(getContext(), "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(getContext(), "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(getContext(), "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                registrarseContraseñaText.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                registrarseContraseñaText.requestFocus();
                break;

        }
    }


}