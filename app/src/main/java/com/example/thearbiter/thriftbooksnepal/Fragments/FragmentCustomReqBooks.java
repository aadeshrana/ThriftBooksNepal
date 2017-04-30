package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadesh Rana on 30-04-17.
 */

public class FragmentCustomReqBooks extends DialogFragment {
    EditText nameOfBook, nameofAuth;
    Button requestBook;
    static String sendName,sendAuth,sendUsername;
    private static final String LOGIN_URL = "http://frame.ueuo.com/thriftbooks/requestBook.php";

    JSONParser jsonParser = new JSONParser();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_req_books, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        nameOfBook = (EditText)view.findViewById(R.id.reqBookName);
        nameofAuth =(EditText)view.findViewById(R.id.reqAuthorName);
        requestBook = (Button)view.findViewById(R.id.reqBookButton);

        requestBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestNewBook();
            }
        });
        return view;

    }


    public void RequestNewBook(){

        if(nameOfBook.getText().toString().length()<=0){
            if(nameofAuth.getText().toString().length()<=0){
                Toast.makeText(getActivity(), "Please Enter All Details", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Please Enter Name of Book", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if(nameofAuth.getText().toString().length()<=0){
                Toast.makeText(getActivity(), "Please Enter Name of Author", Toast.LENGTH_SHORT).show();
            }
            else
            {
                sendName=nameOfBook.getText().toString();
                sendAuth=nameofAuth.getText().toString();
                new RequestBook().execute();

            }
        }
    }


    class RequestBook extends AsyncTask<String, String, String> {
        Context context;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            //check for success tag
            SharedPreferences sharedpref;
            sharedpref = PreferenceManager.getDefaultSharedPreferences(getActivity());
             sendUsername= sharedpref.getString("username", "noValue");
            if(sendUsername.equals("noValue")){
                sendUsername=FragmentCustomDiagLogin.username;
            }

            try {
                //building parameters
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username",sendUsername));
                params.add(new BasicNameValuePair("bookname", sendName));
                params.add(new BasicNameValuePair("authname", sendAuth));
                //getting product details by making http request

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                //check your log for json response



            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(), "Your Book has been succesffuly Requested", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();

            super.onPostExecute(s);
        }
    }

}
