package com.example.thearbiter.thriftbooksnepal.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.ImageFilePath;
import com.example.thearbiter.thriftbooksnepal.R;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;

/**
 * Created by Gaurav Jayasawal on 1/12/2017.
 */

public class ActivitySeller extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    static Boolean img1 = false, img2 = false, img3 = false;
    Boolean checkAllBoxes;
    EditText editTitleOfBook, editAuthorOfBook, editPriceOfBook, editHomeAddress;
    Button sellerSelectImage1, sellerSelectImage2, sellerSelectImage3;
    Button sellerUpload1Button;
    Button sellerUpload2Button;
    Button sellerUpload3Button;
    Button sellerUploadAllButton;
    static String titleOfBook;

    ProgressDialog pdialog;
    String realPath1, realPath2, realPath3;
    static final String FTP_HOST = "93.188.160.157";
    static final String FTP_USER = "u856924126";
    static final String FTP_PASS = "aadesh";
    final FTPClient client = new FTPClient();
    final FTPClient client2 = new FTPClient();
    TextView imageFilePath1, imageFilePath2, imageFilePath3;
    static int selectedSelectImageButton;

    Uri filepath1, filepath2, filepath3;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        verifyStoragePermissions(this);

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
        if (v == sellerSelectImage1) {
            Log.d("CODE HERE", "");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            selectedSelectImageButton = 1;
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
        if (v == sellerSelectImage2) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            selectedSelectImageButton = 2;
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
        if (v == sellerSelectImage3) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            selectedSelectImageButton = 3;
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

        if (v == sellerUpload1Button) {
            try {
                titleOfBook = editTitleOfBook.getText().toString();
                File file1 = new File(filepath1.getLastPathSegment());
                uploadFile1(file1);
            } catch (Exception e) {
                Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == sellerUpload2Button) {

            try {
                titleOfBook = editTitleOfBook.getText().toString();
                File file2 = new File(filepath2.getLastPathSegment());
                uploadFile2(file2);
            } catch (Exception e) {
                Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == sellerUpload3Button) {

            try {
                titleOfBook = editTitleOfBook.getText().toString();
                File file3 = new File(filepath3.getLastPathSegment());
                uploadFile3(file3);
            } catch (Exception e) {
                Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show();
            }
        }

        if (v == sellerUploadAllButton) {
            checkEmptyBoxes();
            if(checkAllBoxes){
                new AddItemToStore().execute();
            }
            else
            {
                Toast.makeText(this, "Please fill all details with at least 1 image", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public class AddItemToStore extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////BELOW THIS ARE UPLOAD FUNCTIONS FOR 3 IMAGES/////////////////////////////

    public void uploadFile1(final File file1Name) {

        class connecthis extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pdialog = new ProgressDialog(ActivitySeller.this);
                pdialog.setMessage("Uploading 1 .. Please Wait");
                pdialog.setIndeterminate(false);
                pdialog.setCancelable(false);
                pdialog.show();
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
                    client.setAutoNoopTimeout(10);
                    client.upload(new File(realPath1));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    client.disconnect(true);

                    String namegetter[] = realPath1.split("/");
                    int finalElement = namegetter.length - 1;
                    Log.d("kateko name 1", "" + namegetter[finalElement]);
                    pdialog.show();
                    try {

                        client2.connect(FTP_HOST, 21);
                        client2.login(FTP_USER, FTP_PASS);
                        client2.rename(namegetter[finalElement], Login.username + titleOfBook + "file1.jpg");
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                    client2.disconnect(true);
                    pdialog.dismiss();

                } catch (Exception e2) {
                    e2.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdialog.dismiss();
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
                pdialog = new ProgressDialog(ActivitySeller.this);
                pdialog.setMessage("Uploading 2 .. Please Wait");
                pdialog.setIndeterminate(false);
                pdialog.setCancelable(false);
                pdialog.show();
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
                    client.upload(new File(realPath2));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    client.disconnect(true);

                    String namegetter[] = realPath2.split("/");
                    int finalElement = namegetter.length - 1;
                    Log.d("kateko name 1", "" + namegetter[finalElement]);
                    pdialog.show();
                    try {
                        client2.connect(FTP_HOST, 21);
                        client2.login(FTP_USER, FTP_PASS);
                        client2.rename(namegetter[finalElement], Login.username + titleOfBook + "file2.jpg");
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                    client2.disconnect(true);
                    pdialog.dismiss();

                } catch (Exception e2) {
                    e2.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdialog.dismiss();
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
                pdialog = new ProgressDialog(ActivitySeller.this);
                pdialog.setMessage("Uploading 3.. Please Wait");
                pdialog.setIndeterminate(false);
                pdialog.setCancelable(false);
                pdialog.show();
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
                    client.upload(new File(realPath3));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    client.disconnect(true);

                    String namegetter[] = realPath3.split("/");
                    int finalElement = namegetter.length - 1;
                    Log.d("kateko name 1", "" + namegetter[finalElement]);
                    pdialog.show();
                    try {

                        client2.connect(FTP_HOST, 21);
                        client2.login(FTP_USER, FTP_PASS);
                        client2.rename(namegetter[finalElement], Login.username + titleOfBook + "file3.jpg");
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                    client2.disconnect(true);
                    pdialog.dismiss();

                } catch (Exception e2) {
                    e2.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdialog.dismiss();
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
        imageFilePath1.setText("UPLOADED!!!");
        imageFilePath1.setTextColor(Color.MAGENTA);
    }

    void value2Poster() {
        imageFilePath2.setText("UPLOADED!!!");
        imageFilePath2.setTextColor(Color.MAGENTA);
    }

    void value3Poster() {
        imageFilePath3.setText("UPLOADED!!!");
        imageFilePath3.setTextColor(Color.MAGENTA);
    }

    //////////////////////////////////////////////POSTER FUNCTIONS END HERE//////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////THIS RUNS AFTER IMAGE IS SELECTED FROM GALLERY///////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (selectedSelectImageButton == 1) {
                filepath1 = data.getData();
            } else if (selectedSelectImageButton == 2) {
                filepath2 = data.getData();
            } else {
                filepath3 = data.getData();
            }

            Log.d("value of filepath1", "" + filepath1);
            Log.d("value of filepath2", "" + filepath2);
            Log.d("value of filepath3", "" + filepath3);

            if (selectedSelectImageButton == 1) {
                realPath1 = ImageFilePath.getPath(ActivitySeller.this, data.getData());
//                realPath1 = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
            } else if (selectedSelectImageButton == 2) {
                realPath2 = ImageFilePath.getPath(ActivitySeller.this, data.getData());
            } else {
                realPath3 = ImageFilePath.getPath(ActivitySeller.this, data.getData());

            }
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
        if (editTitleOfBook.equals("") || editPriceOfBook.equals("") || editAuthorOfBook.equals("") || editHomeAddress.equals("")) {
            if (img1 || img2 || img3) {
                checkAllBoxes = true;
            } else {
                checkAllBoxes = false;
            }
        } else {
            checkAllBoxes = false;
        }

    }
}

