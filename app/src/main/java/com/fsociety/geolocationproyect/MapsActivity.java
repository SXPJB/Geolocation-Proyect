package com.fsociety.geolocationproyect;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String longitud;
    private String latitud;
    private Bundle extra;
    private SupportMapFragment mapFragment;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        extra = getIntent().getExtras();
        longitud = extra.getString("Longitud");
        latitud = extra.getString("Latitud");
        mapaDisponible();
    }
    private void mapaDisponible()
    {
        if(mapFragment == null)
        {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(MapsActivity.this);
        }

        if(mapFragment != null)
        {
            Toast.makeText(this, "Mapa de Google disponible", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        double dLongitud = Double.parseDouble(longitud);
        double dLatitud = Double.parseDouble(latitud);
        try
        {
            Geocoder geoCoder = new Geocoder(this, Locale.getDefault());

            List<Address> addresses = geoCoder.getFromLocation(dLatitud, dLongitud, 5);


            //mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            LatLng nuevaPosicion = new LatLng(dLatitud,dLongitud);

            mMap.setMyLocationEnabled(true);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nuevaPosicion, 20));

            String direccionCoordenadas = "Sin Datos";
            if(addresses.size() > 0)
            {
                address = addresses.get(0);
                direccionCoordenadas = address.getAddressLine(0)
                        + " " + address.getPostalCode()
                        + " " + address.getLocality()
                        + ", " + address.getCountryName();
            }
            mMap.addMarker(new MarkerOptions()
                    .title(direccionCoordenadas)
                    .position(nuevaPosicion));
            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(nuevaPosicion)
                    .zoom(16.0f)
                    .tilt(45.0f)
                    .bearing(45.0f)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
