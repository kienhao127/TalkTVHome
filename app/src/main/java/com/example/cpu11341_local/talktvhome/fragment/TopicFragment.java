package com.example.cpu11341_local.talktvhome.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.cpu11341_local.talktvhome.myview.TalkTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by CPU11341-local on 9/1/2017.
 */

public class TopicFragment extends android.support.v4.app.Fragment {

    RecyclerView topicRecyclerView;
    TopicRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    TextView mTitle;
    String toolbarTitle;
    ArrayList<Topic> arrTopic = new ArrayList<>();
    Boolean isFollow;
    String activityName;
    TalkTextView textViewLoading;
    TalkTextView textViewOver;
    int scrollTimes;
    boolean isAllMsg = false, isResume = false;
    ArrayList<Topic> arrFollowTopic = new ArrayList<>();
    int i = 0;

    public TopicFragment(String toolbarTitle, boolean isFollow, String activityName) {
        this.toolbarTitle = toolbarTitle;
        this.isFollow = isFollow;
        this.activityName = activityName;
    }

    public TopicFragment(String toolbarTitle, boolean isFollow, String activityName, ArrayList<Topic> arrFollowTopic) {
        this.toolbarTitle = toolbarTitle;
        this.isFollow = isFollow;
        this.activityName = activityName;
        this.arrFollowTopic = arrFollowTopic;
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
        textViewLoading = (TalkTextView) view.findViewById(R.id.textViewLoading);
        textViewOver = (TalkTextView) view.findViewById(R.id.textViewOver);
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

        if (arrTopic != null) {
            topicRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMessage);
            registerForContextMenu(topicRecyclerView);
            layoutManager = new LinearLayoutManager(getContext());
            topicRecyclerView.setLayoutManager(layoutManager);
            adapter = new TopicRecyclerAdapter(getContext(), arrTopic, topicRecyclerView);
            topicRecyclerView.setAdapter(adapter);

            adapter.SetOnItemClickListener(new TopicRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    arrTopic.get(position).setHasNewMessage(false);
                    MessageDataManager.getInstance().updateTopic(arrTopic.get(position), getContext());
                    adapter.notifyDataSetChanged();
                    switch (arrTopic.get(position).getAction_type()) {
                        case 1:
                        case 3:
                            ChatFragment chatFragment;
                            if (arrTopic.get(position).isFollow()){
                                chatFragment = new ChatFragment(arrTopic, position);
                            } else {
                                chatFragment = new ChatFragment(arrTopic, position, arrFollowTopic);
                            }
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
                            TopicFragment topicFragment = new TopicFragment("Tin nhắn chưa theo dõi", false, activityName, arrTopic);
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            if (getActivity() instanceof MessageActivity) {
                                ft.setCustomAnimations(R.anim.enter_from_right, 0, 0, R.anim.exit_to_right);
                            } else {
                                ft.setCustomAnimations(R.anim.enter_from_bottom, 0, 0, R.anim.exit_to_bottom);
                            }
                            ft.add(R.id.fragment_container, topicFragment, "MessFrag")
                                    .addToBackStack(null)
                                    .commit();

                            break;
                    }
                }
            });

            adapter.setOnLoadMoreListener(new TopicRecyclerAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (!isAllMsg) {
                        new LoadMoreDataTask().execute();
                    } else {
                        Toast.makeText(getContext(), "Đã load hết tin nhắn", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == topicRecyclerView){
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_topic, menu);
            for (int i = 0; i < menu.size(); ++i) {
                MenuItem item = menu.getItem(i);
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        onContextItemSelected(item);
                        return true;
                    }
                });
            }
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int pos = adapter.getPosition();
        switch (item.getItemId()) {
            case R.id.mnDelete:
                MessageDataManager.getInstance().deleteTopic(arrTopic.get(pos).getUserId(), getContext(), arrFollowTopic);
                Topic removedTopic = arrTopic.remove(pos);
                if (pos == 0 && arrTopic.size() != 0 && !removedTopic.isFollow()){
                    int unfollowTopicIndex = findTopicByID(arrFollowTopic, -1);
                    arrFollowTopic.get(unfollowTopicIndex).setLastMess(arrTopic.get(0).getName() + ": " + arrTopic.get(0).getLastMess());
                    arrFollowTopic.get(unfollowTopicIndex).setDate(arrTopic.get(0).getDate());
                    MessageDataManager.getInstance().updateTopic(arrFollowTopic.get(unfollowTopicIndex), getContext());
                }
                if (adapter != null){
                    adapter.notifyDataSetChanged();
                }
                if (arrTopic.size() == 0){
                    textViewOver.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnBlock:
                Toast.makeText(getContext(), "Chặn" + arrTopic.get(pos).getName(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isResume) {
            LoadTopicTask loadTopicTask = new LoadTopicTask();
            loadTopicTask.execute();
        } else {
            if (arrTopic.size()!=0){
                sortTopic(arrTopic);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            } else {
                textViewOver.setVisibility(View.VISIBLE);
            }
        }

        MessageDataManager.getInstance().setDataListener(new MessageDataManager.DataListener() {
            @Override
            public void onDataChanged(Topic topic, MessageDetail messageDetail) {
                boolean isTopicExists = false;
                if (!isFollow){
                    if (topic.isFollow()){
                        for (int i=0; i<arrFollowTopic.size(); i++){
                            if (arrFollowTopic.get(i).getUserId() == topic.getUserId()){
                                arrFollowTopic.get(i).setDate(topic.getDate());
                                arrFollowTopic.get(i).setLastMess(topic.getLastMess());
                                MessageDataManager.getInstance().updateTopic(arrFollowTopic.get(i), getContext());
                                return;
                            }
                        }
                    }
                }

                if (isFollow){
                    if (!topic.isFollow()){
                        topic = MessageDataManager.getInstance().getTopic(-1, getContext());
                    }
                }

                for (int i = 0; i < arrTopic.size(); i++) {
                    if (arrTopic.get(i).getUserId() == topic.getUserId()) {
                        arrTopic.set(i, topic);
                        isTopicExists = true;
                    }
                }

                if (!isTopicExists) {
                    arrTopic.add(topic);
                }

                if (arrTopic.size()!=0) {
                    sortTopic(arrTopic);
                }

                if (topic.getUserId() == arrTopic.get(0).getUserId() && !topic.isFollow()){
                    int unfollowTopicIndex = findTopicByID(arrFollowTopic, -1);
                    if (unfollowTopicIndex == -1){
                        arrFollowTopic.add(MessageDataManager.getInstance().getTopic(-1, getContext()));
                    } else {
                        arrFollowTopic.get(unfollowTopicIndex).setLastMess(arrTopic.get(0).getName() + ": " + arrTopic.get(0).getLastMess());
                        arrFollowTopic.get(unfollowTopicIndex).setDate(arrTopic.get(0).getDate());
                        MessageDataManager.getInstance().updateTopic(arrFollowTopic.get(unfollowTopicIndex), getContext());
                    }
                }
                textViewOver.setVisibility(View.GONE);

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private class LoadTopicTask extends AsyncTask<String, Void, ArrayList<Topic>> {
        @Override
        protected ArrayList<Topic> doInBackground(String... urls) {
            Log.i("Get list topic", "DO IN BACKGROUND");
            ArrayList<Topic> arrListTopic = new ArrayList<>();
            if (MessageDataManager.getInstance().getListTopic(isFollow, getContext(), scrollTimes) == null){
                return null;
            }
            arrListTopic.addAll(MessageDataManager.getInstance().getListTopic(isFollow, getContext(), scrollTimes));
            return arrListTopic;
        }

        @Override
        protected void onPostExecute(final ArrayList<Topic> result) {
            arrTopic.clear();
            if (result!=null){
                arrTopic.addAll(result);
            }
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            isResume = true;
            textViewLoading.setVisibility(View.GONE);
            if (arrTopic.size() == 0){
                textViewOver.setVisibility(View.VISIBLE);
            } else {
                textViewOver.setVisibility(View.GONE);
            }
        }
    }

    private class LoadMoreDataTask extends AsyncTask<String, Void, ArrayList<Topic>> {
        @Override
        protected void onPreExecute() {
            arrTopic.add(null);
            adapter.notifyItemInserted(arrTopic.size());
            scrollTimes++;
        }

        @Override
        protected ArrayList<Topic> doInBackground(String... urls) {
            Log.i("LoadMoreData", "ASDASD");
            ArrayList<Topic> arrNewMsgDetail = MessageDataManager.getInstance().getListTopic(isFollow, getContext(), scrollTimes);
            return arrNewMsgDetail;
        }

        @Override
        protected void onPostExecute(final ArrayList<Topic> result) {
            int pos = arrTopic.size()-1;
            arrTopic.remove(pos);
            adapter.notifyItemRemoved(pos);
            if (result != null){
                if (result.size() < 30) {
                    isAllMsg = true;
                }
                arrTopic.addAll(pos, result);
                adapter.notifyItemRangeInserted(pos, result.size()-1);
            } else {
                isAllMsg = true;
            }
            adapter.setLoaded();
        }
    }

    public static void sortTopic(ArrayList<Topic> arrTopic){
        Topic systemTopic = null, unFollowTopic = null;

        int i=0;
        while (arrTopic.size()!=0 && i < arrTopic.size()){
            if (arrTopic.get(i).getUserId() == 0){
                systemTopic = arrTopic.get(i);
                arrTopic.remove(i);
                i++;
                break;
            }
            if (arrTopic.get(i).getUserId() == -1){
                unFollowTopic = arrTopic.get(i);
                arrTopic.remove(i);
                i++;
                break;
            }
            i++;
        }

        Collections.sort(arrTopic, new Comparator<Topic>() {
            @Override
            public int compare(Topic o1, Topic o2) {
                return Long.valueOf(o2.getDate()).compareTo(o1.getDate());
            }
        });

        if (unFollowTopic != null){
            arrTopic.add(0, unFollowTopic);
        }
        if (systemTopic != null){
            arrTopic.add(0, systemTopic);
        }
    }

    public static int findTopicByID(ArrayList<Topic> arrTopic, int userID){
        for (int i=0; i < arrTopic.size(); i++){
            if (arrTopic.get(i).getUserId() == userID){
                return i;
            }
        }
        return -1;
    }
}
