package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Information.InformationChatActivity;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.Collections;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    static int logixForLyf = 0;
    static View view;


    List<InformationChatActivity> data = Collections.emptyList();

    public ChatAdapter(Context context, List<InformationChatActivity> data) {

        this.context = context;
        Log.d("hereweare", "3" + context);

        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            view = layoutInflater.inflate(R.layout.message_custom, parent, false);

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

        holder.chatSendersName.setText(current.informationChatSendersName);
        holder.chatMessageToSend.setText(current.informationChatTextMessage);
       holder.chatTimeOfMessage.setText(current.informationChattimeOfChat);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String tempUserName = preferences.getString("a", "");

        if (current.informationChatSendersName.equals(tempUserName)) {
            holder.chatSendersName.setTextColor(Color.RED);
            RelativeLayout.LayoutParams params1 = new
                    RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

            final float scale = context.getResources().getDisplayMetrics().density;
            int pixel = (int) (50 * scale + 0.5f);
            int marginleft =(int) (10 * scale + 0.5f);
            int marginImage =(int) (5 * scale + 0.5f);
            params1.width =pixel;
            params1.height=pixel;
            params1.setMargins(marginImage, marginImage, marginImage, marginImage);

            RelativeLayout.LayoutParams params2 = new
                    RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params2.addRule(RelativeLayout.LEFT_OF, holder.chatUserPicture.getId());
            params2.setMargins(0, 0, marginleft, 0);  // left, top, right, bottom

            RelativeLayout.LayoutParams params3 = new
                    RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params3.addRule(RelativeLayout.LEFT_OF, holder.chatUserPicture.getId());
            params3.addRule(RelativeLayout.BELOW,holder.chatSendersName.getId());
            params3.setMargins(0, 0, marginleft, 0);  // left, top, right, bottom


            RelativeLayout.LayoutParams params4 = new
                    RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);


            holder.chatUserPicture.setLayoutParams(params1);
            holder.chatSendersName.setLayoutParams(params2);
            holder.chatMessageToSend.setLayoutParams(params3);
            holder.card.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        } else {
            holder.chatSendersName.setTextColor(Color.BLUE);

        }
    }

    public void notifyListChanges() {
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