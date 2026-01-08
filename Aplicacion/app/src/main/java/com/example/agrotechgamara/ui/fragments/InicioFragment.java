package com.example.agrotechgamara.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.agrotechgamara.R;
import com.example.agrotechgamara.ui.viewmodel.UbicacionViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class InicioFragment extends Fragment {

    private UbicacionViewModel ubicacionVM;
    private String emailLogeado, contraLogeada;
    private Button cerrarSesion;

    public InicioFragment() {
        // Required empty public constructor
    }

    public static InicioFragment newInstance() {
        InicioFragment fragment = new InicioFragment();
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
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        init(root);
        initListener();
        return root;
    }

    private void init(View view) {
        //inicializar los elementos del layout
        cerrarSesion = view.findViewById(R.id.cerrarSesion);
    }

    public void initListener() {

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Sesion cerrada", Toast.LENGTH_SHORT).show();
                irALogin(v);
            }
        });

        /*
        // 1. OBSERVAMOS el resultado de la API de Google Maps
        ubicacionVM.detalleMapaAvanzado.observe(getViewLifecycleOwner(), detalle -> {
            // Este código se ejecuta cuando el Repositorio termina la llamada a la API
            textoDetalleAvanzado.setText(detalle);
        });

        // 2. INICIAMOS la solicitud (ej. al presionar un botón)
        botonCalcularArea.setOnClickListener(v -> {
            int loteIdActual = obtenerIdLoteDeMapa();
            ubicacionVM.solicitarDetalleAvanzado(loteIdActual);
        });
        */

    }

    private void irALogin(View v) {
        NavController navController = Navigation.findNavController(requireView());

        // Verificamos si el destino actual es realmente el inicio antes de intentar salir de él
        if (navController.getCurrentDestination() != null &&
                navController.getCurrentDestination().getId() == R.id.inicioFragment) {

            Navigation.findNavController(v).navigate(R.id.action_inicioFragment_to_loginFragment);

        }

    }


}
