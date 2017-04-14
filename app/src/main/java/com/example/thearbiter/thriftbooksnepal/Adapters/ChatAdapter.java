package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Information.InformationChatActivity;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.Collections;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<InformationChatActivity> data = Collections.emptyList();

    public ChatAdapter(Context context, List<InformationChatActivity> data) {
        Log.d("LOG", "" + context);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.message_custom, parent, false);
//        ImageView cv = (ImageView) view.findViewById(R.id.messageUserPicture);
//
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cv.getLayoutParams();
//       params.setMargins(10,0,0,0);
//
//        cv.setLayoutParams(params); //causes layout update
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final InformationChatActivity current = data.get(position);
        Log.d("asdfasdfasdf", "" + data.size());
        holder.chatSendersName.setText(current.informationChatSendersName);

        holder.chatMessageToSend.setText(current.informationChatTextMessage);
        holder.chatTimeOfMessage.setText(current.informationChattimeOfChat);
    }

    public void notifyListChanges(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView chatUserPicture;
        TextView chatSendersName;
        TextView chatMessageToSend;
        CardView card;
        TextView chatTimeOfMessage;

        public MyViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.adapterFirstPageCardViewMain);
            chatTimeOfMessage = (TextView) itemView.findViewById(R.id.timeOfNotif);
            chatUserPicture = (ImageView) itemView.findViewById(R.id.messageUserPicture);
            chatSendersName = (TextView) itemView.findViewById(R.id.messageCustomLayoutNameOfSender);
            chatMessageToSend = (TextView) itemView.findViewById(R.id.messageCustomLayoutTextToSend);
        }
    }
}