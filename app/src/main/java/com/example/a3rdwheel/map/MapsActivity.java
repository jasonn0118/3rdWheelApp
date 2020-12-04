package com.example.a3rdwheel.map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.a3rdwheel.R;
import com.example.a3rdwheel.RentalActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient client;
    private static final int REQUEST_CODE = 101;
    private GoogleMap mMap;
    SearchView searchView;

    ArrayList<LatLng>arrayList = new ArrayList<LatLng>();
    LatLng porsche = new LatLng(49.204085, -122.915066);
    LatLng ferrari = new LatLng(49.191421, -122.856483);
    LatLng benz = new LatLng(49.212038, -122.908667);
    LatLng jeep = new LatLng(49.191508, -122.933605);
    LatLng minicooper = new LatLng(49.213334, -122.937397);
    LatLng cadillac = new LatLng (49.193999, -122.889044);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        arrayList.add(porsche);
        arrayList.add(ferrari);
        arrayList.add(benz);
        arrayList.add(jeep);
        arrayList.add(minicooper);
        arrayList.add(cadillac);

        Button gotoList = findViewById(R.id.btn_map_to_list);

        gotoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, RentalActivity.class));
            }
        });

        searchView = findViewById(R.id.search_view_map);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();

                if(location != null || !location.equals("")){
                    if(location.contains("Porsche") || location.contains("porsche")){
                        mMap.addMarker(new MarkerOptions().position(porsche).title("Porsche"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(porsche,15.0f));
                    }else if(location.contains("Ferrari") || location.contains("ferrari")){
                        mMap.addMarker(new MarkerOptions().position(ferrari).title("Ferrari"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ferrari,15.0f));
                    }else if(location.contains("Benz") || location.contains("benz")){
                        mMap.addMarker(new MarkerOptions().position(benz).title("Benz"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(benz,15.0f));
                    }else if(location.contains("Jeep") || location.contains("jeep")){
                        mMap.addMarker(new MarkerOptions().position(jeep).title("Jeep"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jeep,15.0f));
                    }else if (location.contains("Mini Cooper") || location.contains("mini cooper")) {
                        mMap.addMarker(new MarkerOptions().position(minicooper).title("Mini Cooper"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(minicooper,15.0f));
                    }else if (location.contains("Cadillac") || location.contains("cadillac")) {
                        mMap.addMarker(new MarkerOptions().position(cadillac).title("Cadillac"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cadillac,15.0f));
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
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
        mMap = googleMap;


        for(int i =0; i<arrayList.size(); i++){
            mMap.addMarker(new MarkerOptions().position(arrayList.get(0)).title("Porsche"));
            mMap.addMarker(new MarkerOptions().position(arrayList.get(1)).title("Ferrari"));
            mMap.addMarker(new MarkerOptions().position(arrayList.get(2)).title("Benz"));
            mMap.addMarker(new MarkerOptions().position(arrayList.get(3)).title("Jeep"));
            mMap.addMarker(new MarkerOptions().position(arrayList.get(4)).title("Mini Cooper"));
            mMap.addMarker(new MarkerOptions().position(arrayList.get(5)).title("Cadillac"));


            // mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(porsche));

        }
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }else {
            checkLocationPermissionWithRationale();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location info")
                        .setMessage("Need a permission in order to use the app")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}