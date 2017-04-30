package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterSearchAll;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentSearchAll;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavDraerMain;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentSearchAll;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchAllData extends AppCompatActivity  {
    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/thriftbooks/pullallitemsearch.php";
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

    AdapterSearchAll adapterSearchAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_all_data);
        adapterSearchAll = new AdapterSearchAll();
        new PullAllItems().execute();

    }



    public class PullAllItems extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            try {


                List<NameValuePair> params1 = new ArrayList<>();
                params1.add(new BasicNameValuePair("course", "ok"));

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


                FragmentSearchAll.arrayUserName = new String[userName.size()];
                Log.d("usrLength",":"+userName.size());
                FragmentSearchAll.arrayFirstName = new String[firstName.size()];
                FragmentSearchAll.arrayLastName = new String[lastName.size()];
                FragmentSearchAll.arrayNameOfBook = new String[nameofBook.size()];
                FragmentSearchAll.arrayNameOfAuthor = new String[nameofAuthor.size()];
                FragmentSearchAll.arrayPriceOfBook = new String[priceOfBook.size()];
                FragmentSearchAll.arrayHomeAddress = new String[homeAddress.size()];
                FragmentSearchAll.arraySchool = new String[school.size()];
                FragmentSearchAll.arrayImage1Name = new String[image1name.size()];
                FragmentSearchAll.arrayImage2Name = new String[firstName.size()];
                FragmentSearchAll.arrayImage3Name = new String[firstName.size()];
                FragmentSearchAll.arrayPhoneNumber = new String[firstName.size()];
                FragmentSearchAll.arrayEmailAddress = new String[firstName.size()];

                FragmentSearchAll.arrayUserName = userName.toArray(new String[userName.size()]);
                FragmentSearchAll.arrayFirstName = firstName.toArray(new String[firstName.size()]);
                FragmentSearchAll.arrayLastName = lastName.toArray(new String[firstName.size()]);
                FragmentSearchAll.arrayNameOfBook = nameofBook.toArray(new String[firstName.size()]);
                FragmentSearchAll.arrayNameOfAuthor = nameofAuthor.toArray(new String[firstName.size()]);
                FragmentSearchAll.arrayPriceOfBook = priceOfBook.toArray(new String[firstName.size()]);
                FragmentSearchAll.arrayHomeAddress = homeAddress.toArray(new String[firstName.size()]);
                FragmentSearchAll.arraySchool = school.toArray(new String[firstName.size()]);
                FragmentSearchAll.arrayImage1Name = image1name.toArray(new String[firstName.size()]);
                FragmentSearchAll.arrayImage2Name = image2name.toArray(new String[firstName.size()]);
                FragmentSearchAll.arrayImage3Name = image3name.toArray(new String[firstName.size()]);
                FragmentSearchAll.arrayPhoneNumber = phoneNumber.toArray(new String[firstName.size()]);
                FragmentSearchAll.arrayEmailAddress = emailAddress.toArray(new String[firstName.size()]);
//
                FragmentSearchAll.title = FragmentSearchAll.arrayNameOfBook;
                FragmentSearchAll.price = FragmentSearchAll.arrayPriceOfBook;
                FragmentSearchAll.sellerName = FragmentSearchAll.arrayFirstName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            FragmentSearchAll fragmentSearchAll = new FragmentSearchAll();
            FragmentManager manager3 = getFragmentManager();
            FragmentTransaction transaction1 = manager3.beginTransaction();
            transaction1.replace(R.id.searchPaster, fragmentSearchAll, "asdf");
            transaction1.commit();


        }
    }
}
