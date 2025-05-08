package com.example.rodriguezgonzalez.pmdm06;

import static android.content.ContentValues.TAG;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.Manifest;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.rodriguezgonzalez.pmdm06.databinding.ActivityMainBinding;
import com.example.rodriguezgonzalez.pmdm06.databinding.DialogPersonalizedBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityMainBinding binding;
    private DialogPersonalizedBinding dialogBinding;
    private String currentPassword;  // Variable global para almacenar la contraseña actual
    private AlertDialog dialog;  // El cuadro de diálogo que mostraremos
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;


    //Se infla la actividad principal, se configura el toolbar, se configura el Alert Dialog
    //se activan los listeners de los botones y se inicializa el mapa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflamos la actividad principal
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Añadimos el toolbar y configuramos la toolbar
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        //Se configura el Alert Dialog con nuestro diseño personalizado
        dialogBinding = DialogPersonalizedBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogBinding.getRoot());
        dialog = builder.create();

        //Inicialización del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.d("GoogleMaps", "Map error.");
        }
        //Inicializa el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //Añadimos el escuchador de cambios en el editText del diálogo
        //Lo que queremos es que el botón se active cuando el usuario escriba en el editText
        dialogBinding.passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogBinding.finishButton.setEnabled(!s.toString().isEmpty());
            }

            //Métodos que se implementan por defecto
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }

    //Controla la inicialización del mapa, el estilo, la peticion de permisos ubicación y la creación de los marcadores
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //Definimos el estilo del mapa que implementamos en nuestro fichero Json
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));
            if (!success) {
                Log.e(TAG, "Estilo parseado ha fallado");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "No se puede encontrar el estilo. Erro: ", e);
        }

        //Coordenadas de los puntos de la gincana
        LatLng inicioGincana = new LatLng(37.916408, -2.907658);     // 1
        LatLng segundoMarcador = new LatLng(37.911078, -2.951149);   // 2
        LatLng tercerMarcador = new LatLng(38.010340, -2.880170);    // 3
        LatLng cuartoMarcador = new LatLng(38.022300, -2.944000);    // 4
        LatLng quintoMarcador = new LatLng(38.019203, -2.936253);    // 5
        LatLng sextoMarcador = new LatLng(38.069200, -2.887900);     // 6
        LatLng septimoMarcador = new LatLng(38.072219, -2.885847);   // 7
        LatLng octavoMarcador = new LatLng(38.049324, -2.856042);    // 8
        LatLng novenoMarcador = new LatLng(38.029826, -2.915882);    // 9
        LatLng finGincana = new LatLng(38.061753, -2.940352);        // 10


        //Marcadores de la gincana con temática de Crash Bandicoot
        mMap.addMarker(new MarkerOptions()
                .position(inicioGincana)
                .title("Isla N. Sanity")
                .snippet("¡Comenzamos nuestra aventura!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crash)));

        mMap.addMarker(new MarkerOptions()
                .position(segundoMarcador)
                .title("La carrera de ruinas")
                .snippet("Secretos oscuros...")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crash)));

        mMap.addMarker(new MarkerOptions()
                .position(tercerMarcador)
                .title("Bosque de Aku Aku")
                .snippet("Aquí te espera Aku Aku para darte poderes.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crash)));

        mMap.addMarker(new MarkerOptions()
                .position(cuartoMarcador)
                .title("Laboratorio de N. Gin")
                .snippet("Misteriosos experimentos se cocinan aquí.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crash)));

        mMap.addMarker(new MarkerOptions()
                .position(quintoMarcador)
                .title("Río del Cortex Vortex")
                .snippet("Cruza el río y esquiva las plantas carnívoras.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crash)));

        mMap.addMarker(new MarkerOptions()
                .position(sextoMarcador)
                .title("Templo del Nitro")
                .snippet("Los misterios del Nitro.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crash)));

        mMap.addMarker(new MarkerOptions()
                .position(septimoMarcador)
                .title("Cueva de Uka Uka")
                .snippet("Las sombras susurran... o es Uka Uka.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crash)));

        mMap.addMarker(new MarkerOptions()
                .position(octavoMarcador)
                .title("Wumpa, wumpa")
                .snippet("Busca el fruto prohibido")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crash)));

        mMap.addMarker(new MarkerOptions()
                .position(novenoMarcador)
                .title("Isla de las gemas")
                .snippet("¿Qué es eso que brilla?")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crash)));

        mMap.addMarker(new MarkerOptions()
                .position(finGincana)
                .title("Templo final del Time Trial")
                .snippet("¡Victoria! Pero rápido, el tiempo vuela.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.crash)));


        //Petición de permisos de ubicación al usuario mediante dialogo
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        //Al clicar en la ventana del marcador se lanza la función showMarkerDialog que mostrará un diálogo personalizado
        mMap.setOnInfoWindowClickListener(this::showMarkerDialog);

        //Implementa el listener para mostrar o no la ubicación del usuario según el estado del switch
        binding.switchUbicacion.setOnCheckedChangeListener(this::showMyLocationOnMapActive);
    }

    //Recibe el marcador cuya ventana ha sido clicada,
    //Se compara el título del marcador con el nombre de la actividad para mostrar el diálogo personalizado
    //Además, se almacena la contraseña de la actividad en la variable global currentPassword
    private void showMarkerDialog(Marker marker) {
        String title = marker.getTitle();
        if (title.equals("Isla N. Sanity")) { //1
            dialogBinding.dialogTitle.setText(R.string.act_1);
            dialogBinding.dialogInfo.setText(R.string.text_mark_1);
            currentPassword = "NSANITY";
            dialogBinding.passwordInfo.setText("Password: " + currentPassword);
        } else if (title.equals("La carrera de ruinas")) { //2
            dialogBinding.dialogTitle.setText(R.string.act_2);
            dialogBinding.dialogInfo.setText(R.string.text_mark_2);
            currentPassword = "TOTEM";
            dialogBinding.passwordInfo.setText("Password: " + currentPassword);
        } else if (title.equals("Bosque de Aku Aku")) { //3
            dialogBinding.dialogTitle.setText(R.string.act_3);
            dialogBinding.dialogInfo.setText(R.string.text_mark_3);
            currentPassword = "LUZ";
            dialogBinding.passwordInfo.setText("Password: " + currentPassword);
        } else if (title.equals("Laboratorio de N. Gin")) { //4
            dialogBinding.dialogTitle.setText(R.string.act_4);
            dialogBinding.dialogInfo.setText(R.string.text_mark_4);
            currentPassword = "MUTANTE";
            dialogBinding.passwordInfo.setText("Password: " + currentPassword);
        } else if (title.equals("Río del Cortex Vortex")) { //5
            dialogBinding.dialogTitle.setText(R.string.act_5);
            dialogBinding.dialogInfo.setText(R.string.text_mark_5);
            currentPassword = "ESCAPE";
            dialogBinding.passwordInfo.setText("Password: " + currentPassword);
        } else if (title.equals("Templo del Nitro")) { //6
            dialogBinding.dialogTitle.setText(R.string.act_6);
            dialogBinding.dialogInfo.setText(R.string.text_mark_6);
            currentPassword = "MISTERIOS";
            dialogBinding.passwordInfo.setText("Password: " + currentPassword);
        } else if (title.equals("Cueva de Uka Uka")) { //7
            dialogBinding.dialogTitle.setText(R.string.act_7);
            dialogBinding.dialogInfo.setText(R.string.text_mark_7);
            currentPassword = "SOMBRA";
            dialogBinding.passwordInfo.setText("Password: " + currentPassword);
        } else if (title.equals("Wumpa, wumpa")) { //8
            dialogBinding.dialogTitle.setText(R.string.act_8);
            dialogBinding.dialogInfo.setText(R.string.text_mark_8);
            currentPassword = "PROFUNDIZA";
            dialogBinding.passwordInfo.setText("Password: " + currentPassword);
        } else if (title.equals("Isla de las gemas")) { //9
            dialogBinding.dialogTitle.setText(R.string.act_9);
            dialogBinding.dialogInfo.setText(R.string.text_mark_9);
            currentPassword = "PACIENCIA";
            dialogBinding.passwordInfo.setText("Password: " + currentPassword);
        } else if (title.equals("Templo final del Time Trial")) { //10
            dialogBinding.dialogTitle.setText(R.string.act_10);
            dialogBinding.dialogInfo.setText(R.string.text_mark_10);
            currentPassword = "SEMEJANZA";
            dialogBinding.passwordInfo.setText("Password: " + currentPassword);
        }

        dialog.show();

        //Configura el OnClickListener para el botón de "Aceptar" del diálogo personalizado
        dialogBinding.finishButton.setOnClickListener(v -> {
            Log.d("MainActivity", "Button clicked");
            String password = dialogBinding.passwordField.getText().toString();
            checkPasswordAndCloseDialog(password);
        });
    }

    //Controla que la contraseña introducida por el usuario sea la correcta y muestra un mensaje indicándo si se ha acertado o no
    private void checkPasswordAndCloseDialog(String password) {
        if (currentPassword.equals(password.toUpperCase())) {
            Toast.makeText(this, "¡Muy Bien! Has acertado la contraseña.", Toast.LENGTH_SHORT).show();
            //Se resetea el editText
            dialogBinding.passwordField.setText("");
            //Se vuelve a desactivar el botón
            dialogBinding.finishButton.setEnabled(false);
            //Cierra el diálogo
            dialog.dismiss();
        } else {
            //Si no se ha acertado la contraseña se informa al usuario
            Toast.makeText(this, "Contraseña incorrecta. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
        }
    }

    //Controla la ubicación según el estado del switch y lanza un mensaje al usuario avisando del estado
    private void showMyLocationOnMapActive(CompoundButton compoundButton, boolean b) {

        if (binding.switchUbicacion.isChecked()) {
            showMyLocationOnMap();
            Toast.makeText(this, "Ubicación activada", Toast.LENGTH_SHORT).show();

        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(false);
            Toast.makeText(this, "Se ha desactivado tu ubicación", Toast.LENGTH_SHORT).show();
        }
    }


    //Muestra la capa de "mi ubicación" (punto azul) en el mapa
    private void showMyLocationOnMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                        }
                    });
        }
    }

    //Respuesta a la petición de permisos
    //Se ejecuta automáticamente cuando el ususario responde a la petición de permisos

    //Comprueba si el permiso fue concedido.
    //Si lo fue, vuelve a verificar que tiene el permiso de ubicación.
    //Si va bien, activa la capa de "mi ubicacion" (punto azul) en el mapa.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //Sólo mostramos la ubicación si el switch está activado
                    if (binding.switchUbicacion.isChecked()) {
                        showMyLocationOnMap();
                    }
                }
            }
        }
    }
}