package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchool;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavMenu;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavMenuRecycler;
import com.example.thearbiter.thriftbooksnepal.R;

public class MainDrawerHome extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer_home);
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        CustomDiagFindSchool obj = new CustomDiagFindSchool();
        obj.findAllSchool();
        FragmentNavMenu fragmentNavMenu = (FragmentNavMenu)getSupportFragmentManager().findFragmentById(R.id.mainfragmentDrawer);
        fragmentNavMenu.setUp(R.id.mainfragmentDrawer,(DrawerLayout)findViewById(R.id.mainDrawerLayout),toolbar);

        FragmentNavMenuRecycler fragmentAdpater = new FragmentNavMenuRecycler();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mainfragmentDrawer,fragmentAdpater,"abc");
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_drawer_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
