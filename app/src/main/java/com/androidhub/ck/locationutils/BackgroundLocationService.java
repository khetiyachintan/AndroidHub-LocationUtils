package com.androidhub.ck.locationutils;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by sotsys071 on 11/5/16.
 */
public class BackgroundLocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    public static final String NOTIFICATION_LOCATION_CHANGE = "com.sprent.customer";
    private Location mLastLocation = null;
    LocationListener mLocationListener;

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (mLastLocation == null) {
                    mLastLocation = location;
                    Toast.makeText(getApplicationContext(), "Location Update : " + location.getLatitude() + " , " + location.getLongitude(), Toast.LENGTH_LONG).show();
                    /*Publish result using broadcast if you want to update UI*/
                    publishResults("" + location.getLatitude(), "" + location.getLongitude());
                } else {
                    double meter = getDistanceInMeter(mLastLocation.getLatitude(), mLastLocation.getLongitude(), location.getLatitude(), location.getLongitude());

                    /*Add validaation for meter value change or not , as per requirement*/
                    mLastLocation = location;
                    Toast.makeText(getApplicationContext(), "Location Update : " + location.getLatitude() + " , " + location.getLongitude(), Toast.LENGTH_LONG).show();
                        /*Publish result using broadcast if you want to update UI*/
                    publishResults("" + location.getLatitude(), "" + location.getLongitude());
                }
            }
        };

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationListener != null)
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mLocationListener);
    }

    private void publishResults(String lat, String longitude) {
        Intent intent = new Intent(NOTIFICATION_LOCATION_CHANGE);
        intent.putExtra("lat", lat);
        intent.putExtra("long", longitude);
        sendBroadcast(intent);
    }

    public float getDistanceInMeter(double startLati, double startLongi, double goalLati, double goalLongi) {
        Location loc1 = new Location("");
        loc1.setLatitude(startLati);
        loc1.setLongitude(startLongi);

        Location loc2 = new Location("");
        loc2.setLatitude(goalLati);
        loc2.setLongitude(goalLongi);

        float distanceInMeters = loc1.distanceTo(loc2);

        return distanceInMeters;
    }

}
