package com.example.thearbiter.thriftbooksnepal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.thearbiter.thriftbooksnepal.R;

public class Login extends AppCompatActivity {

    EditText loginUsername, loginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginUsername = (EditText)findViewById(R.id.loginuUername);
        loginPassword =(EditText)findViewById(R.id.loginPassword);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


    ///******//// OnClick Buttons Are Here //////****///////


    ////When you press the button login//////
    public void loginButton(View view){

    }

    //When You press on new account////
    public void newAccount(View view){
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }

}
