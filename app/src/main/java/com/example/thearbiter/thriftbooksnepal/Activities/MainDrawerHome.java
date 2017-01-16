package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchool;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavDraerMain;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavMenu;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavMenuRecycler;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainDrawerHome extends AppCompatActivity {
    Toolbar toolbar;

    ArrayList<String> userName = new ArrayList<>();
    ArrayList<String> firstName = new ArrayList<>();
    ArrayList<String> lastName = new ArrayList<>();
    ArrayList<String> nameofBook = new ArrayList<>();
    ArrayList<String> nameofAuthor = new ArrayList<>();
    ArrayList<String> priceOfBook = new ArrayList<>();
    ArrayList<String> homeAddress = new ArrayList<>();
    ArrayList<String> school = new ArrayList<>();
    ArrayList<String> image1name = new ArrayList<>();
    ArrayList<String> image2name = new ArrayList<>();
    ArrayList<String> image3name = new ArrayList<>();
    ArrayList<String> phoneNumber = new ArrayList<>();
    ArrayList<String> emailAddress = new ArrayList<>();


    public static String firstName1;
    public static String lastName1;
    public static String phoneNumber1;
    public static String school1;
    public static String emailAddress1;
    public static String username1;
    public static String password1;


    private static final String LOGIN_URL = "http://frame.ueuo.com/thriftbooks/pullallitems.php";
    private static final String FETCH_NUMBER_URL = "http://frame.ueuo.com/thriftbooks/fetchalldetails.php";
    JSONParser jsonParser = new JSONParser();
    ProgressBar  progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer_home);
        progressBar = (ProgressBar)findViewById(R.id.drawerProgress);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        CustomDiagFindSchool obj = new CustomDiagFindSchool();
        obj.findAllSchool();
        FragmentNavMenu fragmentNavMenu = (FragmentNavMenu) getSupportFragmentManager().findFragmentById(R.id.mainfragmentDrawer);
        fragmentNavMenu.setUp(R.id.mainfragmentDrawer, (DrawerLayout) findViewById(R.id.mainDrawerLayout), toolbar);

        FragmentNavMenuRecycler fragmentAdpater = new FragmentNavMenuRecycler();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mainfragmentDrawer, fragmentAdpater, "abc");

        transaction.commit();
       new PullAllAlevelItems().execute();


    }
    /////

    ///}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_drawer_home, menu);
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
        if (id == R.id.log_out) {
            Intent in = new Intent(MainDrawerHome.this, Login.class);
            startActivity(in);
            SharedPreferences sharedpref;
            sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = sharedpref.edit();

            edit.clear();
            edit.putString("c", "notchecked");
            edit.apply();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    public class PullAllAlevelItems extends AsyncTask<String, String, String> {

//

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... args) {

            Log.d("ETA PUGYO HAI", "");
            try {

                Log.d("Before Vie Orders", "");
                List<NameValuePair> params1 = new ArrayList<>();

                params1.add(new BasicNameValuePair("course", "others"));

                Log.d("CHOICE CHOICE", "" + "others");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params1);


                userName.clear();
                firstName.clear();
                lastName.clear();
                nameofBook.clear();
                nameofAuthor.clear();
                priceOfBook.clear();
                homeAddress.clear();
                school.clear();
                image1name.clear();
                image2name.clear();
                image3name.clear();
                phoneNumber.clear();
                emailAddress.clear();

                try {
                    for (int i = 0; i < 10; i++) {
                        userName.add(json.getString("a" + i));
                        firstName.add(json.getString("b" + i));
                        lastName.add(json.getString("c" + i));
                        nameofBook.add(json.getString("d" + i));
                        nameofAuthor.add(json.getString("e" + i));
                        priceOfBook.add(json.getString("f" + i));
                        homeAddress.add(json.getString("g" + i));
                        school.add(json.getString("h" + i));
                        image1name.add(json.getString("i" + i));
                        image2name.add(json.getString("j" + i));
                        image3name.add(json.getString("k" + i));
                        phoneNumber.add(json.getString("l" + i));
                        emailAddress.add(json.getString("m" + i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                FragmentNavDraerMain.arrayUserName = new String[userName.size()];
                FragmentNavDraerMain.arrayFirstName = new String[firstName.size()];
                FragmentNavDraerMain.arrayLastName = new String[lastName.size()];
                FragmentNavDraerMain.arrayNameOfBook = new String[nameofBook.size()];
                FragmentNavDraerMain.arrayNameOfAuthor = new String[nameofAuthor.size()];
                FragmentNavDraerMain.arrayPriceOfBook = new String[priceOfBook.size()];
                FragmentNavDraerMain.arrayHomeAddress = new String[homeAddress.size()];
                FragmentNavDraerMain.arraySchool = new String[school.size()];
                FragmentNavDraerMain.arrayImage1Name = new String[image1name.size()];
                FragmentNavDraerMain.arrayImage2Name = new String[firstName.size()];
                FragmentNavDraerMain.arrayImage3Name = new String[firstName.size()];
                FragmentNavDraerMain.arrayPhoneNumber = new String[firstName.size()];
                FragmentNavDraerMain.arrayEmailAddress = new String[firstName.size()];

                FragmentNavDraerMain.arrayUserName = userName.toArray(new String[userName.size()]);
                FragmentNavDraerMain.arrayFirstName = firstName.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayLastName = lastName.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayNameOfBook = nameofBook.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayNameOfAuthor = nameofAuthor.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayPriceOfBook = priceOfBook.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayHomeAddress = homeAddress.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arraySchool = school.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayImage1Name = image1name.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayImage2Name = image2name.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayImage3Name = image3name.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayPhoneNumber = phoneNumber.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayEmailAddress = emailAddress.toArray(new String[firstName.size()]);

                FragmentNavDraerMain.title = FragmentNavDraerMain.arrayNameOfBook;
                FragmentNavDraerMain.price = FragmentNavDraerMain.arrayPriceOfBook;
                FragmentNavDraerMain.sellerName = FragmentNavDraerMain.arrayFirstName;

                Log.d("ETA PUGYOO??", "" + FragmentNavDraerMain.title);

            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
progressBar.setVisibility(View.GONE);
            FragmentNavDraerMain fragmentNavDraerMain = new FragmentNavDraerMain();
            FragmentManager manager3 = getFragmentManager();
            FragmentTransaction transaction1 = manager3.beginTransaction();
            transaction1.add(R.id.mainFragmentNavHome, fragmentNavDraerMain, "asdf");
            transaction1.commit();


        }
    }

}
