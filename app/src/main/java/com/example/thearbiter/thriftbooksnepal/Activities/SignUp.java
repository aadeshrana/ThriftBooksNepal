package com.example.thearbiter.thriftbooksnepal.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterFindSchool;
import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchool;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.ImageFilePath;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import it.sauronsoftware.ftp4j.FTPClient;

public class SignUp extends AppCompatActivity implements TextWatcher {
    static int requestToSend =0;
    static EditText firstName, lastName, userName, password, confirmPass, phoneNo, email;
    static EditText school;
    static TextView filePath, checkTextPass, checkTestConf,passStrength,validEmail;
    TextView requireFirst,requiredLast,requireUser,requireEmail,requireNumber,requirePassword,requireconfPassword,requireSchool;

    static ImageView profileImg, checkImgPass, checkImgConf,checkEmail;
    View strengthMeter;
    Button sendButton, choseButton;
    String strFirstName, strLastName, strUserName, strPassword, strConfirmPass, strPhoneNo, strEmail, strSchool, strImage;
    static String fname, lname, uname, passwd, confirmpwd, phoneNum, emailer;
    public static String schoolName;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pdialog;
    static final String FTP_HOST = "93.188.160.157";
    static final String FTP_USER = "u856924126";
    private static final String TAG_SUCCESS = "success";
    static final String FTP_PASS = "aadesh";
    private static final String REGISTER_URL = "http://frame.ueuo.com/thriftbooks/register.php";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String numberPatter = "[0-9]+";
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    int success;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private int PICK_IMAGE_REQUEST = 1;
    final FTPClient client = new FTPClient();
    Uri filepath;
    String realPath1;
    Bitmap bitmap;
    File f;
    final FTPClient client2 = new FTPClient();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AdapterFindSchool.selected = -1;
        requestToSend =1;
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
        filePath = (TextView) findViewById(R.id.imagePathProfile);
        profileImg = (ImageView) findViewById(R.id.profileImage);
        CustomDiagFindSchool customDiagFindSchool = new CustomDiagFindSchool();
        customDiagFindSchool.findAllSchool();
        school.setEnabled(false);

        requireFirst =(TextView)findViewById(R.id.firstnameRequre);
        requiredLast =(TextView)findViewById(R.id.firstlastRequre);
        requireUser =(TextView)findViewById(R.id.usernameRequre);
        requireEmail=(TextView)findViewById(R.id.emailnameRequre);
        requirePassword=(TextView)findViewById(R.id.passwordRequre);
        requireconfPassword=(TextView)findViewById(R.id.passConfRequre);
        requireNumber = (TextView)findViewById(R.id.numberRequre);
        requireSchool = (TextView)findViewById(R.id.schoolnameRequre);

        confirmPass.addTextChangedListener(this);
        password.addTextChangedListener(this);
        email.addTextChangedListener(this);
        firstName.addTextChangedListener(this);
        lastName.addTextChangedListener(this);
        phoneNo.addTextChangedListener(this);
        school.addTextChangedListener(this);

