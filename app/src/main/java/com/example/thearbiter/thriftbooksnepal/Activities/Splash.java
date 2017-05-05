package com.example.thearbiter.thriftbooksnepal.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.thearbiter.thriftbooksnepal.R;

/**
 * Created by Gaurav Jayasawal on 1/15/2017.
 */

public class Splash extends AppCompatActivity {
    Context context;
    String passValue = "Guest";
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sharedpref.edit();

        SharedPreferences.Editor edit = sharedpref.edit();
        edit.putString("firstNameSharePref",passValue);
        edit.apply();
        ImageView imageView = (ImageView) findViewById(R.id.splashScreenImageVie);
        imageView.setImageResource(R.drawable.splash);
        try {


            Login.strLoginUsername = sharedpref.getString("a", "Guest");

            String checked = sharedpref.getString("c", "notchecked");



            Intent intent = new Intent(getApplication(),MainDrawerHome.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

        context = getApplicationContext();


    }


}
