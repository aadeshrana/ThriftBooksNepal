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
import com.example.thearbiter.thriftbooksnepal.Adapters.PlusTwoAdapterBuy;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentPlusTwoBuy extends Fragment {

    static public String title[];
    static public String price[];
    static public String sellerName[];

    public int img[] = {R.drawable.chemistrydemopicture, R.drawable.tomduncanphysicsdemopictures, R.drawable.mathsdemopictures, R.drawable.statisticsdemopictures, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture, R.drawable.chemistrydemopicture, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture, R.drawable.generaldaperdemopicture, R.drawable.generaldaperdemopicture, R.drawable.mathsdemopictures};



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
        PlusTwoAdapterBuy adapter = new PlusTwoAdapterBuy(getActivity(), getdata());
        recyclerView.setAdapter(adapter);

        GridLayoutManager man1 = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

        LinearLayoutManager lin = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lin);
        return view;
    }

    public List<InformationBuyerRecycler> getdata() {
        List<InformationBuyerRecycler> data = new ArrayList<>();
        try {
            for (int j = 0; j < title.length; j++) {
                InformationBuyerRecycler current = new InformationBuyerRecycler();
                current.title = title[j];
                current.priceOfBook = price[j];
                current.sellerName = sellerName[j];
                current.firstBookList = arrayImage1Name[j];
                current.authName = arrayNameOfAuthor[j];
                data.add(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
        //
    }





}

