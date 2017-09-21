package com.example.cpu11341_local.talktvhome;

import android.app.Fragment;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.data.User;
import com.example.cpu11341_local.talktvhome.fragment.ChatFragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by CPU11341-local on 9/11/2017.
 */

public class MessageDataManager {
    Map<Integer, Topic> linkedHashMapTopic = new LinkedHashMap();
    Map<Integer, ArrayList<MessageDetail>> linkedHashMapMsgDetail = new LinkedHashMap();
    ArrayList<Topic> arrListTopic = new ArrayList<>();
    ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();
    DataListener dataListener;
    DateFormat dateFormat = new SimpleDateFormat("d/MM/yy HH:mm:ss");
    Map<Integer, User> linkedHashMapUser = new LinkedHashMap();

    private static MessageDataManager instance = null;
    protected MessageDataManager() throws ParseException {
//        linkedHashMapUser.put(0, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"));
//        linkedHashMapUser.put(1, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"));
//        linkedHashMapUser.put(2, new User(2, "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady"));
//        linkedHashMapUser.put(5, new User(5, "http://is2.mzstatic.com/image/thumb/Purple127/v4/95/75/d9/9575d99b-8854-11cc-25ef-4aa4b4bb6dc3/source/1200x630bb.jpg", "Tui"));

//        arrMessDetail.add(new MessageDetail(1, 1, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
//                "Tên event 1", dateFormat.parse("25/08/17 09:47:02").getTime(), "http://talktv.vcdn.vn/talk/mobile/banner/ad_banner_75.jpg", "Mô tả", 1, "Xem chi tiết", "action_extra"));
//        arrMessDetail.add(new MessageDetail(2, 2, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
//                "Nhắc nhở 1", dateFormat.parse("25/09/17 09:47:02").getTime(), "Nội dung nhắc nhở", 1, "Xem chi tiết", "action_extra"));
//        arrMessDetail.add(new MessageDetail(3, 3, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
//                dateFormat.parse("18/08/17 10:47:03").getTime(), "Nội dung thông báo", false));
//        linkedHashMapMsgDetail.put(0, new ArrayList<>(arrMessDetail));
//        arrMessDetail.clear();
//
//        arrMessDetail.add(new MessageDetail(3, 1, linkedHashMapUser.get(1), dateFormat.parse("18/08/17 10:47:03").getTime(), "Xin chào", false));
//        arrMessDetail.add(new MessageDetail(3, 2, linkedHashMapUser.get(1), dateFormat.parse("1/09/17 11:47:04").getTime(), "Tôi là Thúy Chi", false));
//
//        linkedHashMapMsgDetail.put(1, new ArrayList<>(arrMessDetail));
//        arrMessDetail.clear();

//        linkedHashMapTopic.put(0, new Topic("https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV", "Nội dung thông báo", dateFormat.parse("18/08/17 10:47:03").getTime(), 1, 0, false));
//        linkedHashMapTopic.put(1, new Topic("http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi", "Tôi là Thúy Chi", dateFormat.parse("1/09/17 11:47:04").getTime(), 3, 1, false));

    }

