package com.example.thearbiter.thriftbooksnepal.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.R;

/**
 * Created by Gaurav Jayasawal on 1/18/2017.
 */

public class FinalBuyersActivity extends AppCompatActivity {

    public static String finalBuyersActivityNameOfBook;
    public static String finalBuyersActivityUsernameOfSeller;
    public static String finalBuyersActivityNameOfAuthor;
    public static String finalBuyersActivityPriceOfBook;
    public static String finalBuyersActivityNameOfSeller;
    public static String finalBuyersActivityImage1;
    public static String finalBuyersActivityImage2;
    public static String finalBuyersActivityImage3;

    TextView finalBuyersActivityTextVieTitleOfBook, finalBuyersActivityTextVieNameOfAuthor, finalBuyersActivityTextViePriceOfBook;
    TextView finalBuyersActivityTextVieNameOfSeller;
    CardView sendMessageToSeller;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_buyers);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        finalBuyersActivityTextVieTitleOfBook = (TextView) findViewById(R.id.finalBuyerCardInfoTitleOfBookValue);
        finalBuyersActivityTextVieNameOfAuthor = (TextView) findViewById(R.id.finalBuyerCardInfoNameofAuthorValue);
        finalBuyersActivityTextViePriceOfBook = (TextView) findViewById(R.id.finalBuyerCardInfoPriceOfBookValue);
        finalBuyersActivityTextVieNameOfSeller = (TextView) findViewById(R.id.finalBuyerCardInfoNameOfSellerValue);
        sendMessageToSeller = (CardView) findViewById(R.id.finalBuyerCardSendMessageToSeller);

        setValuesToTextVies();
    }

    private void setValuesToTextVies() {
        finalBuyersActivityTextViePriceOfBook.setText(FinalBuyersActivity.finalBuyersActivityPriceOfBook);
        finalBuyersActivityTextVieTitleOfBook.setText(FinalBuyersActivity.finalBuyersActivityNameOfBook);
        finalBuyersActivityTextVieNameOfAuthor.setText(FinalBuyersActivity.finalBuyersActivityNameOfAuthor);
        finalBuyersActivityTextVieNameOfSeller.setText(FinalBuyersActivity.finalBuyersActivityNameOfSeller);
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

}
