package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.app.Activity;
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

import com.example.thearbiter.thriftbooksnepal.Activities.AboutUs;
import com.example.thearbiter.thriftbooksnepal.Activities.Accounts;
import com.example.thearbiter.thriftbooksnepal.Activities.ActivitySeller;
import com.example.thearbiter.thriftbooksnepal.Activities.MainALevelBuyer;
import com.example.thearbiter.thriftbooksnepal.Activities.MainIbBuyer;
import com.example.thearbiter.thriftbooksnepal.Activities.MainPlusTwoBuyer;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomDiagLogin;
import com.example.thearbiter.thriftbooksnepal.Information.InformationNavMenu;
import com.example.thearbiter.thriftbooksnepal.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aadesh Rana on 11-01-17.
 */

public class AdapterNavMenu extends RecyclerView.Adapter<AdapterNavMenu.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<InformationNavMenu> data = Collections.emptyList();
    String checklog;

    public AdapterNavMenu(Context context, List<InformationNavMenu> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_menu_items_drawer, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final InformationNavMenu current = data.get(position);
        holder.title.setText(current.name);
        Picasso.with(context).load(current.iconId).fit().centerCrop().placeholder(R.drawable.noimageplaceholder).into(holder.iconId);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        intent = new Intent(context, MainALevelBuyer.class);
                        intent.putExtra("chosenValueBoard", "alevel");
                        context.startActivity(intent);
                        ActivitySeller.choiseOfBoard = "alevel";
                        break;

                    case 1:

                        intent = new Intent(context, MainIbBuyer.class);
                        ActivitySeller.choiseOfBoard = "ib";
                        intent.putExtra("chosenValueBoard", "ib");
                        context.startActivity(intent);
                        break;

                    case 2:
                        ActivitySeller.choiseOfBoard = "plustwo";
                        intent = new Intent(context, MainPlusTwoBuyer.class);
                        context.startActivity(intent);
                        break;
                    case 3:

                        SharedPreferences sharedpref;
                        sharedpref = PreferenceManager.getDefaultSharedPreferences(context);
                        checklog = sharedpref.getString("username", "");

                        if (checklog.equals("")) {
                            try {
                                checklog = FragmentCustomDiagLogin.username;

                            } catch (Exception e) {

                            }
                            if (checklog == null) {
                                checklog = "";
                            }
                        }
                        Log.d("valueof2", ":" + checklog);
                        if (checklog.equals("")) {
                            FragmentCustomDiagLogin fragmentCustomDiagLogin = new FragmentCustomDiagLogin();
                            fragmentCustomDiagLogin.show(((Activity) context).getFragmentManager(), "cde");
                        } else {
                            intent = new Intent(context, ActivitySeller.class);
                            intent.putExtra("chosenValueBoard", "ib");
                            context.startActivity(intent);
                        }
                        break;
                    case 4:
                        sharedpref = PreferenceManager.getDefaultSharedPreferences(context);
                        checklog = sharedpref.getString("username", "");

                        if (checklog.equals("")) {
                            try {
                                checklog = FragmentCustomDiagLogin.username;

                            } catch (Exception e) {

                            }
                            if (checklog == null) {
                                checklog = "";
                            }
                        }
                        Log.d("valueof2", ":" + checklog);
                        if (checklog.equals("")) {
                            FragmentCustomDiagLogin fragmentCustomDiagLogin = new FragmentCustomDiagLogin();
                            fragmentCustomDiagLogin.show(((Activity) context).getFragmentManager(), "cde");
                        } else {
                            intent = new Intent(context, Accounts.class);
                            intent.putExtra("chosenValueBoard", "ib");
                            context.startActivity(intent);
                        }
                        break;

                    case 5:
                        intent = new Intent(context, AboutUs.class);
                        context.startActivity(intent);
                        break;


                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView iconId;
        public CardView cardview;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textCustomMain);
            iconId = (ImageView) itemView.findViewById(R.id.imageCustomLayoutMain);
            cardview = (CardView) itemView.findViewById(R.id.cardviewmain);
        }
    }
}