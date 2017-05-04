package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Information.InformationMessageActivity;
import com.example.thearbiter.thriftbooksnepal.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Gaurav Jayasawal on 1/18/2017.
 */

public class MessagerAdapter extends RecyclerView.Adapter<MessagerAdapter.MyViewHolder> {
    public static List<InformationMessageActivity> data = Collections.emptyList();
    Context context;
    private LayoutInflater layoutInflater;

    public MessagerAdapter(Context context, List<InformationMessageActivity> data) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MessagerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.comment_custom, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public void notifySetChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MessagerAdapter.MyViewHolder holder, int position) {
        final InformationMessageActivity current = data.get(position);
        holder.text.setText(current.textMessage);
        holder.sendersName.setText(current.sendersName);
        holder.timeOfNotif.setText(current.timeOfNotification);
        Log.d("user","Name"+current.sendersUsername);
        Picasso.with(context).load("http://aadeshrana.esy.es/" +current.sendersUsername+ "ProfilePic.jpg").fit().centerCrop().placeholder(R.drawable.default_user).into(holder.messageImageProfilePicture);

        if (current.textMessage.equals("null")) {
            holder.text.setVisibility(View.INVISIBLE);
            holder.sendersName.setVisibility(View.INVISIBLE);
            holder.noNotifs.setText("BE THE FIRST ONE TO ENQUIRE");
            holder.imageCard.setVisibility(View.INVISIBLE);
            holder.messageImageProfilePicture.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        TextView sendersName;
        ImageView messageImageProfilePicture;
        TextView noNotifs;
        TextView timeOfNotif;
        CardView imageCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageCard = (CardView)itemView.findViewById(R.id.messageUserPictureCard);
            timeOfNotif = (TextView) itemView.findViewById(R.id.timeOfNotif);
            noNotifs = (TextView) itemView.findViewById(R.id.noNotificationsText);
            sendersName = (TextView) itemView.findViewById(R.id.messageCustomLayoutNameOfSender);
            messageImageProfilePicture = (ImageView) itemView.findViewById(R.id.messageUserPicture);
            text = (TextView) itemView.findViewById(R.id.messageCustomLayoutTextToSend);
        }
    }
}

