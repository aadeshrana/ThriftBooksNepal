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
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentIbBuy;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav Jayasawal on 1/9/2017.
 */

public class MainIbBuyer extends AppCompatActivity {
    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/thriftbooks/pullallitems.php";
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
    private  JSONParser jsonParser = new JSONParser();
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paster_layout_recycle);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressBar = (ProgressBar)findViewById(R.id.alevelBuyProgress);
        new PullAllIBItems().execute();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

        }
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

        return super.onOptionsItemSelected(item);
    }

    public class PullAllIBItems extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            Log.d("CHOICE CHOICE",""+ActivitySeller.choiseOfBoard);
            try {

                Log.d("Before Vie Orders", "");
                List<NameValuePair> params1 = new ArrayList<>();

                params1.add(new BasicNameValuePair("course", "ib"));


                JSONParser jsonParser = new JSONParser();
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
                        Log.d("thisone2",""+ json.getString("c" + i));
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


                FragmentIbBuy.arrayUserName = new String[userName.size()];
                FragmentIbBuy.arrayFirstName = new String[firstName.size()];
                FragmentIbBuy.arrayLastName = new String[lastName.size()];
                FragmentIbBuy.arrayNameOfBook = new String[nameofBook.size()];
                FragmentIbBuy.arrayNameOfAuthor = new String[nameofAuthor.size()];
                FragmentIbBuy.arrayPriceOfBook = new String[priceOfBook.size()];
                FragmentIbBuy.arrayHomeAddress = new String[homeAddress.size()];
                FragmentIbBuy.arraySchool = new String[school.size()];
                FragmentIbBuy.arrayImage1Name = new String[image1name.size()];
                FragmentIbBuy.arrayImage2Name = new String[firstName.size()];
                FragmentIbBuy.arrayImage3Name = new String[firstName.size()];
                FragmentIbBuy.arrayPhoneNumber = new String[firstName.size()];
                FragmentIbBuy.arrayEmailAddress = new String[firstName.size()];
//
                FragmentIbBuy.arrayUserName = userName.toArray(new String[userName.size()]);
                FragmentIbBuy.arrayFirstName = firstName.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayLastName = lastName.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayNameOfBook = nameofBook.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayNameOfAuthor = nameofAuthor.toArray(new String[firstName.size()]);
                Log.d("auth",":"+FragmentIbBuy.arrayNameOfAuthor[1]);
                FragmentIbBuy.arrayPriceOfBook = priceOfBook.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayHomeAddress = homeAddress.toArray(new String[firstName.size()]);
                FragmentIbBuy.arraySchool = school.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayImage1Name = image1name.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayImage2Name = image2name.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayImage3Name = image3name.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayPhoneNumber = phoneNumber.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayEmailAddress = emailAddress.toArray(new String[firstName.size()]);

                FragmentIbBuy.title = FragmentIbBuy.arrayNameOfBook;
                FragmentIbBuy.price = FragmentIbBuy.arrayPriceOfBook;
                FragmentIbBuy.sellerName = FragmentIbBuy.arrayFirstName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            FragmentIbBuy fragmentSellerClass = new FragmentIbBuy();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.relativePaster, fragmentSellerClass, "asdf");
            transaction.commit();
        }


    }
}
