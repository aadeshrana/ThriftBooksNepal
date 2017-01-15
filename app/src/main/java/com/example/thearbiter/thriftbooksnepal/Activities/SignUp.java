package com.example.thearbiter.thriftbooksnepal.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import it.sauronsoftware.ftp4j.FTPClient;

public class SignUp extends AppCompatActivity {

    static EditText firstName, lastName, userName, password, confirmPass, phoneNo, email;
    static EditText school;
    static TextView filePath;
    static ImageView profileImg;
    Button sendButton, choseButton;
    String strFirstName, strLastName, strUserName, strPassword, strConfirmPass, strPhoneNo, strEmail, strSchool, strImage;
    static String fname, lname, uname, passwd, confirmpwd, phoneNum, emailer;
    public static String schoolName;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pdialog;
    static final String FTP_HOST = "93.188.160.157";
    static final String FTP_USER = "u856924126";
    static final String FTP_PASS = "aadesh";
    private static final String REGISTER_URL = "http://frame.ueuo.com/thriftbooks/register.php";
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private int PICK_IMAGE_REQUEST = 1;
    final FTPClient client = new FTPClient();
    Uri filepath;
    String realPath1;
    Bitmap bitmap;
    File f;
    final FTPClient client2 = new FTPClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AdapterFindSchool.selected = -1;
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
        filePath =(TextView)findViewById(R.id.imagePathProfile);
        profileImg =(ImageView)findViewById(R.id.profileImage);
        CustomDiagFindSchool customDiagFindSchool = new CustomDiagFindSchool();
        customDiagFindSchool.findAllSchool();

        sendButton = (Button)findViewById(R.id.butonUpload);
        choseButton =(Button)findViewById(R.id.choseUpload);

        sendButton.setVisibility(View.GONE);
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
    public void onResume(){
        AdapterFindSchool.selected=-1;
        super.onResume();


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        AdapterFindSchool.selected=-1;
    }

    public void getSchool(String schoolName) {
        Log.d("value of school", "" + schoolName);
        SignUp.school.setText(schoolName);
    }

    public void signUpButton(View view) {

        strFirstName = firstName.getText().toString();
        strLastName = lastName.getText().toString();
        strUserName = userName.getText().toString();
        strPhoneNo = phoneNo.getText().toString();
        strEmail = email.getText().toString();
        strImage =userName.getText()+"ProfilePic";
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

        if (strPassword.equals(strConfirmPass)) {
            new CreateUser().execute();
        } else {
            password.setBackgroundColor(Color.RED);
            confirmPass.setBackgroundColor(Color.RED);
        }

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
                params.add(new BasicNameValuePair("password", strPassword));
                params.add(new BasicNameValuePair("emailaddress", strEmail));
                params.add(new BasicNameValuePair("schoolname", strSchool));
                params.add(new BasicNameValuePair("phonenumber", strPhoneNo));
                params.add(new BasicNameValuePair("profilepic",strImage));
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

    }

    public void uploadProfileSignUp(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//        File file1 = new File(filepath.getLastPathSegment());
        //uploadFile1(file1);
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
                choseButton.setVisibility(View.GONE);
                sendButton.setVisibility(View.VISIBLE);
                sendButton.setBackgroundColor(Color.MAGENTA);
            }catch (Exception e){

            }
        }
    }
    public void sendProfileSignUp(View view){
        Toast.makeText(SignUp.this, "upload here", Toast.LENGTH_SHORT).show();
        new uploadImage().execute();

    }

    public class uploadImage extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                client.connect(FTP_HOST, 21);
                client.login(FTP_USER, FTP_PASS);
                client.setPassive(true);
                client.setType(FTPClient.TYPE_BINARY);
                client.setAutoNoopTimeout(20);

                client.upload(f);
                Log.d("send bho",""+f);
            }catch(Exception e){

            }


            return null;
        }

    }
}


/*
//////
try{
        String namegetter[] = realPath1.split("/");
        int finalElement = namegetter.length - 1;
        client2.connect(FTP_HOST, 21);
        client2.login(FTP_USER, FTP_PASS);


        client2.rename(namegetter[finalElement], Login.username +"ProfilePic");
        }catch (Exception e){

        }*/
