package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentALevelBuy;
import com.example.thearbiter.thriftbooksnepal.R;

/**
 * Created by Gaurav Jayasawal on 1/9/2017.
 */

public class MainALevelBuyer extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d("testterrr","");
        setContentView(R.layout.paster_layout_recycle);
        FragmentALevelBuy fragmentSellerClass = new FragmentALevelBuy();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.relativePaster, fragmentSellerClass, "asdf");
        transaction.commit();
    }
}
