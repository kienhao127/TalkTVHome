package com.example.cpu11341_local.talktvhome.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.cpu11341_local.talktvhome.MessageActivity;
import com.example.cpu11341_local.talktvhome.MessageDataManager;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.adapter.MessageDetailRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.data.Wrapper;
import com.example.cpu11341_local.talktvhome.myview.TalkTextView;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class ChatFragment extends Fragment {

    RecyclerView messDetailRecyclerView;
    MessageDetailRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Topic currentTopic;
    Toolbar toolbar;
    TextView mTitle;
    EditText editText;
    String toolbarTitle;
    String topicID, userID;
    ImageView imageViewSend;
    ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();
    TalkTextView textViewLoading;
    TalkTextView textViewOver;
    TalkTextView selectedMsgDetail;
    int scrollTimes = 0;
    boolean isAllMsg = false, isResume = false;
    RelativeLayout relativeLayoutContextMenu;
    int selectedPosition;
    Button btnCopy, btnDelete;
    Topic topic = new Topic();
    RelativeLayout relativeLayoutFollowNoti;
    TalkTextView textViewFollow;

    public ChatFragment(Topic topic) {
        this.toolbarTitle = topic.getName();
        this.topicID = topic.getTopicID();
        this.userID = MessageDataManager.getInstance().splitTopicID(topicID)[0];
        this.topic = topic;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.chat_fragment, container, false);
        textViewLoading = (TalkTextView) view.findViewById(R.id.textViewLoading);
        textViewOver = (TalkTextView) view.findViewById(R.id.textViewOver);
        imageViewSend = (ImageView) view.findViewById(R.id.imageViewSend);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        editText = (EditText) view.findViewById(R.id.editText);
        relativeLayoutContextMenu = (RelativeLayout) view.findViewById(R.id.contextMenu);
        btnCopy = (Button) view.findViewById(R.id.btnCopy);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        selectedMsgDetail = (TalkTextView) view.findViewById(R.id.seletedMsgDetail);
        relativeLayoutFollowNoti = (RelativeLayout) view.findViewById(R.id.relativeLayoutFollowNoti);
        textViewFollow = (TalkTextView) view.findViewById(R.id.textViewFollow);

        if (!MessageDataManager.getInstance().isFollow(topicID)) {
            relativeLayoutFollowNoti.setVisibility(View.VISIBLE);
            textViewFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] strSplited = MessageDataManager.getInstance().splitTopicID(topicID);
                    MessageDataManager.getInstance().followIdol(strSplited[0], strSplited[1], getContext());
                    relativeLayoutFollowNoti.setVisibility(View.GONE);
                }
            });
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && !("".equals(s.toString().trim()))) {
                    imageViewSend.setVisibility(View.VISIBLE);
                } else {
                    imageViewSend.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDetail messageDetail = null;
                messageDetail = new MessageDetail(4, MessageDataManager.getInstance().getCurrentUser(getContext()),
                        Calendar.getInstance().getTimeInMillis(), editText.getText().toString(), false);
                messageDetail.setTopicID(topicID);
                Wrapper wrapper = new Wrapper(MessageDataManager.getInstance().insertMessage(messageDetail, getContext()), messageDetail);
                if (MessageDataManager.getInstance().dataListener != null){
                    MessageDataManager.getInstance().dataListener.onDataChanged(wrapper.getTopic(), wrapper.getMessageDetail());
                }
                editText.setText("");
            }
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        mTitle.setText(toolbarTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                getActivity().onBackPressed();
                if (getActivity() instanceof MessageActivity) {
                    if (getActivity().getSupportFragmentManager().getBackStackEntryCount() != 0) {
                        TopicFragment topicFragment = (TopicFragment) getFragmentManager().findFragmentByTag("MessFrag");
                        topicFragment.onResume();
                    }
                }
            }
        });

        messDetailRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMessDetail);
        messDetailRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        messDetailRecyclerView.setLayoutManager(layoutManager);
        adapter = new MessageDetailRecyclerAdapter(getContext(), arrMessDetail, messDetailRecyclerView);
        messDetailRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
        //set load more listener for the RecyclerView adapter
        adapter.setOnLoadMoreListener(new MessageDetailRecyclerAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isAllMsg) {
                    new LoadMoreDataTask().execute();
                } else {
                    Toast.makeText(getContext(), "Đã load hết tin nhắn", Toast.LENGTH_LONG).show();
                }

            }
        });

        messDetailRecyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new MessageDetailRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TalkTextView textViewDate = (TalkTextView) view.findViewById(R.id.textViewDateTime);
                TalkTextView textViewMessDetail = (TalkTextView) view.findViewById(R.id.textViewMessDetail);
                if (textViewDate.getVisibility() == View.VISIBLE) {
                    Animation showOff = AnimationUtils.loadAnimation(getContext(), R.anim.show_off);
                    textViewDate.setVisibility(View.GONE);
                    textViewDate.startAnimation(showOff);
                    if (arrMessDetail.get(position).getType()==4){
                        textViewMessDetail.setBackgroundResource(R.drawable.my_message_box);
                    } else {
                        textViewMessDetail.setBackgroundResource(R.drawable.rounded_corner);
                    }
                } else {
                    Animation showUp = AnimationUtils.loadAnimation(getContext(), R.anim.show_up);
                    Animation slide_down = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
                    textViewDate.setVisibility(View.VISIBLE);
                    textViewDate.startAnimation(showUp);
                    textViewMessDetail.startAnimation(slide_down);
                    if (arrMessDetail.get(position).getType()==4) {
                        textViewMessDetail.setBackgroundResource(R.drawable.selected_my_msg_box);
                    } else {
                        textViewMessDetail.setBackgroundResource(R.drawable.selected_msg_box);
                    }
                }
            }
        });
        adapter.SetOnItemLongClickListener(new MessageDetailRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                selectedPosition = position;
                selectedMsgDetail.setText(MessageDataManager.getInstance().getUser(userID, getContext()).getName() + ": " + arrMessDetail.get(position).getText());
                Animation enter_from_bottom = AnimationUtils.loadAnimation(getContext(), R.anim.enter_from_bottom);
                relativeLayoutContextMenu.setVisibility(View.VISIBLE);
                relativeLayoutContextMenu.startAnimation(enter_from_bottom);
            }
        });

        messDetailRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    messDetailRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            messDetailRecyclerView.scrollToPosition(
                                    messDetailRecyclerView.getAdapter().getItemCount() - 1);
                        }
                    }, 100);
                }
            }
        });

        messDetailRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            float x1, x2;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    x1 = event.getX();
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    x2 = event.getX();
                    if (x1 == x2){
                        hideKeyboard(v);
                    }
                }
                return false;
            }
        });

        relativeLayoutContextMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relativeLayoutContextMenu.getVisibility() == View.VISIBLE) {
                    Animation context_menu_exit = AnimationUtils.loadAnimation(getContext(), R.anim.context_menu_exit);
                    relativeLayoutContextMenu.setVisibility(View.GONE);
                    relativeLayoutContextMenu.startAnimation(context_menu_exit);
                }
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cData = ClipData.newPlainText("text", arrMessDetail.get(selectedPosition).getText());
                clipboardManager.setPrimaryClip(cData);
                Toast.makeText(getContext(), "Đã sao chép", Toast.LENGTH_LONG).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation context_menu_exit = AnimationUtils.loadAnimation(getContext(), R.anim.context_menu_exit);
                relativeLayoutContextMenu.setVisibility(View.GONE);
                relativeLayoutContextMenu.startAnimation(context_menu_exit);
                boolean isDeleted = MessageDataManager.getInstance().deleteMessage(arrMessDetail.get(selectedPosition).getId(), getContext());
                if (isDeleted) {
                    Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_LONG).show();
                }
                arrMessDetail.remove(selectedPosition);
                if (arrMessDetail.size() == 0){
                    MessageDataManager.getInstance().deleteTopic(topicID, getContext());
                    textViewOver.setVisibility(View.VISIBLE);
                } else {
                    topic.setLastMess(arrMessDetail.get(selectedPosition-1).getText());
                    topic.setDate(arrMessDetail.get(selectedPosition-1).getDatetime());
                    MessageDataManager.getInstance().updateTopic(topic, getContext());
                }
                adapter.notifyDataSetChanged();
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
        editText.clearFocus();
        if (!isResume){
            LoadDataTask dataTask = new LoadDataTask();
            dataTask.execute();
        }

        MessageDataManager.getInstance().setDataListener(new MessageDataManager.DataListener() {
            @Override
            public void onDataChanged(Topic topic, MessageDetail messageDetail) {
                LoadMessageTask loadMessageTask = new LoadMessageTask();
                loadMessageTask.execute(messageDetail);
            }
        });
    }

    private class LoadDataTask extends AsyncTask<String, Void, ArrayList<MessageDetail>> {
        @Override
        protected ArrayList<MessageDetail> doInBackground(String... urls) {
            ArrayList<MessageDetail> arrayListMessageDetail = new ArrayList<>();
            arrayListMessageDetail.addAll(MessageDataManager.getInstance().getListMessageFromDB(topicID, getContext(), scrollTimes));
            return arrayListMessageDetail;
        }

        @Override
        protected void onPostExecute(final ArrayList<MessageDetail> result) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    arrMessDetail.clear();
                    arrMessDetail.addAll(result);
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    messDetailRecyclerView.scrollToPosition(arrMessDetail.size() - 1);
                    textViewLoading.setVisibility(View.GONE);
                    isResume = true;
                    if (arrMessDetail.size() == 0){
                        textViewOver.setVisibility(View.VISIBLE);
                    } else {
                        textViewOver.setVisibility(View.GONE);
                    }
                }
            }, 100);
        }
    }

    private class LoadMoreDataTask extends AsyncTask<String, Void, ArrayList<MessageDetail>> {
        @Override
        protected void onPreExecute() {
            arrMessDetail.add(0, null);
            adapter.notifyItemInserted(0);
            scrollTimes++;
        }

        @Override
        protected ArrayList<MessageDetail> doInBackground(String... urls) {
            ArrayList<MessageDetail> arrNewMsgDetail = MessageDataManager.getInstance().getListMessageFromDB(topicID, getContext(), scrollTimes);
            return arrNewMsgDetail;
        }

        @Override
        protected void onPostExecute(final ArrayList<MessageDetail> result) {
            if (result.size() < 30) {
                isAllMsg = true;
            }
            arrMessDetail.remove(0);
            adapter.notifyItemRemoved(0);
            arrMessDetail.addAll(0, result);
            adapter.notifyItemRangeInserted(0, result.size()-1);
            adapter.setLoaded();
        }
    }

    private class LoadMessageTask extends AsyncTask<MessageDetail, Void, Void>{
        @Override
        protected Void doInBackground(MessageDetail... messageDetails) {
            arrMessDetail.add(messageDetails[0]);
            if (messageDetails[0].getTopicID().equals(topicID)){
                topic = MessageDataManager.getInstance().getTopic(topicID, getContext());
                topic.setHasNewMessage(false);
                Topic unfollowTopic = MessageDataManager.getInstance().getTopic("-1_" + MessageDataManager.getInstance().getCurrentUser(getContext()).getId(), getContext());
                unfollowTopic.setHasNewMessage(false);
                MessageDataManager.getInstance().updateTopic(topic, getContext());
                MessageDataManager.getInstance().updateTopic(unfollowTopic, getContext());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (arrMessDetail.size() == 0){
                textViewOver.setVisibility(View.VISIBLE);
            } else {
                textViewOver.setVisibility(View.GONE);
            }
            long t = System.currentTimeMillis();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(arrMessDetail.size()-2,2);
                adapter.notifyItemInserted(arrMessDetail.size());
            }
            if (arrMessDetail.get(arrMessDetail.size()-1).getType() == 4){
                messDetailRecyclerView.smoothScrollToPosition(arrMessDetail.size());
            }
            t = System.currentTimeMillis() - t;
            Log.i("Time ", String.valueOf(t));
        }
    }
}