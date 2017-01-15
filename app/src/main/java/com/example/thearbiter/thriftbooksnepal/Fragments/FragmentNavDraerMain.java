package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thearbiter.thriftbooksnepal.Activities.MainDrawerHome;
import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterFirstPage;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav Jayasawal on 1/15/2017.
 */

public class FragmentNavDraerMain extends Fragment {
    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/thriftbooks/pullallitems.php";
    static public String title[];
    static public String price[];
    static public String sellerName[];

    public int img[] = {R.drawable.chemistrydemopicture, R.drawable.tomduncanphysicsdemopictures, R.drawable.mathsdemopictures, R.drawable.statisticsdemopictures, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture, R.drawable.chemistrydemopicture, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture, R.drawable.generaldaperdemopicture, R.drawable.generaldaperdemopicture, R.drawable.mathsdemopictures};

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

    static public String arrayUserName[];
    static public String arrayFirstName[];
    static public String arrayLastName[];
    static public String arrayNameOfBook[];
    static public String arrayNameOfAuthor[];
    static public String arrayPriceOfBook[];
    static public String arrayHomeAddress[];
    static public String arraySchool[];
    static public String arrayImage1Name[];
    static public String arrayImage2Name[];
    static public String arrayImage3Name[];
    static public String arrayPhoneNumber[];
    static public String arrayEmailAddress[];


    JSONParser jsonParser = new JSONParser();

//    static public String title[]= {"belloooo","dellooo","mellooo","yellooo","asdfsdf"};
//    static public String price[] = {"144", "554", "55543", "545", "5959"};
//    static public String sellerName[] = {"gaurav","astja","rana","bob","pob"};
//    public int img[] = {R.drawable.chemistrydemopicture, R.drawable.tomduncanphysicsdemopictures, R.drawable.mathsdemopictures, R.drawable.statisticsdemopictures, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture, R.drawable.chemistrydemopicture, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture, R.drawable.generaldaperdemopicture, R.drawable.generaldaperdemopicture, R.drawable.mathsdemopictures};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_first_page, container, false);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerFirstPageKo);
        AdapterFirstPage adapterFirstPage = new AdapterFirstPage(getActivity(), getData());
        recyclerView.setAdapter(adapterFirstPage);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }

    private List<InformationBuyerRecycler> getData() {
        List<InformationBuyerRecycler> data = new ArrayList<>();
        try {
            for (int j = 0; j < title.length; j++) {
                InformationBuyerRecycler current = new InformationBuyerRecycler();
                current.title = title[j];
                current.priceOfBook = price[j];
                current.sellerName = sellerName[j];
                current.imageView = img[j];
                current.firstBookList = arrayImage1Name[j];
                data.add(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void pullAllMainItems(Context context) {
        new PullAllAlevelItems(context).execute();
    }

    public class PullAllAlevelItems extends AsyncTask<String, String, String> {

        private Context context;

        public PullAllAlevelItems(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... args) {

            Log.d("ETA PUGYO HAI", "");
            try {

                Log.d("Before Vie Orders", "");
                List<NameValuePair> params1 = new ArrayList<>();

                params1.add(new BasicNameValuePair("course", "others"));

                Log.d("CHOICE CHOICE", "" + "others");

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

                FragmentNavDraerMain.title = arrayNameOfBook;
                FragmentNavDraerMain.price = arrayPriceOfBook;
                FragmentNavDraerMain.sellerName = arrayFirstName;

                Log.d("ETA PUGYOO??", "" + FragmentNavDraerMain.title);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent in = new Intent(context, MainDrawerHome.class);
            context.startActivity(in);
//            ((Activity)context).finish();
        }
    }

}
