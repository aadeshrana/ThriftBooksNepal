package com.example.thearbiter.thriftbooksnepal.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.thearbiter.thriftbooksnepal.R;

/**
 * Created by Gaurav Jayasawal on 1/12/2017.
 */

public class ActivitySeller extends AppCompatActivity implements View.OnClickListener{

    EditText editTitleOfBook, editAuthorOfBook, editPriceOfBook;
    Button sellerSelectImage1, sellerSelectImage2, sellerSelectImage3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        editTitleOfBook = (EditText)findViewById(R.id.sellerClassNameOfBook);
        editAuthorOfBook = (EditText)findViewById(R.id.sellerClassAuthorOfBook);
        editPriceOfBook = (EditText)findViewById(R.id.sellerClassPriceOfBook);

        sellerSelectImage1 = (Button)findViewById(R.id.sellerSelectImageOneButton);
        sellerSelectImage2 = (Button)findViewById(R.id.sellerSelectImageTwoButton);
        sellerSelectImage3 = (Button)findViewById(R.id.sellerSelectImageThreeButton);


    }

    @Override
    public void onClick(View v) {
        if(v==sellerSelectImage1){

        }
    }
}
