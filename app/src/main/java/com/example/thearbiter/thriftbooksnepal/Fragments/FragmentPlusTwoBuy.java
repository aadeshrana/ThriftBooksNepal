package com.example.thearbiter.thriftbooksnepal.Fragments;

/**
 * Created by Gaurav Jayasawal on 1/9/2017.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thearbiter.thriftbooksnepal.Adapters.PlusTwoAdapterBuy;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentPlusTwoBuy extends Fragment {
    public String title[] = {"Chemistry", "Physics", "Maths", "Statistics", "Economics", "General Paper","a","b","c","d","e","f"};
    public String price[] = {"3500", "3200", "850", "325", "3350", "1250","a","b","c","d","e","f"};
    public String sellerName[] = {"Gaurav", "Astha", "Bader", "Ramesh Prasad", "Hari Yadav", "Barsha Upadhyay","a","b","c","d","e","f"};
    public int img[] = {R.drawable.chemistrydemopicture, R.drawable.tomduncanphysicsdemopictures, R.drawable.mathsdemopictures, R.drawable.statisticsdemopictures, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture,R.drawable.chemistrydemopicture,R.drawable.economicsdemopicture,R.drawable.generaldaperdemopicture,R.drawable.generaldaperdemopicture,R.drawable.generaldaperdemopicture,R.drawable.mathsdemopictures};

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewswipe1);
        PlusTwoAdapterBuy adapter = new PlusTwoAdapterBuy(getActivity(), getdata());
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
}

