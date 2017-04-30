package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterFindSchool;
import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterSearchAll;
import com.example.thearbiter.thriftbooksnepal.Information.InformationAllData;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.Information.InformationFindSchool;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadesh Rana on 01-05-17.
 */

public class FragmentSearchAll  extends Fragment implements SearchView.OnQueryTextListener{
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
    static public String title[];
    static public String price[];
    static public String sellerName[];
    SearchView searchView;
    AdapterSearchAll adapterSearchAll;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search_all_data,container,false);
        RecyclerView recyclerView;
        recyclerView = (RecyclerView)layout.findViewById(R.id.findSchoolRecycler);
        adapterSearchAll = new AdapterSearchAll(getActivity(),getdata1());
        recyclerView.setAdapter(adapterSearchAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchView = (SearchView)layout.findViewById(R.id.searchAllData);
        setUpSearchView();
        return layout;
    }

    public void  setUpSearchView(){
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);

        searchView.setQueryHint("Search Name,Author,Book,Seller");
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapterSearchAll.filter(newText);
        Log.d("Why emplt", "ss" + newText);

        return  true;
    }
    private List<InformationAllData> getdata1() {
        List<InformationAllData> data = new ArrayList<>();
        try {
            for (int j = 0; j < title.length; j++) {
                Log.d("lenght",":"+title.length);
                InformationAllData current = new InformationAllData();
                current.title = title[j];
                current.priceOfBook = price[j];
                current.sellerName = sellerName[j];
                current.firstBookList = arrayImage1Name[j];
                data.add(current);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
        //
    }


}
