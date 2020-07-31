package com.el.mkoba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Terms_and_coditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_coditions);
        WebView web= findViewById(R.id.web1);
        web.loadUrl("file:///android_asset/privacymkoba.html");


    }
}
