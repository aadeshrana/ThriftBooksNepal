package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterMyOrder;
import com.example.thearbiter.thriftbooksnepal.Adapters.IbAdapterBuy;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.Information.infotest;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadesh Rana on 21-01-17.
 */

public class FragmentMyOrder extends Fragment {

    static public String title[];
    static public String price[];
    static public String sellerName[];


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
        AdapterMyOrder adapter = new AdapterMyOrder(getActivity(), getdata());
        recyclerView.setAdapter(adapter);
        GridLayoutManager man1 = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        LinearLayoutManager lin = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lin);
        return view;
    }
    public List<infotest> getdata() {
        List<infotest> data = new ArrayList<>();
        try {
            for (int j = 0; j < title.length; j++) {
               infotest current = new infotest();
                current.title = title[j];

                data.add(current);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
        //
    }
}
