package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentChat;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomDiagLogin;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaurav Jayasawal on 1/25/2017.
 */

public class ChatMainActivity extends AppCompatActivity implements TextWatcher {

    //These store values of Name and Room name sent from Intent
    public static String intentName, intentRoomName;
    Toolbar toolbar;
    private TextView allFieldsRequired, bookNameRequired, checkOutNameRequired, checkOutPhoneRequired,
            bookAuthorRequired, bookMarkedPriceRequired, bookFinalPriceRequired, streetAddressRequired,
            otherDetailsRequired, specialLandmarksRequired;
    private EditText checkOutName, checkOutPhoneNumber, bookName, bookAuthor, bookMarkedPrice,
            bookFinalPrice, houseNo, streetAddress, otherDetails, specialLandmarks;

    String sendfullname, sendsoldTo, sendphoneNumber, sendbookName, sendauthorOfBook,
            sendmarkedPrice, sendfinalPrice, sendhouseNumber, sendstreetAddress, sendotherDetails, sendspecialLandmarks;
    ArrayList<String> removeElement = new ArrayList<>();
    String finalfinalName;
    String newString = "";
    ProgressDialog pb;
    JSONParser jsonParser = new JSONParser();
    public static final String SEND_DELIVERY_REQUEST = "http://frame.ueuo.com/thriftbooks/sendDeliveryRequest.php";
    public static final String MESSAGE_SEEN = "http://frame.ueuo.com/thriftbooks/messageSeen.php";
    public static final String SEND_NOTIFICATION_OF_REJECTION = "http://frame.ueuo.com/images/send_notification_alerts.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Notifications.whereAreYou = 0;
        setContentView(R.layout.chat_recylcer_paster);

