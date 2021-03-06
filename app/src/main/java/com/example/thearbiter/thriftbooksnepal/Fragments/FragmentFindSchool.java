package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterFindSchool;
import com.example.thearbiter.thriftbooksnepal.Information.InformationFindSchool;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadesh Rana on 11-01-17.
 */
public class FragmentFindSchool extends Fragment {
    public RecyclerView recyclerView;
    public AdapterFindSchool adapterFindSchool;
    public Context context;
    String schoolNames[] ={"rbs","tbs","colz","lyf"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_find_school_recycler,container,false);
        recyclerView = (RecyclerView)layout.findViewById(R.id.findSchoolRecycler);
        adapterFindSchool = new AdapterFindSchool(getActivity(),getData());
        recyclerView.setAdapter(adapterFindSchool);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }
    public List<InformationFindSchool> getData() {
        List<InformationFindSchool> data = new ArrayList<>();

        for (int i = 0; i < schoolNames.length; i++) {
            InformationFindSchool current = new InformationFindSchool();

            current.collegeName = schoolNames[i];

            data.add(current);
        }
        return data;
    }
}
