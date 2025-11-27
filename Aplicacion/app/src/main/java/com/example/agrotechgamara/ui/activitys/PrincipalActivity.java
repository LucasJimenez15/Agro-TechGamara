package com.example.agrotechgamara.ui.activitys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.agrotechgamara.R;
import com.example.agrotechgamara.ui.fragments.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        BottomNavigationView bnv = findViewById(R.id.btnNav);

        // Fragment por defecto
        if (savedInstanceState == null) {
            cargarFragment(new LoginFragment());
        }

        // Listener usando IF (EVITA EL ERROR)
        bnv.setOnItemSelectedListener(item -> {

            int id = item.getItemId();
            Fragment fragment = null;

            if (id == R.id.nav_inicio) {
                fragment = new LoginFragment();
            } else if (id == R.id.nav_lotes) {
                fragment = new LoginFragment();
            } else if (id == R.id.nav_control) {
                fragment = new LoginFragment();
            } else if (id == R.id.nav_perfil) {
                fragment = new LoginFragment();
            }

            if (fragment != null) {
                cargarFragment(fragment);
                return true;
            }

            return false;
        });
    }

    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit();
    }
}
