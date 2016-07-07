package com.androidhub.ck.locationutils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by sotsys-149 on 5/7/16.
 */
public class AnimateMarkerActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_animate);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.layMapContainer);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        final LatLng india = new LatLng(23.031966, 72.525027);
        marker = mMap.addMarker(new MarkerOptions().position(india));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, 15));


        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                LatLngInterpolator latLngInterpolator = new LatLngInterpolator.Spherical();
                MarkerAnimation.animateMarkerToGB(marker, new LatLng(23.031122, 72.525827), latLngInterpolator);
            }
        }, 2000);

    }

}
