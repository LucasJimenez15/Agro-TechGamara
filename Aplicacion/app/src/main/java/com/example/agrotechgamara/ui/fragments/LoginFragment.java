package com.example.agrotechgamara.ui.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.agrotechgamara.R;
import com.example.agrotechgamara.data.model.Agricultor;
import com.example.agrotechgamara.ui.activitys.PrincipalActivity;
import com.example.agrotechgamara.ui.viewmodel.AgricultorViewModel;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executors;

public class LoginFragment extends Fragment {

    private Button btnIncioSesion, loginOlvidemicontra;
    private TextView loginBtnRegistrarse;
    private EditText loginEditTextEmail, loginEditTextPassword;
    private ImageView logoGoogle;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db; // Para el envío de correos
    private AgricultorViewModel agricultorViewModel;
    private static final int REQUEST_CODE = 54645;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CredentialManager credentialManager;
    private FirebaseAuth mAuth;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        // Inicializar el ExecutorService solo una vez
        init(root);
        initListener();
        return root;
    }

    private void init(View view) {
        /*Este codigo hace que la barra de control del celular no se quede por encima de la pantalla o fragmento
        que estamos mostrando y lo sobre ponga*/
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            int bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            v.setPadding(0, 0, 0, bottom);
            return insets;
        });

        btnIncioSesion = view.findViewById(R.id.loginBtn);
        loginBtnRegistrarse = view.findViewById(R.id.loginBtnRegistrarse);
        loginEditTextPassword = view.findViewById(R.id.LoginEditTextPassword);
        loginEditTextEmail = view.findViewById(R.id.LoginEditTextEmail);
        logoGoogle = view.findViewById(R.id.logoGoogle);
        loginOlvidemicontra = view.findViewById(R.id.loginOlvidosucontra);

        /*Aqui se inicializa una instancia de firebase auth y de firebase firestore para poder almacenar el usuario, correo o el inicio de sesion
        por asi decirlo del usuario y ademas el uso de el envio de emails*/
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /*Inicializar el ViewModel correctamente Usamos 'requireActivity()'
        si queremos compartir el VM con otros fragments, o 'this' si es solo para este.*/
        agricultorViewModel = new ViewModelProvider(requireActivity()).get(AgricultorViewModel.class);

        mAuth = FirebaseAuth.getInstance();
        // Inicializamos el Credential Manager
        credentialManager = CredentialManager.create(requireContext());

        logoGoogle.setOnClickListener(v -> lanzarLoginGoogle());
    }

    private void initListener() {
        btnIncioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLogin(v);
            }
        });

        loginBtnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                                R.anim.entrada,  // enter
                                R.anim.salida  // exit
                        ).replace(R.id.fragmentContainerView, RegistrarseFragment.newInstance())
                        .commit();
            } Al usar .replace(), estabamos evitando usar el sistema de navegación, por lo que el fragmento de destino (RegistrarseFragment) quedaba huérfano y sin herramientas de navegación.*/

                // --- FORMA CORRECTA (Navigation Component) ---
                // debemos asegurarnos de tener una acción creada en el mobile_navigation.xml
                // que vaya de LoginFragment a RegistrarseFragment
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registrarseFragment);
            }
        });

        loginOlvidemicontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoOlvideContraseña();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Verificamos la sesión aquí, cuando la navegación es segura
        // y cuando ya esta creada la vista, y ademas el navcontroller ya se cargo
        comprobarSesion(view);
    }

    private void comprobarSesion(View view) {
        try {
            if (firebaseAuth == null) firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                irainicio(firebaseUser.getEmail(), view);
            }
        } catch (Exception e) {
            Log.e("ERROR_SESSION", "Error al recuperar usuario: " + e.getMessage());
        }
    }

    private void realizarLogin(View view) {
        String email = loginEditTextEmail.getText().toString().trim();
        String contra = loginEditTextPassword.getText().toString().trim();

        if (email.isEmpty() || contra.isEmpty()) {
            mostrarToastCorto("Completa todos los campos");
            return;
        }

        // 1. Intentamos primero Firebase (Online)
        firebaseAuth.signInWithEmailAndPassword(email, contra)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // LOGIN EXITOSO POR FIREBASE
                        Toast.makeText(getContext(), "Sesión iniciada (Online)", Toast.LENGTH_SHORT).show();

                        agricultorViewModel.buscarPorEmail(email);
                        // Observamos si el usuario ya existe
                        agricultorViewModel.getResultadoBusqueda().observe(getViewLifecycleOwner(), agricultorEncontrado -> {
                            if (agricultorEncontrado == null) {
                                // Guardar en base de datos local si no existe
                                Agricultor nuevo = new Agricultor();
                                nuevo.setEmailAgricultor(email);
                                nuevo.setContaAgricultor(contra);
                                agricultorViewModel.registrarAgricultor(nuevo);
                            }
                        });

                        irainicio(email, view);
                    } else {
                        // 2. Si Firebase falla, verificamos si es por falta de red
                        Exception exception = task.getException();
                        if (exception instanceof com.google.firebase.FirebaseNetworkException) {
                            // NO HAY INTERNET: Intentamos el respaldo con Room
                            intentoLoginOffline(email, contra, view);
                        } else {
                            // ERROR REAL (Contraseña mal, usuario no existe en Firebase, etc.)
                            mostrarToastCorto("Error de autenticación online");
                        }
                    }
                });
    }

    private void intentoLoginOffline(String email, String contra, View view) {
        // Buscamos en Room solo como última instancia
        agricultorViewModel.buscarPorEmail(email);

        // IMPORTANTE: Quitamos observadores previos para no acumular basura
        agricultorViewModel.getResultadoBusqueda().removeObservers(getViewLifecycleOwner());

        agricultorViewModel.getResultadoBusqueda().observe(getViewLifecycleOwner(), agricultor -> {
            if (agricultor != null) {
                if (agricultor.getContaAgricultor().equals(contra)) {
                    Toast.makeText(getContext(), "Modo Offline: Bienvenido " + agricultor.getNomAgricultor(), Toast.LENGTH_LONG).show();
                    irainicio(email, view);
                } else {
                    mostrarToastCorto("Contraseña local incorrecta (Offline) ");
                }
            } else {
                mostrarToastCorto("No existe ese usuario, prueba conectandote a internet");
            }
        });
    }

    private void irainicio(String email, View view) {

        NavController navController = Navigation.findNavController(requireView());

        // Verificamos si el destino actual es realmente el Login antes de intentar salir de él
        if (navController.getCurrentDestination() != null &&
                navController.getCurrentDestination().getId() == R.id.loginFragment) {

            // 1. Crear el Intent para la nueva actividad
            Intent intent = new Intent(getContext(), PrincipalActivity.class);
            // 2. Pasar los datos directamente al Intent
            intent.putExtra("email_agricultor", email);
            // 3. (Opcional) Indicar a qué fragmento ir si PrincipalActivity tiene varios
            intent.putExtra("target_fragment", "inicio");
            // 4. Iniciar la actividad y cerrar la actual (para que no puedan volver al login con el botón atrás)
            startActivity(intent);
            requireActivity().finish();

        }


        /*Conceptos clave
        Bundle: Es una clase de Android diseñada para transportar datos a través de procesos y componentes.
        Funciona como un Diccionario (Map) de pares clave-valor.

        Claves (Keys): Siempre usa String para las llaves. Es una buena práctica usar constantes
        (por ejemplo: public static final String KEY_EMAIL = "email";) para evitar errores de dedo entre un fragmento y otro.

        Tipos de datos: Puedes enviar tipos primitivos (int, float, boolean, String). Si quieres enviar un objeto completo
        (como tu clase Agricultor), esa clase debe implementar la interfaz Serializable o Parcelable.*/
    }

    private void mostrarDialogoOlvideContraseña() {
        EditText resetMail = new EditText(getContext());
        resetMail.setHint("ejemplo@correo.com");
        resetMail.setPadding(50, 50, 50, 50);

        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(getContext());
        passwordResetDialog.setTitle("¿Restablecer contraseña?");
        passwordResetDialog.setMessage("Ingresa tu correo para recibir el enlace de restablecimiento.");
        passwordResetDialog.setView(resetMail);

        passwordResetDialog.setPositiveButton("Enviar", (dialog, which) -> {
            String mail = resetMail.getText().toString().trim();
            if (mail.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, ingresa un correo", Toast.LENGTH_SHORT).show();
            } else {
                enviarEmailDeRecuperacion(mail);
            }
        });

        passwordResetDialog.setNegativeButton("Cancelar", (dialog, which) -> {
            // Cerrar diálogo
        });

        passwordResetDialog.create().show();
    }

    private void enviarEmailDeRecuperacion(String email) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Enlace enviado a tu correo", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void mostrarToastCorto(String mensaje) {
        Toast toast = Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT);
        toast.show();
        // Usamos un Handler para cancelarlo después de 1 segundo (1000ms)
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);
    }

    private void lanzarLoginGoogle() {
        // 1. Configurar la opción de Google
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false) // Ponlo en false para que siempre deje elegir cuenta
                .setServerClientId(getString(R.string.default_web_client_id))
                .build();

        // 2. Crear la solicitud
        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        // 3. Ejecutar de forma asíncrona
        credentialManager.getCredentialAsync(
                requireContext(),
                request,
                null,
                Executors.newSingleThreadExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        handleSignIn(result.getCredential());
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        Log.e("GOOGLE_ERROR", "Error: " + e.getMessage());
                    }
                }
        );
    }

    // Asegúrate de que el parámetro sea de androidx.credentials.Credential
    private void handleSignIn(androidx.credentials.Credential credential) {
        if (credential instanceof androidx.credentials.CustomCredential
                && credential.getType().equals(com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            try {
                com.google.android.libraries.identity.googleid.GoogleIdTokenCredential googleIdTokenCredential =
                        com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.createFrom(credential.getData());

                firebaseAuthWithGoogle(googleIdTokenCredential.getIdToken());

            } catch (Exception e) {
                Log.e("GOOGLE_ERROR", "Error al parsear el token", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        irainicio(user.getEmail(), requireView());
                    } else {
                        mostrarToastCorto("Fallo la autenticación con Firebase");
                    }
                });
    }


}