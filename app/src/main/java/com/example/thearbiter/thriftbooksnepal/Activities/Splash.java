package com.example.thearbiter.thriftbooksnepal.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavDraerMain;
import com.example.thearbiter.thriftbooksnepal.R;

/**
 * Created by Gaurav Jayasawal on 1/15/2017.
 */

public class Splash extends AppCompatActivity {
Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getApplicationContext();
        FragmentNavDraerMain nav = new FragmentNavDraerMain();
        nav.pullAllMainItems(this);
        ImageView imageView = (ImageView) findViewById(R.id.splashScreenImageVie);
        imageView.setImageResource(R.drawable.splash);

    }
}
