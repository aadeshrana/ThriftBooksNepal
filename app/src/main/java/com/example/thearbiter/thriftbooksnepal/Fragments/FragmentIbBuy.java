package com.example.thearbiter.thriftbooksnepal.Fragments;

/**
 * Created by Gaurav Jayasawal on 1/9/2017.
 */

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thearbiter.thriftbooksnepal.Activities.ActivitySeller;
import com.example.thearbiter.thriftbooksnepal.Adapters.IbAdapterBuy;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentIbBuy extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewswipe1);
        IbAdapterBuy adapter = new IbAdapterBuy(getActivity(), getdata());
        recyclerView.setAdapter(adapter);
        GridLayoutManager man1 = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        LinearLayoutManager lin = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lin);
        return view;
    }

    public List<InformationBuyerRecycler> getdata() {
        List<InformationBuyerRecycler> data = new ArrayList<>();
        for (int j = 0; j < title.length; j++) {
            InformationBuyerRecycler current = new InformationBuyerRecycler();
            current.title = title[j];
            current.priceOfBook = price[j];
            current.sellerName = sellerName[j];
            current.firstBookList = arrayImage1Name[j];
            data.add(current);
        }
        return data;
        //
    }

    public void pullItemsFunction(){
        new PullAllAlevelItems().execute();
    }

    public class PullAllAlevelItems extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {

                Log.d("Before Vie Orders", "");
                List<NameValuePair> params1 = new ArrayList<>();

                params1.add(new BasicNameValuePair("course", ActivitySeller.choiseOfBoard));

                Log.d("CHOICE CHOICE",""+ActivitySeller.choiseOfBoard);

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
                FragmentIbBuy.arrayPriceOfBook = priceOfBook.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayHomeAddress = homeAddress.toArray(new String[firstName.size()]);
                FragmentIbBuy.arraySchool = school.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayImage1Name = image1name.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayImage2Name = image2name.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayImage3Name = image3name.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayPhoneNumber = phoneNumber.toArray(new String[firstName.size()]);
                FragmentIbBuy.arrayEmailAddress = emailAddress.toArray(new String[firstName.size()]);

                FragmentIbBuy.title = arrayNameOfBook;
                FragmentIbBuy.price = arrayPriceOfBook;
                FragmentIbBuy.sellerName = arrayFirstName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}

