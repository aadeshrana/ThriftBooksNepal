package com.example.thearbiter.thriftbooksnepal;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterFindSchool;
import com.example.thearbiter.thriftbooksnepal.Information.InformationFindSchool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadesh Rana on 12-01-17.
 */
public class CustomDiagFindSchool extends DialogFragment {
    public RecyclerView recyclerView;
    public AdapterFindSchool adapterFindSchool;
    public Context context;

    String schoolNames[] ={"Rato Bangala School","The British School","A very Long Name To see What happens huh?","lyf","asdf","sagash","this","fuck","lyf","gg","how","this","shjas","sagga","Rato Bangala School","The British School","A very Long Name To see What happens huh?","lyf","asdf","sagash","this","fuck","lyf","gg","how","this","shjas","sagga"};
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_find_school_recycler,container,false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.findSchoolRecycler);
        adapterFindSchool = new AdapterFindSchool(getActivity(),getData());
        recyclerView.setAdapter(adapterFindSchool);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getDialog().setTitle("Choose Your College");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(lp);


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
