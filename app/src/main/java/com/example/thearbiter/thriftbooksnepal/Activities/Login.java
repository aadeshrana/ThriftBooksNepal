package com.example.thearbiter.thriftbooksnepal.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavDraerMain;
import com.example.thearbiter.thriftbooksnepal.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    private static final String LOGIN_URL = "http://frame.ueuo.com/thriftbooks/login.php";
    private static final String FETCH_NUMBER_URL = "http://frame.ueuo.com/thriftbooks/fetchalldetails.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    EditText loginUsername, loginPassword;
    static String strLoginUsername, strLoginPassword;
    private ProgressDialog pdialog;
    JSONParser jsonParser = new JSONParser();
    int success;
    public static String firstName;
    public static String lastName;
    public static String phoneNumber;
    public static String school;
    public static String emailAddress;
    public static String username;
    public static String password;
    CheckBox keepLoggedIn;

    static String checkBoxChecked = "notchecked";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginUsername = (EditText) findViewById(R.id.loginUsername);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        keepLoggedIn = (CheckBox) findViewById(R.id.keepLoggedIn);

        //For keeping logged in

        keepLoggedIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(Login.this, "Please check this on private devices only.", Toast.LENGTH_SHORT).show();

                if (isChecked) {
                    checkBoxChecked = "checked";

                } else {
                    checkBoxChecked = "notchecked";
                    SharedPreferences sharedpref;
                    sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = sharedpref.edit();
                    edit.putString("c", checkBoxChecked);
                    edit.apply();
                }
            }

        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void sharedInfoPuller(Context context) {
        new LoggedInUserInfoPuller(context).execute();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    class LoggedInUserInfoPuller extends AsyncTask<String, String, String> {
        List<NameValuePair> params1 = new ArrayList<>();
        Context context;

        public LoggedInUserInfoPuller(Context context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... params) {
            params1.add(new BasicNameValuePair("username", strLoginUsername));
            Log.d("Username is", "");
            JSONObject json1 = jsonParser.makeHttpRequest(FETCH_NUMBER_URL, "POST", params1);
            try {
                firstName = json1.getString("firstname");
                lastName = json1.getString("lastname");
                phoneNumber = json1.getString("phonenumber");
                school = json1.getString("school");
                emailAddress = json1.getString("emailaddress");
                username = strLoginUsername;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Intent in = new Intent(context, Login.class);
//            context.startActivity(in);
//            ((Activity) context).finish();

            FragmentNavDraerMain nav = new FragmentNavDraerMain();
            nav.pullAllMainItems(context);
            ((Activity) context).finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    ///******//// OnClick Buttons Are Here //////****///////


    ////When you press the button login//////
    public void loginButton(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        strLoginUsername = loginUsername.getText().toString();
        strLoginPassword = loginPassword.getText().toString();

        Login login = new Login();
        strLoginPassword = login.SHA1(strLoginPassword);
        //For keeping logged in
        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        SharedPreferences.Editor edit = sharedpref.edit();
        edit.putString("passwordShared", loginPassword.getText().toString());
        edit.commit();
        password = strLoginPassword;

        try {
            new LoginUser(view.getContext()).execute();

        } catch (Exception e) {

        }
    }

    //When You press on new account////
    public void newAccount(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    //Asyntask Classes below this line  /// // // / / //

    class LoginUser extends AsyncTask<String, String, String> {
        Context context;

        public LoginUser(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(Login.this);
            pdialog.setMessage("Logging In....");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(true);
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            //check for success tag


            try {
                //building parameters
                List<NameValuePair> params = new ArrayList<>();
                List<NameValuePair> params1 = new ArrayList<>();
                params1.add(new BasicNameValuePair("username", strLoginUsername));
                params.add(new BasicNameValuePair("username", strLoginUsername));
                params.add(new BasicNameValuePair("password", strLoginPassword));

                //getting product details by making http request

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                //check your log for json response
                success = json.getInt(TAG_SUCCESS);

                if (json == null) {
                }
                //json success element

                JSONObject json1 = jsonParser.makeHttpRequest(FETCH_NUMBER_URL, "POST", params1);


                firstName = json1.getString("firstname");
                lastName = json1.getString("lastname");
                phoneNumber = json1.getString("phonenumber");
                school = json1.getString("school");
                emailAddress = json1.getString("emailaddress");
                username = strLoginUsername;


                Log.d("The", "number is" + phoneNumber);


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String file_url) {
            // TODO Auto-generated method stub
            if (success == 1) {
                SharedPreferences sharedpref;
                sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = sharedpref.edit();
                edit.putString("a", loginUsername.getText().toString());
                edit.putString("c", checkBoxChecked);

                edit.putString("firstNameSharePref", firstName);
                edit.putString("lastNameSharePref", lastName);
                edit.putString("emailSharePref", emailAddress);
                edit.putString("phoneSharePref", phoneNumber);
                edit.putString("schoolSharePref", school);

                edit.apply();
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                FragmentNavDraerMain frag = new FragmentNavDraerMain();
                frag.pullAllMainItems(context);
                Intent intent = new Intent(getApplication(), MainDrawerHome.class);
                startActivity(intent);
            } else {
                Toast.makeText(Login.this, "Cannot find account. Please sign up", Toast.LENGTH_SHORT).show();
            }
            pdialog.dismiss();

            if (file_url != null) {
                Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
}
