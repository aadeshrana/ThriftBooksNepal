package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.Information.InformationCheckMessages;
import com.example.thearbiter.thriftbooksnepal.Information.infotest;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aadesh Rana on 17-04-17.
 */

public class AdapterCheckMessages extends RecyclerView.Adapter<AdapterFindSchool.MyViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<InformationCheckMessages> data = Collections.emptyList();

    public AdapterCheckMessages(Context context, List<InformationCheckMessages> data){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public AdapterFindSchool.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(AdapterFindSchool.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