        intentName = getIntent().getExtras().get("user_name").toString();
        intentRoomName = getIntent().getExtras().get("room_name").toString();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChatMainActivity.this);
        String sharedMessage = sharedPreferences.getString("messageFrom", "");
        ArrayList<String> neArList = new ArrayList<>();
        for (int i = 0; i < Notifications.chatUsersNeMessagesArray.length; i++) {
            if (Notifications.chatUsersNeMessagesArray[i].equals(intentName)) {
            } else {
                neArList.add(Notifications.chatUsersNeMessagesArray[i]);
            }
        }


        Notifications.chatUsersNeMessagesArray = new String[neArList.size()];
        Notifications.chatUsersNeMessagesArray = neArList.toArray(new String[neArList.size()]);

        Log.d("sharedMessage", sharedMessage);

        if (sharedMessage.length() > 0) {

            String arra[] = sharedMessage.split("[*]+");
            Log.d("sharedMessage", "1");

            for (int i = 0; i < arra.length; i++) {
                Log.d("sharedMessage", "1" + intentName);
                if (arra[i].equals(intentName)) {
                    Log.d("which", "uName" + intentName);
                } else {
                    removeElement.add(arra[i]);
                }
            }
            arra = new String[0];


            for (int k = 0; k < removeElement.size(); k++) {

                if (k == 0) {
                    newString = removeElement.get(0);
                } else {
                    newString = newString + "***" + removeElement.get(k);
                }
            }

        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("messageFrom", newString);
        editor.apply();
        Notifications.messageFromPref = new String[0];

        Log.d("sharedMessage", sharedPreferences.getString("messageFrom", ""));

        String[] a = intentRoomName.split("\\|\\|");
        String[] b = a[1].split("---");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ChatMainActivity.this);
        String prefName = preferences.getString("firstNameSharePref1", "");
        if (prefName.equals("")) {
            prefName = FragmentCustomDiagLogin.firstName;
        }

        if (prefName.equals(b[0])) {
            finalfinalName = b[1];
        } else {
            finalfinalName = b[0];
        }

        Log.d("hi", "bi" + intentRoomName);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(finalfinalName);
        setSupportActionBar(toolbar);
        FragmentChat fragmentChat = new FragmentChat();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.chatRecyclerPasterRelativeLayout, fragmentChat, "abc");
        transaction.commit();


    }

    class Seen extends AsyncTask<String, String, String> {

        List<NameValuePair> params2 = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ChatMainActivity.this);
            String tempSelfName = sharedPref.getString("a", "");

            if (tempSelfName.equals("")) {
                tempSelfName = FragmentCustomDiagLogin.username;
            }


            params2.add(new BasicNameValuePair("sentBy", intentName));
            params2.add(new BasicNameValuePair("selfName", tempSelfName));

            try {
                jsonParser.makeHttpRequest(MESSAGE_SEEN, "POST", params2);
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Seen().execute();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        MenuItem item1 = menu.findItem(R.id.deliverRequest);

        item1.setIcon(R.drawable.deliver);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement

        if (id == R.id.deliverRequest) {
            dialogCreate();

        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogCreate() {
        final Dialog dialog = new Dialog(ChatMainActivity.this, android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.deliver_dialog);
        dialog.setCancelable(true);
        dialog.show();

        CardView placeOffer = (CardView) dialog.findViewById(R.id.placeOrderFinal);

        //Input Fields
        checkOutName = (EditText) dialog.findViewById(R.id.checkOutName);
        checkOutPhoneNumber = (EditText) dialog.findViewById(R.id.checkOutPhoneNumber);
        bookName = (EditText) dialog.findViewById(R.id.offerBookName);
        bookAuthor = (EditText) dialog.findViewById(R.id.offerBookAuthor);
        bookMarkedPrice = (EditText) dialog.findViewById(R.id.offerBookSoldBy);
        bookFinalPrice = (EditText) dialog.findViewById(R.id.offerFinalPrice);
        houseNo = (EditText) dialog.findViewById(R.id.houseNumber);
        streetAddress = (EditText) dialog.findViewById(R.id.streetAddress);
        otherDetails = (EditText) dialog.findViewById(R.id.detailedAddress);
        specialLandmarks = (EditText) dialog.findViewById(R.id.landmarksSpecial);

        //Validation Fields
        allFieldsRequired = (TextView) dialog.findViewById(R.id.allFieldsRequired);
        checkOutNameRequired = (TextView) dialog.findViewById(R.id.nameRequired);
        checkOutPhoneRequired = (TextView) dialog.findViewById(R.id.phoneNumberValidation);
        bookNameRequired = (TextView) dialog.findViewById(R.id.bookNameRequired);
        bookAuthorRequired = (TextView) dialog.findViewById(R.id.authorOfBookRequired);
        bookMarkedPriceRequired = (TextView) dialog.findViewById(R.id.markedPriceRequired);
        bookFinalPriceRequired = (TextView) dialog.findViewById(R.id.agreedPriceRequired);
        streetAddressRequired = (TextView) dialog.findViewById(R.id.streetAddressRequired);
        otherDetailsRequired = (TextView) dialog.findViewById(R.id.otherDetailsRequired);
        specialLandmarksRequired = (TextView) dialog.findViewById(R.id.landmarksRequired);

        checkOutName.addTextChangedListener(this);
        checkOutPhoneNumber.addTextChangedListener(this);
        bookName.addTextChangedListener(this);
        bookAuthor.addTextChangedListener(this);
        bookMarkedPrice.addTextChangedListener(this);
        bookFinalPrice.addTextChangedListener(this);
        streetAddress.addTextChangedListener(this);
        otherDetails.addTextChangedListener(this);
        specialLandmarks.addTextChangedListener(this);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ChatMainActivity.this);
        String firstName = pref.getString("firstNameSharePref1", "");
        String phone = pref.getString("phoneSharePref", "");
        String lastName = pref.getString("lastNameSharePref", "");

        String fullName;

        if (firstName.equals("")) {
            firstName = FragmentCustomDiagLogin.firstName;
            phone = FragmentCustomDiagLogin.phoneNumber;
            lastName = FragmentCustomDiagLogin.lastName;
        }

        fullName = firstName + " " + lastName;
        checkOutName.setText(fullName);
        checkOutPhoneNumber.setText(phone);

        placeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOutName.getText().toString().length() == 0) {
                    allFieldsRequired.setVisibility(View.VISIBLE);
                    checkOutNameRequired.setVisibility(View.VISIBLE);
                }
                if (checkOutPhoneNumber.getText().toString().length() == 0) {
                    allFieldsRequired.setVisibility(View.VISIBLE);
                    checkOutPhoneRequired.setVisibility(View.VISIBLE);
                }
                if (bookName.getText().toString().length() == 0) {
                    allFieldsRequired.setVisibility(View.VISIBLE);
                    bookNameRequired.setVisibility(View.VISIBLE);
                }
                if (bookAuthor.getText().toString().length() == 0) {
                    allFieldsRequired.setVisibility(View.VISIBLE);
                    bookAuthorRequired.setVisibility(View.VISIBLE);
                }
                if (bookMarkedPrice.getText().toString().length() == 0) {
                    allFieldsRequired.setVisibility(View.VISIBLE);
                    bookMarkedPriceRequired.setVisibility(View.VISIBLE);

                }
                if (bookFinalPrice.getText().toString().length() == 0) {
                    allFieldsRequired.setVisibility(View.VISIBLE);
                    bookFinalPriceRequired.setVisibility(View.VISIBLE);

                }
                if (streetAddress.getText().toString().length() == 0) {
                    allFieldsRequired.setVisibility(View.VISIBLE);
                    streetAddressRequired.setVisibility(View.VISIBLE);

                }
                if (otherDetails.getText().toString().length() == 0) {
                    allFieldsRequired.setVisibility(View.VISIBLE);
                    otherDetailsRequired.setVisibility(View.VISIBLE);
                }
                if (specialLandmarks.getText().toString().length() == 0) {
                    allFieldsRequired.setVisibility(View.VISIBLE);
                    specialLandmarksRequired.setVisibility(View.VISIBLE);
                }

                if (allFieldsRequired.getVisibility() == View.INVISIBLE) {
                    sendfullname = checkOutName.getText().toString();
                    sendphoneNumber = checkOutPhoneNumber.getText().toString();
                    sendbookName = bookName.getText().toString();
                    sendauthorOfBook = bookAuthor.getText().toString();
                    sendmarkedPrice = bookMarkedPrice.getText().toString();
                    sendfinalPrice = bookFinalPrice.getText().toString();
                    sendhouseNumber = houseNo.getText().toString();
                    sendstreetAddress = streetAddress.getText().toString();
                    sendotherDetails = otherDetails.getText().toString();
                    sendspecialLandmarks = specialLandmarks.getText().toString();

                    pb = new ProgressDialog(ChatMainActivity.this);
                    dialog.dismiss();
                    pb.setMessage("Posting Request..");
                    pb.show();
                    pb.setCancelable(false);
                    new RequestDelivery().execute();
                }
            }
        });
    }

    class RequestDelivery extends AsyncTask<String, String, String> {

        List<NameValuePair> params2 = new ArrayList<>();
        List<NameValuePair> params3 = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {

            String[] a = intentRoomName.split("\\|\\|");
            String[] b = a[0].split("[*]+");

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ChatMainActivity.this);
            String tempSelfName = sharedPref.getString("a", "");
            if (tempSelfName.equals("")) {
                tempSelfName = FragmentCustomDiagLogin.username;
            }
            String soldByAsyn, soldToAsyn;
            if (tempSelfName.equals(b[0])) {
                soldByAsyn = tempSelfName;
                soldToAsyn = b[1];
            } else {
                soldByAsyn = tempSelfName;
                soldToAsyn = b[0];
            }

            params2.add(new BasicNameValuePair("fullName", sendfullname));
            params2.add(new BasicNameValuePair("soldBy", soldByAsyn));
            params2.add(new BasicNameValuePair("soldTo", soldToAsyn));
            params2.add(new BasicNameValuePair("phoneNumber", sendphoneNumber));
            params2.add(new BasicNameValuePair("bookName", sendbookName));
            params2.add(new BasicNameValuePair("authorOfBook", sendauthorOfBook));
            params2.add(new BasicNameValuePair("markedPrice", sendmarkedPrice));
            params2.add(new BasicNameValuePair("finalPrice", sendfinalPrice));
            params2.add(new BasicNameValuePair("houseNumber", sendhouseNumber));
            params2.add(new BasicNameValuePair("streetAddress", sendstreetAddress));
            params2.add(new BasicNameValuePair("otherDetails", sendotherDetails));
            params2.add(new BasicNameValuePair("specialLandmarks", sendspecialLandmarks));


            jsonParser.makeHttpRequest(SEND_DELIVERY_REQUEST, "POST", params2);

            String notifTitle = "Book Sansar";
            String notifMessage = "You have a new offer by " + tempSelfName + " (" + sendfullname + ")" + " for the book " + sendbookName + " " + " at the price of " + sendfinalPrice + ". Please open the Book Sansar application to accept/decline this offer.";
            String user = soldToAsyn;

            params3.add(new BasicNameValuePair("title", notifTitle));
            params3.add(new BasicNameValuePair("message", notifMessage));
            params3.add(new BasicNameValuePair("user", user));

            jsonParser = new JSONParser();

            try {
                jsonParser.makeHttpRequest(SEND_NOTIFICATION_OF_REJECTION, "POST", params3);
            } catch (Exception ignored) {

            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            thanksDialog();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(ChatMainActivity.this, Notifications.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(in);
    }

    private void thanksDialog() {
        pb.dismiss();

        final Dialog gotYourDeliveryRequest = new Dialog(ChatMainActivity.this);
        gotYourDeliveryRequest.setContentView(R.layout.successfully_recieved_delivery_request_layout);
        gotYourDeliveryRequest.setCancelable(false);
        gotYourDeliveryRequest.show();

        CardView goBack = (CardView) gotYourDeliveryRequest.findViewById(R.id.successReturnToChatButton);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotYourDeliveryRequest.dismiss();
            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (checkOutName.getText().toString().length() >= 1) {
            checkOutNameRequired.setVisibility(View.GONE);
        }
        if (checkOutPhoneNumber.getText().toString().length() > 0) {
            checkOutPhoneRequired.setVisibility(View.INVISIBLE);

        }
        if (bookName.getText().toString().length() > 0) {
            bookNameRequired.setVisibility(View.INVISIBLE);

        }
        if (bookAuthor.getText().toString().length() > 0) {
            bookAuthorRequired.setVisibility(View.INVISIBLE);

        }
        if (bookMarkedPrice.getText().toString().length() > 0) {
            bookMarkedPriceRequired.setVisibility(View.INVISIBLE);

        }
        if (bookFinalPrice.getText().toString().length() > 0) {
            bookFinalPriceRequired.setVisibility(View.INVISIBLE);

        }
        if (streetAddress.getText().toString().length() > 0) {
            streetAddressRequired.setVisibility(View.INVISIBLE);
        }
        if (otherDetails.getText().toString().length() > 0) {
            otherDetailsRequired.setVisibility(View.INVISIBLE);
        }
        if (specialLandmarks.getText().toString().length() > 0) {
            specialLandmarksRequired.setVisibility(View.INVISIBLE);
        }
        if (checkOutName.getText().toString().length() >= 1 && checkOutPhoneNumber.getText().toString().length() > 0 && bookName.getText().toString().length() > 0
                && bookAuthor.getText().toString().length() > 0 && bookMarkedPrice.getText().toString().length() > 0 && bookFinalPrice.getText().toString().length() > 0
                && streetAddress.getText().toString().length() > 0 && otherDetails.getText().toString().length() > 0 && specialLandmarks.getText().toString().length() > 0) {
            allFieldsRequired.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
