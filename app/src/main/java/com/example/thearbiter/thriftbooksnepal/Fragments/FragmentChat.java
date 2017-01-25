package com.example.thearbiter.thriftbooksnepal.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.thearbiter.thriftbooksnepal.Adapters.ChatAdapter;
import com.example.thearbiter.thriftbooksnepal.Information.InformationChatActivity;
import com.example.thearbiter.thriftbooksnepal.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentChat extends Fragment {
    public String title[] = {"dosa1", "dosa2", "dosa3", "dosa4", "dosa5"};
    public String description[] = {"dsdf", "dosa2", "dosa3", "dosa4", "dosa5"};
    public String date[] = {"dsdf", "dosa2", "dosa3", "dosa4", "dosa5"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_activity, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chatActivityRecyclerVie);
        ChatAdapter adapter = new ChatAdapter(getActivity(), getdata());
        Log.d("asdfasdfasdf", "" + adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return view;
    }

    public List<InformationChatActivity> getdata() {
        List<InformationChatActivity> data = new ArrayList<>();
        for (int j = 0; j < title.length; j++) {
            InformationChatActivity current = new InformationChatActivity();
            current.informationChatSendersName = title[j];
            current.informationChatTextMessage = description[j];
            current.informationChattimeOfChat = date[j];
            data.add(current);
        }
        return data;
    }
}

