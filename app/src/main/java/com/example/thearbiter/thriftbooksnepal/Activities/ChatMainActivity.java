package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentChat;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomDiagLogin;
import com.example.thearbiter.thriftbooksnepal.R;

/**
 * Created by Gaurav Jayasawal on 1/25/2017.
 */

public class ChatMainActivity extends AppCompatActivity {

    //These store values of Name and Room name sent from Intent
    public static String intentName, intentRoomName;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Notifications.whereAreYou = 0;
        setContentView(R.layout.chat_recylcer_paster);
        intentName = getIntent().getExtras().get("user_name").toString();

        intentRoomName = getIntent().getExtras().get("room_name").toString();
        String[] a = intentRoomName.split("\\|\\|");
        String[] b = a[1].split("---");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ChatMainActivity.this);
        String prefName = preferences.getString("firstNameSharePref1", "");
        if (prefName.equals("")) {
            prefName = FragmentCustomDiagLogin.firstName;
        }

        String finalfinalName;
        if (prefName.equals(b[0])) {
            finalfinalName = b[1];
        } else {
            finalfinalName = b[0];
        }

        Log.d("hi", "bi" + intentRoomName);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(finalfinalName);
        FragmentChat fragmentChat = new FragmentChat();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.chatRecyclerPasterRelativeLayout, fragmentChat, "abc");
        transaction.commit();


    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//
//    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