        checkImgConf = (ImageView) findViewById(R.id.imageCheckConfirm);
        checkImgPass = (ImageView) findViewById(R.id.imageCheckAccount);
        checkTestConf = (TextView) findViewById(R.id.errorTextConfirmPass);
        checkTextPass = (TextView) findViewById(R.id.errorTextNewPass);
        passStrength =(TextView)findViewById(R.id.passStrength);
        checkEmail = (ImageView)findViewById(R.id.imageCheckEmail);
        validEmail =(TextView)findViewById(R.id.errorTextEmail);
        checkTextPass.setVisibility(View.GONE);
        checkTestConf.setVisibility(View.GONE);
        checkImgConf.setVisibility(View.GONE);
        checkImgPass.setVisibility(View.GONE);
        passStrength.setVisibility(View.GONE);
        strengthMeter = (View)findViewById(R.id.view2);
        strengthMeter.setVisibility(View.GONE);
        verifyStoragePermissions(this);
        try {
            firstName.setText(fname);
            lastName.setText(lname);
            userName.setText(uname);
            password.setText(passwd);
            confirmPass.setText(confirmpwd);
            phoneNo.setText(phoneNum);
            email.setText(emailer);
            school.setText(schoolName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client3 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
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

    @Override
    public void onResume() {
        AdapterFindSchool.selected = -1;
        super.onResume();


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        AdapterFindSchool.selected = -1;
    }

    public void getSchool(String schoolName) {
        Log.d("value of school", "" + schoolName);
        SignUp.school.setText(schoolName);
    }

    public void signUpButton(View view) {
        if(firstName.getText().toString().length()<=0){
            requireFirst.setVisibility(View.VISIBLE);
            requestToSend=0;
        }
        if(lastName.getText().toString().length()<=0){
            requiredLast.setVisibility(View.VISIBLE);
            requestToSend=0;
        }
        if(userName.getText().toString().length()<=0){
            requireUser.setVisibility(View.VISIBLE);
            requestToSend=0;
        }
        if(email.getText().toString().length()<=0){
            requestToSend=0;
            requireEmail.setVisibility(View.VISIBLE);
        }
        if(phoneNo.getText().toString().length()<=0){
            requestToSend=0;
            requireNumber.setVisibility(View.VISIBLE);
        }
        if(password.getText().toString().length()<=0){
            requestToSend=0;
            requirePassword.setVisibility(View.VISIBLE);
        }
        if(confirmPass.getText().toString().length()<=0){
            requestToSend=0;
            requireconfPassword.setVisibility(View.VISIBLE);
        }
        if(school.getText().toString().length()<=0){
            requestToSend=0;
            requireSchool.setVisibility(View.VISIBLE);
        }



        strFirstName = firstName.getText().toString();
        strLastName = lastName.getText().toString();
        strUserName = userName.getText().toString();
        strPhoneNo = phoneNo.getText().toString();
        strEmail = email.getText().toString();
        strImage = userName.getText() + "ProfilePic";
        try {
            strSchool = school.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strSchool.equals(null)) {
            strSchool = "na";
        }

        strPassword = password.getText().toString();

        strConfirmPass = confirmPass.getText().toString();

        if (strPassword.equals(strConfirmPass) && requestToSend ==1) {
            new CreateUser().execute();
            new uploadImage().execute();
        } else {
            createDiag("Please fill all details");

        }



    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(firstName.getText().toString().length()>0){
            requireFirst.setVisibility(View.INVISIBLE);
            requestToSend=1;
        }
        if(lastName.getText().toString().length()>0){
            requiredLast.setVisibility(View.INVISIBLE);
            requestToSend=1;
        }
        if(userName.getText().toString().length()>0){
            requireUser.setVisibility(View.INVISIBLE);
            requestToSend=1;
        }
        if(email.getText().toString().length()>0){
            requestToSend=1;
            requireEmail.setVisibility(View.INVISIBLE);
        }
        if(phoneNo.getText().toString().length()>0){
            requestToSend=1;
            requireNumber.setVisibility(View.INVISIBLE);
        }
        if(password.getText().toString().length()>0){
            requestToSend=1;
            requirePassword.setVisibility(View.INVISIBLE);
        }
        if(confirmPass.getText().toString().length()>0){
            requestToSend=1;
            requireconfPassword.setVisibility(View.INVISIBLE);
        }
        if(school.getText().toString().length()>0){
            requestToSend=1;
            requireSchool.setVisibility(View.INVISIBLE);
        }

        if(email.length()<=0){
            checkEmail.setVisibility(View.GONE);
            validEmail.setVisibility(View.GONE);
            requestToSend =0;
        }
        else if(email.getText().toString().trim().matches(emailPattern) && email.length()>0){
            checkEmail.setVisibility(View.VISIBLE);
            checkEmail.setImageResource(R.drawable.tick_green);
            validEmail.setVisibility(View.VISIBLE);
            validEmail.setText("Valid Email");
            validEmail.setTextColor(Color.parseColor("#67C100"));
            requestToSend=1;
        }
        else{
            checkEmail.setVisibility(View.VISIBLE);
            validEmail.setVisibility(View.VISIBLE);
            checkEmail.setImageResource(R.drawable.errorpass);
            validEmail.setText("Not a Valid Email");
            validEmail.setTextColor(Color.parseColor("#D72828"));
            requestToSend=2;
        }



        if(password.length()<=0){
            passStrength.setVisibility(View.GONE);
            strengthMeter.setVisibility(View.GONE);

            requestToSend =0;
        }
        else if (password.length()<7){
            requestToSend = 0;

           passStrength.setVisibility(View.VISIBLE);
            strengthMeter.setVisibility(View.VISIBLE);
            strengthMeter.getLayoutParams().width=100;
            strengthMeter.setBackgroundColor(Color.parseColor("#FFFF0000"));
            passStrength.setText("Very Weak");
            passStrength.setTextColor(Color.parseColor("#FFFF0000"));
        }
        else if(password.length()==7 || password.length()==8){
            requestToSend =1;
            passStrength.setText("Moderate");
            passStrength.setTextColor(Color.parseColor("#FFC1E87D"));
            strengthMeter.setVisibility(View.VISIBLE);
            strengthMeter.getLayoutParams().width=150;
            strengthMeter.setBackgroundColor(Color.parseColor("#FFC1E87D"));
        }
        else if(password.length()>8){
            requestToSend =1;
            passStrength.setText("Strong");
            passStrength.setTextColor(Color.parseColor("#67C100"));
            strengthMeter.setVisibility(View.VISIBLE);
            strengthMeter.getLayoutParams().width=200;
            strengthMeter.setBackgroundColor(Color.parseColor("#67C100"));

        }



        String changed = password.getText().toString();
        if (changed.equals("") || confirmPass.getText().toString().equals("")) {

            checkImgPass.setVisibility(View.GONE);
            checkImgConf.setVisibility(View.GONE);
            checkTextPass.setVisibility(View.GONE);
            checkTestConf.setVisibility(View.GONE);
        } else {
            if (changed.equals(confirmPass.getText().toString())) {

                checkImgPass.setVisibility(View.VISIBLE);
                checkImgConf.setVisibility(View.VISIBLE);
                checkTextPass.setVisibility(View.VISIBLE);
                checkTestConf.setVisibility(View.VISIBLE);

                checkImgConf.setImageResource(R.drawable.tick_green);
                checkImgPass.setImageResource(R.drawable.tick_green);
                checkTestConf.setText("Match");
                checkTestConf.setTextColor(Color.parseColor("#67C100"));
                checkTextPass.setText("Match");
                checkTextPass.setTextColor(Color.parseColor("#67C100"));
            } else {

                checkImgPass.setVisibility(View.VISIBLE);
                checkImgConf.setVisibility(View.VISIBLE);
                checkTextPass.setVisibility(View.VISIBLE);
                checkTestConf.setVisibility(View.VISIBLE);


                checkImgConf.setImageResource(R.drawable.errorpass);
                checkImgPass.setImageResource(R.drawable.errorpass);
                checkTestConf.setText("Passwords Don't Match");
                checkTestConf.setTextColor(Color.parseColor("#D72828"));
                checkTextPass.setText("Passwords Don't Match");
                checkTextPass.setTextColor(Color.parseColor("#D72828"));
            }
        }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SignUp Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client3.connect();
        AppIndex.AppIndexApi.start(client3, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client3, getIndexApiAction());
        client3.disconnect();
    }

    class CheckSchool extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            return null;
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
                params.add(new BasicNameValuePair("password", Login.SHA1(strPassword)));
                params.add(new BasicNameValuePair("emailaddress", strEmail));
                params.add(new BasicNameValuePair("schoolname", strSchool));
                params.add(new BasicNameValuePair("phonenumber", strPhoneNo));
                params.add(new BasicNameValuePair("profilepic", strImage));
                //posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", params);
                //full json response
                Log.d("registering attempt", json.toString());
                //json success element
                 success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User created!", json.toString());

                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdialog.dismiss();
            if (success == 1) {
                new AlertDialog.Builder(SignUp.this)
                        .setTitle("Success")
                        .setMessage("User Successfully Registered")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
            else
                Toast.makeText(SignUp.this, "Failed to create", Toast.LENGTH_SHORT).show();
        }
    }

    public void findSchools(View view) {

        fname = firstName.getText().toString();
        lname = lastName.getText().toString();
        uname = userName.getText().toString();
        passwd = password.getText().toString();
        emailer = email.getText().toString();
        confirmpwd = confirmPass.getText().toString();
        phoneNum = phoneNo.getText().toString();

        CustomDiagFindSchool customDiagFindSchool = new CustomDiagFindSchool();
        customDiagFindSchool.show(getFragmentManager(), "abc");
        school.setEnabled(true);
        school.setHint("College/School not found?");

    }

    public void uploadProfileSignUp(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//
        Toast.makeText(SignUp.this, "done", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                realPath1 = ImageFilePath.getPath(SignUp.this, data.getData());
                filePath.setText(realPath1);
                filepath = data.getData();
                String namegetter[] = realPath1.split("/");
                int finalElement = namegetter.length - 1;
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                profileImg.setImageBitmap(bitmap);
                Log.d("k ho ta path", "" + namegetter[finalElement]);

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
                Log.d("send bho", "" + f);
                client.disconnect(true);
            } catch (Exception e) {
                Log.d("any error?", "" + e);
            }
            try {
                String namegetter[] = realPath1.split("/");
                int finalElement = namegetter.length - 1;
                client2.connect(FTP_HOST, 21);
                client2.login(FTP_USER, FTP_PASS);


                client2.rename(namegetter[finalElement], strUserName + "ProfilePic.jpg");
            } catch (Exception e) {

            }

            return null;
        }

    }

    public void createDiag(String okay){
        new AlertDialog.Builder(SignUp.this)
                .setTitle("Unsuccessful")
                .setMessage(okay)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}


