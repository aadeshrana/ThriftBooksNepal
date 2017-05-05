package com.example.thearbiter.thriftbooksnepal.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.ImageFilePath;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomDiagLogin;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.sauronsoftware.ftp4j.FTPClient;

/**
 * Created by Gaurav Jayasawal on 1/12/2017.
 */

public class ActivitySeller extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String ADD_ITEM_URL = "http://frame.ueuo.com/thriftbooks/additemtostore.php";
    JSONParser jsonParser = new JSONParser();
    Toolbar toolbar;
    static Boolean img1 = false, img2 = false, img3 = false;
    Boolean checkAllBoxes;
    EditText editTitleOfBook, editAuthorOfBook, editPriceOfBook, editHomeAddress;
    static String title, author, price, homeaddress;
    Button sellerSelectImage1, sellerSelectImage2, sellerSelectImage3;
    Button sellerUpload1Button;
    Button sellerUpload2Button;
    Button sellerUpload3Button;
    Button sellerUploadAllButton;
    static String titleOfBook;
    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap bitmap3;

    TextView bookNameRequired, nameOfAuthorRequired, priceOfBookRequired, homeAddressRequired, allFieldsRequired;

    public static String choiseOfBoard;
    File f;
    File f2;
    File f3;

    File file1, file2, file3;
    ProgressDialog pdialog, pdialog1;
    String realPath1, realPath2, realPath3;
    static final String FTP_HOST = "93.188.160.157";
    static final String FTP_USER = "u856924126";
    static final String FTP_PASS = "aadesh";
    final FTPClient client = new FTPClient();
    final FTPClient client2 = new FTPClient();
    TextView imageFilePath1, imageFilePath2, imageFilePath3;
    static int selectedSelectImageButton;
    ProgressBar imgUploadOneProgress, imgUploadTwoProgress, imgUploadThreeProgress;
    Uri filepath1, filepath2, filepath3;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    String spUsername, spfirstName, splastName, spEmail, spSchool, spPhoneNo;
    Spinner spinnerCourse;
    static String sendcourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        imgUploadOneProgress = (ProgressBar) findViewById(R.id.imageUploadOneProgress);
        imgUploadTwoProgress = (ProgressBar) findViewById(R.id.imageUploadTwoProgress);
        imgUploadThreeProgress = (ProgressBar) findViewById(R.id.imageUploadThreeProgress);

        spinnerCourse = (Spinner) findViewById(R.id.spinnerCourse);
        spinnerCourse.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        imgUploadOneProgress.setVisibility(View.GONE);
        imgUploadTwoProgress.setVisibility(View.GONE);
        imgUploadThreeProgress.setVisibility(View.GONE);

        bookNameRequired = (TextView) findViewById(R.id.nameOfBookRequired);
        nameOfAuthorRequired = (TextView) findViewById(R.id.nameOfAuthorRequired);
        priceOfBookRequired = (TextView) findViewById(R.id.priceToBeMarkedRequired);
        homeAddressRequired = (TextView) findViewById(R.id.homeAddressRequired);
        allFieldsRequired = (TextView) findViewById(R.id.allFieldsRequired);

        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sharedpref.edit();

        spUsername = sharedpref.getString("a", "nullSeller");
        spfirstName = sharedpref.getString("firstNameSharePref1", "nullSeller");
        splastName = sharedpref.getString("lastNameSharePref", "nullSeller");
        spEmail = sharedpref.getString("emailSharePref", "nullSeller");
        spSchool = sharedpref.getString("schoolSharePref", "nullSeller");
        spPhoneNo = sharedpref.getString("phoneSharePref", "nullSeller");

        Log.d("shared", "bataako " + spfirstName);
        try {
            if (spUsername.equals("nullSeller")) {
                spUsername = FragmentCustomDiagLogin.username;
                spfirstName = FragmentCustomDiagLogin.firstName;
                splastName = FragmentCustomDiagLogin.lastName;
                spEmail = FragmentCustomDiagLogin.emailAddress;
                spSchool = FragmentCustomDiagLogin.school;
                spPhoneNo = FragmentCustomDiagLogin.phoneNumber;
                Log.d("Fragment", "bataako " + spfirstName);
            }


        } catch (Exception e) {

        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        verifyStoragePermissions(this);


        try {
            Bundle chosenValueBoard = getIntent().getExtras();


            ActivitySeller.choiseOfBoard = chosenValueBoard.getString("chosenValueBoard");
            Log.d("valuemain", "" + ActivitySeller.choiseOfBoard);

        } catch (Exception e) {

        }


        //////////////////////////FIELD INITIALIZERS//////////////////////////////
        imageFilePath1 = (TextView) findViewById(R.id.sellerImageFileName1);
        imageFilePath2 = (TextView) findViewById(R.id.sellerImageFileName2);
        imageFilePath3 = (TextView) findViewById(R.id.sellerImageFileName3);

        editTitleOfBook = (EditText) findViewById(R.id.sellerClassNameOfBook);
        editAuthorOfBook = (EditText) findViewById(R.id.sellerClassAuthorOfBook);
        editPriceOfBook = (EditText) findViewById(R.id.sellerClassPriceOfBook);
        editHomeAddress = (EditText) findViewById(R.id.sellerClassAddressOfHome);

        sellerSelectImage1 = (Button) findViewById(R.id.sellerSelectImageOneButton);
        sellerSelectImage2 = (Button) findViewById(R.id.sellerSelectImageTwoButton);
        sellerSelectImage3 = (Button) findViewById(R.id.sellerSelectImageThreeButton);

        sellerUpload1Button = (Button) findViewById(R.id.sellerUpload1Button);
        sellerUpload2Button = (Button) findViewById(R.id.sellerUpload2Button);
        sellerUpload3Button = (Button) findViewById(R.id.sellerUpload3Button);
        sellerUploadAllButton = (Button) findViewById(R.id.sellerUploadAllButton);

        sellerSelectImage1.setOnClickListener(this);
        sellerSelectImage2.setOnClickListener(this);
        sellerSelectImage3.setOnClickListener(this);

        sellerUpload1Button.setOnClickListener(this);
        sellerUpload2Button.setOnClickListener(this);
        sellerUpload3Button.setOnClickListener(this);
        sellerUploadAllButton.setOnClickListener(this);

        editTitleOfBook.addTextChangedListener(this);
        editAuthorOfBook.addTextChangedListener(this);
        editPriceOfBook.addTextChangedListener(this);
        editHomeAddress.addTextChangedListener(this);
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
    public void onClick(View v) {
        if (editTitleOfBook.getText().toString().equals("") || editAuthorOfBook.getText().toString().equals("")
                || editPriceOfBook.getText().toString().equals("") || editHomeAddress.getText().toString().equals("")) {
            allFieldsRequired.setVisibility(View.VISIBLE);
            if (editTitleOfBook.getText().toString().equals("")) {
                bookNameRequired.setVisibility(View.VISIBLE);
            }
            if (editAuthorOfBook.getText().toString().equals("")) {
                nameOfAuthorRequired.setVisibility(View.VISIBLE);
            }
            if (editPriceOfBook.getText().toString().equals("")) {
                priceOfBookRequired.setVisibility(View.VISIBLE);
            }
            if (editHomeAddress.getText().toString().equals("")) {
                homeAddressRequired.setVisibility(View.VISIBLE);
            }
        }

        if (v == sellerSelectImage1) {
            if (allFieldsRequired.getVisibility() == View.GONE) {
                Log.d("CODE HERE", "");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                selectedSelectImageButton = 1;
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                imgUploadOneProgress.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Please enter all details first.", Toast.LENGTH_SHORT).show();
            }
        }
        if (v == sellerSelectImage2) {
            if (allFieldsRequired.getVisibility() == View.GONE) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                selectedSelectImageButton = 2;
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                imgUploadTwoProgress.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Please enter all details first.", Toast.LENGTH_SHORT).show();
            }
        }
        if (v == sellerSelectImage3) {
            if (allFieldsRequired.getVisibility() == View.GONE) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                selectedSelectImageButton = 3;
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                imgUploadThreeProgress.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Please enter all details first.", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == sellerUpload1Button) {
            try {
                titleOfBook = editTitleOfBook.getText().toString();

                    file1 = new File(filepath1.getLastPathSegment());
                    uploadFile1(file1);

            } catch (Exception e) {
                imageFilePath1.setHint("No image selected");
                imageFilePath1.setHintTextColor(Color.RED);
            }
        }

        if (v == sellerUpload2Button) {

            try {
                titleOfBook = editTitleOfBook.getText().toString();
                  file2 = new File(filepath2.getLastPathSegment());
                uploadFile2(file2);

            } catch (Exception e) {
                imageFilePath2.setHint("No image selected");
                imageFilePath2.setHintTextColor(Color.RED);
            }
        }

        if (v == sellerUpload3Button) {

            try {
                titleOfBook = editTitleOfBook.getText().toString();

                    file3 = new File(filepath3.getLastPathSegment());
                    uploadFile3(file3);

            } catch (Exception e) {
                imageFilePath3.setHint("No image selected");
                imageFilePath3.setHintTextColor(Color.RED);
            }
        }

        if (v == sellerUploadAllButton) {
            checkEmptyBoxes();
            try {
                if (checkAllBoxes) {
                    title = editTitleOfBook.getText().toString();
                    author = editAuthorOfBook.getText().toString();
                    price = editPriceOfBook.getText().toString();
                    homeaddress = editHomeAddress.getText().toString();

                    new AddItemToStore().execute();
                } else {
                    if (allFieldsRequired.getVisibility() == View.GONE) {
                        Toast.makeText(this, "Please upload at least 1 image.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
            }


        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (editTitleOfBook.getText().toString().length() > 0) {
            bookNameRequired.setVisibility(View.INVISIBLE);
        }
        if (editAuthorOfBook.getText().toString().length() > 0) {
            nameOfAuthorRequired.setVisibility(View.INVISIBLE);
        }
        if (editPriceOfBook.getText().toString().length() > 0) {
            priceOfBookRequired.setVisibility(View.INVISIBLE);
        }
        if (editHomeAddress.getText().toString().length() > 0) {
            homeAddressRequired.setVisibility(View.INVISIBLE);
        }

        if (editTitleOfBook.getText().toString().length() > 0 && editAuthorOfBook.getText().toString().length() > 0
                && editPriceOfBook.getText().toString().length() > 0 && editHomeAddress.getText().toString().length() > 0) {
            allFieldsRequired.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public class AddItemToStore extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog1 = new ProgressDialog(ActivitySeller.this);
            pdialog1.setMessage("Posting your Ad..");
            pdialog1.setIndeterminate(false);
            pdialog1.setCancelable(false);
            pdialog1.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {


                List<NameValuePair> param1 = new ArrayList<>();
                param1.add(new BasicNameValuePair("username", spUsername));
                param1.add(new BasicNameValuePair("firstname", spfirstName));
                param1.add(new BasicNameValuePair("lastname", splastName));
                param1.add(new BasicNameValuePair("nameofbook", ActivitySeller.title));
                param1.add(new BasicNameValuePair("nameofauthor", ActivitySeller.author));
                param1.add(new BasicNameValuePair("price", ActivitySeller.price));
                param1.add(new BasicNameValuePair("homeaddress", ActivitySeller.homeaddress));
                param1.add(new BasicNameValuePair("school", spSchool));

                param1.add(new BasicNameValuePair("image1name", spUsername + titleOfBook + "file1.jpg"));

                if (img2) {
                    param1.add(new BasicNameValuePair("image2name", spUsername + titleOfBook + "file2.jpg"));
                } else {
                    param1.add(new BasicNameValuePair("image2name", "null"));
                }
                if (img3) {
                    param1.add(new BasicNameValuePair("image3name", spUsername + titleOfBook + "file3.jpg"));
                } else {
                    param1.add(new BasicNameValuePair("image3name", "null"));
                }
                param1.add(new BasicNameValuePair("phonenumber", spPhoneNo));
                param1.add(new BasicNameValuePair("emailaddress", spEmail));

                param1.add(new BasicNameValuePair("course", sendcourse));


                JSONObject json = jsonParser.makeHttpRequest(ADD_ITEM_URL, "POST", param1);

                Log.d("registering attempt", json.toString());

            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdialog1.dismiss();
            Toast.makeText(ActivitySeller.this, "Successfully added your item to our shop", Toast.LENGTH_SHORT).show();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////BELOW THIS ARE UPLOAD FUNCTIONS FOR 3 IMAGES/////////////////////////////

    public void uploadFile1(final File file1Name) {

        class connecthis extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                imgUploadOneProgress.setVisibility(View.VISIBLE);
                sellerUpload1Button.setVisibility(View.INVISIBLE);
            }

            @Override
            protected String doInBackground(String... params) {


                try {
                    client.connect(FTP_HOST, 21);
                    client.login(FTP_USER, FTP_PASS);
           /* client.enterLocalPassiveMode();*/
                    client.setPassive(true);
                    Log.d("REAL PATH OF LIFE IS 1 ", "" + realPath1);
                    client.setType(FTPClient.TYPE_BINARY);
                    client.setAutoNoopTimeout(20);
                    client.upload(f);


                } catch (Exception e) {
                    e.printStackTrace();
//
                }

                try {
                    client.disconnect(true);

                    String namegetter[] = realPath1.split("/");
                    int finalElement = namegetter.length - 1;
                    Log.d("kateko name 1", "" + namegetter[finalElement]);
//
                    try {

                        client2.connect(FTP_HOST, 21);
                        client2.login(FTP_USER, FTP_PASS);

                        titleOfBook = titleOfBook.replaceAll("\\s+", "");
                        client2.rename(namegetter[finalElement], spUsername + titleOfBook + "file1.jpg");
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                    client2.disconnect(true);
//                    pdialog.dismiss();

                } catch (Exception e2) {
                    e2.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                pdialog.dismiss();
                Toast.makeText(ActivitySeller.this, "Uploaded Image 1", Toast.LENGTH_SHORT).show();


                img1 = true;
                value1Poster();
            }
        }

        new

                connecthis().execute();

    }

    public void uploadFile2(final File file2Name) {


        class connecthis extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                imgUploadTwoProgress.setVisibility(View.VISIBLE);
                sellerUpload2Button.setVisibility(View.INVISIBLE);
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    client.connect(FTP_HOST, 21);
                    client.login(FTP_USER, FTP_PASS);
           /* client.enterLocalPassiveMode();*/
                    client.setPassive(true);
                    Log.d("REAL PATH OF LIFE IS 2", "" + realPath2);
                    client.setType(FTPClient.TYPE_BINARY);
                    client.setAutoNoopTimeout(10);
                    client.upload(f2);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    client.disconnect(true);

                    String namegetter[] = realPath2.split("/");
                    int finalElement = namegetter.length - 1;
                    Log.d("kateko name 1", "" + namegetter[finalElement]);
//                    pdialog.show();
                    try {
                        client2.connect(FTP_HOST, 21);
                        client2.login(FTP_USER, FTP_PASS);

                        titleOfBook = titleOfBook.replaceAll("\\s+", "");


                        client2.rename(namegetter[finalElement], spUsername + titleOfBook + "file2.jpg");
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                    client2.disconnect(true);
//                    pdialog.dismiss();

                } catch (Exception e2) {
                    e2.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                pdialog.dismiss();
                img2 = true;
                Toast.makeText(ActivitySeller.this, "Uploaded Image 2", Toast.LENGTH_SHORT).show();
                value2Poster();
            }
        }
        new connecthis().execute();
    }

    public void uploadFile3(final File file3Name) {

        class connecthis extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                imgUploadThreeProgress.setVisibility(View.VISIBLE);
                sellerUpload3Button.setVisibility(View.INVISIBLE);
            }

            @Override
            protected String doInBackground(String... params) {


                try {
                    client.connect(FTP_HOST, 21);
                    client.login(FTP_USER, FTP_PASS);
           /* client.enterLocalPassiveMode();*/
                    client.setPassive(true);
                    Log.d("REAL PATH OF LIFE IS 3", "" + realPath3);
                    client.setType(FTPClient.TYPE_BINARY);
                    client.setAutoNoopTimeout(10);
                    client.upload(f3);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    client.disconnect(true);

                    String namegetter[] = realPath3.split("/");
                    int finalElement = namegetter.length - 1;
                    Log.d("kateko name 1", "" + namegetter[finalElement]);
//                    pdialog.show();
                    try {

                        client2.connect(FTP_HOST, 21);
                        client2.login(FTP_USER, FTP_PASS);

                        titleOfBook = titleOfBook.replaceAll("\\s+", "");

                        client2.rename(namegetter[finalElement], spUsername + titleOfBook + "file3.jpg");
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                    client2.disconnect(true);
//                    pdialog.dismiss();

                } catch (Exception e2) {
                    e2.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                pdialog.dismiss();
                img3 = true;
                Toast.makeText(ActivitySeller.this, "Uploaded Image 3", Toast.LENGTH_SHORT).show();
                value3Poster();
            }
        }
        new connecthis().execute();
    }

    //////////////////////////////// UPLOAD FUNCTIONS END HERE///////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////THESE FUNCTIONS SET COLORS AND VALUES IF IMAGE IS SUCCESSFULLY UPLOADED//////////////////

    void value1Poster() {
        imageFilePath1.setText("");
        imageFilePath1.setHint("UPLOADED!!!");
        imageFilePath1.setHintTextColor(Color.MAGENTA);
        imgUploadOneProgress.setVisibility(View.GONE);
        sellerUpload1Button.setVisibility(View.VISIBLE);
    }

    void value2Poster() {
        imageFilePath2.setText("");
        imageFilePath2.setHint("UPLOADED!!!");
        imageFilePath2.setHintTextColor(Color.MAGENTA);
        imgUploadTwoProgress.setVisibility(View.GONE);
        sellerUpload2Button.setVisibility(View.VISIBLE);
    }

    void value3Poster() {
        imageFilePath3.setText("");
        imageFilePath3.setHint("UPLOADED!!!");
        imageFilePath3.setHintTextColor(Color.MAGENTA);
        imgUploadThreeProgress.setVisibility(View.GONE);
        sellerUpload3Button.setVisibility(View.VISIBLE);
    }

    //////////////////////////////////////////////POSTER FUNCTIONS END HERE//////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////THIS RUNS AFTER IMAGE IS SELECTED FROM GALLERY///////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (selectedSelectImageButton == 1) {
                realPath1 = ImageFilePath.getPath(ActivitySeller.this, data.getData());
            } else if (selectedSelectImageButton == 2) {
                realPath2 = ImageFilePath.getPath(ActivitySeller.this, data.getData());
            } else {
                realPath3 = ImageFilePath.getPath(ActivitySeller.this, data.getData());

            }
            if (selectedSelectImageButton == 1) {
                filepath1 = data.getData();
                try {
                    String namegetter[] = realPath1.split("/");
                    int finalElement = namegetter.length - 1;

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath1);

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

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (selectedSelectImageButton == 2) {
                filepath2 = data.getData();
                try {
                    String namegetter2[] = realPath2.split("/");
                    int finalElement2 = namegetter2.length - 1;

                    bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath2);

                    ByteArrayOutputStream out2 = new ByteArrayOutputStream();
                    bitmap2 = Bitmap.createScaledBitmap(bitmap2, 400, 400, true);
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 80, out2);
                    byte[] bitMapData2 = out2.toByteArray();

                    f2 = new File(this.getCacheDir(), namegetter2[finalElement2]);
                    f2.createNewFile();
                    FileOutputStream fos2 = new FileOutputStream(f2);
                    fos2.write(bitMapData2);
                    fos2.flush();
                    fos2.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                filepath3 = data.getData();
                try {
                    String namegetter3[] = realPath3.split("/");
                    int finalElement3 = namegetter3.length - 1;

                    bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath3);

                    ByteArrayOutputStream out3 = new ByteArrayOutputStream();
                    bitmap3 = Bitmap.createScaledBitmap(bitmap3, 400, 400, true);
                    bitmap3.compress(Bitmap.CompressFormat.JPEG, 80, out3);
                    byte[] bitMapData3 = out3.toByteArray();

                    f3 = new File(this.getCacheDir(), namegetter3[finalElement3]);
                    f3.createNewFile();
                    FileOutputStream fos2 = new FileOutputStream(f3);
                    fos2.write(bitMapData3);
                    fos2.flush();
                    fos2.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.d("value of filepath1", "" + filepath1);
            Log.d("value of filepath2", "" + filepath2);
            Log.d("value of filepath3", "" + filepath3);


            Log.i("SEE REAL PATH", "onActivityResult: file path : " + realPath1);
            filePathSetter();

        }
    }

    ////////////////////EXTRA FUNCTIONS///////////////////////////////////////////////
    private void filePathSetter() {
        if (selectedSelectImageButton == 1) {
            imageFilePath1.setText(realPath1);
        } else if (selectedSelectImageButton == 2) {
            imageFilePath2.setText(realPath2);
        } else {
            imageFilePath3.setText(realPath3);
        }
    }

    void checkEmptyBoxes() {
        if (!editTitleOfBook.getText().toString().equals("") && !editPriceOfBook.getText().toString().equals("") && !editAuthorOfBook.getText().toString().equals("") && !editHomeAddress.getText().toString().equals("")) {
            if (img1 || img2 || img3) {
                checkAllBoxes = true;
            } else {
                checkAllBoxes = false;
            }
        } else {
            checkAllBoxes = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alevel_options, menu);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            switch (pos) {
                case 0:
                    sendcourse = "alevel";
                    break;
                case 1:
                    sendcourse = "ib";
                    break;
                case 2:
                    sendcourse = "plustwo";
                    break;
                case 3:
                    sendcourse = "others";
                    break;
                default:
                    sendcourse = "nullValue";
                    break;

            }
            Log.d("Value", ":" + sendcourse);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
}

