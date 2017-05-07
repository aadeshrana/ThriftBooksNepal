package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterMyChats;
import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterMyOrder;
import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterMyRequests;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.FcmMessagingService;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.SlidingTabLayout;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomDiagLogin;
import com.example.thearbiter.thriftbooksnepal.Information.InformationCheckChats;
import com.example.thearbiter.thriftbooksnepal.Information.InformationCheckRequests;
import com.example.thearbiter.thriftbooksnepal.Information.infotest;
import com.example.thearbiter.thriftbooksnepal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

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

    public static String[] requestUsername;
    public static String[] requestBookName;
    public static String[] requestAuthorName;
    public static String[] requestName;
    public static String[] chatUsersNeMessagesArray;

    ArrayList<String> requestUsernameAl = new ArrayList<>(), requestBooknameAl = new ArrayList<>(), requestAuthornameAl = new ArrayList<>(),
            requestNameAl = new ArrayList<>();

    DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    ViewPager viewPager;
    Toolbar toolbar;
    static String title[] = {"test", "test1", "ssss"};
    public static String[] allChats;
    public static String[] allRooms;
    public static String[] messageFromPref;
    public Set<String> set;
    ProgressDialog progressDialog;
    //Used to differentiate between different versions of onDataChange since it gets called everytime there is a change in Database.
    public static int whereAreYou = 0;

    public static int positionOfView;
    public static final String PULL_ALL_REQUESTED_BOOKS = "http://frame.ueuo.com/thriftbooks/fetchallrequests.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        progressDialog = new ProgressDialog(Notifications.this);
        progressDialog.setMessage("Loading messages..");
        progressDialog.show();
        whereAreYou = 1;
        allChats = new String[0];
        allRooms = new String[0];
        messageFunction();
        for (int i = 1; i < allData.size(); i++) {
            refinedRoomNames.add(allData.get(i));
        }


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Notifications.this);
        String from = preferences.getString("messageFrom", "");
        Log.d("PREF1", preferences.getString("messageFrom", ""));
        Log.d("PREF2", from);

        if (from.length() > 0) {
            String tryyy[] = from.split("[*]+");
            messageFromPref = new String[tryyy.length];
            for (int i = 0; i < tryyy.length; i++) {
                Log.d("from", tryyy[i]);
                messageFromPref[i] = tryyy[i];
            }
        }
        FcmMessagingService.check = 0;
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent setIntent = new Intent(Notifications.this, MainDrawerHome.class);
        startActivity(setIntent);
    }

    public void pullRequestFunc() {
        new PullAllRequests().execute();
    }

    public void messageFunction() {

        //To pull the data from firebase for chat
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                set = new HashSet<>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());

                }
