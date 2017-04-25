package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentMessager;
import com.example.thearbiter.thriftbooksnepal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gaurav Jayasawal on 1/18/2017.
 */

public class FinalBuyersActivity extends AppCompatActivity {

    Toolbar toolbar;
    JSONParser jsonParser = new JSONParser();
    private static final String PULL_ALL_MESSAGES_URL = "http://frame.ueuo.com/thriftbooks/pullAllMessages.php";
    ArrayList<String> sender = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    ArrayList<String> message = new ArrayList<>();
    public static String newRoomName;

    ArrayList<String> time = new ArrayList<>();
    public static String senderArray[], messageArray[], timeArray[];
    private static final String CREATE_ROOM_TABLE = "http://frame.ueuo.com/images/createRoomThrift.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity_recycle_paster);
        Log.d("OH GOD", "");
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new LoadAllMessages().execute();


    }

    // THIS IS LOAD NOTIFICATION CLASS
    public class LoadAllMessages extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {


                List<NameValuePair> param1 = new ArrayList<>();
//                param1.add(new BasicNameValuePair("username", spUsername));

                Log.d("SHARED ISSS", "" + FragmentMessager.finalBuyersActivityImage2 + FragmentMessager.finalBuyersActivityPriceOfBook);

                String salesId = FragmentMessager.finalBuyersActivityImage2 + FragmentMessager.finalBuyersActivityPriceOfBook;

                param1.add(new BasicNameValuePair("saleid", salesId));

                sender.clear();
                message.clear();
                time.clear();
                JSONObject json;

                json = jsonParser.makeHttpRequest(PULL_ALL_MESSAGES_URL, "POST", param1);
                //full json response


                try {
                    for (int i = 0; i < json.length(); i++) {
                        sender.add(json.getString("b" + i));
                        message.add(json.getString("a" + i));
                        time.add(json.getString("c" + i));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {

            }

            FinalBuyersActivity.senderArray = new String[sender.size()];
            FinalBuyersActivity.messageArray = new String[message.size()];
            FinalBuyersActivity.timeArray = new String[time.size()];

            FinalBuyersActivity.senderArray = sender.toArray(new String[sender.size()]);
            FinalBuyersActivity.messageArray = message.toArray(new String[message.size()]);
            FinalBuyersActivity.timeArray = time.toArray(new String[time.size()]);

            try {
                Log.d("REACH HERE", "" + sender.get(0));

                Toast.makeText(FinalBuyersActivity.this, "NULL BABYY", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {

            }
            FragmentMessager.title = message.toArray(new String[message.size()]);
            FragmentMessager.nameOfSender = sender.toArray(new String[sender.size()]);
            FragmentMessager.Notiftime = time.toArray(new String[time.size()]);
            sender = null;
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            FragmentMessager fragmentMessager = new FragmentMessager();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.recyclerPasterId, fragmentMessager, "asdf");
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final_buyer, menu);
        return true;
    }

    //CREATES ROOM IN REALTIME DATABASE AND PhpMySql DATABASE
    class CreateRoom extends AsyncTask<String, String, String> {
        List<NameValuePair> params1 = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FinalBuyersActivity.this);
            String userNameOfUser = preferences.getString("a", "");
            String new2RoomName = userNameOfUser + FragmentMessager.finalBuyersActivityUsernameOfSeller;
            params1.add(new BasicNameValuePair("roomName", new2RoomName));

            Log.d("Room","Name"+FinalBuyersActivity.newRoomName);
            try {
                jsonParser.makeHttpRequest(CREATE_ROOM_TABLE, "POST", params1);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;

        }
        if (id == R.id.leaveAMessage) {

            // FIREBASE ROOM CREATION IS DONE HERE
            // TABLE FOR ROOM IS ALSO CREATED IN THE DATABASE


            final Map<String, Object> map = new HashMap<>();
            try {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FinalBuyersActivity.this);
                String userNameOfUser = preferences.getString("a", "");
                newRoomName = userNameOfUser +"***"+FragmentMessager.finalBuyersActivityUsernameOfSeller;
            } catch (Exception e) {
                e.printStackTrace();
            }

            map.put(newRoomName, "");

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(newRoomName)) {
                        Toast.makeText(FinalBuyersActivity.this, "Already there", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(FinalBuyersActivity.this, "Not present NEWW", Toast.LENGTH_SHORT).show();
                        new CreateRoom().execute();
                        root.updateChildren(map);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //THIS CREATES TABLE TO STORE IN OUR DATABASE

            Intent intent = new Intent(getApplicationContext(), ChatMainActivity.class);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FinalBuyersActivity.this);
            String username = preferences.getString("a", "");
            intent.putExtra("room_name",username+"***"+FragmentMessager.finalBuyersActivityUsernameOfSeller);
            intent.putExtra("user_name", FragmentMessager.finalBuyersActivityNameOfSeller);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
