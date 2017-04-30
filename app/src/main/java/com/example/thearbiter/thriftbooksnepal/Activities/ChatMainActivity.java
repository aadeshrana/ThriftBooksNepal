package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.Dialog;
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
import android.view.MenuItem;
import android.widget.Toast;

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
        setSupportActionBar(toolbar);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        MenuItem item2 = menu.findItem(R.id.attach);
        MenuItem item1 = menu.findItem(R.id.deliverRequest);

        item1.setIcon(R.drawable.deliver);
        item2.setIcon(R.drawable.attach);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.attach) {
            Toast.makeText(this, "attach coming soon", Toast.LENGTH_SHORT).show();

        }

        if (id == R.id.deliverRequest) {
            final Dialog dialog = new Dialog(ChatMainActivity.this);
            dialog.setContentView(R.layout.deliver_dialog);
            dialog.setCancelable(true);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
