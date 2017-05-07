package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.Activities.ChatMainActivity;
import com.example.thearbiter.thriftbooksnepal.Activities.Notifications;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomDiagLogin;
import com.example.thearbiter.thriftbooksnepal.Information.InformationCheckChats;
import com.example.thearbiter.thriftbooksnepal.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * THIS CLASS IS THE ADAPTER OF CHECK CHAT MESSAGES.
 */

public class AdapterMyChats extends RecyclerView.Adapter<AdapterMyChats.MyViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<InformationCheckChats> data = Collections.emptyList();
    String user;

    public AdapterMyChats(Context context, List<InformationCheckChats> data) {
        Log.d("LOG", "" + context);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.check_my_chat_messages_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InformationCheckChats current = data.get(position);

//        if (Notifications.positionOfView == 0) {
//            holder.content.setText("Has Commented On Your Post!");
//        } else
//            holder.content.setText("Has Requested a new book!");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String nameSelf = sharedPreferences.getString("a", "");
        if (nameSelf.equals("")) {
            nameSelf = FragmentCustomDiagLogin.username;
        }

        String[] firstRoundSplitter = current.infoUsernameOfChatSender.split("\\|\\|");
        final String[] splitter = firstRoundSplitter[0].split("[*]+");
        if (splitter[0].equals(nameSelf) || splitter[1].equals(nameSelf)) {

            String[] finalNameSplit = firstRoundSplitter[1].split("---");
            String firstNameFromSharedPref = sharedPreferences.getString("firstNameSharePref1", "");
            if (firstNameFromSharedPref.equals("")) {
                firstNameFromSharedPref = FragmentCustomDiagLogin.firstName;
            }


            if (finalNameSplit[0].equals(firstNameFromSharedPref)) {
                holder.from.setText(finalNameSplit[1]);
            } else {
                holder.from.setText(finalNameSplit[0]);
            }

            if (splitter[0].equals(nameSelf)) {
                Picasso.with(context).load("http://aadeshrana.esy.es/" + splitter[1] + "ProfilePic.jpg").fit().centerCrop().placeholder(R.drawable.noimageplaceholder).into(holder.imageProfile);
                user = splitter[1];
            } else {
                Picasso.with(context).load("http://aadeshrana.esy.es/" + splitter[0] + "ProfilePic.jpg").fit().centerCrop().placeholder(R.drawable.noimageplaceholder).into(holder.imageProfile);
                user = splitter[0];
            }
        }
        String user;
        if (splitter[0].equals(nameSelf)) {
            user = splitter[1];
        } else {
            user = splitter[0];
        }

        try {
            for (int i = 0; i < Notifications.messageFromPref.length; i++) {
                Log.d("user:", user);
                if (Notifications.messageFromPref[i].equals(user)) {
                    holder.content.setText("new");
                    holder.content.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {

        }


        try {
            for (int q = 0; q < Notifications.chatUsersNeMessagesArray.length; q++) {
                if (Notifications.chatUsersNeMessagesArray[q].equals(user)) {
                    holder.content.setText("new");
                    holder.content.setVisibility(View.VISIBLE);
                } else {
                }
            }
        } catch (Exception e) {

        }

        holder.checkChatMessagesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "what is life", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ChatMainActivity.class);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

                String username = preferences.getString("a", "");
                if (username.equals("")) {
                    username = FragmentCustomDiagLogin.username;
                }
                String temp = "";
                if (username.equals(splitter[0])) {
                    temp = splitter[1];
                } else {
                    temp = splitter[0];
                }
//                String stringToSendInIntent = username+"***"+ FragmentMessager.finalBuyersActivityUsernameOfSeller+"||"+FragmentMessager.finalBuyersActivityNameOfSeller+"---"+nameOfUser;
                intent.putExtra("room_name", current.infoUsernameOfChatSender);
                intent.putExtra("user_name", temp);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView from;
        TextView content;
        CardView checkChatMessagesCardView;
        ImageView imageProfile;

        public MyViewHolder(View itemView) {
            super(itemView);
            from = (TextView) itemView.findViewById(R.id.userName);
            content = (TextView) itemView.findViewById(R.id.contentMyOrder);
            imageProfile = (ImageView) itemView.findViewById(R.id.imageOfMyChats);
            checkChatMessagesCardView = (CardView) itemView.findViewById(R.id.checkChatsLayoutCardViewMain);
        }
    }
}
