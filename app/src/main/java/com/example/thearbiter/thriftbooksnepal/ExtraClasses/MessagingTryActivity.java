package com.example.thearbiter.thriftbooksnepal.ExtraClasses;

/**
 * Created by Gaurav Jayasawal on 1/18/2017.
 */

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thearbiter.thriftbooksnepal.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagingTryActivity extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    Button button;
    String app_server_url = "http://frame.ueuo.com/images/fcm_insert.php";
    String FETCH_NUMBER_URL = "http://frame.ueuo.com/images/send_notification.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_try);
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String recent_token = FirebaseInstanceId.getInstance().getToken();
                SharedPreferences sharedpref;
                sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final String token = sharedpref.getString("token", "");
                Log.d("RECENT TOKEN FOR LYF", "" + token);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("fcm_token", recent_token);
                        params.put("User", "Gaurav");


                        return params;
                    }
                };
                MySingleton.getmInstance(MessagingTryActivity.this).addToRequestque(stringRequest);
            }
        });

    }

    class LoggedInUserInfoPuller extends AsyncTask<String, String, String> {
        List<NameValuePair> params1 = new ArrayList<>();


        @Override
        protected String doInBackground(String... params) {
            params1.add(new BasicNameValuePair("user", "Gaurav"));
            params1.add(new BasicNameValuePair("message", "This Is Message"));
            params1.add(new BasicNameValuePair("title", "This is title"));
            Log.d("Username is", "");
            jsonParser.makeHttpRequest(FETCH_NUMBER_URL, "POST", params1);
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void sendMessage(View view) {
        new LoggedInUserInfoPuller().execute();
    }
}
