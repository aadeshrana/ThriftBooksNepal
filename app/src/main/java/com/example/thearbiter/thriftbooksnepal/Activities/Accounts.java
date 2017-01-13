package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterFindSchool;
import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchool;
import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchoolAcc;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Accounts extends AppCompatActivity {
    EditText firstName, lastName, email,phoneNo,college,passwordOld,passwordNew,passwordConfirm;
    static EditText newschoolname;
    JSONParser jsonParser = new JSONParser();
    static String sendFirstName, sendLastName, sendEmail, sendPhoneNo, sendCollege, sendPassword,senduser;
    private static final String UPDATE_URL = "http://frame.ueuo.com/thriftbooks/updateacc.php";
    int okayTosend =1;
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
        newschoolname = (EditText)findViewById(R.id.accountCollegeEdit);

        firstName.setText(Login.firstName);
        lastName.setText(Login.lastName);
        email.setText(Login.emailAddress);
        phoneNo.setText(Login.phoneNumber);
        college.setText(Login.school);



    }

    public void checkPassword(){
        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sharedpref.edit();
        Login.password = sharedpref.getString("passwordShared", "any");
        sendFirstName = firstName.getText().toString();
        sendLastName = lastName.getText().toString();
        sendCollege = college.getText().toString();
        sendPhoneNo = phoneNo.getText().toString();
        sendEmail = email.getText().toString();
        senduser =Login.username;
        if(!passwordOld.getText().toString().equals("")) {
            if (passwordOld.getText().toString().equals(Login.password)) {
                Log.d("password", "" + Login.password);

                if(!passwordNew.getText().toString().equals("") || !passwordConfirm.getText().toString().equals("")) {


                    if (passwordNew.getText().toString().equals(passwordConfirm.getText().toString())) {
                        Toast.makeText(Accounts.this, "password changed", Toast.LENGTH_SHORT).show();
                        sendPassword = passwordNew.getText().toString();
                        okayTosend = 1;
                    } else {
                        okayTosend = 0;
                        new AlertDialog.Builder(Accounts.this)
                                .setTitle("Save")
                                .setMessage("Password dont match")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(Accounts.this, "changed" + sendPassword, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
                else{
                    Toast.makeText(Accounts.this, "Password field cant be blank", Toast.LENGTH_SHORT).show();
                    okayTosend =0;
                }


            } else {
                Toast.makeText(Accounts.this, "Invalid Old Password", Toast.LENGTH_SHORT).show();
                okayTosend=0;
            }
        }else {
            sendPassword = Login.password;
        }


        if(okayTosend ==1) {
            new AlertDialog.Builder(Accounts.this)
                    .setTitle("Save")
                    .setMessage("Are you sure you want to save the changes?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Accounts.this, "changed" + sendPassword, Toast.LENGTH_SHORT).show();

                            new updateAccount().execute();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

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
        CustomDiagFindSchoolAcc customDiagFindSchool = new CustomDiagFindSchoolAcc();
        customDiagFindSchool.show(getFragmentManager(), "abc");
    }

    public void updateaccount(View view){
        checkPassword();
    }


    class updateAccount extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            try{
                List<NameValuePair> params1 = new ArrayList<>();
                params1.add(new BasicNameValuePair("firstname",sendFirstName));
                params1.add(new BasicNameValuePair("lastname",sendLastName));
                params1.add(new BasicNameValuePair("password",sendPassword));
                params1.add(new BasicNameValuePair("phonenumber",sendPhoneNo));
                params1.add(new BasicNameValuePair("emailaddress",sendEmail));
                params1.add(new BasicNameValuePair("schoolname",sendCollege));
                params1.add(new BasicNameValuePair("username",senduser));

                JSONObject json = jsonParser.makeHttpRequest(UPDATE_URL, "POST", params1);

            }catch (Exception e){

            }

            return null;
        }
    }

    public void getSchool(String schoolName) {
        Log.d("value of school", "" + schoolName);
        Accounts.newschoolname.setText(schoolName);
    }
}
