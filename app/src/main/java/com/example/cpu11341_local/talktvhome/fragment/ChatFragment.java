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
import com.example.cpu11341_local.talktvhome.adapter.MessageDetailRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class ChatFragment extends android.support.v4.app.Fragment {

    RecyclerView messDetailRecyclerView;
    MessageDetailRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    TextView mTitle;
    String toolbarTitle;
    int userId;
    int action_type;
    ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();

    public ChatFragment(String toolbarTitle, int userId, int action_type) {
        this.toolbarTitle = toolbarTitle;
        this.userId = userId;
        this.action_type = action_type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrMessDetail.add(new MessageDetail("Tên event 1", "17/08/25 09:47", "http://talktv.vcdn.vn/talk/mobile/banner/ad_banner_75.jpg", "Mô tả", "Xem chi tiết", 1, "action_extra"));
        arrMessDetail.add(new MessageDetail("Tên event 2", "18/08/25 10:47", "http://talktv.vcdn.vn/talk/mobile/banner/ad_banner_47.jpg", "Mô tả", "Xem chi tiết", 1, "action_extra"));
        arrMessDetail.add(new MessageDetail("Tên event 3", "19/08/25 11:47", "http://talktv.vcdn.vn/talk/mobile/banner/ad_banner_69.jpg", "Mô tả", "Xem chi tiết", 1, "action_extra"));
        arrMessDetail.add(new MessageDetail("Tên event 4", "20/08/25 12:47", "http://talktv.vcdn.vn/talk/mobile/banner/ad_banner_88.jpg", "Mô tả", "Xem chi tiết", 1, "action_extra"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment,container,false);

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

        messDetailRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMessDetail);
        adapter = new MessageDetailRecyclerAdapter(getContext(), arrMessDetail);

        layoutManager = new LinearLayoutManager(getContext());
        messDetailRecyclerView.setLayoutManager(layoutManager);
        messDetailRecyclerView.setAdapter(adapter);

        return view;
    }
}