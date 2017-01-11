package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterNavMenu;
import com.example.thearbiter.thriftbooksnepal.Information.InformationNavMenu;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadesh Rana on 11-01-17.
 */
public class FragmentNavMenuRecycler extends  android.app.Fragment {
    RecyclerView recyclerView;
    private AdapterNavMenu adapter;
    ArrayAdapter<String> adater1;

    int[]icons={R.drawable.generaldaperdemopicture,R.drawable.generaldaperdemopicture,R.drawable.chemistrydemopicture};
    String title[] ={"A-Level","+2","IB","Account"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_nav_menu_drawer,container,false);
        recyclerView =(RecyclerView)layout.findViewById(R.id.recyclerLayoutNavMenu);
        adapter = new AdapterNavMenu(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


     return layout;
    }

    public List<InformationNavMenu> getData(){
        List<InformationNavMenu> data = new ArrayList<>();
        for(int i =0;i<title.length && i< icons.length;i++){
            InformationNavMenu current = new InformationNavMenu();
            current.name = title[i];
            current.iconId = icons[i];
            data.add(current);
        }
        return data;
    }
}