//                try {
//                    if (whereAreYou == 84) {
//
//                    }
//                } catch (Exception e) {
//
//                }

                try {
                    allData.clear();
                    allData.addAll(set);
                } catch (Exception e) {

                }

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Notifications.this);
                String nameSelf = sharedPreferences.getString("a", "");
                if (nameSelf.equals("")) {
                    nameSelf = FragmentCustomDiagLogin.username;
                }

                try {
                    for (int j = 0; j < allData.size(); j++) {

                        String[] firstRoundSplitter = allData.get(j).split("\\|\\|");

                        String[] splitter = firstRoundSplitter[0].split("[*]+");
                        if (splitter[0].equals(nameSelf) || splitter[1].equals(nameSelf)) {
                            refinedRoomNames.add(allData.get(j));
                            String[] finalNameSplit = firstRoundSplitter[1].split("---");
                            String firstNameFromSharedPref = sharedPreferences.getString("firstNameSharePref1", "");
                            if (firstNameFromSharedPref.equals("")) {
                                firstNameFromSharedPref = FragmentCustomDiagLogin.firstName;
                            }

                            if (finalNameSplit[0].equals(firstNameFromSharedPref)) {
                                refinedData.add(finalNameSplit[1]);
                            } else {
                                refinedData.add(finalNameSplit[0]);
                            }
                        } else {
                            Log.d("not", "compatible" + allData.get(j));
                        }
                    }
                } catch (Exception e) {

                }

                try {
                    allChats = new String[refinedData.size()];
                    allChats = refinedData.toArray(new String[refinedData.size()]);
                    allRooms = new String[refinedRoomNames.size()];
                    allRooms = refinedRoomNames.toArray(new String[refinedRoomNames.size()]);
                    if (whereAreYou == 1) {
                        pullRequestFunc();
                    }
                } catch (Exception e) {

                }
                viewPager = (ViewPager) findViewById(R.id.pager);
                try {

                    if (whereAreYou == 1) {

                        whereAreYou = 84;
                    }
                } catch (Exception e) {
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
        refinedRoomNames.clear();
        Log.d("on", "Destroy");
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notif, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
    public static class MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
        public AdapterMyChats adapter2;

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
                    Log.d("value", "bookname18");

                    AdapterMyRequests adapter1 = new AdapterMyRequests(getActivity(), getdata1());
                    recyclerView1.setAdapter(adapter1);
                    LinearLayoutManager lin1 = new LinearLayoutManager(getActivity());
                    recyclerView1.setLayoutManager(lin1);
                    whereAreYou = 84;
                    break;
                case 2:
                    Notifications.positionOfView = 2;
                    layout = inflater.inflate(R.layout.fragment_message, container, false);
                    RecyclerView recyclerView2 = (RecyclerView) layout.findViewById(R.id.recyclerviewMyMessage);
                    adapter2 = new AdapterMyChats(getActivity(), getdata2());
                    recyclerView2.setAdapter(adapter2);
                    SwipeRefreshLayout srl = (SwipeRefreshLayout) layout.findViewById(R.id.swiperefresh);
                    srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            Intent in = new Intent(getActivity(), Notifications.class);
                            startActivity(in);
                        }
                    });
                    LinearLayoutManager lin2 = new LinearLayoutManager(getActivity());
                    recyclerView2.setLayoutManager(lin2);
                    adapter2.notifyDataSetChanged();
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

        public List<InformationCheckRequests> getdata1() {
            List<InformationCheckRequests> data = new ArrayList<>();
            Log.d("value", "bookname19");

            try {
                for (int j = 0; j < requestUsername.length; j++) {
                    InformationCheckRequests current = new InformationCheckRequests();
                    current.infoUsername = requestUsername[j];
                    current.infoBookname = requestBookName[j];
                    current.infoAuthorname = requestAuthorName[j];
                    current.infoName = requestName[j];

                    data.add(current);

                }
            } catch (Exception e) {

            }
            return data;

        }

        public List<InformationCheckChats> getdata2() {
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


        @Override
        public void onRefresh() {
            if (getId() == R.id.swiperefresh) {
                Toast.makeText(getActivity(), "refresh", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class PullAllRequests extends AsyncTask<String, String, String> {
        private JSONParser jsonParser1 = new JSONParser();
        JSONObject json1 = null;

        @Override
        protected String doInBackground(String... args) {

            try {

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Notifications.this);
                String tempName = pref.getString("a", "");
                if (tempName.equals("")) {
                    tempName = FragmentCustomDiagLogin.username;
                }

                List<NameValuePair> params4 = new ArrayList<>();
                params4.add(new BasicNameValuePair("anything", tempName));

                json1 = jsonParser1.makeHttpRequest(PULL_ALL_REQUESTED_BOOKS, "POST", params4);
            } catch (Exception e) {
                e.printStackTrace();
            }

            requestUsernameAl.clear();
            requestBooknameAl.clear();
            requestAuthornameAl.clear();
            requestNameAl.clear();

            try {
                for (int i = 0; i < json1.length(); i++) {
                    requestUsernameAl.add(json1.getString("a" + i));
                    requestBooknameAl.add(json1.getString("b" + i));
                    requestAuthornameAl.add(json1.getString("c" + i));
                    requestNameAl.add(json1.getString("d" + i));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            Notifications.requestUsername = new String[requestUsernameAl.size()];
            Notifications.requestBookName = new String[requestBooknameAl.size()];
            Notifications.requestAuthorName = new String[requestAuthornameAl.size()];
            Notifications.requestName = new String[requestUsernameAl.size()];

            Notifications.requestUsername = requestUsernameAl.toArray(new String[requestUsernameAl.size()]);
            Notifications.requestBookName = requestBooknameAl.toArray(new String[requestBooknameAl.size()]);
            Notifications.requestAuthorName = requestAuthornameAl.toArray(new String[requestAuthornameAl.size()]);
            Notifications.requestName = requestNameAl.toArray(new String[requestNameAl.size()]);
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            viewPager = (ViewPager) findViewById(R.id.pager);
            viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
            viewPager.setCurrentItem(2);
            tabs = (SlidingTabLayout) findViewById(R.id.tabs);
            tabs.setBackgroundColor(Color.parseColor("#FF232B2F"));
            tabs.setSmoothScrollingEnabled(true);
            tabs.setDistributeEvenly(true);
            tabs.setViewPager(viewPager);
        }
    }


}