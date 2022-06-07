package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Toolbar;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment supportMapFragment;
    SearchView searchView;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);


        //tb.setDisplayHomeAsUpEnabled(true);


       supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.activitymaps);

        tb = findViewById(R.id.maptoolbar);
        tb.setTitle("Τοποθεσία Ομάδας");
        setActionBar(tb);
        getActionBar().setDisplayHomeAsUpEnabled(true);



        String mapitem = getIntent().getStringExtra("key");

        searchView = findViewById(R.id.search);
        searchView.setQuery(mapitem,true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String location = searchView.getQuery().toString();
                List<Address> list = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(GoogleMapsActivity.this);

                    try {
                        list = geocoder.getFromLocationName(location, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address add = list.get(0);

                    LatLng latLng = new LatLng(add.getLatitude(), add.getLongitude());

                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        supportMapFragment.getMapAsync(this);




    }


    @Override
    public boolean onNavigateUp() {
        finish();

        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

    }



}