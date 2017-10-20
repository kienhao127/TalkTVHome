package com.example.cpu11341_local.talktvhome;

import android.app.Fragment;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

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
    Map<String, Topic> linkedHashMapFollowTopic = new LinkedHashMap();
    Map<String, Topic> linkedHashMapUnfollowTopic = new LinkedHashMap();
    public DataListener dataListener;
    Map<String, ArrayList<MessageDetail>> linkedHashMapMsgDetail = new LinkedHashMap<>();
    ArrayList<String> followID = new ArrayList<>();
    private static MessageDataManager instance = null;

    protected MessageDataManager() throws ParseException {
//        linkedHashMapUser.put(0, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"));
//        linkedHashMapUser.put(1, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"));
//        linkedHashMapUser.put(2, new User(2, "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady"));
//        linkedHashMapUser.put(3, new User(3, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi 2"));
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
        followID.add("0");
        followID.add("1");
    }

    public static MessageDataManager getInstance() {
        if (instance == null) {
            try {
                instance = new MessageDataManager();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }


    //-----------MESSAGE
    public ArrayList<MessageDetail> getListMessageFromDB(String topicID, Context context, int scrollTimes) {
        ArrayList<MessageDetail> arrMessageDetailOfSender = new ArrayList<>();
        long t = System.currentTimeMillis();
        arrMessageDetailOfSender = DatabaseHelper.getInstance(context).getListMessage(topicID, scrollTimes);
        ArrayList<MessageDetail> arrMsgDetail = new ArrayList<>();
        long d = System.currentTimeMillis() - t;
        return arrMessageDetailOfSender;
    }

    public boolean deleteMessage(int id, Context context) {
        return DatabaseHelper.getInstance(context).deleteMessage(id);
    }

    public Topic insertMessage(MessageDetail messageDetail, Context context) {
        DatabaseHelper.getInstance(context).insertMessage(messageDetail);
        String topicID = messageDetail.getTopicID();

        String strText = "";
        if (messageDetail.getType() == 4) {
            strText = "Bạn: " + messageDetail.getText();
        } else {
            strText = messageDetail.getText();
        }
        User userOfTopic = DatabaseHelper.getInstance(context).getUser(splitTopicID(messageDetail.getTopicID())[0]);
        Topic topic = DatabaseHelper.getInstance(context).getTopic(topicID);
        // Nếu topic đã tồn tại và chưa theo dõi,
        // Cập nhật lại unfollowTopic
        // Tại mới nếu unfollowTopic chưa có.
        if (topic.getName() != null) {
            topic.setLastMess(strText);
            topic.setDate(messageDetail.getDatetime());
            DatabaseHelper.getInstance(context).updateTopic(topic);
            if (messageDetail.getType() == 4) {
                topic.setHasNewMessage(false);
            } else {
                topic.setHasNewMessage(true);
            }
            if (!topic.isFollow()) {
                Topic unfollowTopic = DatabaseHelper.getInstance(context).getTopic("-1_" + getCurrentUser(context).getId());
                updateUnfollowTopic(unfollowTopic, messageDetail, context, userOfTopic, strText);
                MessageDataManager.getInstance().updateTopic(unfollowTopic, context);
                if (messageDetail.getType() == 4) {
                    unfollowTopic.setHasNewMessage(false);
                } else {
                    unfollowTopic.setHasNewMessage(true);
                }
                linkedHashMapUnfollowTopic.put(topicID, topic);
                linkedHashMapFollowTopic.put(unfollowTopic.getTopicID(), unfollowTopic);
            } else {
                linkedHashMapFollowTopic.put(topicID, topic);
            }
        } else {
            // Nếu topic chưa tồn tại => tạo mới,
            // Nếu topic chưa theo dõi
            // Cập nhật lại unfollowTopic
            // Tại mới nếu unfollowTopic chưa có.
            topic = new Topic(userOfTopic.getAvatar(), userOfTopic.getName(), strText, messageDetail.getDatetime(), 3, topicID, true, false);
            if (isFollow(topic.getTopicID())) {
                topic.setFollow(true);
                if (messageDetail.getType() == 4) {
                    topic.setHasNewMessage(false);
                } else {
                    topic.setHasNewMessage(true);
                }
                linkedHashMapFollowTopic.put(topic.getTopicID(), topic);
            } else {
                Topic unfollowTopic = DatabaseHelper.getInstance(context).getTopic("-1_" + getCurrentUser(context).getId());
                if (messageDetail.getType() == 4) {
                    unfollowTopic.setHasNewMessage(false);
                } else {
                    unfollowTopic.setHasNewMessage(true);
                }
                updateUnfollowTopic(unfollowTopic, messageDetail, context, userOfTopic, strText);
                linkedHashMapUnfollowTopic.put(topic.getTopicID(), topic);
            }
            DatabaseHelper.getInstance(context).insertTopic(topic);
        }
        return topic;
    }

    //update unfollowTopic sau khi insert
    void updateUnfollowTopic(Topic unfollowTopic, MessageDetail messageDetail, Context context, User userOfTopic, String strText) {
        if (unfollowTopic.getName() != null) {
            unfollowTopic.setLastMess(userOfTopic.getName() + ": " + strText);
            unfollowTopic.setDate(messageDetail.getDatetime());
            unfollowTopic.setHasNewMessage(true);
            DatabaseHelper.getInstance(context).updateTopic(unfollowTopic);
            linkedHashMapFollowTopic.put(unfollowTopic.getTopicID(), unfollowTopic);
        } else {
            unfollowTopic = new Topic("http://i.imgur.com/xFdNVDs.png", "Tin nhắn",
                    userOfTopic.getName() + ": " + strText,
                    messageDetail.getDatetime(), 2, "-1_" + getCurrentUser(context).getId(), true, true);
            linkedHashMapFollowTopic.put(unfollowTopic.getTopicID(), unfollowTopic);
            DatabaseHelper.getInstance(context).insertTopic(unfollowTopic);
        }
    }

    //----------------TOPIC
    public ArrayList<Topic> getListTopic(boolean isFollow, Context context, int scrollTimes) {
        ArrayList<Topic> arrTopic = new ArrayList<>();
        if (DatabaseHelper.getInstance(context).getListTopic(scrollTimes, isFollow).size() == 0) {
            return null;
        }
        Topic systemTopic = null, unFollowTopic = null;
        for (Topic topic : DatabaseHelper.getInstance(context).getListTopic(scrollTimes, isFollow)) {
            if (isFollow) {
                linkedHashMapFollowTopic.put(topic.getTopicID(), topic);
            } else {
                linkedHashMapUnfollowTopic.put(topic.getTopicID(), topic);
            }

            if (topic.getTopicID().equals("0_" + getCurrentUser(context).getId())) {
                systemTopic = topic;
                continue;
            }
            if (isFollow) {
                if (topic.getTopicID().equals("-1_" + getCurrentUser(context).getId())) {
                    unFollowTopic = topic;
                    continue;
                }
            } else {
                unFollowTopic = DatabaseHelper.getInstance(context).getTopic("-1_" + getCurrentUser(context).getId());
            }
            if (topic.isFollow() == isFollow) {
                arrTopic.add(topic);
            }
        }
        Collections.sort(arrTopic, new Comparator<Topic>() {
            @Override
            public int compare(Topic o1, Topic o2) {
                return Long.valueOf(o2.getDate()).compareTo(o1.getDate());
            }
        });

        if (!isFollow && arrTopic.size() != 0) {
            unFollowTopic.setLastMess(arrTopic.get(0).getName() + ": " + arrTopic.get(0).getLastMess());
            unFollowTopic.setDate(arrTopic.get(0).getDate());
            DatabaseHelper.getInstance(context).updateTopic(unFollowTopic);
            linkedHashMapFollowTopic.put(unFollowTopic.getTopicID(), unFollowTopic);
        } else {
            if (unFollowTopic != null) {
                arrTopic.add(0, unFollowTopic);
            }
            if (systemTopic != null) {
                arrTopic.add(0, systemTopic);
            }
        }
        return arrTopic;
    }

    public Topic getTopic(String topicID, Context context) {
        return DatabaseHelper.getInstance(context).getTopic(topicID);
    }

    public boolean updateTopic(Topic topic, Context context) {
        DatabaseHelper.getInstance(context).updateTopic(topic);
        if (topic.isFollow()) {
            linkedHashMapFollowTopic.put(topic.getTopicID(), topic);
        } else {
            linkedHashMapUnfollowTopic.put(topic.getTopicID(), topic);
        }
        return true;
    }

    public boolean deleteTopic(String topicID, Context context) {
        Topic topic = DatabaseHelper.getInstance(context).getTopic(topicID);
        if (topicID.equals("-1_" + getCurrentUser(context).getId())) {
            for (Topic t : DatabaseHelper.getInstance(context).getListTopic(false)) {
                if (!t.isFollow()) {
                    for (String str : splitTopicID(t.getTopicID())) {
                        DatabaseHelper.getInstance(context).deleteAllMessage(str, t.getTopicID());
                    }
                    linkedHashMapUnfollowTopic.remove(t.getTopicID());
                    DatabaseHelper.getInstance(context).deleteTopic(t.getTopicID());
                }
            }
            linkedHashMapFollowTopic.remove("-1_" + getCurrentUser(context).getId());
            return DatabaseHelper.getInstance(context).deleteTopic("-1_" + getCurrentUser(context).getId());
        }
        if (topic.isFollow()) {
            for (String str : splitTopicID(topicID)) {
                DatabaseHelper.getInstance(context).deleteAllMessage(str, topicID);
            }
            linkedHashMapFollowTopic.remove(topicID);
            return DatabaseHelper.getInstance(context).deleteTopic(topicID);
        }
        if (!topic.isFollow()) {
            for (String str : splitTopicID(topicID)) {
                DatabaseHelper.getInstance(context).deleteAllMessage(str, topicID);
            }
            linkedHashMapUnfollowTopic.remove(topicID);
            DatabaseHelper.getInstance(context).deleteTopic(topicID);
            if (DatabaseHelper.getInstance(context).isExistUnfollowTopic() != 0) {
                updateUnfollowTopic(context);
                return true;
            }
        }
        linkedHashMapFollowTopic.remove("-1_" + getCurrentUser(context).getId());
        return DatabaseHelper.getInstance(context).deleteTopic("-1_" + getCurrentUser(context).getId());
    }

    public ArrayList<Topic> getListTopic(boolean isFollow, Context context) {
        if (isFollow) {
            updateUnfollowTopic(context);
            return new ArrayList<>(linkedHashMapFollowTopic.values());
        }
        return new ArrayList<>(linkedHashMapUnfollowTopic.values());
    }

    //update unfollowtopic sau khi delete topic
    void updateUnfollowTopic(Context context) {
        Topic unfollowTopic = linkedHashMapFollowTopic.get("-1_" + getCurrentUser(context).getId());
        Topic newestUnfollowTopic = DatabaseHelper.getInstance(context).getNewestUnfollowTopic();
        if (newestUnfollowTopic.getName() != null) {
            unfollowTopic.setDate(newestUnfollowTopic.getDate());
            unfollowTopic.setLastMess(newestUnfollowTopic.getName() + ": " + newestUnfollowTopic.getLastMess());
            DatabaseHelper.getInstance(context).updateTopic(unfollowTopic);
            linkedHashMapFollowTopic.put(("-1_" + getCurrentUser(context).getId()), unfollowTopic);
        }
    }

    //------------USER

//    public boolean insertUser(User user, Context context){
//        DatabaseHelper.getInstance(context).insertUser(user);
//        return true;
//    }

    public User getUser(String userID, Context context) {
        return DatabaseHelper.getInstance(context).getUser(userID);
    }

    public User getCurrentUser(Context context) {
        return DatabaseHelper.getInstance(context).getUser("5");
    }


    //----------LISTENER
    public interface DataListener {
        void onDataChanged(Topic topic, MessageDetail messageDetail);
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    public boolean isFollow(String topicID) {
        String senderID = splitTopicID(topicID)[0];
        for (String id : followID){
            if (senderID.equals(id)){
                return true;
            }
        }
        return false;
    }

    //-------------ORTHER
    public void followIdol(String IdolID, String userID, Context context) {
        //Gọi lên server, userID theo dõi idolID
        followID.add(IdolID);
        Topic removedTopic = linkedHashMapUnfollowTopic.remove(IdolID + "_" + userID);
        if (removedTopic != null) {
            removedTopic.setFollow(true);
            DatabaseHelper.getInstance(context).updateTopic(removedTopic);

            if (linkedHashMapUnfollowTopic.size() == 0) {
                linkedHashMapFollowTopic.remove("-1_" + getCurrentUser(context).getId());
                deleteTopic("-1_" + getCurrentUser(context).getId(), context);
            }
            linkedHashMapFollowTopic.put(IdolID + "_" + userID, removedTopic);
        }
    }

    public String[] splitTopicID(String topicID) {
        return topicID.split("_");
    }
//    public static Map<Integer, Topic> sortTopicByValue(Map<Integer, Topic> unsortMap) {
//
//        // 1. Convert Map to List of Map
//        Topic topicSystem = unsortMap.get(0);
//        Topic topicUnfollowMsg = unsortMap.get(-1);
//        unsortMap.remove(0);
//        if (topicUnfollowMsg != null){
//            unsortMap.remove(-1);
//        }
//        List<Map.Entry<Integer, Topic>> list = new LinkedList<>(unsortMap.entrySet());
//
//        // 2. Sort list with Collections.sort(), provide a custom Comparator
//        //    Try switch the o1 o2 position for a different order
//        Collections.sort(list, new Comparator<Map.Entry<Integer, Topic>>() {
//            public int compare(Map.Entry<Integer, Topic> o1,
//                               Map.Entry<Integer, Topic> o2) {
//                return (Long.valueOf(o2.getValue().getDate())).compareTo(o1.getValue().getDate());
//            }
//        });
//
//        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
//        Map<Integer, Topic> sortedMap = new LinkedHashMap<Integer, Topic>();
//        if (topicSystem != null){
//            sortedMap.put(topicSystem.getUserId(), topicSystem);
//        }
//        if (topicUnfollowMsg != null){
//            sortedMap.put(topicUnfollowMsg.getUserId(), topicUnfollowMsg);
//        }
//        for (Map.Entry<Integer, Topic> entry : list) {
//            sortedMap.put(entry.getKey(), entry.getValue());
//        }
//
//        return sortedMap;
//    }
}
