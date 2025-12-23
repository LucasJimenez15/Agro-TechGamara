package com.example.agrotechgamara.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.navigation.Navigation;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.agrotechgamara.R;
import com.example.agrotechgamara.ui.activitys.MainActivity;
import com.example.agrotechgamara.ui.activitys.PrincipalActivity;

public class LoginFragment extends Fragment {

    private Button btnIncioSesion;
    private TextView loginBtnRegistrarse;

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
        btnIncioSesion = view.findViewById(R.id.loginBtn);
        loginBtnRegistrarse = view.findViewById(R.id.loginBtnRegistrarse);
    }

    private void initListener() {
        btnIncioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), PrincipalActivity.class);
                startActivity(intent);*/
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
            }
            Al usar .replace(), estabamos evitando usar el sistema de navegación, por lo que el fragmento de destino (RegistrarseFragment) quedaba huérfano y sin herramientas de navegación.
            */

                // --- FORMA CORRECTA (Navigation Component) ---
                // debemos asegurarnos de tener una acción creada en el mobile_navigation.xml
                // que vaya de LoginFragment a RegistrarseFragment
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registrarseFragment);
            }
        });
    }




}