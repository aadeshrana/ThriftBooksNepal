package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.R;

/**
 * Created by Aadesh Rana on 25-01-17.
 */

public class AdapterMyOrders extends RecyclerView.Adapter<AdapterMyOrders.MyViewHolder> {

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
