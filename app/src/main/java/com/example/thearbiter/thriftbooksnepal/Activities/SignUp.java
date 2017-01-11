package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchool;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {

    EditText firstName, lastName, userName, password, confirmPass, phoneNo, email;
    EditText school;
    String strFirstName, strLastName, strUserName, strPassword, strConfirmPass, strPhoneNo, strEmail, strSchool;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pdialog;
    private static final String REGISTER_URL = "http://frame.ueuo.com/thriftbooks/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstName = (EditText) findViewById(R.id.signUpFirstName);
        lastName = (EditText) findViewById(R.id.signUpLastName);
        userName = (EditText) findViewById(R.id.signUpUserName);
        password = (EditText) findViewById(R.id.signUpPassword);
        confirmPass = (EditText) findViewById(R.id.signUpConfirmPassword);
        phoneNo = (EditText) findViewById(R.id.signUpPhoneNumber);
        email = (EditText) findViewById(R.id.signUpEmail);
        school = (EditText) findViewById(R.id.signUpSchool);

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

    public void getSchool(String schoolName){
        Log.d("value of school",""+schoolName);
    school.setText(schoolName);
    }

    public void signUpButton(View view) {

        strFirstName = firstName.getText().toString();
        strLastName = lastName.getText().toString();
        strUserName = userName.getText().toString();
        strPhoneNo = phoneNo.getText().toString();
        strEmail = email.getText().toString();
        strSchool = school.getText().toString();

        strPassword = password.getText().toString();
        strConfirmPass = confirmPass.getText().toString();

        if(strPassword.equals(strConfirmPass)) {
            new CreateUser().execute();
        }
        else
        {
            password.setBackgroundColor(Color.RED);
            confirmPass.setBackgroundColor(Color.RED);
        }

    }

    class CreateUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(SignUp.this);
            pdialog.setMessage("Registering User....");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(true);
            pdialog.show();
        }

        protected String doInBackground(String... args) {

            try {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("firstname", strFirstName));
                params.add(new BasicNameValuePair("lastname", strLastName));
                params.add(new BasicNameValuePair("username", strUserName));
                params.add(new BasicNameValuePair("password", strPassword));
                params.add(new BasicNameValuePair("emailaddress", strEmail));
                params.add(new BasicNameValuePair("schoolname", strSchool));
                params.add(new BasicNameValuePair("phonenumber", strPhoneNo));

                //posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", params);
                //full json response
                Log.d("registering attempt", json.toString());
                //json success element
                //success = json.getInt(TAG_SUCCESS);
//                if(success == 1){
//                    Log.d("User created!",json.toString());
//                    finish();
//                    return json.getString(TAG_MESSAGE);
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdialog.dismiss();
        }
    }

    public void findSchools(View view){

       /* LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.custom_recycler_find_school, null);
       final FrameLayout relativeLayout =(FrameLayout)promptView.findViewById(R.id.findSchoolRecyclerlayout);
        AlertDialog builder1 = new AlertDialog.Builder(this).create();
        builder1.setView(promptView);
        builder1.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = builder1.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(lp);

        FragmentFindSchool fragmentFindSchool = new FragmentFindSchool();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.add(relativeLayout.getId(),fragmentFindSchool,"abc");
        fragmentTransaction.commit();*/

        CustomDiagFindSchool customDiagFindSchool = new CustomDiagFindSchool();
        customDiagFindSchool.show(getFragmentManager(),"abc");

    }
}
