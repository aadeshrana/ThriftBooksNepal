package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterMyChats;
import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterMyOrder;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.SlidingTabLayout;
import com.example.thearbiter.thriftbooksnepal.Information.InformationCheckChats;
import com.example.thearbiter.thriftbooksnepal.Information.infotest;
import com.example.thearbiter.thriftbooksnepal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Notifications extends AppCompatActivity {
    SlidingTabLayout tabs;
    final ArrayList<String> allData = new ArrayList<>();
    final ArrayList<String> refinedData = new ArrayList<>();
    final ArrayList<String> refinedRoomNames = new ArrayList<>();

    DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    ViewPager viewPager;
    Toolbar toolbar;
    static String title[] = {"test", "test1", "ssss"};
    public static String[] allChats;
    public static String[] allRooms;

    //Used to differentiate between different versions of onDataChange since it gets called everytime there is a change in Database.
    public static int whereAreYou = 0;

    public static int positionOfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        whereAreYou = 1;
        allChats = new String[0];
        allRooms = new String[0];

        final ProgressDialog progressDialog = new ProgressDialog(Notifications.this);
        progressDialog.setMessage("Loading messages..");
        progressDialog.show();
        //To pull the data from firebase for chat
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());

                }

                if (whereAreYou == 84) {
                    viewPager = (ViewPager) findViewById(R.id.pager);
                    viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                    viewPager.setCurrentItem(2);
                    progressDialog.dismiss();
                    tabs = (SlidingTabLayout) findViewById(R.id.tabs);
                    tabs.setBackgroundColor(Color.parseColor("#FF232B2F"));

                    tabs.setSmoothScrollingEnabled(true);
                    tabs.setDistributeEvenly(true);
                    tabs.setViewPager(viewPager);
                }

                allData.clear();
                allData.addAll(set);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Notifications.this);
                String nameSelf = sharedPreferences.getString("a", "");

                Log.d("kaayo", "value=" + allData.get(0));

                for (int j = 0; j < allData.size(); j++) {

                    String[] firstRoundSplitter = allData.get(j).split("\\|\\|");

                    String[] splitter = firstRoundSplitter[0].split("[*]+");
                    if (splitter[0].equals(nameSelf) || splitter[1].equals(nameSelf)) {
                        refinedRoomNames.add(allData.get(j));
                        String[] finalNameSplit = firstRoundSplitter[1].split("---");
                        String firstNameFromSharedPref = sharedPreferences.getString("firstNameSharePref1", "");
                        if(finalNameSplit[0].equals(firstNameFromSharedPref)){
                            refinedData.add(finalNameSplit[1]);
                        }
                        else{
                            refinedData.add(finalNameSplit[0]);
                        }
                    } else {
                        Log.d("not", "compatible" + allData.get(j));
                    }
                }

                allChats = new String[refinedData.size()];
                allChats = refinedData.toArray(new String[refinedData.size()]);
                allRooms = new String[refinedRoomNames.size()];
                allRooms = refinedRoomNames.toArray(new String[refinedRoomNames.size()]);

                if (whereAreYou == 1) {
                    viewPager = (ViewPager) findViewById(R.id.pager);
                    viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                    progressDialog.dismiss();
                    tabs = (SlidingTabLayout) findViewById(R.id.tabs);
                    tabs.setBackgroundColor(Color.parseColor("#FF232B2F"));
                    viewPager.setCurrentItem(2);
                    tabs.setSmoothScrollingEnabled(true);
                    tabs.setDistributeEvenly(true);
                    tabs.setViewPager(viewPager);

                    whereAreYou = 84;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        String[] tabs = {"My Orders", "Requests", "Messages"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            MyFragment myFragment = MyFragment.getInstance(position);
            Log.d("ss", "" + position);
            return myFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        whereAreYou = 0;
        Log.d("on", "Destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("on", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("on", "Stop");

    }

    /////////FRAGMENT STARTS HERE
    public static class MyFragment extends Fragment {

        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            TextView textView;
            Bundle bundle = getArguments();
            final View layout;
            ViewPager viewPagerSlideShowCommon;

            switch (bundle.getInt("position")) {
                case 0:
                    Notifications.positionOfView = 0;
                    layout = inflater.inflate(R.layout.fragment_myorders, container, false);
                    RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recycleviewMyOrder);
                    AdapterMyOrder adapter = new AdapterMyOrder(getActivity(), getdata());
                    recyclerView.setAdapter(adapter);
                    LinearLayoutManager lin = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(lin);
                    whereAreYou = 0;

                    break;
                case 1:
                    Notifications.positionOfView = 1;
                    layout = inflater.inflate(R.layout.fragment_requests, container, false);
                    RecyclerView recyclerView1 = (RecyclerView) layout.findViewById(R.id.recycleviewRequest);
                    AdapterMyOrder adapter1 = new AdapterMyOrder(getActivity(), getdata());
                    recyclerView1.setAdapter(adapter1);
                    LinearLayoutManager lin1 = new LinearLayoutManager(getActivity());
                    recyclerView1.setLayoutManager(lin1);
                    whereAreYou = 0;
                    break;
                case 2:
                    Notifications.positionOfView = 2;
                    layout = inflater.inflate(R.layout.fragment_message, container, false);
                    RecyclerView recyclerView2 = (RecyclerView) layout.findViewById(R.id.recyclerviewMyMessage);
                    final AdapterMyChats adapter2 = new AdapterMyChats(getActivity(), getdata1());
                    recyclerView2.setAdapter(adapter2);
                    LinearLayoutManager lin2 = new LinearLayoutManager(getActivity());
                    recyclerView2.setLayoutManager(lin2);
//                    adapter2.notifyDataSetChanged();
                    break;


                default:
                    layout = inflater.inflate(R.layout.fragment_requests, container, false);
            }
            return layout;
        }


        public List<infotest> getdata() {
            List<infotest> data = new ArrayList<>();
            try {
                for (int j = 0; j < title.length; j++) {
                    infotest current = new infotest();
                    current.title = title[j];

                    data.add(current);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
            //
        }


        public List<InformationCheckChats> getdata1() {
            List<InformationCheckChats> data = new ArrayList<>();
            try {
                for (int j = 0; j < allRooms.length; j++) {
                    InformationCheckChats current = new InformationCheckChats();
                    current.infoUsernameOfChatSender = allRooms[j];

                    data.add(current);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
            //
        }


    }

}
