package com.example.thearbiter.thriftbooksnepal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.thearbiter.thriftbooksnepal.R;

public class IbOptions extends AppCompatActivity {
    Toolbar toolbar;
    String chosenboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ib_options);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        try{
            Bundle chosenValueBoard = getIntent().getExtras();
            Log.d("valueis", "" + chosenValueBoard.getString("chosenValueBoard"));

            chosenboard = chosenValueBoard.getString("chosenValueBoard");
        }catch (Exception e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ib_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ibSellButton(View view) {
        Intent in = new Intent(IbOptions.this,ActivitySeller.class);
        in.putExtra("chosenValueBoard", chosenboard);
        startActivity(in);
    }

    public void ibBuyButton(View view) {
        Intent in = new Intent(IbOptions.this, MainALevelBuyer.class);
        startActivity(in);
    }
}
