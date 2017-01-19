package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Adapters.MessagerAdapter;
import com.example.thearbiter.thriftbooksnepal.Information.InformationMessageActivity;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentMessager extends Fragment {

    public static String finalBuyersActivityNameOfBook;
    public static String finalBuyersActivityUsernameOfSeller;
    public static String finalBuyersActivityNameOfAuthor;
    public static String finalBuyersActivityPriceOfBook;
    public static String finalBuyersActivityNameOfSeller;
    public static String finalBuyersActivityImage1;
    public static String finalBuyersActivityImage2;
    public static String finalBuyersActivityImage3;

    public static String title[];
    public static String nameOfSender[];
    LinearLayoutManager manager;
    TextView finalBuyersActivityTextVieTitleOfBook, finalBuyersActivityTextVieNameOfAuthor, finalBuyersActivityTextViePriceOfBook;
    TextView finalBuyersActivityTextVieNameOfSeller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_final_buyers, container, false);
        finalBuyersActivityTextVieTitleOfBook = (TextView) view.findViewById(R.id.finalBuyerCardInfoTitleOfBookValue);
        finalBuyersActivityTextVieNameOfAuthor = (TextView) view.findViewById(R.id.finalBuyerCardInfoNameofAuthorValue);
        finalBuyersActivityTextViePriceOfBook = (TextView) view.findViewById(R.id.finalBuyerCardInfoPriceOfBookValue);
        finalBuyersActivityTextVieNameOfSeller = (TextView) view.findViewById(R.id.finalBuyerCardInfoNameOfSellerValue);

        setValuesToTextVies();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerVieMessageActivity);
        MessagerAdapter adapter = new MessagerAdapter(getActivity(), getdata());
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        manager.setStackFromEnd(true);

        return view;
    }

    private void setValuesToTextVies() {
        finalBuyersActivityTextViePriceOfBook.setText(FragmentMessager.finalBuyersActivityPriceOfBook);
        finalBuyersActivityTextVieTitleOfBook.setText(FragmentMessager.finalBuyersActivityNameOfBook);
        finalBuyersActivityTextVieNameOfAuthor.setText(FragmentMessager.finalBuyersActivityNameOfAuthor);
        finalBuyersActivityTextVieNameOfSeller.setText(FragmentMessager.finalBuyersActivityNameOfSeller);
    }


    public List<InformationMessageActivity> getdata() {
        List<InformationMessageActivity> data = new ArrayList<>();

        Log.d("randi ko ban", "" + title[0]);

        for (int j = 0; j < title.length; j++) {
            InformationMessageActivity current = new InformationMessageActivity();
            current.textMessage = title[j];
            Log.d("jjjjjj", "" + title[j]);
            current.sendersName = nameOfSender[j];
            data.add(current);
        }
        return data;
    }
}