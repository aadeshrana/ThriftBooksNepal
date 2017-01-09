package com.example.thearbiter.thriftbooksnepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /////******//////////////

   /* 1. SplashScreen
    2. SignUp
    3. Login
    4. MainDrawerHome
    5. AlevelOptions
    6. PlusTwoOptions
    7. IbOptions
    8. AdapterAlevelBuy
    9. IbAdapterBuy
    10.PlusTwoAdapterBuy
    11.ActivitySeller
    12.FragmentAlevelBuy
    13.FragmentPlusTwoBuy
    14.FragmentIbBuy
    15.InformationMenuItems
    16.InfromationBuyerRecycler
    17.FragmentNavMenu
    18.AdapterNavMenu
    19.ActivityFinalBuy
    20.ActivityAccount
    21.ActivityAboutUs
    */

    //////*****//////


}
