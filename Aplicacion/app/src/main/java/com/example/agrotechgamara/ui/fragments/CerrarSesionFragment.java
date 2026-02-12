package com.example.agrotechgamara.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.agrotechgamara.R;
import com.example.agrotechgamara.ui.activitys.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class CerrarSesionFragment extends Fragment {

    private Button cerrarSesion;

    public CerrarSesionFragment() {
        // Required empty public constructor
    }

    public static CerrarSesionFragment newInstance(String param1, String param2) {
        CerrarSesionFragment fragment = new CerrarSesionFragment();
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
        View root = inflater.inflate(R.layout.fragment_cerrar_sesion, container, false);
        init(root);
        initListener();
        return root;
    }

    private void init(View view) {
        //inicializar los elementos del layout
        cerrarSesion = view.findViewById(R.id.cerrarSesion);
    }
    private void initListener() {
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Sesion cerrada", Toast.LENGTH_SHORT).show();
                irALogin(v);
            }
        });
    }

    private void irALogin(View v) {
        NavController navController = Navigation.findNavController(requireView());

        // Verificamos si el destino actual es realmente el inicio antes de intentar salir de él
        if (navController.getCurrentDestination() != null &&
                navController.getCurrentDestination().getId() == R.id.inicioFragment) {

            //Si el LoginFragment está en OTRA Activity (Recomendado para Auth)
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish(); // Cerramos la principalActivity para que no puedan volver atrás


        }

    }

}