    public static MessageDataManager getInstance() {
        if(instance == null) {
            try {
                instance = new MessageDataManager();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public ArrayList<MessageDetail> getListMessage(int senderID, Context context){
        ArrayList<MessageDetail> arrMessageDetailOfSender = new ArrayList<>();
        long t = System.currentTimeMillis();
        arrMessageDetailOfSender = DatabaseHelper.getInstance(context).getListMessage(senderID);
        long d = System.currentTimeMillis() - t;
        Log.i("Load msg time ", String.valueOf(d));
        return arrMessageDetailOfSender;
    }

    public ArrayList<Topic> getListTopic(boolean isFollow, Context context) {
        ArrayList<Topic> arrTopic = new ArrayList<>();
        if (DatabaseHelper.getInstance(context).getListTopic().size() == 0){
            return null;
        }
        for (Topic topic : DatabaseHelper.getInstance(context).getListTopic()){
            if (isFollow(topic.getUserId()) == isFollow){
                topic.setDate(topic.getDate());
                arrTopic.add(topic);
                linkedHashMapTopic.put(topic.getUserId(), topic);
            }
        }

        return arrTopic;
    }

    public boolean insertUser(User user, Context context){
        DatabaseHelper.getInstance(context).insertUser(user);
        return true;
    }

    public User getUser(int userID, Context context){
        return DatabaseHelper.getInstance(context).getUser(userID);
    }

    public User getCurrentUser(){
        return linkedHashMapUser.get(5);
    }

    public boolean insertMessage(MessageDetail messageDetail, Context context){
        DatabaseHelper.getInstance(context).insertMessage(messageDetail);
        updateTopic(messageDetail.getUser().getId(), messageDetail, context);
        return true;
    }

    public boolean updateTopic(int senderID, MessageDetail messageDetail, Context context){
        String strText = "";

        if (messageDetail.getType() == 4){
            strText = "Bạn: " + messageDetail.getText();
        } else {
            strText = messageDetail.getText();
        }

        Topic topic = linkedHashMapTopic.get(-1);
        if (!isFollow(senderID)) {
            if (topic != null) {
                topic.setLastMess(messageDetail.getUser().getName() + ": " + strText);
                topic.setDate(messageDetail.getDatetime());
            }
        }

        topic = linkedHashMapTopic.get(senderID);
        if (topic != null){
            topic.setLastMess(strText);
            topic.setDate(messageDetail.getDatetime());
            DatabaseHelper.getInstance(context).updateTopic(topic);
            linkedHashMapTopic.put(senderID, topic);
            long t = System.currentTimeMillis();
            linkedHashMapTopic = sortByValue(linkedHashMapTopic);
            long d = System.currentTimeMillis() - t;
            Log.i("Time: ", String.valueOf(d));
            if (dataListener!=null){
                dataListener.onDataChanged(topic);
            }
            return true;
        }

        Topic newTopic = new Topic(messageDetail.getUser().getAvatar(), messageDetail.getUser().getName(), strText, messageDetail.getDatetime(), 3, senderID, true);
        DatabaseHelper.getInstance(context).insertTopic(newTopic);

        if (dataListener!=null){
            dataListener.onDataChanged(newTopic);
        }
        linkedHashMapTopic.put(senderID, newTopic);
        if (!isFollow(senderID)) {
            if (linkedHashMapTopic.get(-1) == null)
                linkedHashMapTopic.put(-1, new Topic("http://i.imgur.com/xFdNVDs.png", "Tin nhắn", messageDetail.getUser().getName() + ": " + strText, messageDetail.getDatetime(), 2, -1, true));
        }
        long t = System.currentTimeMillis();
        linkedHashMapTopic = sortByValue(linkedHashMapTopic);
        long d = System.currentTimeMillis() - t;
        Log.i("Time: ", String.valueOf(d));
        return true;
    }

    public interface DataListener{
        void onDataChanged(Topic topic);
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    private boolean isFollow(int senderID){
        if (senderID == 2){
            return false;
        }
        return true;
    }

    private static Map<Integer, Topic> sortByValue(Map<Integer, Topic> unsortMap) {

        // 1. Convert Map to List of Map
        Topic topicSystem = unsortMap.get(0);
        Topic topicUnfollowMsg = unsortMap.get(-1);
        unsortMap.remove(0);
        if (topicUnfollowMsg != null){
            unsortMap.remove(-1);
        }
        List<Map.Entry<Integer, Topic>> list = new LinkedList<>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Integer, Topic>>() {
            public int compare(Map.Entry<Integer, Topic> o1,
                               Map.Entry<Integer, Topic> o2) {
                return (Long.valueOf(o2.getValue().getDate())).compareTo(o1.getValue().getDate());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, Topic> sortedMap = new LinkedHashMap<Integer, Topic>();
        if (topicSystem != null){
            sortedMap.put(topicSystem.getUserId(), topicSystem);
        }
        if (topicUnfollowMsg != null){
            sortedMap.put(topicUnfollowMsg.getUserId(), topicUnfollowMsg);
        }
        for (Map.Entry<Integer, Topic> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
