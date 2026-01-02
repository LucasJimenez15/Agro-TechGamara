package com.example.agrotechgamara.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agrotechgamara.R;
import com.example.agrotechgamara.ui.viewmodel.AgricultorViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {

    private Button btnIncioSesion, loginOlvidemicontra;
    private TextView loginBtnRegistrarse;
    private EditText loginEditTextEmail, loginEditTextPassword;
    private ImageView logoGoogle;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db; // Para el envío de correos
    private AgricultorViewModel agricultorViewModel;

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
    }

    private void realizarLogin(View view) {
        // Observamos si el usuario ya existe
        agricultorViewModel.getResultadoBusqueda().observe(getViewLifecycleOwner(), agricultorEncontrado -> {
            if (agricultorEncontrado != null) {
                // Si entra aquí, significa que el email YA EXISTE en la base de datos

                String email = loginEditTextEmail.getText().toString();
                String contra = loginEditTextPassword.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(email, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            irainicio(email, contra, view);
                        } else {
                            // Error de Firebase (ej. correo ya en uso en la nube)
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            dameToastdeerror(errorCode);
                        }
                    }
                });
            }
        });
    }

    private void irainicio(String email, String contra, View view) {
        // 1. Creamos el paquete (Bundle)
        Bundle bundle = new Bundle();

        // 2. Agregamos la información (Clave, Valor)
        bundle.putString("email_agricultor", email);
        bundle.putString("contra_agricultor", contra);

        //3. Enviamostodo al fragment de inicio
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_inicioFragment, bundle);

        /*Conceptos clave
        Bundle: Es una clase de Android diseñada para transportar datos a través de procesos y componentes.
        Funciona como un Diccionario (Map) de pares clave-valor.

        Claves (Keys): Siempre usa String para las llaves. Es una buena práctica usar constantes
        (por ejemplo: public static final String KEY_EMAIL = "email";) para evitar errores de dedo entre un fragmento y otro.

        Tipos de datos: Puedes enviar tipos primitivos (int, float, boolean, String). Si quieres enviar un objeto completo
        (como tu clase Agricultor), esa clase debe implementar la interfaz Serializable o Parcelable.*/
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
                loginEditTextEmail.setError("La dirección de correo electrónico está mal formateada.");
                loginEditTextEmail.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(getContext(), "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                loginEditTextPassword.setError("la contraseña es incorrecta ");
                loginEditTextPassword.requestFocus();
                loginEditTextPassword.setText("");
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
                loginEditTextEmail.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                loginEditTextEmail.requestFocus();
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
                loginEditTextPassword.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                loginEditTextPassword.requestFocus();
                break;

        }
    }

}