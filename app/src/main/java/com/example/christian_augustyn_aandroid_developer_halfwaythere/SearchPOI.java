package com.example.christian_augustyn_aandroid_developer_halfwaythere;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;

public class SearchPOI extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager lm;
    double lat, lng;
    private EditText friendpos, poi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_p_o_i);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        friendpos = findViewById(R.id.friendLocation);
        poi = findViewById(R.id.poi);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Criteria criteria = new Criteria();
        String bestProvider = String.valueOf(lm.getBestProvider(criteria, true)).toString();
        Location location = lm.getLastKnownLocation(lm.GPS_PROVIDER);
        if (location != null) {
            onLocationChanged(location);

        } else {
            lm.requestLocationUpdates(bestProvider, 2000, 0, SearchPOI.this);
        }
    }

    public void searchPoi(View view) throws IOException {
        String friendpos = this.friendpos.getText().toString();
        String poi = this.poi.getText().toString();

        //gets the geo location of the address typed in by the user and generates the lat and lng from the first item in the list of possible geocodes
        Geocoder coder = new Geocoder(SearchPOI.this);
        List<Address> friend_addr = coder.getFromLocationName(friendpos, 3);
        Address fgeo = friend_addr.get(0);
        //create a marker for the friend
        LatLng flatlng = new LatLng(fgeo.getLatitude(), fgeo.getLongitude());
        mMap.addMarker(new MarkerOptions().position(flatlng).title("Friend"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(flatlng));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) { //displays the user on the map with a marker
        mMap = googleMap;
        LatLng userpos = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(userpos).title("You"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userpos));
    }

    @Override
    public void onLocationChanged(Location location) {
        lng = location.getLongitude();
        lat = location.getLatitude();
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
}
