package com.example.thearbiter.thriftbooksnepal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchool;
import com.example.thearbiter.thriftbooksnepal.R;

public class Accounts extends AppCompatActivity {
    EditText firstName, lastName, email,phoneNo,college,passwordOld,passwordNew,passwordConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        firstName = (EditText)findViewById(R.id.accountFirstNameEdit);
        lastName = (EditText)findViewById(R.id.accountLastNameEdit);
        email = (EditText)findViewById(R.id.accountsEmailEdit);
        phoneNo = (EditText)findViewById(R.id.accountPhoneEdit);
        college =(EditText)findViewById(R.id.accountCollegeEdit);
        passwordOld =(EditText)findViewById(R.id.accountsCurrentPassEdit);
        passwordNew = (EditText)findViewById(R.id.accountsNewPassEdit);
        passwordConfirm =(EditText)findViewById(R.id.accountConfirmPassEdit);


        firstName.setText(Login.firstName);
        lastName.setText(Login.lastName);
        email.setText(Login.emailAddress);
        phoneNo.setText(Login.phoneNumber);
        college.setText(Login.school);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accounts, menu);
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

    public void findnewSchool(View view){
        CustomDiagFindSchool customDiagFindSchool = new CustomDiagFindSchool();
        customDiagFindSchool.show(getFragmentManager(), "abc");
    }
}
