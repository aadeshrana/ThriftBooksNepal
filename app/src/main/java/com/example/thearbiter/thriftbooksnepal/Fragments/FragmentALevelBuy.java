package com.example.thearbiter.thriftbooksnepal.Fragments;

/**
 * Created by Gaurav Jayasawal on 1/9/2017.
 */

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thearbiter.thriftbooksnepal.Adapters.ALevelAdapterBuy;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentALevelBuy extends Fragment {
    public String title[] = {"Chemistry", "Physics", "Maths", "Statistics", "Economics", "General Paper","a","b","c","d","e","f"};
    public String price[] = {"3500", "3200", "850", "325", "3350", "1250","a","b","c","d","e","f"};
    public String sellerName[] = {"Gaurav", "Astha", "Bader", "Ramesh Prasad", "Hari Yadav", "Barsha Upadhyay","a","b","c","d","e","f"};
    public int img[] = {R.drawable.chemistrydemopicture, R.drawable.tomduncanphysicsdemopictures, R.drawable.mathsdemopictures, R.drawable.statisticsdemopictures, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture,R.drawable.chemistrydemopicture,R.drawable.economicsdemopicture,R.drawable.generaldaperdemopicture,R.drawable.generaldaperdemopicture,R.drawable.generaldaperdemopicture,R.drawable.mathsdemopictures};

    ArrayList<String> p = new ArrayList<>();
    ArrayList<String> q = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewswipe1);
        ALevelAdapterBuy adapter = new ALevelAdapterBuy(getActivity(), getdata());
        recyclerView.setAdapter(adapter);
        GridLayoutManager man1 = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(man1);
        return view;
    }

    public List<InformationBuyerRecycler> getdata() {
        List<InformationBuyerRecycler> data = new ArrayList<>();
        for (int j = 0; j < title.length; j++) {
            InformationBuyerRecycler current = new InformationBuyerRecycler();
            current.title = title[j];
            current.priceOfBook = price[j];
            current.sellerName = sellerName[j];
            current.imageView = img[j];
            data.add(current);
        }
        return data;
        //
    }

    public class PullAllAlevelItems extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {

                Log.d("Before Vie Orders", "");
                List<NameValuePair> params1 = new ArrayList<>();

                params1.add(new BasicNameValuePair("course", Login.nameofuser));
                JSONObject json = jsonParser.makeHttpRequest(ORDERS_URL, "POST", params1);

                p.clear();
                q.clear();
                try {
                    for (int i = 0; i < 5; i++) {
                        p.add(json.getString("a" + i));
                        q.add(json.getString("b" + i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                VieOrdersFragment.title = new String[10];
                VieOrdersFragment.description = new String[10];

                VieOrdersFragment.title = p.toArray(new String[p.size()]);
                VieOrdersFragment.description = q.toArray(new String[q.size()]);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}

