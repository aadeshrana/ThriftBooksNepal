package com.example.thearbiter.thriftbooksnepal.Adapters;

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
import android.widget.Toast;

import com.example.thearbiter.thriftbooksnepal.Activities.ChatMainActivity;
import com.example.thearbiter.thriftbooksnepal.Fragments.FragmentCustomDiagLogin;
import com.example.thearbiter.thriftbooksnepal.Information.InformationCheckRequests;
import com.example.thearbiter.thriftbooksnepal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aadesh Rana on 21-01-17.
 */

public class AdapterMyRequests extends RecyclerView.Adapter<AdapterMyRequests.MyViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<InformationCheckRequests> data = Collections.emptyList();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    public static String newRoomName;


    public AdapterMyRequests(Context context, List<InformationCheckRequests> data) {
        Log.d("json77", ":");
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

        Log.d("value", "bookname15" + current.infoBookname);

        Picasso.with(context).load("http://aadeshrana.esy.es/" + current.infoUsername + "ProfilePic.jpg").fit().centerCrop().placeholder(R.drawable.noimageplaceholder).into(holder.imgOfPerson);

        holder.requestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Map<String, Object> map = new HashMap<>();
                try {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    String userNameOfUser = preferences.getString("a", "");
                    if (userNameOfUser.equals("")) {
                        userNameOfUser = FragmentCustomDiagLogin.username;
                    }

                    String nameOfUser = preferences.getString("firstNameSharePref1", "");
                    if (nameOfUser.equals("")) {
                        nameOfUser = FragmentCustomDiagLogin.firstName;
                    }

                    newRoomName = userNameOfUser + "***" + current.infoUsername + "||" + current.infoName + "---" + nameOfUser;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                map.put(newRoomName, "");

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(newRoomName)) {
                            Toast.makeText(context, "Already there", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Not present NEWW", Toast.LENGTH_SHORT).show();
//                            new CreateRoom().execute();
                            root.updateChildren(map);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //THIS CREATES TABLE TO STORE IN OUR DATABASE
                Intent intent = new Intent(context, ChatMainActivity.class);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String username = preferences.getString("a", "");
                if (username.equals("")) {
                    username = FragmentCustomDiagLogin.username;
                }
                String nameOfUser = preferences.getString("firstNameSharePref1", "");
                if (nameOfUser.equals("")) {
                    nameOfUser = FragmentCustomDiagLogin.firstName;
                }

                String stringToSendInIntent = username + "***" + current.infoUsername + "||" + current.infoName + "---" + nameOfUser;
                Log.d("room", "fromIntent " + stringToSendInIntent);

                intent.putExtra("room_name", stringToSendInIntent);
                intent.putExtra("user_name", current.infoUsername);

                context.startActivity(intent);
            }

        });

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
        CardView requestCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.requestPageUsername);
            bookName = (TextView) itemView.findViewById(R.id.requestPageBookName);
            authorName = (TextView) itemView.findViewById(R.id.requestNameOfAuthor);
            imgOfPerson = (ImageView) itemView.findViewById(R.id.imageOfRequestFragment);
            requestCard = (CardView) itemView.findViewById(R.id.adapterRequestCardViewMain);
        }
    }
}
