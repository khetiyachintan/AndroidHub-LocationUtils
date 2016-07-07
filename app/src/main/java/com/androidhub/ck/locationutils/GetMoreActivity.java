package com.androidhub.ck.locationutils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by sotsys-149 on 5/7/16.
 */
public class GetMoreActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView Webview = new WebView(this);
        Webview.getSettings().setJavaScriptEnabled(true);

        if (getIntent().getStringExtra("callfrom").equalsIgnoreCase("getmore")) {
            Webview.loadUrl("https://chintankhetiya.wordpress.com/2016/07/05/android-basic-utils-function/");
        } else {
            Webview.loadUrl("https://chintankhetiya.wordpress.com/");
        }
        setContentView(Webview);
    }
}
