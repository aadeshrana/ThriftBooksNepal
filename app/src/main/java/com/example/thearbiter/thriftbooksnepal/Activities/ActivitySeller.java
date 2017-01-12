package com.example.thearbiter.thriftbooksnepal.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.ImageFilePath;
import com.example.thearbiter.thriftbooksnepal.R;

import java.io.File;

/**
 * Created by Gaurav Jayasawal on 1/12/2017.
 */

public class ActivitySeller extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    EditText editTitleOfBook, editAuthorOfBook, editPriceOfBook;
    Button sellerSelectImage1, sellerSelectImage2, sellerSelectImage3;
    Button sellerUploadButton;
    String realPath;
//    final FTPClient client = new FTPClient();
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

        imageFilePath1 = (TextView) findViewById(R.id.sellerImageFileName1);
        imageFilePath2 = (TextView) findViewById(R.id.sellerImageFileName2);
        imageFilePath3 = (TextView) findViewById(R.id.sellerImageFileName3);

        editTitleOfBook = (EditText) findViewById(R.id.sellerClassNameOfBook);
        editAuthorOfBook = (EditText) findViewById(R.id.sellerClassAuthorOfBook);
        editPriceOfBook = (EditText) findViewById(R.id.sellerClassPriceOfBook);

        sellerSelectImage1 = (Button) findViewById(R.id.sellerSelectImageOneButton);
        sellerSelectImage2 = (Button) findViewById(R.id.sellerSelectImageTwoButton);
        sellerSelectImage3 = (Button) findViewById(R.id.sellerSelectImageThreeButton);

        sellerUploadButton = (Button) findViewById(R.id.sellerUploadButton);

        sellerSelectImage1.setOnClickListener(this);
        sellerSelectImage2.setOnClickListener(this);
        sellerSelectImage3.setOnClickListener(this);
        sellerUploadButton.setOnClickListener(this);

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

        if (v == sellerUploadButton) {

            File file1 = new File(filepath1.getLastPathSegment());
            File file2 = new File(filepath2.getLastPathSegment());
            File file3 = new File(filepath2.getLastPathSegment());

            uploadFile1(file1);
//            uploadFile2(file);
//            uploadFile3(file);
        }
    }

    public void uploadFile1(final File file1Name) {


//        class connecthis extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected String doInBackground(String... params) {
//
//
//                try {
//                    client.connect(FTP_HOST, 21);
//                    client.login(FTP_USER, FTP_PASS);
//           /* client.enterLocalPassiveMode();*/
//                    client.setPassive(true);
//                    Log.d("REAL PATH OF LIFE IS ", "" + realPath);
//                    client.setType(FTPClient.TYPE_BINARY);
//                    client.setAutoNoopTimeout(20);
//                    client.upload(new File(realPath));
//
//                    String namegetter[] = realPath.split("/");
//                    int finalElement = namegetter.length - 1;
//                    Log.d("try try", "" + namegetter[finalElement]);
//                    try {
//                        client.rename(namegetter[finalElement], "gauravfile.jpg");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    try {
//                        client.disconnect(true);
//                    } catch (Exception e2) {
//                        e2.printStackTrace();
//                    }
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//
//
//            }
//        }
//        new connecthis().execute();
    }

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

            realPath = ImageFilePath.getPath(ActivitySeller.this, data.getData());
//                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());

            Log.i("SEE REAL PATH", "onActivityResult: file path : " + realPath);
            filePathSetter();

        }
    }

    private void filePathSetter() {
        if (selectedSelectImageButton == 1) {
            imageFilePath1.setText(realPath);
        } else if (selectedSelectImageButton == 2) {
            imageFilePath2.setText(realPath);
        } else {
            imageFilePath3.setText(realPath);
        }
    }
}

