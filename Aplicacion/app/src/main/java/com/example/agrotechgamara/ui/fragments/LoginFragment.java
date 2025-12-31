package com.example.agrotechgamara.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

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
import com.example.agrotechgamara.R;
import com.example.agrotechgamara.ui.activitys.MainActivity;
import com.example.agrotechgamara.ui.activitys.PrincipalActivity;
import com.example.agrotechgamara.ui.viewmodel.AgricultorViewModel;
import com.google.firebase.auth.FirebaseAuth;
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
                realizarLogin();
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

    private void realizarLogin() {

    }


}