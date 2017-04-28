package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import com.example.thearbiter.thriftbooksnepal.Activities.ChatMainActivity;
import com.example.thearbiter.thriftbooksnepal.Adapters.ChatAdapter;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Information.InformationChatActivity;
import com.example.thearbiter.thriftbooksnepal.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FragmentChat extends Fragment {
    public String title[] = {""};
    public String message[] = {""};
    public String date[] = {""};

    public static String selfUsername;

    ArrayList<String> titleAl = new ArrayList<>();
    ArrayList<String> messageAl = new ArrayList<>();

    //ArrayList<String> titleAl = new ArrayList<>();

    //Reference to Realtime database
    private DatabaseReference root;
    private String temp_key;
    public static String messageForNotif;

    //These are send button and the edittext for user to write the message
    private Button sendChatMessage;
    private EditText chatMessageEt;

    //NOTIFICATION URL
    private static final String SEND_NOTIFICATION_TO_PARTICIPANTS = "http://frame.ueuo.com/images/send_notification_chat.php";
    private static final String SEND_CHAT_TO_SERVER = "http://frame.ueuo.com/thriftbooks/sendChatToServer.php";

    JSONParser jsonParser = new JSONParser();

    private String chat_msg, chat_user_name;
    private TextView chat_coversation;
    Context context;
    ChatAdapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chat_activity, container, false);
        context = getActivity();

        //Inflating Recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.chatActivityRecyclerVie);


        //These are send button and the edittext for user to write the message
        sendChatMessage = (Button) view.findViewById(R.id.chatActivitySendButton);
        chatMessageEt = (EditText) view.findViewById(R.id.chatActvityEditText);

        //Reference to Realtime database
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String username = preferences.getString("a", "");
        if (username.equals("")) {
            username = FragmentCustomDiagLogin.username;
        }

        Log.d("intent", "" + ChatMainActivity.intentRoomName);
        String roomName2 = ChatMainActivity.intentRoomName;

//        String roomName2 = username +"***"+ FragmentMessager.finalBuyersActivityUsernameOfSeller;

        root = FirebaseDatabase.getInstance().getReference().child(roomName2);

        sendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String username = sharedPreferences.getString("a", "");
                if (username.equals("")) {
                    username = FragmentCustomDiagLogin.username;
                }

                map2.put("name", username);
                map2.put("msg", chatMessageEt.getText().toString());

                messageForNotif = chatMessageEt.getText().toString();

                message_root.updateChildren(map2);
                chatMessageEt.setText("");


                //SEND NOTIFICATION OF MESSAGE TO SELLER
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                selfUsername = preferences.getString("a", "");
                if (selfUsername.equals("")) {
                    selfUsername = FragmentCustomDiagLogin.username;
                }
                Log.d("kbhayo", "pher");
                new SendNotification().execute();

                //This sends message to our database
                new SendMessageToMainDatabase().execute();

            }
        });

        //When Data is changed in the Database this event listener can handle
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("hereweare", "1");
                append_chat_conversation(dataSnapshot, context);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot, context);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    //Function to load data when data is changed in database
    private void append_chat_conversation(DataSnapshot dataSnapshot, Context context) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot) i.next()).getValue();

            Log.d("OPIOU", "" + chat_msg);
            Log.d("OPIOUaa", "" + chat_user_name);

            titleAl.add(chat_user_name);
            messageAl.add(chat_msg);

            title = new String[titleAl.size()];
            message = new String[messageAl.size()];
            date = new String[titleAl.size()];

            title = titleAl.toArray(new String[titleAl.size()]);
            message = messageAl.toArray(new String[messageAl.size()]);
            Log.d("hereweare", "2" + context);

            adapter = new ChatAdapter(context, getdata());

            recyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            layoutManager.setStackFromEnd(true);
//            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }


    class SendNotification extends AsyncTask<String, String, String> {

        List<NameValuePair> params2 = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {

            //Finding out the username to whom we have to send the notification to
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String yourName = preferences.getString("a", "");
            if (yourName.equals("")) {
                yourName = FragmentCustomDiagLogin.username;
            }
            String roomName = ChatMainActivity.intentRoomName;
            String[] stepOne = roomName.split("\\|\\|");
            String[] stepTwo = stepOne[0].split("[*]+");
            String nameToSendNotificationTo;
            if (stepTwo[0].equals(yourName)) {
                nameToSendNotificationTo = stepTwo[1];
            } else {
                nameToSendNotificationTo = stepTwo[0];
            }
            Log.d("nameTo", "Send" + nameToSendNotificationTo);

            params2.add(new BasicNameValuePair("user", nameToSendNotificationTo));
            params2.add(new BasicNameValuePair("title", "Book Sansar new message"));
            params2.add(new BasicNameValuePair("message", selfUsername + ": " + messageForNotif));

            jsonParser.makeHttpRequest(SEND_NOTIFICATION_TO_PARTICIPANTS, "POST", params2);
            return null;
        }

    }

    class SendMessageToMainDatabase extends AsyncTask<String, String, String> {

        List<NameValuePair> params2 = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {


            //This is the firebase roomName
            String wholeRoomName = ChatMainActivity.intentRoomName;

            String stepOne[] = wholeRoomName.split("\\|\\|");
            String stepTwo[] = stepOne[0].split("[*]+");

            String usernameTo;
            if (stepTwo[0].equals(selfUsername)) {
                usernameTo = stepTwo[1];
            } else {
                usernameTo = stepTwo[0];
            }

            //Adding parametres to send to PHP File

            params2.add(new BasicNameValuePair("usernameFrom", selfUsername));
            params2.add(new BasicNameValuePair("to", usernameTo));
            params2.add(new BasicNameValuePair("message", messageForNotif));

            jsonParser.makeHttpRequest(SEND_CHAT_TO_SERVER, "POST", params2);
            return null;
        }

    }

    public List<InformationChatActivity> getdata() {
        List<InformationChatActivity> data = new ArrayList<>();
        for (int j = 0; j < title.length; j++) {
            InformationChatActivity current = new InformationChatActivity();
            current.informationChatSendersName = title[j];
            current.informationChatTextMessage = message[j];
            current.informationChattimeOfChat = date[j];
            data.add(current);
        }
        return data;
    }
}

