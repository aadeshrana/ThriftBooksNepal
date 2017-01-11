package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final InformationNavMenu current = data.get(position);
       holder.title.setText(current.name);
        Picasso.with(context).load(current.iconId).fit().centerCrop().into(holder.iconId);


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

