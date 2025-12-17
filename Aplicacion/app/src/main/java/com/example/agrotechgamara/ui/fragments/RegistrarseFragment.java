package com.example.agrotechgamara.ui.fragments;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agrotechgamara.R;

public class RegistrarseFragment extends Fragment {

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
    }
}