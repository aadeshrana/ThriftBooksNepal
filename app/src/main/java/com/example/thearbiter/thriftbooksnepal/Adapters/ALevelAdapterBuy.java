package com.example.thearbiter.thriftbooksnepal.Adapters;

/**
 * Created by Gaurav Jayasawal on 1/9/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ALevelAdapterBuy extends RecyclerView.Adapter<ALevelAdapterBuy.MyViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    List<InformationBuyerRecycler> data = Collections.emptyList();

    public ALevelAdapterBuy(Context context, List<InformationBuyerRecycler> data) {
        Log.d("LOG", "" + context);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_item_buyer, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final InformationBuyerRecycler current = data.get(position);
        holder.title.setText(current.title);
        holder.priceOfBook.setText(current.priceOfBook);
        holder.sellerName.setText(current.sellerName);

        Picasso.with(context).load("http://aadeshrana.esy.es/" + current.firstBookList).fit().placeholder(R.drawable.noimageplaceholder).into(holder.imgOfBook);
        Log.d("what is     ","http://aadeshrana.esy.es/" + current.firstBookList);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOfBook;
        TextView title;
        TextView priceOfBook;
        TextView sellerName;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgOfBook = (ImageView) itemView.findViewById(R.id.imageOfBookForALevelBuyer);
            title = (TextView) itemView.findViewById(R.id.titleOfBookForALevelBuyer);
            priceOfBook = (TextView) itemView.findViewById(R.id.priceOfBookForALevelBuyer);
            sellerName = (TextView) itemView.findViewById(R.id.nameOfSellerForBuyerCustomALevel);
        }
    }
}