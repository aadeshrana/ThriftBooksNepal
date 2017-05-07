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
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomDiagLogin;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentMessager;
import com.example.thearbiter.thriftbooksnepal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gaurav Jayasawal on 1/18/2017.
 */

public class FinalBuyersActivity extends AppCompatActivity implements TextWatcher {

    LoadAllMessages obj1;

    public static final String SEND_DELIVERY_REQUEST = "http://frame.ueuo.com/thriftbooks/sendDeliveryRequest.php";
    public static final String SEND_NOTIFICATION_OF_REJECTION = "http://frame.ueuo.com/images/send_notification_alerts.php";

    private TextView allFieldsRequired, bookNameRequired, checkOutNameRequired, checkOutPhoneRequired,
            bookAuthorRequired, bookMarkedPriceRequired, bookFinalPriceRequired, streetAddressRequired,
            otherDetailsRequired, specialLandmarksRequired;
    private EditText checkOutName, checkOutPhoneNumber, bookName, bookAuthor, bookMarkedPrice,
            bookFinalPrice, houseNo, streetAddress, otherDetails, specialLandmarks;

    String sendfullname, sendsoldTo, sendphoneNumber, sendbookName, sendauthorOfBook,
            sendmarkedPrice, sendfinalPrice, sendhouseNumber, sendstreetAddress, sendotherDetails, sendspecialLandmarks;
    ProgressDialog pb;

    Toolbar toolbar;
    JSONParser jsonParser = new JSONParser();
    private static final String PULL_ALL_MESSAGES_URL = "http://frame.ueuo.com/thriftbooks/pullAllMessages.php";
    ArrayList<String> sender = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    ArrayList<String> message = new ArrayList<>();
    ArrayList<String> username = new ArrayList<>();
    public static String newRoomName;
    android.support.v7.app.ActionBar actionBar;

    ArrayList<String> time = new ArrayList<>();
    public static String senderArray[], messageArray[], timeArray[], usernameArray[];
    private static final String CREATE_ROOM_TABLE = "http://frame.ueuo.com/images/createRoomThrift.php";
    CardView buyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obj1 = new LoadAllMessages();
        obj1.execute();
        setContentView(R.layout.message_activity_recycle_paster);
        Log.d("OH GOD", "");
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Notifications.whereAreYou = 0;
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        buyButton = (CardView) findViewById(R.id.adapterFirstPageCardViewMain);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpref;
                sharedpref = PreferenceManager.getDefaultSharedPreferences(FinalBuyersActivity.this);
                String loggedIn = sharedpref.getString("loggedIn", "noValue");

                if (loggedIn.equals("noValue")) {

                    FragmentCustomDiagLogin fragmentCustomDiagLogin = new FragmentCustomDiagLogin();
                    fragmentCustomDiagLogin.show(getFragmentManager(), "cde");

                }
                else {
                    dialogCreate();
                }
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

    class RequestDelivery extends AsyncTask<String, String, String> {

        List<NameValuePair> params2 = new ArrayList<>();
        List<NameValuePair> params3 = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(FinalBuyersActivity.this);
            String tempSelfName = sharedPref.getString("a", "");

            if (tempSelfName.equals("")) {
                tempSelfName = FragmentCustomDiagLogin.username;
            }

            String soldToAsyn = FragmentMessager.finalBuyersActivityUsernameOfSeller;

            params2.add(new BasicNameValuePair("fullName", sendfullname));
            params2.add(new BasicNameValuePair("soldBy", tempSelfName));
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

