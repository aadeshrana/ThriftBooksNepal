package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.EditTextPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AboutUs extends AppCompatActivity {
EditText name,emailaddress,mobilenumber,query;
    //http://frame.ueuo.com/thriftbooks/queryrequest.php
    Button submitQuery;
    int success;
    Toolbar toolbar;
    String sendname,sendemail,sendnumber,sendquery;
    private static final String TAG_SUCCESS = "success";
    static final String FTP_PASS = "aadesh";
    private static final String REGISTER_URL = "http://frame.ueuo.com/thriftbooks/queryrequest.php";
    JSONParser jsonParser = new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        name = (EditText) findViewById(R.id.CUName);
        emailaddress=(EditText)findViewById(R.id.CUEmail);
        mobilenumber =(EditText)findViewById(R.id.CUPhone);
        query =(EditText)findViewById(R.id.CUQuery);
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }
    public void submitQuery(View view){
        sendname = name.getText().toString();
        sendemail = emailaddress.getText().toString();
        sendnumber = mobilenumber.getText().toString();
        sendquery = query.getText().toString();
        new CreateUser().execute();
    }


    class CreateUser extends AsyncTask<String, String, String> {


        protected String doInBackground(String... args) {


            try {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("firstname", sendname));
                params.add(new BasicNameValuePair("emailaddress", sendemail));
                params.add(new BasicNameValuePair("phonenumber", sendnumber));
                params.add(new BasicNameValuePair("querys", sendquery));
                //posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", params);

                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User created!", json.toString());

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (success == 1) {
                new AlertDialog.Builder(AboutUs.this)
                        .setTitle("Success")
                        .setMessage("Successfully Sent")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
            else
                Toast.makeText(AboutUs.this, "Failed to create", Toast.LENGTH_SHORT).show();
        }
    }

}
