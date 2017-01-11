package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.Information.InformationFindSchool;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aadesh Rana on 11-01-17.
 */
public class AdapterFindSchool extends RecyclerView.Adapter<AdapterFindSchool.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<InformationFindSchool> data = Collections.emptyList();

    public AdapterFindSchool(Context context, List<InformationFindSchool> data){
        this.context =context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_find_school,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        InformationFindSchool current = data.get(position);
        holder.title.setText(current.collegeName);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Toast.makeText(context, "You chose 0", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public CardView cardview;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.findSchoolTitle);
            cardview = (CardView)itemView.findViewById(R.id.cardViewfindSchool);
        }
    }
}
