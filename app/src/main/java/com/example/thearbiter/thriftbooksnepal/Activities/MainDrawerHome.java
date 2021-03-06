package com.example.thearbiter.thriftbooksnepal.Activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchool;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.MySingleton;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomDiagLogin;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomReqBooks;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavDraerMain;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavMenu;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentNavMenuRecycler;
import com.example.thearbiter.thriftbooksnepal.Information.InformationMessageActivity;
import com.example.thearbiter.thriftbooksnepal.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainDrawerHome extends AppCompatActivity implements TextWatcher {
    private static final String CHECK_REJECTED_OFFERS = "http://frame.ueuo.com/thriftbooks/pullRejectedOffers.php";
    private static final String EMPTY_TOKEN = "http://frame.ueuo.com/thriftbooks/emptyToken.php";
    Toolbar toolbar;
    int jsonLength = 0;
    int jsonLength2 = 0;
    TextView refresh, refreshMessage;
    int wereThereOffers = 0;
    static String rejectedBy, rejectedByName, rejectedByPrice, rejectedByBookName, rejectedByAuthror;

    ArrayList<String> userName = new ArrayList<>();
    ArrayList<String> firstName = new ArrayList<>();
    ArrayList<String> lastName = new ArrayList<>();
    ArrayList<String> nameofBook = new ArrayList<>();
    ArrayList<String> nameofAuthor = new ArrayList<>();
    ArrayList<String> priceOfBook = new ArrayList<>();
    ArrayList<String> homeAddress = new ArrayList<>();
    ArrayList<String> school = new ArrayList<>();
    ArrayList<String> image1name = new ArrayList<>();
    ArrayList<String> image2name = new ArrayList<>();
    ArrayList<String> image3name = new ArrayList<>();
    ArrayList<String> phoneNumber = new ArrayList<>();
    ArrayList<String> emailAddress = new ArrayList<>();
    JSONObject json66;
    JSONObject json60;
    ArrayList<String> offeredBy = new ArrayList<>();
    ArrayList<String> offeredByNameOfUser = new ArrayList<>();
    ArrayList<String> bookName = new ArrayList<>();
    ArrayList<String> authorName = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();

    PullAllAlevelItems obj1;

    private TextView allFieldsRequired, bookNameRequired, checkOutNameRequired, checkOutPhoneRequired,
            bookAuthorRequired, bookFinalPriceRequired, streetAddressRequired,
            otherDetailsRequired, specialLandmarksRequired;
    private EditText etFullName, etPhoneNumber, etBookName, etBookAuthorName, etBookFinalPrice, etHouseNumber, etStreetAddress, etOtherDetails, etSpecialLandmarks;

    TextView requestText;


    public String getEtFullName, getEtPhoneNumber, getEtBookName, getEtBookAuthorName, getEtBookFinalPrice, getEtHouseNumber,
            getEtStreetAddress, getEtOtherDetails, getEtSpecialLandmarks;

    public static String firstName1, lastName1, phoneNumber1, school1, emailAddress1, username1, password1;


    public static final String SEND_DELIVERY_REQUEST = "http://frame.ueuo.com/thriftbooks/finishDeliveryRequest.php";
    String app_server_url = "http://frame.ueuo.com/images/fcm_insert.php";
    private static final String LOGIN_URL = "http://frame.ueuo.com/thriftbooks/pullallitems.php";
    private static final String FETCH_NUMBER_URL = "http://frame.ueuo.com/thriftbooks/fetchalldetails.php";
    JSONParser jsonParser = new JSONParser();
    ProgressBar progressBar;
    String loggedIn;
    int resumeOrNot = 0;
    TextView requestBooks;
    public static final String PENDING_OFFERS = "http://frame.ueuo.com/thriftbooks/pullPendingOffers.php";
    public static final String DISMISS_REJECTED_OFFER = "http://frame.ueuo.com/thriftbooks/dismissRejectDialog.php";
    public static final String REJECT_OFFER = "http://frame.ueuo.com/thriftbooks/rejectOffer.php";
    public static final String SEND_NOTIFICATION_OF_REJECTION = "http://frame.ueuo.com/images/send_notification_alerts.php";
    private PullAllAlevelItems obj2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer_home);
        requestText = (TextView) findViewById(R.id.requestBooksText);
        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(this);
        loggedIn = sharedpref.getString("loggedIn", "noValue");
        resumeOrNot = 0;
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        refresh = (TextView) findViewById(R.id.refreshAfterInternetConnectivity);
        refreshMessage = (TextView) findViewById(R.id.noInternetTextview);
        progressBar = (ProgressBar) findViewById(R.id.drawerProgress);
        progressBar.setVisibility(View.INVISIBLE);
        requestBooks = (TextView) findViewById(R.id.requestBooksButton);
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;

        if (!connected) {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
            requestText.setVisibility(View.GONE);
            refreshMessage.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.VISIBLE);
            requestBooks.setVisibility(View.GONE);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(MainDrawerHome.this, MainDrawerHome.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(in);
                }
            });
        } else {
            progressBar = (ProgressBar) findViewById(R.id.drawerProgress);
            progressBar.setVisibility(View.VISIBLE);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            new CheckPendingOffers().execute();

            getToken();
            requestBooks.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (loggedIn.equals("noValue")) {
                        FragmentCustomDiagLogin fragmentCustomDiagLogin = new FragmentCustomDiagLogin();
                        fragmentCustomDiagLogin.show(getFragmentManager(), "cde");
                    } else {
                        FragmentCustomReqBooks fragmentCustomReqBooks = new FragmentCustomReqBooks();
                        fragmentCustomReqBooks.show(getFragmentManager(), "cde");
                        resumeOrNot = 1;

                    }

                }
            });
            CustomDiagFindSchool obj = new CustomDiagFindSchool();
            obj.findAllSchool();
            FragmentNavMenu fragmentNavMenu = (FragmentNavMenu) getSupportFragmentManager().findFragmentById(R.id.mainfragmentDrawer);
            fragmentNavMenu.setUp(R.id.mainfragmentDrawer, (DrawerLayout) findViewById(R.id.mainDrawerLayout), toolbar);

            FragmentNavMenuRecycler fragmentAdpater = new FragmentNavMenuRecycler();

            FragmentManager fragmentManager = getFragmentManager();
            final FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.add(R.id.mainfragmentDrawer, fragmentAdpater, "abc");


            InformationMessageActivity informationMessageActivityObject = new InformationMessageActivity();
            informationMessageActivityObject.sendersName = null;
            informationMessageActivityObject.textMessage = null;
            informationMessageActivityObject.timeOfNotification = null;
            transaction.commit();
            obj1 = new PullAllAlevelItems();
            obj1.execute();
            Notifications.whereAreYou = 0;

            if (getIntent().getExtras() != null) {
                for (String key : getIntent().getExtras().keySet()) {
                    if (key.equals("title")) {
                        Log.d("title", getIntent().getExtras().getString(key));
                    } else if (key.equals("message")) {
                        Log.d("title", getIntent().getExtras().getString(key));
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d("destroyed", "act");

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainDrawerHome.this);
        SharedPreferences.Editor edit = pref.edit();
        String check = pref.getString("a", "");
        if (check.equals("")) {
            edit.putString("loggedIn", "noValue");
            edit.apply();
        }

    }

    class TokenClear extends AsyncTask<String, String, String> {

        List<NameValuePair> params2 = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainDrawerHome.this);
            String tempSelfName = sharedPref.getString("a", "");

            if (tempSelfName.equals("")) {
                tempSelfName = FragmentCustomDiagLogin.username;
            }

            params2.add(new BasicNameValuePair("username", tempSelfName));

            try {
                jsonParser.makeHttpRequest(EMPTY_TOKEN, "POST", params2);
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

//   d

    public class CheckPendingOffers extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainDrawerHome.this);
            String tempUsername = sharedPreferences.getString("a", "");
            if (tempUsername.equals("")) {
                try {
                    tempUsername = FragmentCustomDiagLogin.username;
                    Log.d("dialog", "check1" + tempUsername);
                } catch (Exception e) {

                }
            }

            jsonParser = new JSONParser();
            List<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("username", tempUsername));
            offeredBy.clear();
            offeredByNameOfUser.clear();
            bookName.clear();
            authorName.clear();
            price.clear();
            jsonParser = new JSONParser();
            try {
                json66 = jsonParser.makeHttpRequest(PENDING_OFFERS, "POST", param);
                Log.d("dialog", "check2" + json66.length());
                if (json66.length() > 0) {
                    for (int i = 0; i < json66.length(); i++) {
                        offeredBy.add(json66.getString("a" + i));
                        offeredByNameOfUser.add(json66.getString("b" + i));
                        bookName.add(json66.getString("d" + i));
                        authorName.add(json66.getString("e" + i));
                        price.add(json66.getString("f" + i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Log.d("offered", "by" + offeredBy.get(0));
                jsonLength = json66.length();
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (jsonLength > 5) {
                wereThereOffers = 1;
                Log.d("length", "json" + jsonLength);
                dialogFinishPending(jsonLength);
            }
        }
    }

    private void dialogFinishPending(int howMany) {
        Log.d("dialog", "check3" + howMany / 6);

        final Dialog dialog = new Dialog(MainDrawerHome.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.accept_offer_dialog);
        dialog.show();

        final TextView totalOffersTv = (TextView) dialog.findViewById(R.id.offerTotalPendingOffers);
        TextView bookNameTv = (TextView) dialog.findViewById(R.id.offerBookName);
        TextView bookAuthorTv = (TextView) dialog.findViewById(R.id.offerBookAuthor);
        TextView bookSoldByTv = (TextView) dialog.findViewById(R.id.offerBookSoldBy);
        TextView bookFinalPriceTv = (TextView) dialog.findViewById(R.id.offerFinalPrice);

        totalOffersTv.setText(howMany / 6 + " total offers");

        try {
            bookNameTv.setText(bookName.get(0));
            bookAuthorTv.setText(authorName.get(0));
            bookSoldByTv.setText(offeredBy.get(0));
            bookFinalPriceTv.setText(price.get(0));
        } catch (Exception e) {

        }

        CardView acceptOffer, declineOffer;
        acceptOffer = (CardView) dialog.findViewById(R.id.acceptOfferCard);
        declineOffer = (CardView) dialog.findViewById(R.id.declineOfferCard);

        declineOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                final Dialog declineDialog = new Dialog(MainDrawerHome.this);
                declineDialog.setContentView(R.layout.decline_offer_dialog);
                declineDialog.setCancelable(false);
                declineDialog.show();

                CardView viewAgain = (CardView) declineDialog.findViewById(R.id.declineReturnToDialogButton);
                CardView confirmDecline = (CardView) declineDialog.findViewById(R.id.confirmDecline);

                viewAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        declineDialog.dismiss();
                        dialog.show();
                    }
                });
                confirmDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        declineDialog.dismiss();
                        new RejectOffer().execute();
                        Toast.makeText(MainDrawerHome.this, "Successfully rejected offer.", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(MainDrawerHome.this, MainDrawerHome.class);
                        startActivity(in);
                    }
                });
            }
        });

        acceptOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                final Dialog enterDetails2 = new Dialog(MainDrawerHome.this, android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
                enterDetails2.setContentView(R.layout.deliver_dialog2);
                enterDetails2.show();
                enterDetails2.setCancelable(true);

                CardView placeFinalAcceptance;

                etFullName = (EditText) enterDetails2.findViewById(R.id.checkOutName);
                etPhoneNumber = (EditText) enterDetails2.findViewById(R.id.checkOutPhoneNumber);
                etBookName = (EditText) enterDetails2.findViewById(R.id.offerBookName);
                etBookAuthorName = (EditText) enterDetails2.findViewById(R.id.offerBookAuthor);
                etBookFinalPrice = (EditText) enterDetails2.findViewById(R.id.offerFinalPrice);
                etHouseNumber = (EditText) enterDetails2.findViewById(R.id.houseNumber);
                etStreetAddress = (EditText) enterDetails2.findViewById(R.id.streetAddress);
                etOtherDetails = (EditText) enterDetails2.findViewById(R.id.detailedAddress);
                etSpecialLandmarks = (EditText) enterDetails2.findViewById(R.id.landmarksSpecial);

                allFieldsRequired = (TextView) enterDetails2.findViewById(R.id.allFieldsRequired);
                checkOutNameRequired = (TextView) enterDetails2.findViewById(R.id.nameRequired);
                checkOutPhoneRequired = (TextView) enterDetails2.findViewById(R.id.phoneNumberValidation);
                bookNameRequired = (TextView) enterDetails2.findViewById(R.id.bookNameRequired);
                bookAuthorRequired = (TextView) enterDetails2.findViewById(R.id.authorOfBookRequired);
                bookFinalPriceRequired = (TextView) enterDetails2.findViewById(R.id.agreedPriceRequired);
                streetAddressRequired = (TextView) enterDetails2.findViewById(R.id.streetAddressRequired);
                otherDetailsRequired = (TextView) enterDetails2.findViewById(R.id.otherDetailsRequired);
                specialLandmarksRequired = (TextView) enterDetails2.findViewById(R.id.landmarksRequired);

                placeFinalAcceptance = (CardView) enterDetails2.findViewById(R.id.confirmConfirmConfirm);

                etFullName.addTextChangedListener(MainDrawerHome.this);
                etPhoneNumber.addTextChangedListener(MainDrawerHome.this);
                etBookName.addTextChangedListener(MainDrawerHome.this);
                etBookAuthorName.addTextChangedListener(MainDrawerHome.this);
                etBookFinalPrice.addTextChangedListener(MainDrawerHome.this);
                etStreetAddress.addTextChangedListener(MainDrawerHome.this);
                etOtherDetails.addTextChangedListener(MainDrawerHome.this);
                etSpecialLandmarks.addTextChangedListener(MainDrawerHome.this);

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainDrawerHome.this);

                String firstName = pref.getString("firstNameSharePref1", "");
                String lastName = pref.getString("lastNameSharePref", "");
                String phoneNum = pref.getString("phoneSharePref", "");

                if (firstName.equals("")) {
                    firstName = FragmentCustomDiagLogin.firstName;
                    lastName = FragmentCustomDiagLogin.lastName;
                    phoneNum = FragmentCustomDiagLogin.phoneNumber;
                }

                etFullName.setText(firstName + " " + lastName);
                etPhoneNumber.setText(phoneNum);
                etBookName.setText(bookName.get(0));
                etBookAuthorName.setText(authorName.get(0));
                etBookFinalPrice.setText(price.get(0));

                placeFinalAcceptance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etFullName.getText().toString().length() == 0) {
                            allFieldsRequired.setVisibility(View.VISIBLE);
                            checkOutNameRequired.setVisibility(View.VISIBLE);
                        }
                        if (etPhoneNumber.getText().toString().length() == 0) {
                            allFieldsRequired.setVisibility(View.VISIBLE);
                            checkOutPhoneRequired.setVisibility(View.VISIBLE);
                        }
                        if (etBookName.getText().toString().length() == 0) {
                            allFieldsRequired.setVisibility(View.VISIBLE);
                            bookNameRequired.setVisibility(View.VISIBLE);
                        }
                        if (etBookAuthorName.getText().toString().length() == 0) {
                            allFieldsRequired.setVisibility(View.VISIBLE);
                            bookAuthorRequired.setVisibility(View.VISIBLE);
                        }
                        if (etBookFinalPrice.getText().toString().length() == 0) {
                            allFieldsRequired.setVisibility(View.VISIBLE);
                            bookFinalPriceRequired.setVisibility(View.VISIBLE);

                        }
                        if (etStreetAddress.getText().toString().length() == 0) {
                            allFieldsRequired.setVisibility(View.VISIBLE);
                            streetAddressRequired.setVisibility(View.VISIBLE);

                        }
                        if (etOtherDetails.getText().toString().length() == 0) {
                            allFieldsRequired.setVisibility(View.VISIBLE);
                            otherDetailsRequired.setVisibility(View.VISIBLE);
                        }

                        if (etSpecialLandmarks.getText().toString().length() == 0) {
                            allFieldsRequired.setVisibility(View.VISIBLE);
                            specialLandmarksRequired.setVisibility(View.VISIBLE);
                        }

                        if (allFieldsRequired.getVisibility() == View.INVISIBLE) {
                            Toast.makeText(MainDrawerHome.this, "success", Toast.LENGTH_SHORT).show();
                            enterDetails2.dismiss();

                            getEtFullName = etFullName.getText().toString();
                            getEtPhoneNumber = etPhoneNumber.getText().toString();
                            getEtBookName = etBookName.getText().toString();
                            getEtBookAuthorName = etBookAuthorName.getText().toString();
                            getEtBookFinalPrice = etBookFinalPrice.getText().toString();
                            getEtHouseNumber = etHouseNumber.getText().toString();
                            getEtStreetAddress = etStreetAddress.getText().toString();
                            getEtOtherDetails = etOtherDetails.getText().toString();
                            getEtSpecialLandmarks = etSpecialLandmarks.getText().toString();

                            new ConfirmFinalFinalOrder().execute();
                        }

                    }
                });

            }
        });

    }

    class RejectOffer extends AsyncTask<String, String, String> {

        List<NameValuePair> params2 = new ArrayList<>();
        List<NameValuePair> params3 = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainDrawerHome.this);
            String tempSelfName = sharedPref.getString("a", "");

            if (tempSelfName.equals("")) {
                tempSelfName = FragmentCustomDiagLogin.username;
            }

            params2.add(new BasicNameValuePair("soldTo", tempSelfName));
            params2.add(new BasicNameValuePair("soldBy", offeredBy.get(0)));
            params2.add(new BasicNameValuePair("bookName", bookName.get(0)));

            try {
                jsonParser.makeHttpRequest(REJECT_OFFER, "POST", params2);
            } catch (Exception e) {

            }

            jsonParser = new JSONParser();

            String notifTitle = "Book Sansar";
            String notifMessage = "Your offer of book " + bookName.get(0) + " to " + tempSelfName + " has been rejected." +
                    " Please renegotiate and place offer again.";
            String user = offeredBy.get(0);

            params3.add(new BasicNameValuePair("title", notifTitle));
            params3.add(new BasicNameValuePair("message", notifMessage));
            params3.add(new BasicNameValuePair("user", user));

            try {
                jsonParser.makeHttpRequest(SEND_NOTIFICATION_OF_REJECTION, "POST", params3);
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new CheckRejectedOffers().execute();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (etFullName.getText().toString().length() >= 1) {
            checkOutNameRequired.setVisibility(View.GONE);
        }
        if (etPhoneNumber.getText().toString().length() > 0) {
            checkOutPhoneRequired.setVisibility(View.INVISIBLE);

        }
        if (etBookName.getText().toString().length() > 0) {
            bookNameRequired.setVisibility(View.INVISIBLE);

        }
        if (etBookAuthorName.getText().toString().length() > 0) {
            bookAuthorRequired.setVisibility(View.INVISIBLE);

        }

        if (etBookFinalPrice.getText().toString().length() > 0) {
            bookFinalPriceRequired.setVisibility(View.INVISIBLE);

        }
        if (etStreetAddress.getText().toString().length() > 0) {
            streetAddressRequired.setVisibility(View.INVISIBLE);
        }
        if (etOtherDetails.getText().toString().length() > 0) {
            otherDetailsRequired.setVisibility(View.INVISIBLE);
        }
        if (etSpecialLandmarks.getText().toString().length() > 0) {
            specialLandmarksRequired.setVisibility(View.INVISIBLE);
        }

        if (etFullName.getText().toString().length() >= 1 && etPhoneNumber.getText().toString().length() > 0 && etBookName.getText().toString().length() > 0
                && etBookAuthorName.getText().toString().length() > 0 && etBookFinalPrice.getText().toString().length() > 0
                && etStreetAddress.getText().toString().length() > 0 && etOtherDetails.getText().toString().length() > 0 && etSpecialLandmarks.getText().toString().length() > 0) {
            allFieldsRequired.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    class ConfirmFinalFinalOrder extends AsyncTask<String, String, String> {

        List<NameValuePair> params2 = new ArrayList<>();
        List<NameValuePair> params3 = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {

//            String[] a = intentRoomName.split("\\|\\|");
//            String[] b = a[0].split("[*]+");

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainDrawerHome.this);
            String tempSelfName = sharedPref.getString("a", "");

            if (tempSelfName.equals("")) {
                tempSelfName = FragmentCustomDiagLogin.username;
            }

            params2.add(new BasicNameValuePair("fullName", getEtFullName));
            params2.add(new BasicNameValuePair("soldBy", offeredBy.get(0)));
            params2.add(new BasicNameValuePair("soldTo", tempSelfName));
            params2.add(new BasicNameValuePair("phoneNumber", getEtPhoneNumber));
            params2.add(new BasicNameValuePair("bookName", getEtBookName));
            params2.add(new BasicNameValuePair("authorOfBook", getEtBookAuthorName));
            params2.add(new BasicNameValuePair("markedPrice", "NOBODYCARESANYMORE"));
            params2.add(new BasicNameValuePair("finalPrice", getEtBookFinalPrice));
            params2.add(new BasicNameValuePair("houseNumber", getEtHouseNumber));
            params2.add(new BasicNameValuePair("streetAddress", getEtStreetAddress));
            params2.add(new BasicNameValuePair("otherDetails", getEtOtherDetails));
            params2.add(new BasicNameValuePair("specialLandmarks", getEtSpecialLandmarks));

            try {
                jsonParser.makeHttpRequest(SEND_DELIVERY_REQUEST, "POST", params2);
            } catch (Exception e) {

            }

            String notifTitle = "Book Sansar";
            String notifMessage = "Your offer of book " + bookName.get(0) + " to " + tempSelfName + " has been Accepted!!" +
                    "We shall pickup/deliver your book withing 5 business days.";
            String user = offeredBy.get(0);

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
            new CheckRejectedOffers().execute();
        }
    }

    public class CheckRejectedOffers extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainDrawerHome.this);
            String tempUsername = sharedPreferences.getString("a", "");
            if (tempUsername.equals("")) {
                try {
                    tempUsername = FragmentCustomDiagLogin.username;
                } catch (Exception e) {

                }
            }
            Log.d("entered", "asyntask1");

            List<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("username", tempUsername));

            try {
                Log.d("entered", "asyntask2");

                jsonParser = new JSONParser();
                json60 = jsonParser.makeHttpRequest(CHECK_REJECTED_OFFERS, "POST", param);

                if (json60.length() > 0) {
                    rejectedBy = json60.getString("c0");
                    rejectedByName = json60.getString("b0");
                    rejectedByBookName = json60.getString("d0");
                    rejectedByAuthror = json60.getString("e0");
                    rejectedByPrice = json60.getString("f0");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("entered", "asyntask3");

            try {
                jsonLength2 = json60.length();

            } catch (Exception e) {

            }
            Log.d("entered", "lenghth" + jsonLength2);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("entered", "asyntask4");
            if (jsonLength2 > 0) {
                Log.d("length", "json" + jsonLength);
                dialogSeeRejectedOffers(jsonLength);
            }
        }
    }

    private void dialogSeeRejectedOffers(int jsonLength) {

        final Dialog seeRejected = new Dialog(MainDrawerHome.this);
        seeRejected.setCancelable(false);
        seeRejected.setContentView(R.layout.view_declined_offer_dialog);
        seeRejected.show();
        TextView declinedMessage = (TextView) seeRejected.findViewById(R.id.declinedOffersMessageText);
        declinedMessage.setText("Your offer to " + rejectedBy + " for the book '" + rejectedByBookName + "' at the price of " + rejectedByPrice + " has been rejected.\nYou may renegotiate through the chat and place a new offer.");
        CardView dismiss = (CardView) seeRejected.findViewById(R.id.declinedOffersExit);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeRejected.dismiss();
                new DismissRejectedOffer().execute();
                Intent in = new Intent(MainDrawerHome.this, MainDrawerHome.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(in);
            }
        });
    }

    class DismissRejectedOffer extends AsyncTask<String, String, String> {

        List<NameValuePair> params2 = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainDrawerHome.this);
            String tempSelfName = sharedPref.getString("a", "");

            if (tempSelfName.equals("")) {
                tempSelfName = FragmentCustomDiagLogin.username;
            }

            Log.d("sold", "To" + tempSelfName);
            Log.d("sold", "by" + rejectedBy);
            Log.d("sold", "boooook" + rejectedByBookName);

            params2.add(new BasicNameValuePair("soldTo", rejectedBy));
            params2.add(new BasicNameValuePair("soldBy", tempSelfName));
            params2.add(new BasicNameValuePair("bookName", rejectedByBookName));

            try {
                jsonParser.makeHttpRequest(DISMISS_REJECTED_OFFER, "POST", params2);
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private void getToken() {
        final String recent_token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = sharedpref.edit();
        edit.putString("token", recent_token);
        edit.apply();
        final String token = sharedpref.getString("token", "");
        Log.d("TOKEN LOG", "km  " + token);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fcm_token", token);
                params.put("user", Login.strLoginUsername);
//

                return params;
            }
        };
        MySingleton.getmInstance(MainDrawerHome.this).addToRequestque(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_drawer_home, menu);
        MenuItem item2 = menu.findItem(R.id.log_out);
        MenuItem item1 = menu.findItem(R.id.action_settings);
        MenuItem item3 = menu.findItem(R.id.messager);
        MenuItem item4 = menu.findItem(R.id.search);
        item3.setIcon(R.drawable.openmessage);
        item4.setIcon(R.drawable.search);
        try {
            if (loggedIn.equals("noValue")) {

                item2.setVisible(false);
                item1.setVisible(true);
                item3.setVisible(false);
                item1.setIcon(R.drawable.login);
            } else {
                item2.setVisible(true);
                item3.setVisible(true);
                item2.setIcon(R.drawable.logout);
                item1.setVisible(false);
            }
        } catch (Exception e) {

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (resumeOrNot == 1) {
          /*  obj2 = new PullAllAlevelItems();
            obj2.execute();*/
            obj1.execute();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        if (didEverythingLoad == 1) {
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentCustomDiagLogin fragmentCustomDiagLogin = new FragmentCustomDiagLogin();
            fragmentCustomDiagLogin.show(getFragmentManager(), "cde");

            /*Intent intent = new Intent(MainDrawerHome.this,Login.class);
            startActivity(intent);*/


        }
        if (id == R.id.messager) {
            if (loggedIn.equals("noValue")) {
                Toast.makeText(this, "Please log in to continue.", Toast.LENGTH_SHORT).show();
            } else {
                Notifications not = new Notifications();
                Log.d("value", "bookname1");
                Intent in = new Intent(getBaseContext(), Notifications.class);
                startActivity(in);
            }
        }

        if (id == R.id.log_out) {
            Intent in = new Intent(MainDrawerHome.this, MainDrawerHome.class);
            startActivity(in);
            tokenClear();
            SharedPreferences sharedpref;
            sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = sharedpref.edit();
            FragmentCustomDiagLogin.firstName = "";
            FragmentCustomDiagLogin.emailAddress = "";
            FragmentCustomDiagLogin.username = "";
            edit.clear();
            edit.putString("checkbox", "notchecked");
            edit.apply();


            finish();
        }
        if (id == R.id.search) {
            Intent intent = new Intent(this, SearchAllData.class);
            startActivity(intent);
        }
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        Log.d("this", "ran");
        obj1.cancel(true);

        super.onPause();
    }

    private void tokenClear() {

        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(MainDrawerHome.this);
        SharedPreferences.Editor edit = sharedpref.edit();
        edit.putString("token", "");
        edit.apply();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fcm_token", "");
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainDrawerHome.this);
                String loginUsername = pref.getString("a", "");
                params.put("user", loginUsername);

                return params;
            }
        };
        MySingleton.getmInstance(MainDrawerHome.this).addToRequestque(stringRequest);
    }


    public class PullAllAlevelItems extends AsyncTask<String, String, String> {
        //
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... args) {


            try {


                List<NameValuePair> params1 = new ArrayList<>();

                params1.add(new BasicNameValuePair("course", "others"));


                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params1);


                userName.clear();
                firstName.clear();
                lastName.clear();
                nameofBook.clear();
                nameofAuthor.clear();
                priceOfBook.clear();
                homeAddress.clear();
                school.clear();
                image1name.clear();
                image2name.clear();
                image3name.clear();
                phoneNumber.clear();
                emailAddress.clear();

                try {
                    for (int i = 0; i < json.length(); i++) {
                        userName.add(json.getString("a" + i));
                        firstName.add(json.getString("b" + i));
                        lastName.add(json.getString("c" + i));
                        nameofBook.add(json.getString("d" + i));
                        nameofAuthor.add(json.getString("e" + i));
                        priceOfBook.add(json.getString("f" + i));
                        homeAddress.add(json.getString("g" + i));
                        school.add(json.getString("h" + i));
                        image1name.add(json.getString("i" + i));
                        image2name.add(json.getString("j" + i));
                        image3name.add(json.getString("k" + i));
                        phoneNumber.add(json.getString("l" + i));
                        emailAddress.add(json.getString("m" + i));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                FragmentNavDraerMain.arrayUserName = new String[userName.size()];
                FragmentNavDraerMain.arrayFirstName = new String[firstName.size()];
                FragmentNavDraerMain.arrayLastName = new String[lastName.size()];
                FragmentNavDraerMain.arrayNameOfBook = new String[nameofBook.size()];
                FragmentNavDraerMain.arrayNameOfAuthor = new String[nameofAuthor.size()];
                FragmentNavDraerMain.arrayPriceOfBook = new String[priceOfBook.size()];
                FragmentNavDraerMain.arrayHomeAddress = new String[homeAddress.size()];
                FragmentNavDraerMain.arraySchool = new String[school.size()];
                FragmentNavDraerMain.arrayImage1Name = new String[image1name.size()];
                FragmentNavDraerMain.arrayImage2Name = new String[firstName.size()];
                FragmentNavDraerMain.arrayImage3Name = new String[firstName.size()];
                FragmentNavDraerMain.arrayPhoneNumber = new String[firstName.size()];
                FragmentNavDraerMain.arrayEmailAddress = new String[firstName.size()];

                FragmentNavDraerMain.arrayUserName = userName.toArray(new String[userName.size()]);
                FragmentNavDraerMain.arrayFirstName = firstName.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayLastName = lastName.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayNameOfBook = nameofBook.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayNameOfAuthor = nameofAuthor.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayPriceOfBook = priceOfBook.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayHomeAddress = homeAddress.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arraySchool = school.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayImage1Name = image1name.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayImage2Name = image2name.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayImage3Name = image3name.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayPhoneNumber = phoneNumber.toArray(new String[firstName.size()]);
                FragmentNavDraerMain.arrayEmailAddress = emailAddress.toArray(new String[firstName.size()]);

                FragmentNavDraerMain.title = FragmentNavDraerMain.arrayNameOfBook;
                FragmentNavDraerMain.price = FragmentNavDraerMain.arrayPriceOfBook;
                FragmentNavDraerMain.sellerName = FragmentNavDraerMain.arrayFirstName;

                Log.d("ETA PUGYOO??", "" + FragmentNavDraerMain.title);

            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            if (wereThereOffers == 0) {
                Log.d("entered", "if");
                new CheckRejectedOffers().execute();
            }
            FragmentNavDraerMain fragmentNavDraerMain = new FragmentNavDraerMain();
            FragmentManager manager3 = getFragmentManager();
            FragmentTransaction transaction1 = manager3.beginTransaction();
            transaction1.add(R.id.mainFragmentNavHome, fragmentNavDraerMain, "asdf");
            transaction1.commit();
        }
    }


}