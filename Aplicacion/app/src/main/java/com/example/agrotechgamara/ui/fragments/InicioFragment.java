package com.example.agrotechgamara.ui.fragments;

import android.content.ContentQueryMap;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agrotechgamara.R;
import com.example.agrotechgamara.ui.activitys.MainActivity;
import com.example.agrotechgamara.ui.viewmodel.UbicacionViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class InicioFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private UbicacionViewModel ubicacionVM;
    private String emailLogeado, contraLogeada;
    private EditText txtLongitud, txtLatitud;
    private GoogleMap mMap;

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
        //Inicializar el ViewModel
        ubicacionVM = new ViewModelProvider(this).get(UbicacionViewModel.class);
        txtLongitud = view.findViewById(R.id.txtLongitud);
        txtLatitud = view.findViewById(R.id.txtLatitud);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }

    public void initListener() {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        LatLng arg = new LatLng(	-34.61315, -58.37723);
        mMap.addMarker(new MarkerOptions().position(arg).title("BS AS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(arg));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        txtLatitud.setText(" "+latLng.latitude);
        txtLongitud.setText(" "+latLng.longitude);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        txtLatitud.setText(" "+latLng.latitude);
        txtLongitud.setText(" "+latLng.longitude);
    }
}
