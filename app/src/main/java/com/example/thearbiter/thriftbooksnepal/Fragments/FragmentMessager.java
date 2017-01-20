package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.Adapters.MessagerAdapter;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Information.InformationMessageActivity;
import com.example.thearbiter.thriftbooksnepal.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentMessager extends Fragment {

    private static final String SEND_MESSAGE_URL = "http://frame.ueuo.com/thriftbooks/postComment.php";
    JSONParser jsonParser = new JSONParser();
    public static String finalBuyersActivityNameOfBook;
    public static String finalBuyersActivityUsernameOfSeller;
    public static String finalBuyersActivityNameOfAuthor;
    public static String finalBuyersActivityPriceOfBook;
    public static String finalBuyersActivityNameOfSeller;
    public static String finalBuyersActivityImage1;
    public static String finalBuyersActivityImage2;
    public static String finalBuyersActivityImage3;

    String stringMessageToSend;
    public static String title[];
    public static String nameOfSender[];
    LinearLayoutManager manager;
    TextView finalBuyersActivityTextVieTitleOfBook, finalBuyersActivityTextVieNameOfAuthor, finalBuyersActivityTextViePriceOfBook;
    TextView finalBuyersActivityTextVieNameOfSeller;
    Button sendFinalMessage;
    EditText messageEditText;

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
        setValuesToTextVies();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerVieMessageActivity);
        MessagerAdapter adapter = new MessagerAdapter(getActivity(), getdata());
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        manager.setStackFromEnd(true);


        sendFinalMessage.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (messageEditText.length() == 0) {
                    Toast.makeText(getActivity(), "Please rite message first", Toast.LENGTH_SHORT).show();
                } else {
                    stringMessageToSend = messageEditText.getText().toString();
                    new AddItemToStore().execute();
                }
            }
        });
        return view;
    }

    public class AddItemToStore extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                List<NameValuePair> param1 = new ArrayList<>();
                Log.d("usernamsending", "km");

                SharedPreferences sharedpref;
                sharedpref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedpref.edit();
                String sendersUsername = sharedpref.getString("a", "noValue");
                String fullName = sharedpref.getString("firstNameSharePref", "noValue") + " " + sharedpref.getString("lastNameSharePref", "noValue");

                param1.add(new BasicNameValuePair("senderusername", sendersUsername));
                param1.add(new BasicNameValuePair("sender", fullName));
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

        Log.d("randi ko ban", "" + title[0]);

        for (int j = 0; j < title.length; j++) {
            InformationMessageActivity current = new InformationMessageActivity();
            current.textMessage = title[j];
            Log.d("jjjjjj", "" + title[j]);
            current.sendersName = nameOfSender[j];
            data.add(current);
        }
        return data;
    }


}