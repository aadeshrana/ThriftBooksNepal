package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentALevelBuy;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav Jayasawal on 1/9/2017.
 */

public class MainALevelBuyer extends AppCompatActivity {
    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/thriftbooks/pullallitems.php";
    Toolbar toolbar;
    String loggedIn;
    int timesBackPressed = 0;
    public static int didEverythingLoad = 1;
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
    JSONParser jsonParser = new JSONParser();
    ProgressBar progressBarAlevel;
    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paster_layout_recycle);
        new PullAllAlevelItems().execute();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        progressBarAlevel = (ProgressBar) findViewById(R.id.alevelBuyProgress);
        progressBarAlevel.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(this);
        loggedIn = sharedpref.getString("loggedIn", "noValue");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commonone, menu);
        MenuItem item3 = menu.findItem(R.id.messager);
        MenuItem item4 = menu.findItem(R.id.search);
        item3.setIcon(R.drawable.openmessage);
        item4.setIcon(R.drawable.search);
        if (loggedIn.equals("noValue")) {


            item3.setVisible(false);

        } else {

            item3.setVisible(true);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (didEverythingLoad == 1) {
            if (id == R.id.messager) {

                if (loggedIn.equals("noValue")) {
                    Toast.makeText(this, "Please log in to continue.", Toast.LENGTH_SHORT).show();
                } else {
                    Notifications not = new Notifications();
                    Log.d("value", "bookname1");
                    Intent in = new Intent(getBaseContext(), Notifications.class);
                    startActivity(in);
                }
            }
            if (id == R.id.search) {
                Intent intent = new Intent(this, SearchAllData.class);
                startActivity(intent);
            }
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }
            switch (item.getItemId()) {

                case android.R.id.home:
                    Log.d("pugyo", "eta9");
                    onBackPressed();
                    return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void pullAllitems() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timesBackPressed++;
        if (didEverythingLoad == 1) {
            if (timesBackPressed == 2) {
                finish();
            }
        }
    }

    public class PullAllAlevelItems extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            try {

                Log.d("Before Vie Orders", "");
                List<NameValuePair> params1 = new ArrayList<>();

                params1.add(new BasicNameValuePair("course", "alevel"));

                Log.d("CHOICE CHOICE", "" + ActivitySeller.choiseOfBoard);

                JSONObject json = jsonParser.makeHttpRequest(PULL_ITEMS_URL, "POST", params1);

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
                    for (int i = 0; i < json.length(); i++) {
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


                FragmentALevelBuy.arrayUserName = new String[userName.size()];
                FragmentALevelBuy.arrayFirstName = new String[firstName.size()];
                FragmentALevelBuy.arrayLastName = new String[lastName.size()];
                FragmentALevelBuy.arrayNameOfBook = new String[nameofBook.size()];
                FragmentALevelBuy.arrayNameOfAuthor = new String[nameofAuthor.size()];
                FragmentALevelBuy.arrayPriceOfBook = new String[priceOfBook.size()];
                FragmentALevelBuy.arrayHomeAddress = new String[homeAddress.size()];
                FragmentALevelBuy.arraySchool = new String[school.size()];
                FragmentALevelBuy.arrayImage1Name = new String[image1name.size()];
                FragmentALevelBuy.arrayImage2Name = new String[firstName.size()];
                FragmentALevelBuy.arrayImage3Name = new String[firstName.size()];
                FragmentALevelBuy.arrayPhoneNumber = new String[firstName.size()];
                FragmentALevelBuy.arrayEmailAddress = new String[firstName.size()];

                FragmentALevelBuy.arrayUserName = userName.toArray(new String[userName.size()]);
                FragmentALevelBuy.arrayFirstName = firstName.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arrayLastName = lastName.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arrayNameOfBook = nameofBook.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arrayNameOfAuthor = nameofAuthor.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arrayPriceOfBook = priceOfBook.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arrayHomeAddress = homeAddress.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arraySchool = school.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arrayImage1Name = image1name.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arrayImage2Name = image2name.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arrayImage3Name = image3name.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arrayPhoneNumber = phoneNumber.toArray(new String[firstName.size()]);
                FragmentALevelBuy.arrayEmailAddress = emailAddress.toArray(new String[firstName.size()]);
//
                FragmentALevelBuy.title = FragmentALevelBuy.arrayNameOfBook;
                FragmentALevelBuy.price = FragmentALevelBuy.arrayPriceOfBook;
                FragmentALevelBuy.sellerName = FragmentALevelBuy.arrayFirstName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBarAlevel.setVisibility(View.GONE);
            FragmentALevelBuy fragmentSellerClass = new FragmentALevelBuy();
            FragmentManager manager = getFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.relativePaster, fragmentSellerClass, "asdf");
            transaction.commit();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

            MainALevelBuyer.didEverythingLoad = 1;
        }
    }
}
