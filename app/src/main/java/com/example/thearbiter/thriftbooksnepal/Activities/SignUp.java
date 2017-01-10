package com.example.thearbiter.thriftbooksnepal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.thearbiter.thriftbooksnepal.R;

public class SignUp extends AppCompatActivity {

    EditText firstName,lastName,userName,password,confirmPass,phoneNo,email,school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstName = (EditText)findViewById(R.id.signUpFirstName);
        lastName= (EditText)findViewById(R.id.signUpLastName);
        userName = (EditText)findViewById(R.id.signUpUserName);
        password =(EditText)findViewById(R.id.signUpPassword);
        confirmPass=(EditText)findViewById(R.id.signUpConfirmPassword);
        phoneNo=(EditText)findViewById(R.id.signUpPhoneNumber);
        email=(EditText)findViewById(R.id.signUpEmail);
        school = (EditText)findViewById(R.id.signUpSchool);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
