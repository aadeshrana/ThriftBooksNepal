package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentMyOrder;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentPlusTwoBuy;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyOrders extends AppCompatActivity {
    Toolbar toolbar;
    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/thriftbooks/pullallitems.php";
    JSONParser jsonParser = new JSONParser();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        new PullAllAlevelItems().execute();
    }
    public class PullAllAlevelItems extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {

                Log.d("Before Vie Orders", "");
                List<NameValuePair> params1 = new ArrayList<>();

                params1.add(new BasicNameValuePair("course", ActivitySeller.choiseOfBoard));

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


               FragmentMyOrder.arrayUserName = new String[userName.size()];
               FragmentMyOrder.arrayFirstName = new String[firstName.size()];
               FragmentMyOrder.arrayLastName = new String[lastName.size()];
               FragmentMyOrder.arrayNameOfBook = new String[nameofBook.size()];
               FragmentMyOrder.arrayNameOfAuthor = new String[nameofAuthor.size()];
               FragmentMyOrder.arrayPriceOfBook = new String[priceOfBook.size()];
               FragmentMyOrder.arrayHomeAddress = new String[homeAddress.size()];
               FragmentMyOrder.arraySchool = new String[school.size()];
               FragmentMyOrder.arrayImage1Name = new String[image1name.size()];
               FragmentMyOrder.arrayImage2Name = new String[firstName.size()];
               FragmentMyOrder.arrayImage3Name = new String[firstName.size()];
               FragmentMyOrder.arrayPhoneNumber = new String[firstName.size()];
               FragmentMyOrder.arrayEmailAddress = new String[firstName.size()];

               FragmentMyOrder.arrayUserName = userName.toArray(new String[userName.size()]);
               FragmentMyOrder.arrayFirstName = firstName.toArray(new String[firstName.size()]);
               FragmentMyOrder.arrayLastName = lastName.toArray(new String[firstName.size()]);
               FragmentMyOrder.arrayNameOfBook = nameofBook.toArray(new String[firstName.size()]);
               FragmentMyOrder.arrayNameOfAuthor = nameofAuthor.toArray(new String[firstName.size()]);
               FragmentMyOrder.arrayPriceOfBook = priceOfBook.toArray(new String[firstName.size()]);
               FragmentMyOrder.arrayHomeAddress = homeAddress.toArray(new String[firstName.size()]);
               FragmentMyOrder.arraySchool = school.toArray(new String[firstName.size()]);
               FragmentMyOrder.arrayImage1Name = image1name.toArray(new String[firstName.size()]);
               FragmentMyOrder.arrayImage2Name = image2name.toArray(new String[firstName.size()]);
               FragmentMyOrder.arrayImage3Name = image3name.toArray(new String[firstName.size()]);
               FragmentMyOrder.arrayPhoneNumber = phoneNumber.toArray(new String[firstName.size()]);
               FragmentMyOrder.arrayEmailAddress = emailAddress.toArray(new String[firstName.size()]);
            Log.d("k bho","s"+FragmentMyOrder.title);
               FragmentMyOrder.title =FragmentMyOrder.arrayNameOfBook;
               FragmentMyOrder.price =FragmentMyOrder.arrayPriceOfBook;
               FragmentMyOrder.sellerName =FragmentMyOrder.arrayFirstName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            FragmentMyOrder fragmentSellerClass = new FragmentMyOrder();
            FragmentManager manager = getFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.pastehere, fragmentSellerClass, "asdf");
            transaction.commit();
        }
    }
}
