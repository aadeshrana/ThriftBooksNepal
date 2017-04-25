package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import com.example.thearbiter.thriftbooksnepal.Activities.MainDrawerHome;
import com.example.thearbiter.thriftbooksnepal.Activities.SignUp;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadesh Rana on 30-03-17.
 */

public class FragmentCustomDiagLogin extends DialogFragment {
    EditText userName,passWord;
    Button loginButton,signUpButton;
    CheckBox keepLoggedIn;
    private static final String LOGIN_URL = "http://frame.ueuo.com/thriftbooks/login.php";
    private static final String FETCH_NUMBER_URL = "http://frame.ueuo.com/thriftbooks/fetchalldetails.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static String firstName, lastName,phoneNumber,school,emailAddress,username,password;
    private ProgressDialog pdialog;
    int success;
    JSONParser jsonParser = new JSONParser();
    static String checkBoxChecked = "notchecked",strLoginUsername,strLoginPassword;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_login_diag,container,false);

            userName = (EditText)view.findViewById(R.id.loginUsername);
            passWord = (EditText)view.findViewById(R.id.loginPassword);
            loginButton = (Button)view.findViewById(R.id.loginButton);
            signUpButton = (Button)view.findViewById(R.id.LoginNewAccount);
            keepLoggedIn = (CheckBox)view.findViewById(R.id.keepLoggedIn);
            keepLoggedIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(getActivity(), "Please check this on private devices only.", Toast.LENGTH_SHORT).show();

                    if (isChecked) {
                        checkBoxChecked = "checked";

                    } else {
                        checkBoxChecked = "notchecked";
                        SharedPreferences sharedpref;
                        sharedpref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor edit = sharedpref.edit();
                        edit.putString("checkbox", checkBoxChecked);
                        edit.apply();
                    }
                }

            });

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   strLoginUsername = userName.getText().toString();
                    strLoginPassword = passWord.getText().toString();
                    try {
                        strLoginPassword = SHA1(strLoginPassword);

                        password = strLoginPassword;
                        try {
                            new LoginUser(getActivity()).execute();

                        } catch (Exception e) {

                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            });
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignUp.class);
                   startActivityForResult(intent,1);
                }
            });

            getDialog().setTitle("Login");
            getDialog().setCancelable(true);

            return view;
        }


    class LoginUser extends AsyncTask<String, String, String> {
        Context context;

        public LoginUser(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(getActivity());
            pdialog.setMessage("Logging In....");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(true);
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            //check for success tag


            try {
                //building parameters
                List<NameValuePair> params = new ArrayList<>();
                List<NameValuePair> params1 = new ArrayList<>();
                params1.add(new BasicNameValuePair("username", strLoginUsername));
                params.add(new BasicNameValuePair("username", strLoginUsername));
                params.add(new BasicNameValuePair("password", password));
                Log.d("Username"," UserName: "+strLoginUsername);
                Log.d("password","Password: "+password);
                //getting product details by making http request

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                //check your log for json response

                success = json.getInt(TAG_SUCCESS);
                if (json == null) {
                }
                //json success element

                JSONObject json1 = jsonParser.makeHttpRequest(FETCH_NUMBER_URL, "POST", params1);


                firstName = json1.getString("firstname");
                lastName = json1.getString("lastname");
                phoneNumber = json1.getString("phonenumber");
                school = json1.getString("school");
                emailAddress = json1.getString("emailaddress");
                username = strLoginUsername;


                Log.d("The", "number is" + phoneNumber);


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String file_url) {
            // TODO Auto-generated method stub
            if (success == 1) {
                SharedPreferences sharedpref;
                sharedpref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor edit = sharedpref.edit();


                if(checkBoxChecked.equals("checked")) {
                    Log.d("kxa1",":"+firstName);
                    edit.putString("firstNameSharePref", firstName);
                    edit.putString("firstNameSharePref1", firstName);
                    edit.putString("lastNameSharePref", lastName);
                    edit.putString("emailSharePref", emailAddress);
                    edit.putString("phoneSharePref", phoneNumber);
                    edit.putString("schoolSharePref", school);

                }
                edit.putString("username", strLoginUsername);
                edit.putString("a",strLoginUsername);
                edit.putString("checkbox", checkBoxChecked);
                edit.apply();
                Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                FragmentNavDraerMain frag = new FragmentNavDraerMain();
                frag.pullAllMainItems(context);
                Intent intent = new Intent(getActivity(), MainDrawerHome.class);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Cannot find account. Please sign up", Toast.LENGTH_SHORT).show();
            }
            pdialog.dismiss();

            if (file_url != null) {
                Toast.makeText(getActivity(), file_url, Toast.LENGTH_LONG).show();
              
            }
        }
    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
}
