package com.example.thearbiter.thriftbooksnepal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.thearbiter.thriftbooksnepal.R;

public class ALevelOptions extends AppCompatActivity {

    Toolbar toolbar;
    String chosenboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alevel_options);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("valueis", "here");
        try{
            Bundle chosenValueBoard = getIntent().getExtras();
            Log.d("valueis",""+chosenValueBoard.getString("chosenValueBoard"));

            chosenboard = chosenValueBoard.getString("chosenValueBoard");
        }catch (Exception e){

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alevel_options, menu);
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
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    ///***/// OnClicks Starts //**////
    public void alevelBuyButton(View view){
        Intent in = new Intent(ALevelOptions.this,MainALevelBuyer.class);
        startActivity(in);
    }

    public void alevelSellButton(View view){
        Intent in = new Intent(ALevelOptions.this,ActivitySeller.class);
        in.putExtra("chosenValueBoard", chosenboard);
        startActivity(in);
    }
}
