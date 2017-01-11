package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Activities.SignUp;
import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchool;
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InformationFindSchool current = data.get(position);
        holder.title.setText(current.collegeName);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < data.size(); i++) {
                    if (position == i) {

                        SignUp obj = new SignUp();
                        obj.getSchool(current.collegeName);
                        CustomDiagFindSchool obj2 = new CustomDiagFindSchool();

                        holder.cardview.setCardBackgroundColor(Color.GREEN);
break;


                    }
                    else {
                        holder.cardview.setCardBackgroundColor(Color.RED);
                        break;
                    }
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
