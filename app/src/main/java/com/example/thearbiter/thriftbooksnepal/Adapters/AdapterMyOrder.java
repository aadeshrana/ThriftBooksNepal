package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Activities.Notifications;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.Information.infotest;
import com.example.thearbiter.thriftbooksnepal.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aadesh Rana on 21-01-17.
 */

public class AdapterMyOrder extends RecyclerView.Adapter<AdapterMyOrder.MyViewHolder>{
    Context context;
    LayoutInflater layoutInflater;
    List<infotest> data = Collections.emptyList();

    public AdapterMyOrder(Context context, List<infotest> data) {
        Log.d("LOG", "" + context);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.my_order_layout, parent, false);
        Log.d("hellooo","s"+Notifications.positionOfView);
     MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final infotest current = data.get(position);

        if(Notifications.positionOfView==0){
            holder.content.setText("Has Commented On Your Post!");
        }
        else
        holder.content.setText("Has Requested a new book!");
        holder.username.setText(current.title);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOfBook;
        TextView username;
        TextView content;
        TextView sellerName;

        public MyViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.userName);
            content =(TextView)itemView.findViewById(R.id.contentMyOrder);
        }
    }
}
