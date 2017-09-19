package com.example.cpu11341_local.talktvhome.fragment;

import android.app.Activity;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.example.cpu11341_local.talktvhome.ElapsedTime;
import com.example.cpu11341_local.talktvhome.MessageActivity;
import com.example.cpu11341_local.talktvhome.MessageDataManager;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.adapter.MessageDetailRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.data.User;
import com.example.cpu11341_local.talktvhome.myview.TalkTextView;

import java.text.ParseException;
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
    TalkTextView talkTextViewSend;
    MessageDataManager.DataListener dataListener;
    ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();

    public ChatFragment(String toolbarTitle, int senderID) {
        this.toolbarTitle = toolbarTitle;
        this.senderID = senderID;
    }

    public int getSenderID() {
        return senderID;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataListener = new MessageDataManager.DataListener() {
            @Override
            public void onDataChanged(Topic topic) {
                ChatFragment chatFragment = (ChatFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (chatFragment.getSenderID() == topic.getUserId()) {
                    topic.setHasNewMessage(false);
                    arrMessDetail.clear();
                    arrMessDetail.addAll(MessageDataManager.getInstance().getListMessage(topic.getUserId(), getContext()));
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };
        MessageDataManager.getInstance().setDataListener(dataListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment,container,false);

        talkTextViewSend = (TalkTextView) view.findViewById(R.id.talkTextViewSend);
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

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    talkTextViewSend.setVisibility(View.VISIBLE);
                } else {
                    talkTextViewSend.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        talkTextViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MessageDetail messageDetail = null;
                messageDetail = new MessageDetail(4, 1, MessageDataManager.getInstance().getUser(senderID, getContext()),
                        Calendar.getInstance().getTimeInMillis(), editText.getText().toString(), false);
                MessageDataManager.getInstance().insertMessage(messageDetail, getContext());
                editText.setText("");
                messDetailRecyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
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

                if (getActivity() instanceof MessageActivity){
                    if (getActivity().getSupportFragmentManager().getBackStackEntryCount() != 0){
                        MessageFragment messageFragment =  (MessageFragment) getFragmentManager().findFragmentByTag("MessFrag");
                        messageFragment.onResume();
                    }
                }
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

        arrMessDetail = MessageDataManager.getInstance().getListMessage(senderID, getContext());
        adapter = new MessageDetailRecyclerAdapter(getContext(), arrMessDetail);

        layoutManager = new LinearLayoutManager(getContext());
        messDetailRecyclerView.setLayoutManager(layoutManager);
        messDetailRecyclerView.setAdapter(adapter);
        messDetailRecyclerView.scrollToPosition(adapter.getItemCount()-1);
        return view;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        arrMessDetail.clear();
        arrMessDetail.addAll(MessageDataManager.getInstance().getListMessage(senderID, getContext()));
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        MessageDataManager.getInstance().setDataListener(new MessageDataManager.DataListener() {
            @Override
            public void onDataChanged(Topic topic) {
                ChatFragment chatFragment = (ChatFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (chatFragment.getSenderID() == topic.getUserId()) {
                    topic.setHasNewMessage(false);
                    arrMessDetail.clear();
                    arrMessDetail.addAll(MessageDataManager.getInstance().getListMessage(topic.getUserId(), getContext()));
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}