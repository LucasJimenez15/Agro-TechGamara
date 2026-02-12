package com.example.agrotechgamara.ui.activitys;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.agrotechgamara.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // 1. Buscamos el NavHostFragment:
        // Es el contenedor en el XML donde se van a inflar los fragmentos.
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frameContainerPrincipal);

        if (navHostFragment != null) {
            // El NavController es el "Cerebro" de la navegación.
            // Sabe qué rutas existen y cómo saltar de una a otra.
            NavController navController = navHostFragment.getNavController();

            // 2. Vincular el BottomNavigationView con el NavController.
            // Al hacer esto, Android detecta automáticamente cuando haces clic en un ítem
            // del menú y busca un destino con el mismo ID en tu nav_graph.xml.
            BottomNavigationView bnv = findViewById(R.id.btnNav);
            NavigationUI.setupWithNavController(bnv, navController);

            // 3. Gestión de datos recibidos:
            // Si venimos del Login, el Intent trae el correo del agricultor.
            // Verificamos 'savedInstanceState == null' para que esto solo ocurra al abrir la actividad,
            // no cuando se gire la pantalla (evitando reinicios innecesarios).
            if (savedInstanceState == null) {
                manejarNavegacionInicial(navController);
            }
        }
    }

    private void manejarNavegacionInicial(NavController navController) {
        String email = getIntent().getStringExtra("email_agricultor");
        String target = getIntent().getStringExtra("target_fragment");

        // Si el login nos dice que vayamos a "inicio", navegamos programáticamente
        if ("inicio".equals(target) && email != null) {
            Bundle bundle = new Bundle();
            bundle.putString("email_agricultor", email);

            // Enviamos el email como argumento al primer fragmento (InicioFragment)
            navController.navigate(R.id.inicioFragment, bundle);
        }
    }
}