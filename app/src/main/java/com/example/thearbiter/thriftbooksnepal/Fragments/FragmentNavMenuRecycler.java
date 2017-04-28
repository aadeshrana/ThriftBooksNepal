package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterNavMenu;
import com.example.thearbiter.thriftbooksnepal.Adapters.SectionedGridRecyclerViewAdapter;
import com.example.thearbiter.thriftbooksnepal.Information.InformationNavMenu;
import com.example.thearbiter.thriftbooksnepal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aadesh Rana on 11-01-17.
 */
public class FragmentNavMenuRecycler extends android.app.Fragment {
    RecyclerView recyclerView;
    private AdapterNavMenu adapter;
    ArrayAdapter<String> adater1;
    TextView navMenuUsername;
    TextView navMenuEmailAddress;
    final String WELCOME_TEXT = "Welcome ";
    int[] icons = {R.drawable.temp, R.drawable.temp, R.drawable.temp, R.drawable.temp, R.drawable.temp, R.drawable.temp, R.drawable.temp, R.drawable.temp, R.drawable.temp};
    String title[] = {"Alevel", "IB", "+2", "Sell any books", "Accounts", "About Us"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_nav_menu_drawer, container, false);

        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getActivity());


        String userProfilePic = sharedpref.getString("a", "noValue");
        try {
            if (userProfilePic.equals("noValue")) {
                userProfilePic = FragmentCustomDiagLogin.username;
                Log.d("log","chiryo1 "+FragmentCustomDiagLogin.username);
            }
        } catch (Exception e) {

        }
        String userNameDraw = sharedpref.getString("firstNameSharePref", "gg");
        try {
            if (userNameDraw.equals("gg")) {
                userNameDraw = FragmentCustomDiagLogin.firstName;
                Log.d("log","chiryo2 "+FragmentCustomDiagLogin.firstName);
            }
        } catch (Exception e) {

        }
        CircleImageView circleImageView = (CircleImageView) layout.findViewById(R.id.profile_image);
        String kk = "http://aadeshrana.esy.es/" + FragmentCustomDiagLogin.username + "ProfilePic";

        Picasso.with(getActivity()).load("http://aadeshrana.esy.es/" + userProfilePic + "ProfilePic.jpg").placeholder(R.drawable.default_user).into(circleImageView);
        navMenuUsername = (TextView) layout.findViewById(R.id.navDrawerUserName);
        navMenuEmailAddress = (TextView) layout.findViewById(R.id.navDrawerEmailAddress);

        if (userNameDraw.equals("gg")) {
            circleImageView.isClickable();
            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentCustomDiagLogin fragmentCustomDiagLogin = new FragmentCustomDiagLogin();
                    fragmentCustomDiagLogin.show(getFragmentManager(), "cde");
                }
            });
        }


        if (FragmentCustomDiagLogin.firstName != null) {
            navMenuUsername.setText(WELCOME_TEXT + FragmentCustomDiagLogin.firstName);
            navMenuEmailAddress.setText(FragmentCustomDiagLogin.emailAddress);


        } else {

            String sharedFirstName = sharedpref.getString("firstNameSharePref1", "Guest");
            String sharedEmail = sharedpref.getString("emailSharePref", " ");
            navMenuUsername.setText(WELCOME_TEXT + sharedFirstName);
            navMenuEmailAddress.setText(sharedEmail);
        }

        sharedpref.edit();


        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerLayoutNavMenu);
        adapter = new AdapterNavMenu(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<SectionedGridRecyclerViewAdapter.Section> sections =
                new ArrayList<SectionedGridRecyclerViewAdapter.Section>();
        sections.add(new SectionedGridRecyclerViewAdapter.Section(0, "Buy"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(3, "Sell"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(4, "Settings"));
        //sections.add(new SectionedGridRecyclerViewAdapter.Section(6,"Settings"));

        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                SectionedGridRecyclerViewAdapter(getActivity(), R.layout.section, R.id.section_text, recyclerView, adapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));
        recyclerView.setAdapter(mSectionedAdapter);

        return layout;
    }

    public List<InformationNavMenu> getData() {
        List<InformationNavMenu> data = new ArrayList<>();
        for (int i = 0; i < title.length && i < icons.length; i++) {
            InformationNavMenu current = new InformationNavMenu();
            current.name = title[i];
            current.iconId = icons[i];
            data.add(current);
        }
        return data;
    }


}
