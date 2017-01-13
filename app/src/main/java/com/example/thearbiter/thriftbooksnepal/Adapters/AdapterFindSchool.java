package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Activities.SignUp;
import com.example.thearbiter.thriftbooksnepal.CustomDiagFindSchool;
import com.example.thearbiter.thriftbooksnepal.Information.InformationFindSchool;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Aadesh Rana on 11-01-17.
 */
public class AdapterFindSchool extends RecyclerView.Adapter<AdapterFindSchool.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<InformationFindSchool> data = Collections.emptyList();
    public static int selected = -1;
    public static  int count =0;
    List<InformationFindSchool> filerlist;
    public AdapterFindSchool(Context context, List<InformationFindSchool> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

        this.filerlist = new ArrayList<InformationFindSchool>();
        this.filerlist.addAll(this.data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_find_school, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final InformationFindSchool current = filerlist.get(position);
        final int choice[] = new int[data.size()];
        holder.title.setText(current.collegeName);
        holder.cardview.setCardBackgroundColor(Color.WHITE);

        if (selected == position) {

            holder.cardview.setCardBackgroundColor(Color.GREEN);

        } else
            holder.cardview.setCardBackgroundColor(Color.WHITE);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = position;
                notifyDataSetChanged();

                for (int i = 0; i < data.size(); i++) {
                    if (position == i) {

                        SignUp obj = new SignUp();
                        obj.getSchool(current.collegeName);
                        CustomDiagFindSchool obj2 = new CustomDiagFindSchool();


                        holder.cardview.setCardBackgroundColor(Color.WHITE);
                        if (selected == position) {

                            holder.cardview.setCardBackgroundColor(Color.GREEN);
                            Log.d("what", "" + choice[i]);
                        } else
                            holder.cardview.setCardBackgroundColor(Color.BLACK);

                    }


                }
            }
        });

    }


    public void filter(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                filerlist.clear();
                Log.d("ss", "ss" + text);

                if (TextUtils.isEmpty(text)) {


                    filerlist.addAll(data);


                } else {
                    for (InformationFindSchool information : data) {

                        if (information.collegeName.toLowerCase().contains(text.toLowerCase())) {
                            Log.d("ss1", "ss =" + information.collegeName);
                            filerlist.add(information);
                        }
                    }
                }

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        notifyDataSetChanged();
                    }
                });
            }


        }).start();

    }

    @Override
    public int getItemCount() {
        return (null != filerlist ? filerlist.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CardView cardview;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.findSchoolTitle);
            cardview = (CardView) itemView.findViewById(R.id.cardViewfindSchool);
        }
    }
}
