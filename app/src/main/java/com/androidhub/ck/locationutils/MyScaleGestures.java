package com.androidhub.ck.locationutils;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by sotsys-074 on 3/5/16.
 */
public class MyScaleGestures implements View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {

    private View view;
    private ScaleGestureDetector gestureScale;
    private GoogleMap googleMap;

    public MyScaleGestures(Context c, GoogleMap googleMap) {
        gestureScale = new ScaleGestureDetector(c, this);
        this.googleMap = googleMap;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        this.view = view;
        gestureScale.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        double zoom = googleMap.getCameraPosition().zoom;
        zoom = zoom + Math.log(detector.getScaleFactor()) / Math.log(1.5d);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(googleMap.getCameraPosition().target, (float) zoom);
        googleMap.moveCamera(update);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

}
