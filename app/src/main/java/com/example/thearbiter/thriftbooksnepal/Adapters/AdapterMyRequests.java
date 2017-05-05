package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thearbiter.thriftbooksnepal.Information.InformationCheckRequests;
import com.example.thearbiter.thriftbooksnepal.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aadesh Rana on 21-01-17.
 */

public class AdapterMyRequests extends RecyclerView.Adapter<AdapterMyRequests.MyViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<InformationCheckRequests> data = Collections.emptyList();

    public AdapterMyRequests(Context context, List<InformationCheckRequests> data) {
        Log.d("json77",":");
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.my_requests_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final InformationCheckRequests current = data.get(position);

            holder.bookName.setText(current.infoBookname);
            holder.authorName.setText(current.infoAuthorname);
            holder.username.setText(current.infoUsername);

        Log.d("value","bookname15"+current.infoBookname);

        Picasso.with(context).load("http://aadeshrana.esy.es/" + current.infoUsername+"ProfilePic.jpg").fit().centerCrop().placeholder(R.drawable.noimageplaceholder).into(holder.imgOfPerson);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOfPerson;
        TextView username;
        TextView bookName;
        TextView authorName;

        public MyViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.requestPageUsername);
            bookName = (TextView) itemView.findViewById(R.id.requestPageBookName);
            authorName = (TextView) itemView.findViewById(R.id.requestNameOfAuthor);
            imgOfPerson = (ImageView) itemView.findViewById(R.id.imageOfRequestFragment);
        }
    }
}
