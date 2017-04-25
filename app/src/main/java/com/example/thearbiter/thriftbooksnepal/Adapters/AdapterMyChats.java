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
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.Activities.ChatMainActivity;
import com.example.thearbiter.thriftbooksnepal.Activities.Notifications;
import com.example.thearbiter.thriftbooksnepal.Information.InformationCheckChats;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.Collections;
import java.util.List;

/**
THIS CLASS IS THE ADAPTER OF CHECK CHAT MESSAGES.
 */

public class AdapterMyChats extends RecyclerView.Adapter<AdapterMyChats.MyViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<InformationCheckChats> data = Collections.emptyList();

    public AdapterMyChats(Context context, List<InformationCheckChats> data) {
        Log.d("LOG", "" + context);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.check_my_chat_messages_layout, parent, false);
        Log.d("hellooo", "s" + Notifications.positionOfView);
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

        holder.from.setText(current.infoUsernameOfChatSender);
        holder.content.setText("");
        holder.checkChatMessagesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "what is life", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ChatMainActivity.class);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String username = preferences.getString("a", "");
//                String stringToSendInIntent = username+"***"+ FragmentMessager.finalBuyersActivityUsernameOfSeller+"||"+FragmentMessager.finalBuyersActivityNameOfSeller+"---"+nameOfUser;
                intent.putExtra("room_name",current.infoUsernameOfChatSender);
                intent.putExtra("user_name", username);
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

        public MyViewHolder(View itemView) {
            super(itemView);
            from = (TextView) itemView.findViewById(R.id.userName);
            content = (TextView) itemView.findViewById(R.id.contentMyOrder);
            checkChatMessagesCardView = (CardView)itemView.findViewById(R.id.checkChatsLayoutCardViewMain);
        }
    }
}
