package com.example.thearbiter.thriftbooksnepal.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav Jayasawal on 1/15/2017.
 */

public class Splash2 extends AppCompatActivity {
    Context context;
    ArrayList<String> chatUsersNeMessages = new ArrayList<>();
    String passValue = "Guest";
    public static final String PULL_NEW_CHATS="http://frame.ueuo.com/thriftbooks/pullneChats.php";
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sharedpref.edit();

        SharedPreferences.Editor edit = sharedpref.edit();
        edit.putString("firstNameSharePref", passValue);
        edit.apply();
        ImageView imageView = (ImageView) findViewById(R.id.splashScreenImageVie);

        try {

            Login.strLoginUsername = sharedpref.getString("a", "Guest");
            String checked = sharedpref.getString("c", "notchecked");

        } catch (Exception e) {
            e.printStackTrace();
        }

        context = getApplicationContext();
        new PullNewChats().execute();


    }

    public class PullNewChats extends AsyncTask<String, String, String> {
        private JSONParser jsonParser2 = new JSONParser();
        JSONObject json1 = null;

        @Override
        protected String doInBackground(String... args) {

            try {

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Splash2.this);
                String tempName = pref.getString("a", "");
                List<NameValuePair> params4 = new ArrayList<>();
                params4.add(new BasicNameValuePair("anything", tempName));

                json1 = jsonParser2.makeHttpRequest(PULL_NEW_CHATS, "POST", params4);
            } catch (Exception e) {
                e.printStackTrace();
            }

            chatUsersNeMessages.clear();

            try {
                for (int i = 0; i < json1.length(); i++) {

                    chatUsersNeMessages.add(json1.getString("a" + i));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            Notifications.chatUsersNeMessagesArray = new String[chatUsersNeMessages.size()];
            Notifications.chatUsersNeMessagesArray = chatUsersNeMessages.toArray(new String[chatUsersNeMessages.size()]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(getApplication(), Notifications.class);
            startActivity(intent);
            finish();
        }
    }


}
