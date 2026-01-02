package com.example.agrotechgamara.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agrotechgamara.R;
import com.example.agrotechgamara.ui.viewmodel.UbicacionViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    private UbicacionViewModel ubicacionVM;
    private String emailLogeado, contraLogeada;

    public InicioFragment() {
        // Required empty public constructor
    }


    public static InicioFragment newInstance(String param1, String param2) {
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

        // 1. Verificamos si existen argumentos
        if (getArguments() != null) {
            // 2. Extraemos la información usando las mismas llaves
            emailLogeado = getArguments().getString("email_agricultor");
            contraLogeada = getArguments().getString("contra_agricultor");
            // ya con esta info podemos hacer lo que sea ya sea enviarla a otro fragment o usarla a nuestro favor
        }

        //inicializar los elementos del layout
    }

    public void initListener() {

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


}
