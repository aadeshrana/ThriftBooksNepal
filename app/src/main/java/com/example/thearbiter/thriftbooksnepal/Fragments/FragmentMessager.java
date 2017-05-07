package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.Activities.FinalBuyersActivity;
import com.example.thearbiter.thriftbooksnepal.Adapters.MessagerAdapter;
import com.example.thearbiter.thriftbooksnepal.Adapters.SlideShowAdapter;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.InfinitePagerAdapter;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Information.InformationMessageActivity;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentMessager extends Fragment {

    private static final String PULL_ALL_MESSAGES_URL = "http://frame.ueuo.com/thriftbooks/pullAllMessages.php";
    String SEND_NOTIFICATION_TO_USER = "http://frame.ueuo.com/images/send_notification.php";
    ArrayList<String> sender1 = new ArrayList<>();
    ArrayList<String> message1 = new ArrayList<>();
    ArrayList<String> time1 = new ArrayList<>();
    ArrayList<String> username1 = new ArrayList<>();
    private static final String SEND_MESSAGE_URL = "http://frame.ueuo.com/thriftbooks/postComment.php";
    JSONParser jsonParser = new JSONParser();
    public static String finalBuyersActivityNameOfBook;
    public static String finalBuyersActivityUsernameOfSeller;
    public static String finalBuyersActivityNameOfAuthor;
    public static String finalBuyersActivityPriceOfBook;
    public static String finalBuyersActivityNameOfSeller;
    public static String finalBuyersActivityImage1;
    public static String finalBuyersActivityImage2;

    public static int scrollToBot = 0;
    public static String finalBuyersActivityImage3;
    ProgressDialog pdialog;
    String stringMessageToSend;
    public static String title[];
    public static String usernameOfSender[];
    public static String nameOfSender[];
    public static String Notiftime[];
    public static String Notiftime2[];
    ImageView sentOrNotImage;
    public static String timeOfNotification;
    LinearLayoutManager manager;
    TextView finalBuyersActivityTextVieTitleOfBook, finalBuyersActivityTextVieNameOfAuthor, finalBuyersActivityTextViePriceOfBook;
    TextView finalBuyersActivityTextVieNameOfSeller;
    Button sendFinalMessage;
    EditText messageEditText;
    ScrollView scrollView;
    MessagerAdapter adapter;
    String userLoggedIn;
    String xyz[]={"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpkdmF3JBCuvZuKscnzMVYTDZ_IOTRlQWXRvoKHrxM-XD8tRp6","R.drawable.tempbackground"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_final_buyers, container, false);
        finalBuyersActivityTextVieTitleOfBook = (TextView) view.findViewById(R.id.finalBuyerCardInfoTitleOfBookValue);
        finalBuyersActivityTextVieNameOfAuthor = (TextView) view.findViewById(R.id.finalBuyerCardInfoNameofAuthorValue);
        finalBuyersActivityTextViePriceOfBook = (TextView) view.findViewById(R.id.finalBuyerCardInfoPriceOfBookValue);
        finalBuyersActivityTextVieNameOfSeller = (TextView) view.findViewById(R.id.finalBuyerCardInfoNameOfSellerValue);
        sendFinalMessage = (Button) view.findViewById(R.id.sendMessageButton);
        messageEditText = (EditText) view.findViewById(R.id.messageEditText);
        scrollView = (ScrollView) view.findViewById(R.id.scrollVieFinalBuyer);
        ImageView testImage =(ImageView)view.findViewById(R.id.testImage);
        String sendImage1, sendImage2,sendImage3;
        sendImage1 = "http://aadeshrana.esy.es/" + finalBuyersActivityImage1;
        String[] gg;
        Log.d("eta",":"+finalBuyersActivityImage2);
        if(finalBuyersActivityImage2.equals("null")){
            gg =new String[]{sendImage1};
        }
        else{
            sendImage2 = "http://aadeshrana.esy.es/" +finalBuyersActivityImage2;
            if(finalBuyersActivityImage3.equals("null")){
                gg=new String[]{sendImage1,sendImage2};
            }
            else{
                sendImage3 = "http://aadeshrana.esy.es/" + finalBuyersActivityImage3;
                gg=new String[]{sendImage1,sendImage2,sendImage3};
                Log.d("eta",":");
            }
        }



        Log.d("length11",":"+gg.length);


        ViewPager viewPagerSlideShow;
        SlideShowAdapter adater;
        viewPagerSlideShow =(ViewPager)view.findViewById(R.id.mainSlideShow);
        adater = new SlideShowAdapter(getActivity(),gg);
        PagerAdapter wrapperAdapter = new InfinitePagerAdapter(adater);
        viewPagerSlideShow.setAdapter(wrapperAdapter);
       // adater.setArray(xyz);


        ///



        setValuesToTextVies();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerVieMessageActivity);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new MessagerAdapter(getActivity(), getdata());
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(getActivity());
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        if (scrollToBot == 1) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.setSmoothScrollingEnabled(false);
// Now scroll the view
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
// Now enable the animation again if needed
                    scrollView.setSmoothScrollingEnabled(true);
