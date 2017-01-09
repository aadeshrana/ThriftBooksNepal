package com.example.thearbiter.thriftbooksnepal.Fragments;

/**
 * Created by Gaurav Jayasawal on 1/9/2017.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thearbiter.thriftbooksnepal.Adapters.ALevelAdapterBuy;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentALevelBuy extends Fragment {
    public String title[] = {"Chemistry", "Physics", "Maths", "Statistics", "Economics", "General Paper"};
    public String price[] = {"3500", "3200", "850", "325", "3350", "1250"};
    public String sellerName[] = {"Gaurav", "Astha", "Bader", "Ramesh Prasad", "Hari Yadav", "Barsha Upadhyay"};
    public int img[] = {R.drawable.chemistrydemopicture, R.drawable.tomduncanphysicsdemopictures, R.drawable.mathsdemopictures, R.drawable.statisticsdemopictures, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture};
    //done?
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewswipe1);
        ALevelAdapterBuy adapter = new ALevelAdapterBuy(getActivity(), getdata());
        Log.d("asdfasdfasdf", "" + adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
}

