package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterFirstPage;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav Jayasawal on 1/15/2017.
 */

public class FragmentNavDraerMain extends Fragment {

    static public String title[]= {"belloooo","dellooo","mellooo","yellooo","asdfsdf"};
    static public String price[] = {"144", "554", "55543", "545", "5959"};
    static public String sellerName[] = {"gaurav","astja","rana","bob","pob"};
    public int img[] = {R.drawable.chemistrydemopicture, R.drawable.tomduncanphysicsdemopictures, R.drawable.mathsdemopictures, R.drawable.statisticsdemopictures, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture, R.drawable.chemistrydemopicture, R.drawable.economicsdemopicture, R.drawable.generaldaperdemopicture, R.drawable.generaldaperdemopicture, R.drawable.generaldaperdemopicture, R.drawable.mathsdemopictures};

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
        for (int j = 0; j < title.length; j++) {
            InformationBuyerRecycler current = new InformationBuyerRecycler();
            current.title = title[j];
            current.priceOfBook = price[j];
            current.sellerName = sellerName[j];
            current.imageView = img[j];
//            current.firstBookList =arrayImage1Name[j];
            data.add(current);
        }
        return data;
    }


}
