package com.example.cpu11341_local.talktvhome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.adapter.MessageRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.data.Topic;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/1/2017.
 */

public class MessageFragment extends android.support.v4.app.Fragment {

    RecyclerView messRecyclerView;
    MessageRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    TextView mTitle;
    String toolbarTitle;
    ArrayList<Topic> arrTopic = new ArrayList<>();
    int userId;

    public MessageFragment(String toolbarTitle, int userId) {
        this.toolbarTitle = toolbarTitle;
        this.userId = userId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrTopic.add( new Topic("https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV", "Tin nhắn cuối cùng", "Hôm qua", 1));
        arrTopic.add( new Topic("http://i.imgur.com/xFdNVDs.png", "Tin nhắn", "Tên người dùng: tin nhắn cuối cùng", "Hôm qua", 2));
        arrTopic.add( new Topic("http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi", "Tin nhắn cuối cùng", "Hôm qua", 3));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment,container,false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        mTitle.setText(toolbarTitle);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        messRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMessage);
        adapter = new MessageRecyclerAdapter(getContext(), arrTopic);

        layoutManager = new LinearLayoutManager(getContext());
        messRecyclerView.setLayoutManager(layoutManager);
        messRecyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new MessageRecyclerAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {

                switch (arrTopic.get(position).getAction_type()){
                    case 1:
                    case 3:
                        ChatFragment chatFragment = new ChatFragment(arrTopic.get(position).getName(), userId, arrTopic.get(position).getAction_type());
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, chatFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        MessageFragment messageFragment = new MessageFragment("Tin nhắn chưa theo dõi", userId);
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, messageFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }
        });
        return view;
    }
}