    private void thanksDialog() {
        pb.dismiss();

        final Dialog gotYourDeliveryRequest = new Dialog(FinalBuyersActivity.this);
        gotYourDeliveryRequest.setContentView(R.layout.successfully_recieved_delivery_request_layout);
        TextView returnToChat = (TextView)findViewById(R.id.returnToChatText);
        returnToChat.setText("RETURN TO BOOK");
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

    private void dialogCreate() {
        final Dialog dialog = new Dialog(FinalBuyersActivity.this, android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
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

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(FinalBuyersActivity.this);
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
        bookName.setText(FragmentMessager.finalBuyersActivityNameOfBook);
        bookAuthor.setText(FragmentMessager.finalBuyersActivityNameOfAuthor);
        bookMarkedPrice.setText(FragmentMessager.finalBuyersActivityPriceOfBook);
        bookFinalPrice.setText(FragmentMessager.finalBuyersActivityPriceOfBook);


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

                    pb = new ProgressDialog(FinalBuyersActivity.this);
                    dialog.dismiss();
                    pb.setMessage("Posting Request..");
                    pb.show();
                    pb.setCancelable(false);
                    new RequestDelivery().execute();

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        obj1.cancel(true);
    }

    // THIS IS LOAD NOTIFICATION CLASS
    public class LoadAllMessages extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {


                List<NameValuePair> param1 = new ArrayList<>();
//                param1.add(new BasicNameValuePair("username", spUsername));

                Log.d("SHARED ISSS", "" + FragmentMessager.finalBuyersActivityImage2 + FragmentMessager.finalBuyersActivityPriceOfBook);

                String salesId = FragmentMessager.finalBuyersActivityImage2 + FragmentMessager.finalBuyersActivityPriceOfBook;

                param1.add(new BasicNameValuePair("saleid", salesId));

                sender.clear();
                message.clear();
                time.clear();
                username.clear();
                JSONObject json;

                json = jsonParser.makeHttpRequest(PULL_ALL_MESSAGES_URL, "POST", param1);
                //full json response


                try {
                    Log.d("values", ":" + json.length());
                    for (int i = 0; i < json.length(); i++) {
                        sender.add(json.getString("b" + i));
                        message.add(json.getString("a" + i));
                        time.add(json.getString("c" + i));
                        username.add(json.getString("d" + i));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {

            }

            FinalBuyersActivity.senderArray = new String[sender.size()];
            FinalBuyersActivity.messageArray = new String[message.size()];
            FinalBuyersActivity.timeArray = new String[time.size()];
            FinalBuyersActivity.usernameArray = new String[username.size()];

            FinalBuyersActivity.senderArray = sender.toArray(new String[sender.size()]);
            FinalBuyersActivity.messageArray = message.toArray(new String[message.size()]);
            FinalBuyersActivity.timeArray = time.toArray(new String[time.size()]);
            FinalBuyersActivity.usernameArray = username.toArray(new String[username.size()]);

            FragmentMessager.title = message.toArray(new String[message.size()]);
            FragmentMessager.nameOfSender = sender.toArray(new String[sender.size()]);
            FragmentMessager.usernameOfSender = username.toArray(new String[username.size()]);
            FragmentMessager.Notiftime = time.toArray(new String[time.size()]);
            sender = null;
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            FragmentMessager fragmentMessager = new FragmentMessager();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.recyclerPasterId, fragmentMessager, "asdf");
            transaction.commit();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final_buyer, menu);
        MenuItem item2 = menu.findItem(R.id.leaveAMessage);
        item2.setIcon(R.drawable.chat);
        return true;
    }

    //CREATES ROOM IN REALTIME DATABASE AND PhpMySql DATABASE
//    class CreateRoom extends AsyncTask<String, String, String> {
//        List<NameValuePair> params1 = new ArrayList<>();
//
//        @Override
//        protected String doInBackground(String... params) {
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FinalBuyersActivity.this);
//            String userNameOfUser = preferences.getString("a", "");
//            if (userNameOfUser.equals("")) {
//                userNameOfUser = FragmentCustomDiagLogin.username;
//            }
//
//            String new2RoomName = userNameOfUser + "nameSeparatorHere" + FragmentMessager.finalBuyersActivityUsernameOfSeller;
//            params1.add(new BasicNameValuePair("roomName", new2RoomName));
//
//            try {
//                jsonParser.makeHttpRequest(CREATE_ROOM_TABLE, "POST", params1);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;

        }
        if (id == R.id.leaveAMessage) {

            // FIREBASE ROOM CREATION IS DONE HERE
            // TABLE FOR ROOM IS ALSO CREATED IN THE DATABASE
            SharedPreferences sharedpref;
            sharedpref = PreferenceManager.getDefaultSharedPreferences(this);
            String loggedIn = sharedpref.getString("loggedIn", "noValue");

            if (loggedIn.equals("noValue")) {

                FragmentCustomDiagLogin fragmentCustomDiagLogin = new FragmentCustomDiagLogin();
                fragmentCustomDiagLogin.show(getFragmentManager(), "cde");

            } else {
                final Map<String, Object> map = new HashMap<>();
                try {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FinalBuyersActivity.this);
                    String userNameOfUser = preferences.getString("a", "");
                    if (userNameOfUser.equals("")) {
                        userNameOfUser = FragmentCustomDiagLogin.username;
                    }

                    String nameOfUser = preferences.getString("firstNameSharePref1", "");
                    if (nameOfUser.equals("")) {
                        nameOfUser = FragmentCustomDiagLogin.firstName;
                    }

                    newRoomName = userNameOfUser + "***" + FragmentMessager.finalBuyersActivityUsernameOfSeller + "||" + FragmentMessager.finalBuyersActivityNameOfSeller + "---" + nameOfUser;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                map.put(newRoomName, "");

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(newRoomName)) {
                        } else {
                            root.updateChildren(map);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //THIS CREATES TABLE TO STORE IN OUR DATABASE
                Intent intent = new Intent(getApplicationContext(), ChatMainActivity.class);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FinalBuyersActivity.this);
                String username = preferences.getString("a", "");
                if (username.equals("")) {
                    username = FragmentCustomDiagLogin.username;
                }
                String nameOfUser = preferences.getString("firstNameSharePref1", "");
                if (nameOfUser.equals("")) {
                    nameOfUser = FragmentCustomDiagLogin.firstName;
                }
                String stringToSendInIntent = username + "***" + FragmentMessager.finalBuyersActivityUsernameOfSeller + "||" + FragmentMessager.finalBuyersActivityNameOfSeller + "---" + nameOfUser;
                Log.d("room", "fromIntent " + stringToSendInIntent);

                intent.putExtra("room_name", stringToSendInIntent);
                intent.putExtra("user_name", FragmentMessager.finalBuyersActivityNameOfSeller);
                intent.putExtra("book_name", FragmentMessager.finalBuyersActivityNameOfBook);
                intent.putExtra("author_name", FragmentMessager.finalBuyersActivityNameOfAuthor);
                intent.putExtra("price_of_book", FragmentMessager.finalBuyersActivityPriceOfBook);

                startActivity(intent);
            finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
