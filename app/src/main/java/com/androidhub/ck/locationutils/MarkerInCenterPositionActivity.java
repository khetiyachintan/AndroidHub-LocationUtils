package com.androidhub.ck.locationutils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerInCenterPositionActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageView imgMapTransparent;
    private boolean isMapChangeRequired = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_center);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.layMapContainer);
        mapFragment.getMapAsync(this);
        imgMapTransparent = (ImageView) findViewById(R.id.imgMapTransparent);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        imgMapTransparent.setOnTouchListener(new MyScaleGestures(MarkerInCenterPositionActivity.this, googleMap));

        // Add a marker in Sydney and move the camera
        final LatLng india = new LatLng(23.031966, 72.525027);
        final Marker marker = mMap.addMarker(new MarkerOptions().position(india));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, 15));
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                if (!isMapChangeRequired) {
                    isMapChangeRequired = true;
                    return;
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, mMap.getCameraPosition().zoom));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, 12));
                isMapChangeRequired = false;
            }

        });
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }
}
