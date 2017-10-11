package com.example.cpu11341_local.talktvhome.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpu11341_local.talktvhome.DatabaseHelper;
import com.example.cpu11341_local.talktvhome.MessageActivity;
import com.example.cpu11341_local.talktvhome.MessageDataManager;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.adapter.TopicRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.Topic;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/1/2017.
 */

public class MessageFragment extends android.support.v4.app.Fragment {

    RecyclerView topicRecyclerView;
    TopicRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    TextView mTitle;
    String toolbarTitle;
    ArrayList<Topic> arrTopic = new ArrayList<>();
    Boolean isFollow;
    String activityName;
    TextView textViewLoading;

    public MessageFragment(String toolbarTitle, boolean isFollow, String activityName) {
        this.toolbarTitle = toolbarTitle;
        this.isFollow = isFollow;
        this.activityName = activityName;
    }

//    private String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = getActivity().getAssets().open("messageTest.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
//
//    private void loadJsonToArray(){
//        String json = loadJSONFromAsset();
//        JSONObject jsonResponse;
//        try {
//            jsonResponse = new JSONObject(json);
//            JSONObject message = jsonResponse.getJSONObject("doc");
//            JSONArray messItems = message.getJSONArray("child");
//            for(int i=0;i<messItems.length();i++){
//                JSONObject messItem = messItems.getJSONObject(i);
//                switch (messItem.getInt("type")){
//                    case 1:{
//                        JSONObject user = messItem.getJSONObject("user");
//                        User u = new User(user.getInt("id"), user.getString("avatar"), user.getString("name"), user.getBoolean("isFollow"));
//                        JSONObject action = messItem.getJSONObject("action");
//                        ActionOfSystemMessage a = new ActionOfSystemMessage(action.getInt("action_type"), action.getString("action_title"), action.getString("action_extra"));
//                        arrMessDetail.add(new MessageDetail(messItem.getInt("type"), messItem.getInt("id"), u, messItem.getString("title"), messItem.getString("datetime"),
//                                messItem.getString("imageURL"), messItem.getString("description"), a));
//                        Topic topic = new Topic(u, arrMessDetail.get(i).getDescription(), arrMessDetail.get(i).getDatetime(), 1);
//                        if (arrTopic.size() > 0){
//                            arrTopic.set(0, topic);
//                        } else {
//                            arrTopic.add(topic);
//                        }
//                        break;
//                    }
//                    case 2:{
//                        break;
//                    }
//                    case 3:{
//                        boolean isUpdate = false;
//                        JSONObject user = messItem.getJSONObject("user");
//                        User u = new User(user.getInt("id"), user.getString("avatar"), user.getString("name"), user.getBoolean("isFollow"));
//                        arrMessDetail.add(new MessageDetail(messItem.getInt("type"), messItem.getInt("id"), u, messItem.getString("datetime"),
//                                messItem.getString("message")));
//                        Topic topic = new Topic(u, arrMessDetail.get(i).getMessage(), arrMessDetail.get(i).getDatetime(), 3);
//                        for (int j = 0; j < arrTopic.size(); j++){
//                            if (arrTopic.get(j).getUser().getId() == u.getId()){
//                                arrTopic.set(j , topic);
//                                isUpdate = true;
//                            }
//                        }
//                        if (isUpdate){
//                            break;
//                        } else {
//                            arrTopic.add(topic);
//                        }
//                        break;
//                    }
//                }
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);
        textViewLoading = (TextView) view.findViewById(R.id.textViewLoading);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        mTitle.setText(toolbarTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        if (arrTopic == null) {

        } else {

            topicRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMessage);
            adapter = new TopicRecyclerAdapter(getContext(), arrTopic);

            layoutManager = new LinearLayoutManager(getContext());
            topicRecyclerView.setLayoutManager(layoutManager);
            topicRecyclerView.setAdapter(adapter);

            topicRecyclerView.scrollToPosition(adapter.getItemCount() - 1);

            adapter.SetOnItemClickListener(new TopicRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    arrTopic.get(position).setHasNewMessage(false);
                    DatabaseHelper.getInstance(getContext()).updateTopic(arrTopic.get(position));
                    adapter.notifyDataSetChanged();
                    switch (arrTopic.get(position).getAction_type()) {
                        case 1:
                        case 3:
                            ChatFragment chatFragment = new ChatFragment(arrTopic.get(position).getName(), arrTopic.get(position).getUserId());
                            FragmentTransaction Chatft = getActivity().getSupportFragmentManager().beginTransaction();
                            if (getActivity() instanceof MessageActivity) {
                                Chatft.setCustomAnimations(R.anim.enter_from_right, 0, 0, R.anim.exit_to_right);
                            } else {
                                Chatft.setCustomAnimations(R.anim.enter_from_bottom, 0, 0, R.anim.exit_to_bottom);
                            }
                            Chatft.add(R.id.fragment_container, chatFragment, "ChatFragment")
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case 2:
                            MessageFragment messageFragment = new MessageFragment("Tin nhắn chưa theo dõi", false, activityName);
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            if (getActivity() instanceof MessageActivity) {
                                ft.setCustomAnimations(R.anim.enter_from_right, 0, 0, R.anim.exit_to_right);
                            } else {
                                ft.setCustomAnimations(R.anim.enter_from_bottom, 0, 0, R.anim.exit_to_bottom);
                            }
                            ft.add(R.id.fragment_container, messageFragment, "MessFrag")
                                    .addToBackStack(null)
                                    .commit();

                            break;
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (arrTopic != null){
            LoadTopicTask loadTopicTask = new LoadTopicTask();
            loadTopicTask.execute();
        }

        MessageDataManager.getInstance().setDataListener(new MessageDataManager.DataListener() {
            @Override
            public void onDataChanged(Topic topic, MessageDetail messageDetail) {
                if (!MessageDataManager.getInstance().isFollow(topic.getUserId())) {
                    Topic unFollowTopic = MessageDataManager.getInstance().getTopic(-1);
                    unFollowTopic.setHasNewMessage(true);
                    MessageDataManager.getInstance().updateTopic(unFollowTopic, getContext());
                    for (int i=0; i <arrTopic.size(); i++){
                        if (arrTopic.get(i).getUserId() == -1){
                            arrTopic.set(i, unFollowTopic);
                            break;
                        }
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                topic.setHasNewMessage(true);
                MessageDataManager.getInstance().updateTopic(topic, getContext());
                arrTopic.clear();
                arrTopic.addAll(MessageDataManager.getInstance().getListTopic(isFollow, getContext()));
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private class LoadTopicTask extends AsyncTask<String, Void, ArrayList<Topic>> {
        @Override
        protected ArrayList<Topic> doInBackground(String... urls) {
            ArrayList<Topic> arrListTopic = new ArrayList<>();
            arrListTopic.addAll(MessageDataManager.getInstance().getListTopic(isFollow, getContext()));
            return arrListTopic;
        }

        @Override
        protected void onPostExecute(final ArrayList<Topic> result) {
            arrTopic.clear();
            arrTopic.addAll(result);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            textViewLoading.setVisibility(View.GONE);
        }
    }
}
