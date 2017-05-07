package com.example.thearbiter.thriftbooksnepal.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchoolAcc;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.ImageFilePath;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomDiagLogin;
import com.example.thearbiter.thriftbooksnepal.R;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import it.sauronsoftware.ftp4j.FTPClient;

public class Accounts extends AppCompatActivity implements TextWatcher {
    EditText firstName, lastName, email, phoneNo, college, passwordOld, passwordNew, passwordConfirm;
    ImageView errorImagePass, erroImageConfirm;
    TextView errorTextPass, errorTextConfirm;
    String dispFirst, dispSecond;

    static EditText newschoolname;
    JSONParser jsonParser = new JSONParser();
    static String sendFirstName, sendLastName, sendEmail, sendPhoneNo, sendCollege, sendPassword, senduser;
    static final String FTP_HOST = "93.188.160.157";
    static final String FTP_USER = "u856924126";
    static final String FTP_PASS = "aadesh";
    static String shareUserName;
    final FTPClient client2 = new FTPClient();
    private static final String UPDATE_URL = "http://frame.ueuo.com/thriftbooks/updateacc.php";
    private static final String REGISTER_URL = "http://frame.ueuo.com/thriftbooks/register.php";
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    Boolean passwordChecked = false;
    int okayTosend = 1;
    Toolbar toolbar;
    private int PICK_IMAGE_REQUEST = 1;
    final FTPClient client = new FTPClient();
    Uri filePath;
    String realPath;
    Bitmap bitmap;
    File f;
    CircleImageView profilePic;
    SharedPreferences sharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);


        profilePic = (CircleImageView) findViewById(R.id.profile_image_accounts);
        firstName = (EditText) findViewById(R.id.accountFirstNameEdit);
        lastName = (EditText) findViewById(R.id.accountLastNameEdit);
        email = (EditText) findViewById(R.id.accountsEmailEdit);
        phoneNo = (EditText) findViewById(R.id.accountPhoneEdit);
        college = (EditText) findViewById(R.id.accountCollegeEdit);
        passwordOld = (EditText) findViewById(R.id.accountsCurrentPassEdit);
        passwordNew = (EditText) findViewById(R.id.accountsNewPassEdit);
        passwordConfirm = (EditText) findViewById(R.id.accountConfirmPassEdit);
        newschoolname = (EditText) findViewById(R.id.accountCollegeEdit);

        passwordConfirm.addTextChangedListener(this);

        erroImageConfirm = (ImageView) findViewById(R.id.errorConfirmPass);
        errorImagePass = (ImageView) findViewById(R.id.errorNewPass);

        errorTextPass = (TextView) findViewById(R.id.errorTextNewPass);
        errorTextConfirm = (TextView) findViewById(R.id.errorTextConfirmPass);


        erroImageConfirm.setVisibility(View.GONE);
        errorImagePass.setVisibility(View.GONE);
        errorTextConfirm.setVisibility(View.GONE);
        errorTextPass.setVisibility(View.GONE);

        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedpref.edit();
        String sharedFirstName = sharedpref.getString("firstNameSharePref1", "");
        String sharedEmail = sharedpref.getString("emailSharePref", "");
        String sharedLastName = sharedpref.getString("lastNameSharePref", "");
        String sharedPhoneNo = sharedpref.getString("phoneSharePref", "");
        String sharedSchool = sharedpref.getString("schoolSharePref", "");
        shareUserName = sharedpref.getString("username", "username");
        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.profile_image_accounts);

        if(sharedFirstName.equals("")){
            sharedFirstName = FragmentCustomDiagLogin.firstName;
            sharedEmail = FragmentCustomDiagLogin.emailAddress;
            sharedLastName = FragmentCustomDiagLogin.lastName;
            sharedPhoneNo = FragmentCustomDiagLogin.phoneNumber;
            sharedSchool = FragmentCustomDiagLogin.school;
            shareUserName = FragmentCustomDiagLogin.username;
        }
        Picasso.with(this).load("http://aadeshrana.esy.es/" + shareUserName + "ProfilePic.jpg").placeholder(R.drawable.default_user).into(circleImageView);
