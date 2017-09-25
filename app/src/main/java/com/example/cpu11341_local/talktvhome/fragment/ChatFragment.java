package com.example.cpu11341_local.talktvhome.fragment;

import android.app.Activity;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.cpu11341_local.talktvhome.DatabaseHelper;
import com.example.cpu11341_local.talktvhome.MessageActivity;
import com.example.cpu11341_local.talktvhome.MessageDataManager;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.adapter.MessageDetailRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.myview.TalkTextView;

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
    int scrollTimes = 0;
    boolean isAllMsg = false;

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
        arrMessDetail.clear();
        arrMessDetail.addAll(MessageDataManager.getInstance().getListMessageFromDB(senderID, getContext(), scrollTimes));
//        new MyTask().execute();
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

        layoutManager = new LinearLayoutManager(getContext());
        messDetailRecyclerView.setLayoutManager(layoutManager);
        adapter = new MessageDetailRecyclerAdapter(getContext(), arrMessDetail, messDetailRecyclerView);
        messDetailRecyclerView.scrollToPosition(adapter.getItemCount()-1);
        //set load more listener for the RecyclerView adapter
        adapter.setOnLoadMoreListener(new MessageDetailRecyclerAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isAllMsg){
                    arrMessDetail.add(0, null);
                    adapter.notifyItemInserted(0);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("RunHandler", "Load data");
                            arrMessDetail.remove(0);
                            adapter.notifyItemRemoved(0);
                            //Generating more data
                            scrollTimes++;
                            ArrayList<MessageDetail> arrNewMsgDetail = MessageDataManager.getInstance().getListMessageFromDB(senderID, getContext(), scrollTimes);
                            if (arrNewMsgDetail.size() < 30){
                                isAllMsg = true;
                            }
                            arrMessDetail.addAll(0, arrNewMsgDetail);
                            adapter.notifyItemRangeInserted(0, arrNewMsgDetail.size());
                            adapter.setLoaded();
                        }
                    }, 1000);
                } else {
                    Toast.makeText(getContext(), "Đã load hết tin nhắn", Toast.LENGTH_LONG).show();
                }

            }
        });
        messDetailRecyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new MessageDetailRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                TextView textViewDateTime = (TextView) view.findViewById(R.id.textViewDateTime);
                TextView textViewText = (TextView) view.findViewById(R.id.textViewMessDetail);
                if (textViewDateTime.getVisibility() == View.VISIBLE){
                    Animation showOff = AnimationUtils.loadAnimation(getContext(), R.anim.show_off);
                    textViewDateTime.setVisibility(View.GONE);
                    textViewDateTime.startAnimation(showOff);
                } else {
                    Animation showUp = AnimationUtils.loadAnimation(getContext(), R.anim.show_up);
                    Animation slide_down = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
                    textViewDateTime.setVisibility(View.VISIBLE);
                    textViewDateTime.startAnimation(showUp);
                    textViewText.startAnimation(slide_down);
                }
            }
        });
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
        Log.i("ArrMsgDetailSize", String.valueOf(MessageDataManager.getInstance().getListMessage(senderID, getContext()).size()));
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
                    DatabaseHelper.getInstance(getContext()).updateTopic(topic);
                    arrMessDetail.clear();
                    arrMessDetail.addAll(MessageDataManager.getInstance().getListMessage(topic.getUserId(), getContext()));
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                        messDetailRecyclerView.smoothScrollToPosition(arrMessDetail.size());
                    }
                }
            }
        });
    }
}