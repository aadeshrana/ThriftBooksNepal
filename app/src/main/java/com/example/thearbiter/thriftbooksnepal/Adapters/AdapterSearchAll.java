package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Activities.FinalBuyersActivity;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentALevelBuy;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentMessager;
import com.example.thearbiter.thriftbooksnepal.Information.InformationAllData;
import com.example.thearbiter.thriftbooksnepal.Information.InformationBuyerRecycler;
import com.example.thearbiter.thriftbooksnepal.Information.InformationFindSchool;
import com.example.thearbiter.thriftbooksnepal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Aadesh Rana on 01-05-17.
 */

public class AdapterSearchAll extends RecyclerView.Adapter<AdapterSearchAll.MyViewHolder>{
    Context context;
    List<InformationAllData> data = Collections.emptyList();
    LayoutInflater inflater;
    List<InformationAllData> filerlist= Collections.emptyList();
    public  AdapterSearchAll(){

    }
    public AdapterSearchAll(Context context,List<InformationAllData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

        this.filerlist = new ArrayList<InformationAllData>();
        //this.filerlist.addAll(this.data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.temp_custom_recyler, parent, false);
        AdapterSearchAll.MyViewHolder holder = new AdapterSearchAll.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       final InformationAllData current = filerlist.get(position);
      //  final int choice[] = new int[data.size()];
        holder.priceOfBook.setText(current.priceOfBook);
        holder.sellerName.setText(current.sellerName);
        holder.title.setText(current.title);
        holder.authorName.setText(current.authors);
       Picasso.with(context).load("http://aadeshrana.esy.es/" + current.firstBookList).fit().placeholder(R.drawable.noimageplaceholder).into(holder.imgOfBook);

        holder.cardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMessager.finalBuyersActivityNameOfBook = current.title;
                FragmentMessager.finalBuyersActivityPriceOfBook = current.priceOfBook;
                FragmentMessager.finalBuyersActivityNameOfSeller = current.sellerName;
                FragmentMessager.finalBuyersActivityNameOfAuthor = current.authors;
                FragmentMessager.finalBuyersActivityImage1 = current.image1;
                FragmentMessager.finalBuyersActivityImage2 = current.image2;
                FragmentMessager.finalBuyersActivityImage3 = current.image3;
                FragmentMessager.finalBuyersActivityUsernameOfSeller = current.username;
                Intent in = new Intent(context, FinalBuyersActivity.class);
                context.startActivity(in);
            }
        });
    }


    @Override
    public int getItemCount() {
        return (null != filerlist ? filerlist.size() : 0);
    }

    public void filter(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                filerlist.clear();
                Log.d("ss", "ss" + text);

                if (TextUtils.isEmpty(text)) {


                    filerlist.clear();


                } else {
                    for (InformationAllData information : data) {

                        if (information.title.toLowerCase().contains(text.toLowerCase()) || information.authors.toLowerCase().contains(text.toLowerCase()) || information.sellerName.toLowerCase().contains(text.toLowerCase())|| information.sellerName.toLowerCase().contains(text.toLowerCase())  ) {

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







    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOfBook;
        TextView title;
        CardView cardMain;
        TextView priceOfBook;
        TextView sellerName;
        TextView authorName;

        public MyViewHolder(View itemView) {
        super(itemView);
        cardMain = (CardView) itemView.findViewById(R.id.adapterFirstPageCardViewMain);
        imgOfBook = (ImageView) itemView.findViewById(R.id.imageOfBookForALevelBuyer);
        title = (TextView) itemView.findViewById(R.id.titleOfBookForALevelBuyer);
        priceOfBook = (TextView) itemView.findViewById(R.id.priceOfBookForALevelBuyer);
        sellerName = (TextView) itemView.findViewById(R.id.nameOfSellerForBuyerCustomALevel);
            authorName=(TextView)itemView.findViewById(R.id.nameOfAuthor);
    }
}
}
