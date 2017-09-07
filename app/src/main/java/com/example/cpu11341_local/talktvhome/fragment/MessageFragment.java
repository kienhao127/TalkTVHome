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
    Boolean isFollow;

    public MessageFragment(String toolbarTitle, boolean isFollow) {
        this.toolbarTitle = toolbarTitle;
        this.isFollow = isFollow;
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
        arrTopic = getListTopic(isFollow);
    }

    ArrayList<Topic> getListTopic(boolean isFollow){
        ArrayList<Topic> arrListTopic = new ArrayList<>();
        if (isFollow){
            arrListTopic.add( new Topic("https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV", "Tin nhắn cuối cùng", "Hôm qua", 1, 0));
            arrListTopic.add( new Topic("http://i.imgur.com/xFdNVDs.png", "Tin nhắn", "Tên người dùng: tin nhắn cuối cùng", "Hôm qua", 2, -1));
            arrListTopic.add( new Topic("http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi", "Tin nhắn cuối cùng", "Hôm qua", 3, 1));
        } else {
            arrListTopic.add( new Topic("http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady", "Tin nhắn cuối cùng", "Hôm qua", 3, 2));
        }
        return arrListTopic;
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
                        ChatFragment chatFragment = new ChatFragment(arrTopic.get(position).getName(), arrTopic.get(position).getUserId());
                        getFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                .replace(R.id.fragment_container, chatFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        MessageFragment messageFragment = new MessageFragment("Tin nhắn chưa theo dõi", false);
                        getFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
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
