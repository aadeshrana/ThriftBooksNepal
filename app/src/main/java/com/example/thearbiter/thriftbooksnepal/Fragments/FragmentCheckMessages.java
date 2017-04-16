package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.os.Bundle;

import com.example.thearbiter.thriftbooksnepal.Activities.Notifications;

/**
 * Created by Aadesh Rana on 17-04-17.
 */

public class FragmentCheckMessages {
    public static Notifications.MyFragment getInstance(int position) {
        Notifications.MyFragment myFragment = new Notifications.MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        myFragment.setArguments(args);
        return myFragment;
    }
}
