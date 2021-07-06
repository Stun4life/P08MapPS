package com.example.p08mapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Button btnNorth, btnSouth, btnCentral;

    Spinner spinner;

    private GoogleMap map;

    ArrayAdapter aa;

    String[] functions = {"North", "South", "Central"};

    LatLng poi_HomeAddress = new LatLng(1.3077962844712865, 103.85433751038616);
    LatLng poi_BugisPlus = new LatLng(1.2997778423386914, 103.854229825728);
    LatLng poi_CitySquareMall = new LatLng(1.3116982976174822, 103.8561929833989);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this, "toais", Toast.LENGTH_SHORT).show();

        spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //int idSelected = Integer.parseInt(functions[position]);
                if (position == 0){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_HomeAddress, 15));
                }
                else if (position == 1) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_CitySquareMall, 15));
                }
                else if (position == 2) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_BugisPlus, 15));
                }
                else {
                    Toast.makeText(MainActivity.this, "Error displaying dropdown", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, functions);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(aa);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)fm.findFragmentById(R.id.map);
        LatLng poi_Singapore = new LatLng(1.357929488892267, 103.86895792417953);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                Toast.makeText(getApplicationContext(), "onMapReady", Toast.LENGTH_SHORT).show();

                map = googleMap;

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Singapore,15));

                UiSettings ui = map.getUiSettings();

                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                }
                else {
                    Log.e("GMAP - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                Marker homeMarker = map.addMarker(new MarkerOptions().position(poi_HomeAddress).title("Home").snippet("Where I Belong").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                Marker bugisMarker = map.addMarker(new MarkerOptions().position(poi_BugisPlus).title("Bugis+").snippet("Second Favorite Shopping Mall").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                Marker mallMarker = map.addMarker(new MarkerOptions().position(poi_CitySquareMall).title("City Square Mall").snippet("Favorite Shopping Mall").icon(BitmapDescriptorFactory.fromResource(R.drawable.staricon)));

                  map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.equals(homeMarker)){
                            Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                            Log.i("Testing", "Home");
                            return false;
                        }
                        else if (marker.equals(bugisMarker)) {
                            Toast.makeText(MainActivity.this, "Bugis+", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        else if (marker.equals(mallMarker)) {
                            Toast.makeText(MainActivity.this, "City Square Mall", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                   }
                });
            }
        });
        btnNorth = findViewById(R.id.btnNorth);
        btnSouth = findViewById(R.id.btnSouth);
        btnCentral = findViewById(R.id.btnCentral);

        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_HomeAddress, 15));
                }
            }
        });
        btnSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_CitySquareMall, 15));
                }
            }
        });
        btnCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_BugisPlus, 15));
                }
            }
        });
    }

}