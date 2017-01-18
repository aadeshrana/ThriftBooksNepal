package com.example.thearbiter.thriftbooksnepal.ExtraClasses;

/**
 * Created by Gaurav Jayasawal on 1/18/2017.
 */

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Gaurav Jayasawal on 1/15/2017.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = sharedpref.edit();
        edit.putString("token", recent_token);
        edit.apply();

    }
}
