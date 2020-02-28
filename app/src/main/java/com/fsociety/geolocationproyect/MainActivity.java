package com.fsociety.geolocationproyect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LocationManager locManager;
    private LocationListener locListener;
    private TextView tvLatitud, tvLongitud, tvPrecision, tvAltura, tvPorDefecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLatitud = (TextView) findViewById(R.id.tvLatitud);
        tvLongitud = (TextView) findViewById(R.id.tvLongitud);
        tvPrecision = (TextView) findViewById(R.id.tvPrecision);
        tvAltura = (TextView) findViewById(R.id.tvAltura);
        tvPorDefecto = (TextView) findViewById(R.id.tvPorDefecto);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void rastreoGPS() {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mostrarPosicion(loc);
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locListener);

    }

    private String[] mostrarPosicion(Location loc) {
        String[] datos;
        if(loc != null)
        {
            tvPorDefecto.setText("(valores GPS)");
            tvLatitud.setText(String.valueOf(loc.getLatitude()));
            tvLongitud.setText(String.valueOf(loc.getLongitude()));
            tvAltura.setText(String.valueOf(loc.getAltitude()));
            tvPrecision.setText(String.valueOf(loc.getAccuracy()));
            datos = new String[]{String.valueOf(loc.getLongitude()),String.valueOf(loc.getLatitude())};
        }
        else
        {
            tvPorDefecto.setText("(valores por defecto)");
            datos = new String[]{String.valueOf(40.4167754), String.valueOf(-3.7037901999999576),"Posición por defecto"};
            tvLatitud.setText(String.valueOf(40.4167754));
            tvLongitud.setText(String.valueOf(-3.7037901999999576));
            tvAltura.setText(String.valueOf(15.00));
            tvPrecision.setText(String.valueOf(1.0));
        }
        return datos;
    }
}
