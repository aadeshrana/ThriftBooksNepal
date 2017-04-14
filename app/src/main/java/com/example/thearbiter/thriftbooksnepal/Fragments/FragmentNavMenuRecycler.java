package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
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
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.Activities.Login;
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
    int[] icons = {R.drawable.temp,R.drawable.temp,R.drawable.temp,R.drawable.temp,R.drawable.temp,R.drawable.temp,R.drawable.temp,R.drawable.temp,R.drawable.temp};
    String title[] = {"Buy", "Sell", "Buy", "Sell", "Buy", "Sell","Accounts","About Us","My Orders"};



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_nav_menu_drawer, container, false);
        SharedPreferences sharedpref1;
        sharedpref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedpref1.edit();

        String userProfilePic = sharedpref1.getString("a", "noValue");
        String userNameDraw = sharedpref.getString("firstNameSharePref","gg");
        CircleImageView circleImageView = (CircleImageView)layout.findViewById(R.id.profile_image);
        String kk= "http://aadeshrana.esy.es/"+Login.username+"ProfilePic";
        Log.d("k ho path?", "hm" + kk);
        Picasso.with(getActivity()).load("http://aadeshrana.esy.es/"+userProfilePic+"ProfilePic.jpg").placeholder(R.drawable.default_user).into(circleImageView);
        navMenuUsername = (TextView) layout.findViewById(R.id.navDrawerUserName);
        navMenuEmailAddress = (TextView) layout.findViewById(R.id.navDrawerEmailAddress);

        if(userNameDraw.equals("Guest")){
            circleImageView.isClickable();
            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentCustomDiagLogin fragmentCustomDiagLogin= new FragmentCustomDiagLogin();
                    fragmentCustomDiagLogin.show(getFragmentManager(),"cde");
                }
            });
        }


        sharedpref.edit();
        if(Login.firstName!=null) {
            navMenuUsername.setText(WELCOME_TEXT + FragmentCustomDiagLogin.firstName);
            navMenuEmailAddress.setText(FragmentCustomDiagLogin.emailAddress);

        }

        else{
            String sharedFirstName = sharedpref.getString("firstNameSharePref", "User");
            String sharedEmail = sharedpref.getString("emailSharePref", "email");
            navMenuUsername.setText(WELCOME_TEXT + sharedFirstName);
            navMenuEmailAddress.setText(sharedEmail);
        }




        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerLayoutNavMenu);
        adapter = new AdapterNavMenu(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<SectionedGridRecyclerViewAdapter.Section> sections =
                new ArrayList<SectionedGridRecyclerViewAdapter.Section>();
        sections.add(new SectionedGridRecyclerViewAdapter.Section(0,"Alevel"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(2,"IB"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(4,"+2"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(6,"Settings"));

        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                SectionedGridRecyclerViewAdapter(getActivity(),R.layout.section,R.id.section_text,recyclerView,adapter);
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