//                    sentOrNotImage.setImageResource(R.drawable.tick_green);
                    sendMessage();
                }
            });
        }

        sendFinalMessage.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpref;
                sharedpref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                userLoggedIn = sharedpref.getString("loggedIn", "noValue");
                if (userLoggedIn.equals("noValue")) {
                    FragmentCustomDiagLogin fragmentCustomDiagLogin = new FragmentCustomDiagLogin();
                    fragmentCustomDiagLogin.show(getFragmentManager(), "cde");
                } else {
                    if (messageEditText.length() == 0) {
                        Toast.makeText(getActivity(), "Please rite message first", Toast.LENGTH_SHORT).show();
                    } else {
                        stringMessageToSend = messageEditText.getText().toString();
                        new AddItemToStore().execute();
                        messageEditText.setText("");
                    }
                }
            }
        });
        return view;
    }

    void timeFinder() {
        FragmentMessager.timeOfNotification = DateFormat.getDateTimeInstance().format(new Date());

    }

    void changeToIndianTime() {
        try {
            //////////////////SYSTEM TIME////////////////////////
            String localArray1[], localArray2[];
            int year, month, date, hour, minutes, seconds;
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:SS");
            String formattedDate = df.format(c.getTime());
            String formattedTime = df2.format(c.getTime());
            localArray1 = (formattedDate.split("-"));
            localArray2 = (formattedTime.split(":"));
            year = Integer.parseInt(localArray1[0]);
            month = Integer.parseInt(localArray1[1]);
            date = Integer.parseInt(localArray1[2]);
            hour = Integer.parseInt(localArray2[0]);
            minutes = Integer.parseInt(localArray2[1]);
            seconds = Integer.parseInt(localArray2[2]);

            /////////////////////SYSTEM TIME//////////////////////
            Notiftime2 = new String[Notiftime.length];

            for (int i = 0; i < Notiftime.length; i++) {
                String localArray4[], localArray5[], localArray6[];
                int serverYear, serverMonth, serverDate, serverHour, serverMinutes, serverSeconds;

                localArray4 = Notiftime[i].split(" ");

                localArray5 = localArray4[0].split("-");
                localArray6 = localArray4[1].split(":");

                Log.d("pugyo ki", "pugena" + localArray5[0]);

                serverYear = Integer.parseInt(localArray5[0]);
                serverMonth = Integer.parseInt(localArray5[1]);
                serverDate = Integer.parseInt(localArray5[2]);
                serverHour = Integer.parseInt(localArray6[0]);
                serverMinutes = Integer.parseInt(localArray6[1]);
                serverSeconds = Integer.parseInt(localArray6[2]);

                ////////////////////// SERVER TIME///////////////////////////////////

                int totalTime = ((year - 2017) * 365 * 24 * 60 * 60) + (month * 30 * 24 * 60 * 60) + (date * 24 * 60 * 60) + (hour * 60 * 60) + (minutes * 60) + seconds;
                int serverTime = ((serverYear - 2017) * 365 * 24 * 60 * 60) + (serverMonth * 30 * 24 * 60 * 60) + (serverDate * 24 * 60 * 60) + (serverHour * 60 * 60) + (serverMinutes * 60) + serverSeconds;
                try {
                    int difference = totalTime - serverTime - 16200;
                    if (difference < 60) {
                        Notiftime2[i] = "before a few seconds";
                    } else {
                        float differenceInMinutes = (float) difference / (float) 60;
                        if (differenceInMinutes < 60) {
                            Notiftime2[i] = "before " + (int) differenceInMinutes + " minutes";
                        } else {
                            float differenceInHour = (float) differenceInMinutes / (float) 60;
                            if (differenceInHour < 24) {
                                Notiftime2[i] = "before " + (int) differenceInHour + " hours";
                            } else {
                                float differenceInDay = (float) differenceInHour / (float) 24;
                                if (differenceInDay < 30) {
                                    Notiftime2[i] = "before " + (int) differenceInDay + " days";
                                } else {
                                    Notiftime2[i] = "before more than a month";
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class AddItemToStore extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            timeFinder();
            pdialog = new ProgressDialog(getActivity());
            pdialog.setMessage("Posting Message");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                List<NameValuePair> param1 = new ArrayList<>();
                Log.d("usernamsending", "km");

                SharedPreferences sharedpref;
                sharedpref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedpref.edit();
                String sendersUsername = sharedpref.getString("a", "");
                if (sendersUsername.equals("")) {
                    sendersUsername = FragmentCustomDiagLogin.username;
                }

                String fullName = sharedpref.getString("firstNameSharePref", "") + " " + sharedpref.getString("lastNameSharePref", "");
                if (fullName.equals(" ")) {
                    fullName = FragmentCustomDiagLogin.firstName + " " + FragmentCustomDiagLogin.lastName;
                }

                param1.add(new BasicNameValuePair("senderusername", sendersUsername));
                param1.add(new BasicNameValuePair("sender", fullName));
                param1.add(new BasicNameValuePair("time", FragmentMessager.timeOfNotification));
                param1.add(new BasicNameValuePair("recipient", FragmentMessager.finalBuyersActivityImage2 + FragmentMessager.finalBuyersActivityPriceOfBook));
                param1.add(new BasicNameValuePair("message", stringMessageToSend));

                JSONObject json = jsonParser.makeHttpRequest(SEND_MESSAGE_URL, "POST", param1);

            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new LoadAllMessages().execute();
        }
    }

    private void setValuesToTextVies() {
        finalBuyersActivityTextViePriceOfBook.setText(FragmentMessager.finalBuyersActivityPriceOfBook);
        finalBuyersActivityTextVieTitleOfBook.setText(FragmentMessager.finalBuyersActivityNameOfBook);
        finalBuyersActivityTextVieNameOfAuthor.setText(FragmentMessager.finalBuyersActivityNameOfAuthor);
        finalBuyersActivityTextVieNameOfSeller.setText(FragmentMessager.finalBuyersActivityNameOfSeller);
    }

    public List<InformationMessageActivity> getdata() {
        List<InformationMessageActivity> data = new ArrayList<>();

        for (int j = 0; j < title.length; j++) {
            InformationMessageActivity current = new InformationMessageActivity();
            current.textMessage = title[j];
            Log.d("jjjjjj", "" + title[j]);
            current.sendersName = nameOfSender[j];
            changeToIndianTime();
            current.sendersUsername = usernameOfSender[j];
            current.timeOfNotification = Notiftime2[j];
            data.add(current);
        }
        return data;
    }

    public class LoadAllMessages extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            Log.d("thisis", "beforeASy");
            try {

                Log.d("thisis2", "insideasy");
                List<NameValuePair> param1 = new ArrayList<>();
//                param1.add(new BasicNameValuePair("username", spUsername));

                String salesId = FragmentMessager.finalBuyersActivityImage2 + FragmentMessager.finalBuyersActivityPriceOfBook;

                param1.add(new BasicNameValuePair("saleid", salesId));

                sender1.clear();
                message1.clear();
                time1.clear();
                username1.clear();

                JSONObject json;

                json = jsonParser.makeHttpRequest(PULL_ALL_MESSAGES_URL, "POST", param1);
                //full json response
                Log.d("thisis3", "afterReq");

                try {
                    Log.d("thisis", ":" + json.length());
                    for (int i = 0; i < json.length(); i++) {
                        sender1.add(json.getString("b" + i));
                        message1.add(json.getString("a" + i));
                        time1.add(json.getString("c" + i));
                        username1.add(json.getString("d" + i));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {

            }

            FinalBuyersActivity.senderArray = new String[sender1.size()];
            FinalBuyersActivity.messageArray = new String[message1.size()];
            FinalBuyersActivity.timeArray = new String[time1.size()];
            FinalBuyersActivity.usernameArray = new String[username1.size()];

            FinalBuyersActivity.senderArray = sender1.toArray(new String[sender1.size()]);
            FinalBuyersActivity.messageArray = message1.toArray(new String[message1.size()]);
            FinalBuyersActivity.timeArray = time1.toArray(new String[time1.size()]);
            FinalBuyersActivity.usernameArray = username1.toArray(new String[username1.size()]);

            try {
                Log.d("REACH HERE", "" + sender1.get(0));

            } catch (Exception e) {

            }
            FragmentMessager.title = message1.toArray(new String[message1.size()]);
            FragmentMessager.nameOfSender = sender1.toArray(new String[sender1.size()]);
            FragmentMessager.Notiftime = time1.toArray(new String[time1.size()]);
            FragmentMessager.usernameOfSender = username1.toArray(new String[username1.size()]);

            sender1 = null;
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdialog.dismiss();
            adapter.notifyDataSetChanged();
            FragmentMessager fragmentMessager = new FragmentMessager();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.recyclerPasterId, fragmentMessager, "asdf");
            transaction.commit();
            FragmentMessager.scrollToBot = 1;
        }
    }

    class SendNotification extends AsyncTask<String, String, String> {
        List<NameValuePair> params1 = new ArrayList<>();


        @Override
        protected String doInBackground(String... params) {

            Log.d("this is ", "life" + finalBuyersActivityUsernameOfSeller);

            params1.add(new BasicNameValuePair("user", finalBuyersActivityUsernameOfSeller));
            params1.add(new BasicNameValuePair("message", "Somebody just posted a message on your ad"));
            params1.add(new BasicNameValuePair("title", "New Notification"));

            jsonParser.makeHttpRequest(SEND_NOTIFICATION_TO_USER, "POST", params1);
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void sendMessage() {
        new SendNotification().execute();
    }
}