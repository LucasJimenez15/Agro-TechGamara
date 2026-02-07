package com.example.agrotechgamara.ui.activitys;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.agrotechgamara.R;
import com.example.agrotechgamara.ui.fragments.InicioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // 1. Configurar el NavController correctamente
        // IMPORTANTE: El ID aquí debe ser el del NavHostFragment del XML (ej: R.id.nav_host_fragment)
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frameContainerPrincipal);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // 2. Vincular Automáticamente el BottomNavigationView con el NavController
            // Esto hace que el menú funcione solo, sin necesidad de IFs o Switches
            BottomNavigationView bnv = findViewById(R.id.btnNav);
            NavigationUI.setupWithNavController(bnv, navController);

            // 3. Recibir datos del Intent y navegar si es necesario
            String email = getIntent().getStringExtra("email_agricultor");
            String target = getIntent().getStringExtra("target_fragment");

            // Procesar datos del Intent solo la primera vez que se crea la actividad
            if (savedInstanceState == null) {
                manejarNavegacionInicial(navController);
            }
        }
    }
    private void manejarNavegacionInicial(NavController navController) {
        String email = getIntent().getStringExtra("email_agricultor");
        String target = getIntent().getStringExtra("target_fragment");

        if ("inicio".equals(target) && email != null) {
            Bundle bundle = new Bundle();
            bundle.putString("email_agricultor", email);

            // Usar popUpTo para limpiar el stack si es necesario
            navController.navigate(R.id.inicioFragment, bundle);
        }
    }
}
