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

import java.util.Objects;

import static android.R.id.edit;

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

           /* if (checked.equals("checked") && !Objects.equals(Login.strLoginUsername, "aa") && !Objects.equals(Login.username, "")) {

                /////FETCHING DETAILS OF SAVED USER FROM THE SERVER
                Login login = new Login();
                //    login.sharedInfoPuller(this);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent in = new Intent(getApplication(), MainDrawerHome.class);
                        startActivity(in);
                        finish();

                    }
                });
                thread.start();


            } else {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Intent in = new Intent(getApplication(), Login.class);
                            startActivity(in);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                });
                thread.start();*/


            //}
            Intent intent = new Intent(getApplication(),MainDrawerHome.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        context = getApplicationContext();


    }


}
