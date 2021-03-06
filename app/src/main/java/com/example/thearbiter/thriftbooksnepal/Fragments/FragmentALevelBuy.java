package com.example.thearbiter.thriftbooksnepal.Fragments;

/**
 * Created by Gaurav Jayasawal on 1/9/2017.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thearbiter.thriftbooksnepal.Adapters.ALevelAdapterBuy;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentALevelBuy extends Fragment {

    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/thriftbooks/pullallitems.php";
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




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewswipe1);
        ALevelAdapterBuy adapter = new ALevelAdapterBuy(getActivity(), getdata());
        recyclerView.setAdapter(adapter);
        GridLayoutManager man1 = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        StaggeredGridLayoutManager sGLM = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sGLM);
//        recyclerView.setLayoutManager(glm);
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
                current.imageView = img[j];
                current.firstBookList = arrayImage1Name[j];
                current.authName =arrayNameOfAuthor[j];
                data.add(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
        //
    }



}

