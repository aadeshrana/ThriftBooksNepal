package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentChat;
import com.example.thearbiter.thriftbooksnepal.R;

/**
 * Created by Gaurav Jayasawal on 1/25/2017.
 */

public class ChatMainActivity extends AppCompatActivity {

    //These store values of Name and Room name sent from Intent
    public static String intentName, intentRoomName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Notifications.whereAreYou=0;
        setContentView(R.layout.chat_recylcer_paster);
        intentName = getIntent().getExtras().get("user_name").toString();

        intentRoomName = getIntent().getExtras().get("room_name").toString();
        Log.d("hi","bi"+intentRoomName);

        FragmentChat fragmentChat = new FragmentChat();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.chatRecyclerPasterRelativeLayout, fragmentChat, "abc");
        transaction.commit();


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
