package com.example.thearbiter.thriftbooksnepal.Activities;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.SlidingTabLayout;
import com.example.thearbiter.thriftbooksnepal.R;

public class Notifications extends AppCompatActivity {
    SlidingTabLayout tabs;
    ViewPager viewPager;
    Toolbar toolbar;

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
                    layout= inflater.inflate(R.layout.fragment_myorders,container,false);


                    break;
                case 1:
                    layout= inflater.inflate(R.layout.fragment_requests,container,false);
                    break;

                    default:
                        layout = inflater.inflate(R.layout.fragment_requests, container, false);
            }
            return layout;
        }
    }


}
