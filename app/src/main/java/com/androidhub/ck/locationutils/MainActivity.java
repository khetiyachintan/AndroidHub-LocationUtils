package com.androidhub.ck.locationutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout layGetMore, layFooter;
    private Button btnAnimate, btnCenterPosition, btnBgService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layGetMore = (LinearLayout) findViewById(R.id.layGetMore);
        layGetMore.setOnClickListener(this);

        layFooter = (LinearLayout) findViewById(R.id.layFooter);
        layFooter.setOnClickListener(this);

        btnAnimate = (Button) findViewById(R.id.btnAnimate);
        btnAnimate.setOnClickListener(this);

        btnCenterPosition = (Button) findViewById(R.id.btnCenterPosition);
        btnCenterPosition.setOnClickListener(this);

        btnBgService = (Button) findViewById(R.id.btnBgService);
        btnBgService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(layGetMore)) {
            Intent intent = new Intent(MainActivity.this, GetMoreActivity.class);
            intent.putExtra("callfrom", "getmore");
            startActivity(intent);
        } else if (v.equals(layFooter)) {
            Intent intent = new Intent(MainActivity.this, GetMoreActivity.class);
            intent.putExtra("callfrom", "footer");
            startActivity(intent);
        } else if (v.equals(btnAnimate)) {
            if (isKeyMissing()) {
                Intent intent = new Intent(MainActivity.this, AnimateMarkerActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Google API KEY MISSING, Please add in String.xml ", Toast.LENGTH_LONG).show();
            }
        } else if (v.equals(btnCenterPosition)) {
            if (isKeyMissing()) {
                Intent intent = new Intent(MainActivity.this, MarkerInCenterPositionActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Google API KEY MISSING, Please add in String.xml ", Toast.LENGTH_LONG).show();
            }
        } else if (v.equals(btnBgService)) {
            if (isKeyMissing()) {
                Intent intent = new Intent(MainActivity.this, LocationUpdateActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Google API KEY MISSING, Please add in String.xml ", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isKeyMissing() {
        if (getString(R.string.google_maps_key) != null && getString(R.string.google_maps_key).length() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
