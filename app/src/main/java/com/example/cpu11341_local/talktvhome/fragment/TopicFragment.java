package com.example.cpu11341_local.talktvhome.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
    int loadMoreFrom = 30;
    public static int followLoadMoreFrom = 30;
    public static int unfollowLoadMoreFrom = 30;
    boolean isAllMsg = false, isResume = false;
    int i = 0;
    ProgressDialog progressDialog;
    public TopicFragment(String toolbarTitle, boolean isFollow, String activityName) {
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
        textViewLoading = (TalkTextView) view.findViewById(R.id.textViewLoading);
        textViewOver = (TalkTextView) view.findViewById(R.id.textViewOver);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        progressDialog = new ProgressDialog(getActivity());
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
                            ChatFragment chatFragment = new ChatFragment(arrTopic.get(position));
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
                            TopicFragment topicFragment = new TopicFragment("Tin nhắn chưa theo dõi", false, activityName);
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
        if (v == topicRecyclerView) {
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
                if (arrTopic.get(pos).getTopicID() == "-1_" + MessageDataManager.getInstance().getCurrentUser(getContext()).getId()){
                    unfollowLoadMoreFrom = 0;
                }
                if (isFollow){
                    followLoadMoreFrom--;
                } else {
                    unfollowLoadMoreFrom--;
                }
                loadMoreFrom--;
                DeleteTopicTask deleteTopicTask = new DeleteTopicTask();
                deleteTopicTask.execute(arrTopic.get(pos).getTopicID());
                arrTopic.remove(pos);
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
        if (isFollow){
            followLoadMoreFrom = 30;
        } else {
            unfollowLoadMoreFrom = 30;
        }
        LoadTopicTask loadTopicTask = new LoadTopicTask();
        loadTopicTask.execute();

        MessageDataManager.getInstance().setDataListener(new MessageDataManager.DataListener() {
            @Override
            public void onDataChanged(Topic topic, MessageDetail messageDetail) {
                boolean isTopicExists = false;

                if (isFollow && !topic.isFollow()) {
                    topic = MessageDataManager.getInstance().getTopic("-1_"+MessageDataManager.getInstance().getCurrentUser(getContext()).getId(), getContext());
                }

                for (int i = 0; i < arrTopic.size(); i++) {
                    if (arrTopic.get(i).getTopicID().equals(topic.getTopicID())) {
                        arrTopic.set(i, topic);
                        isTopicExists = true;
                    }
                }

                if (!isTopicExists) {
                    arrTopic.add(topic);
                }
                sortTopic(arrTopic, getContext());
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
            long t = System.currentTimeMillis();
            ArrayList<Topic> arrListTopic = new ArrayList<>();
            arrListTopic = MessageDataManager.getInstance().getListTopic(isFollow, getContext(), 0);
            if (arrListTopic == null) {
                return null;
            }
            long time = System.currentTimeMillis() - t;
            Log.i("time get list topic", String.valueOf(time));
            return arrListTopic;
        }

        @Override
        protected void onPostExecute(final ArrayList<Topic> result) {
            arrTopic.clear();
            if (result != null) {
                arrTopic.addAll(result);
            }
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            isResume = true;
            textViewLoading.setVisibility(View.GONE);
            if (arrTopic.size() == 0) {
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
        }

        @Override
        protected ArrayList<Topic> doInBackground(String... urls) {
            ArrayList<Topic> arrNewTopic = MessageDataManager.getInstance().getListTopic(isFollow, getContext(), loadMoreFrom);
            return arrNewTopic;
        }

        @Override
        protected void onPostExecute(final ArrayList<Topic> result) {
            int pos = arrTopic.size() - 1;
            arrTopic.remove(pos);
            adapter.notifyItemRemoved(pos);
            if (result != null) {
                if (isFollow){
                    followLoadMoreFrom += result.size();
                } else {
                    unfollowLoadMoreFrom += result.size();
                }
                loadMoreFrom += result.size();
                if (result.size() < 30) {
                    isAllMsg = true;
                }
                arrTopic.addAll(pos, result);
                adapter.notifyItemRangeInserted(pos, result.size() - 1);
            } else {
                isAllMsg = true;
            }
            adapter.setLoaded();
        }
    }

    private class DeleteTopicTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.deleting_layout);
        }

        @Override
        protected Void doInBackground(String... params) {
            MessageDataManager.getInstance().deleteTopic(params[0], getContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (adapter != null) {
                adapter.setData(arrTopic);
            }
            if (arrTopic.size() == 0) {
                textViewOver.setVisibility(View.VISIBLE);
            }
            Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
        }
    }

    public static void sortTopic(ArrayList<Topic> arrTopic, Context context) {
        Topic systemTopic = null, unFollowTopic = null;

        int i = 0;
        while (arrTopic.size() != 0 && i < arrTopic.size()) {
            if (arrTopic.get(i).getTopicID().equals("0_"+MessageDataManager.getInstance().getCurrentUser(context).getId())) {
                systemTopic = arrTopic.get(i);
                arrTopic.remove(i);
                continue;
            }
            if (arrTopic.get(i).getTopicID().equals("-1_"+MessageDataManager.getInstance().getCurrentUser(context).getId())) {
                unFollowTopic = arrTopic.get(i);
                arrTopic.remove(i);
                continue;
            }
            i++;
        }

        Collections.sort(arrTopic, new Comparator<Topic>() {
            @Override
            public int compare(Topic o1, Topic o2) {
                return Long.valueOf(o2.getDate()).compareTo(o1.getDate());
            }
        });

        if (unFollowTopic != null) {
            arrTopic.add(0, unFollowTopic);
        }
        if (systemTopic != null) {
            arrTopic.add(0, systemTopic);
        }
    }
}
