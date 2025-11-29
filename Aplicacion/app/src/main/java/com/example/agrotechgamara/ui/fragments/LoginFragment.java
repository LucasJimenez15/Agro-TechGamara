package com.example.agrotechgamara.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.agrotechgamara.R;
import com.example.agrotechgamara.ui.activitys.MainActivity;
import com.example.agrotechgamara.ui.activitys.PrincipalActivity;

public class LoginFragment extends Fragment {
    private Button btnIncioSesion;

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
        btnIncioSesion = view.findViewById(R.id.LoginBtn);
    }

    private void initListener() {
        btnIncioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PrincipalActivity.class);
                startActivity(intent);
            }
        });
    }

}