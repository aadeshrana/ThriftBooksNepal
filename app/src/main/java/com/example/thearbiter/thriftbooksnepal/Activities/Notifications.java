package com.example.thearbiter.thriftbooksnepal.Activities;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Adapters.ALevelAdapterBuy;
import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterMyOrder;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.SlidingTabLayout;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.Information.infotest;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {
    SlidingTabLayout tabs;
    ViewPager viewPager;
    Toolbar toolbar;
    static String title[] ={"test","test1","ssss"};
    public static int positionOfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setBackgroundColor(Color.parseColor("#FF232B2F"));

        tabs.setSmoothScrollingEnabled(true);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(viewPager);

    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tabs = {"My Orders", "Requests"};

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
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

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
            View layout;
            ViewPager viewPagerSlideShowCommon;

            switch (bundle.getInt("position")) {
                case 0:
                    Notifications.positionOfView =0;
                    layout= inflater.inflate(R.layout.fragment_myorders,container,false);
                    RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recycleviewMyOrder);
                    AdapterMyOrder adapter = new AdapterMyOrder(getActivity(), getdata());
                    recyclerView.setAdapter(adapter);
                    LinearLayoutManager lin = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(lin);

                    break;
                case 1:
                    Notifications.positionOfView=1;
                    layout= inflater.inflate(R.layout.fragment_requests,container,false);
                    RecyclerView recyclerView1 = (RecyclerView) layout.findViewById(R.id.recycleviewRequest);
                    AdapterMyOrder adapter1 = new AdapterMyOrder(getActivity(), getdata());
                    recyclerView1.setAdapter(adapter1);
                    LinearLayoutManager lin1 = new LinearLayoutManager(getActivity());
                    recyclerView1.setLayoutManager(lin1);

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
    }}

}
