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

import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentPlusTwoBuy;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav Jayasawal on 1/9/2017.
 */

public class MainPlusTwoBuyer extends AppCompatActivity {
    Toolbar toolbar;
    String loggedIn;
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
    ProgressBar progressBar;
    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/thriftbooks/pullallitems.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paster_layout_recycle);
        progressBar = (ProgressBar)findViewById(R.id.alevelBuyProgress);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        new PullAllAlevelItems().execute();
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
        if (id == R.id.messager) {

            if (loggedIn.equals("noValue")) {
                Toast.makeText(this, "Please log in to continue.", Toast.LENGTH_SHORT).show();
            } else {
                Notifications not = new Notifications();
                Log.d("value","bookname1");
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
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    public class PullAllAlevelItems extends AsyncTask<String, String, String> {
        private JSONParser jsonParser1 = new JSONParser();
        @Override
        protected String doInBackground(String... args) {

            try {

                Log.d("Before Vie Orders", "");
                List<NameValuePair> params4 = new ArrayList<>();

                params4.add(new BasicNameValuePair("course", "plustwo"));

                Log.d("CHOICE CHOICE", "" + ActivitySeller.choiseOfBoard);
JSONObject json1 = null;
                 json1 = jsonParser1.makeHttpRequest(PULL_ITEMS_URL, "POST", params4);

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
                Log.d("thisone",""+json1.length());
                try {
                    for (int i = 0; i < json1.length(); i++) {
                        userName.add(json1.getString("a" + i));
                        firstName.add(json1.getString("b" + i));
                        lastName.add(json1.getString("c" + i));
                        Log.d("thisone2",""+ json1.getString("c" + i));
                        nameofBook.add(json1.getString("d" + i));
                        nameofAuthor.add(json1.getString("e" + i));
                        priceOfBook.add(json1.getString("f" + i));
                        homeAddress.add(json1.getString("g" + i));
                        school.add(json1.getString("h" + i));
                        image1name.add(json1.getString("i" + i));
                        image2name.add(json1.getString("j" + i));
                        image3name.add(json1.getString("k" + i));
                        phoneNumber.add(json1.getString("l" + i));
                        emailAddress.add(json1.getString("m" + i));
                    }
                } catch (Exception e) {
                   Log.d("error11",":"+e);
                }


                FragmentPlusTwoBuy.arrayUserName = new String[userName.size()];
                FragmentPlusTwoBuy.arrayFirstName = new String[firstName.size()];
                FragmentPlusTwoBuy.arrayLastName = new String[lastName.size()];
                FragmentPlusTwoBuy.arrayNameOfBook = new String[nameofBook.size()];
                FragmentPlusTwoBuy.arrayNameOfAuthor = new String[nameofAuthor.size()];
                FragmentPlusTwoBuy.arrayPriceOfBook = new String[priceOfBook.size()];
                FragmentPlusTwoBuy.arrayHomeAddress = new String[homeAddress.size()];
                FragmentPlusTwoBuy.arraySchool = new String[school.size()];
                FragmentPlusTwoBuy.arrayImage1Name = new String[image1name.size()];
                FragmentPlusTwoBuy.arrayImage2Name = new String[firstName.size()];
                FragmentPlusTwoBuy.arrayImage3Name = new String[firstName.size()];
                FragmentPlusTwoBuy.arrayPhoneNumber = new String[firstName.size()];
                FragmentPlusTwoBuy.arrayEmailAddress = new String[firstName.size()];

                FragmentPlusTwoBuy.arrayUserName = userName.toArray(new String[userName.size()]);
                FragmentPlusTwoBuy.arrayFirstName = firstName.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arrayLastName = lastName.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arrayNameOfBook = nameofBook.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arrayNameOfAuthor = nameofAuthor.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arrayPriceOfBook = priceOfBook.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arrayHomeAddress = homeAddress.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arraySchool = school.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arrayImage1Name = image1name.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arrayImage2Name = image2name.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arrayImage3Name = image3name.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arrayPhoneNumber = phoneNumber.toArray(new String[firstName.size()]);
                FragmentPlusTwoBuy.arrayEmailAddress = emailAddress.toArray(new String[firstName.size()]);

                FragmentPlusTwoBuy.title = FragmentPlusTwoBuy.arrayNameOfBook;
                FragmentPlusTwoBuy.price = FragmentPlusTwoBuy.arrayPriceOfBook;
                FragmentPlusTwoBuy.sellerName = FragmentPlusTwoBuy.arrayFirstName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            FragmentPlusTwoBuy fragmentSellerClass = new FragmentPlusTwoBuy();
            FragmentManager manager = getFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.relativePaster, fragmentSellerClass, "asdf");
            transaction.commit();
        }
    }
}
