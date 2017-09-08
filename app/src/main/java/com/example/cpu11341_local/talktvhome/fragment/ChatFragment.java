package com.example.cpu11341_local.talktvhome.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.adapter.MessageDetailRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.User;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class ChatFragment extends Fragment {

    RecyclerView messDetailRecyclerView;
    MessageDetailRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    TextView mTitle;
    EditText editText;
    String toolbarTitle;
    int senderID;
    ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();

    public ChatFragment(String toolbarTitle, int senderID) {
        this.toolbarTitle = toolbarTitle;
        this.senderID = senderID;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrMessDetail.add(new MessageDetail(1, 1, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV", true),
                "Tên event 1", "17/08/25 09:47:02", "http://talktv.vcdn.vn/talk/mobile/banner/ad_banner_75.jpg", "Mô tả", 1, "Xem chi tiết", "action_extra"));
        arrMessDetail.add(new MessageDetail(2, 2, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV", true),
                "Nhắc nhở 1", "17/09/25 09:47:02", "Nội dung nhắc nhở", 1, "Xem chi tiết", "action_extra"));
        arrMessDetail.add(new MessageDetail(3, 3, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV", true),
                "18/08/25 10:47:03", "Nội dung thông báo"));

        arrMessDetail.add(new MessageDetail(3, 1, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi" , true),
                "18/08/25 10:47:03", "Xin chào"));
        arrMessDetail.add(new MessageDetail(3, 2, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi" , true),
                "18/08/25 11:47:04", "Tôi là Thúy Chi"));

        arrMessDetail.add(new MessageDetail(3, 1, new User(2, "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady" , false),
                "19/08/25 12:47:05", "Tôi là Trang Lady"));
    }

    ArrayList<MessageDetail> getMessageDetail(int senderID){
        ArrayList<MessageDetail> arrMessageDetailOfSender = new ArrayList<>();
        for (MessageDetail messageDetail : arrMessDetail){
            if (messageDetail.getUser().getId() == senderID){
                arrMessageDetailOfSender.add(messageDetail);
            }
        }
        return arrMessageDetailOfSender;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment,container,false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        editText = (EditText) view.findViewById(R.id.editText);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

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
        messDetailRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        adapter = new MessageDetailRecyclerAdapter(getContext(), getMessageDetail(senderID));

        layoutManager = new LinearLayoutManager(getContext());
        messDetailRecyclerView.setLayoutManager(layoutManager);
        messDetailRecyclerView.setAdapter(adapter);
        messDetailRecyclerView.scrollToPosition(messDetailRecyclerView.getAdapter().getItemCount()-1);
        return view;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}