try {
    dispFirst = sharedFirstName.substring(0, 1).toUpperCase() + sharedFirstName.substring(1);
    dispSecond = sharedLastName.substring(0, 1).toUpperCase() + sharedLastName.substring(1);
    firstName.setText(dispFirst);
    lastName.setText(dispSecond);
    email.setText(sharedEmail);
    phoneNo.setText(sharedPhoneNo);
    college.setText(sharedSchool);
}catch (Exception e){

}
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void checkPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String passwordEntered, passwordUse;

        sharedpref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sharedpref.edit();
        passwordUse =sharedpref.getString("sharedPassword", "null");
        if(passwordUse.equals("null")){
            passwordUse = FragmentCustomDiagLogin.password;
        }
        passwordEntered=SHA1(passwordOld.getText().toString());

        if(passwordEntered.equals(passwordUse)){
            passwordChecked = true;
        }else
        {
            passwordChecked = false;
        }


    }
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
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
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void findnewSchool(View view) {
        CustomDiagFindSchoolAcc customDiagFindSchool = new CustomDiagFindSchoolAcc();
        customDiagFindSchool.show(getFragmentManager(), "abc");
    }

    public void updateaccount(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        checkPassword();
        if(passwordChecked){
            if(realPath !=null){
                new uploadImage().execute();
                Toast.makeText(this, "Image only changed", Toast.LENGTH_SHORT).show();
            }
            senduser = sharedpref.getString("username", "null");
            sendFirstName = firstName.getText().toString();
            sendLastName = lastName.getText().toString();
            sendCollege = college.getText().toString();
            sendPhoneNo = phoneNo.getText().toString();
            sendEmail = email.getText().toString();

            if(senduser.equals("null")) {
                senduser = Login.username;
            }
            if(passwordNew.getText().toString().length()<=0 && passwordConfirm.getText().toString().length()<=0){
                sendPassword =sharedpref.getString("sharedPassword", "null");
                if(sendPassword.equals("null")){
                    sendPassword = FragmentCustomDiagLogin.password;
                }
                new updateAccount().execute();
                Toast.makeText(this, "All changed except password", Toast.LENGTH_SHORT).show();
            }

            if(okayTosend ==10){
                sendPassword = SHA1(passwordConfirm.getText().toString());
                new updateAccount().execute();
            }

        }
        else{
            Toast.makeText(this, "Old password does not match with the given password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String changed = passwordNew.getText().toString();
        if (changed.equals("") || passwordConfirm.getText().toString().equals("")) {

            erroImageConfirm.setVisibility(View.GONE);
            errorImagePass.setVisibility(View.GONE);
            errorTextConfirm.setVisibility(View.GONE);
            errorTextPass.setVisibility(View.GONE);
        } else {
            if (changed.equals(passwordConfirm.getText().toString())) {


                erroImageConfirm.setVisibility(View.VISIBLE);
                errorImagePass.setVisibility(View.VISIBLE);
                errorTextConfirm.setVisibility(View.VISIBLE);
                errorTextPass.setVisibility(View.VISIBLE);

                erroImageConfirm.setImageResource(R.drawable.tick_green);
                errorImagePass.setImageResource(R.drawable.tick_green);
                errorTextConfirm.setText("Match");
                errorTextConfirm.setTextColor(Color.parseColor("#67C100"));
                errorTextPass.setText("Match");
                errorTextPass.setTextColor(Color.parseColor("#67C100"));
                okayTosend =10;
            } else {


                erroImageConfirm.setVisibility(View.VISIBLE);
                errorImagePass.setVisibility(View.VISIBLE);
                errorTextConfirm.setVisibility(View.VISIBLE);
                errorTextPass.setVisibility(View.VISIBLE);


                erroImageConfirm.setImageResource(R.drawable.errorpass);
                errorImagePass.setImageResource(R.drawable.errorpass);
                errorTextConfirm.setText("Passwords Don't Match");
                errorTextConfirm.setTextColor(Color.parseColor("#D72828"));
                errorTextPass.setText("Passwords Don't Match");
                errorTextPass.setTextColor(Color.parseColor("#D72828"));
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    class updateAccount extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                List<NameValuePair> params1 = new ArrayList<>();
                params1.add(new BasicNameValuePair("firstname", sendFirstName));
                params1.add(new BasicNameValuePair("lastname", sendLastName));
                params1.add(new BasicNameValuePair("password", sendPassword));
                params1.add(new BasicNameValuePair("phonenumber", sendPhoneNo));
                params1.add(new BasicNameValuePair("emailaddress", sendEmail));
                params1.add(new BasicNameValuePair("schoolname", sendCollege));
                params1.add(new BasicNameValuePair("username", senduser));

                JSONObject json = jsonParser.makeHttpRequest(UPDATE_URL, "POST", params1);

            } catch (Exception e) {

            }

            return null;
        }
    }

    public void getSchool(String schoolName) {

        Accounts.newschoolname.setText(schoolName);
    }

    public void uploadImage(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                realPath = ImageFilePath.getPath(Accounts.this, data.getData());

                filePath = data.getData();
                String namegetter[] = realPath.split("/");
                int finalElement = namegetter.length - 1;
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profilePic.setImageBitmap(bitmap);


                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                byte[] bitMapData = out.toByteArray();

                f = new File(this.getCacheDir(), namegetter[finalElement]);
                f.createNewFile();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitMapData);
                fos.flush();
                fos.close();

            } catch (Exception e) {

            }
        }
    }

    public class uploadImage extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                client.connect(FTP_HOST, 21);
                client.login(FTP_USER, FTP_PASS);
                client.setPassive(true);
                client.setType(FTPClient.TYPE_BINARY);
                client.setAutoNoopTimeout(20);

                client.upload(f);


                client.disconnect(true);
            } catch (Exception e) {

            }
            try {
                String namegetter[] = realPath.split("/");
                int finalElement = namegetter.length - 1;
                client2.connect(FTP_HOST, 21);
                client2.login(FTP_USER, FTP_PASS);


                client2.rename(namegetter[finalElement], shareUserName + "ProfilePic.jpg");
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Accounts.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
        }
    }



}
