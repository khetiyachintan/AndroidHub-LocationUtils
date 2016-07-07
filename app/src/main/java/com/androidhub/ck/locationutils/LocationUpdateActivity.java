package com.androidhub.ck.locationutils;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sotsys-149 on 5/7/16.
 */
public class LocationUpdateActivity extends FragmentActivity implements View.OnClickListener {

    private Button btnStart, btnStop;
    private TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_update);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
    }

    /*get publish result using broadcast if you want to update UI , check BackgroundLocationService and uncomment  publishResults */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String Latitude = bundle.getString("lat");
                String Longitude = bundle.getString("long");
                txtStatus.setText("Location Update :" + "\nLatitude : " + Latitude + "\nLongitude : " + Longitude);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mMessageReceiver, new IntentFilter(BackgroundLocationService.NOTIFICATION_LOCATION_CHANGE));
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnStart)) {
            /*Start Background Location service*/
            if (!isMyServiceRunning(BackgroundLocationService.class)) {
                startService(new Intent(LocationUpdateActivity.this, BackgroundLocationService.class));
                txtStatus.setText("Service Running");
            } else {
                txtStatus.setText("Service already running");
            }
        } else if (v.equals(btnStop)) {
            if (isMyServiceRunning(BackgroundLocationService.class)) {
                /*STOP LOCATION SERVICE*/
                Intent intentOfYourService = new Intent(LocationUpdateActivity.this, BackgroundLocationService.class);
                stopService(intentOfYourService);
                txtStatus.setText("Service Stop");
            } else {
                txtStatus.setText("Service not running");
            }
        }
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
