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

import com.example.cpu11341_local.talktvhome.data.EventMessage;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.RemindMessage;
import com.example.cpu11341_local.talktvhome.data.SimpleMessage;
import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.data.User;
import com.example.cpu11341_local.talktvhome.fragment.ChatFragment;
import com.example.cpu11341_local.talktvhome.fragment.TopicFragment;

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
    public ArrayList<MessageDetail> getListMessageFromDB(String topicID, Context context, int loadMoreFrom) {
        ArrayList<MessageDetail> arrMessageDetailOfSender = new ArrayList<>();
        long t = System.currentTimeMillis();
        arrMessageDetailOfSender = DatabaseHelper.getInstance(context).getListMessage(topicID, loadMoreFrom);
        ArrayList<MessageDetail> arrMsgDetail = new ArrayList<>();
        long d = System.currentTimeMillis() - t;
        return arrMessageDetailOfSender;
    }

//    public boolean deleteMessage(int id, Context context) {
//        return DatabaseHelper.getInstance(context).deleteMessage(id);
//    }

    public Topic insertMessage(MessageDetail messageDetail, Context context) {
        DatabaseHelper.getInstance(context).insertMessage(messageDetail);
        String topicID = messageDetail.getTopicID();
        String idolID = splitTopicID(topicID)[0];
        String strText = "";
        User idol = new User();
        if (messageDetail.getType() == 4) {
            strText = "Bạn: " + messageDetail.getText();
            idol = MessageDataManager.getInstance().getUser(idolID, context);
        } else {
            idol = messageDetail.getUser();
            strText = messageDetail.getText();
        }
        Topic topic = DatabaseHelper.getInstance(context).getTopic(topicID);
        // Nếu topic đã tồn tại và chưa theo dõi,
        // Cập nhật lại unfollowTopic
        // Tại mới nếu unfollowTopic chưa có.
        if (topic.getName() != null) {
            topic.setLastMess(strText);
            topic.setDate(messageDetail.getDatetime());
            topic.setHasNewMessage(true);
            DatabaseHelper.getInstance(context).updateTopic(topic);
            if (!topic.isFollow()) {
                Topic unfollowTopic = DatabaseHelper.getInstance(context).getTopic("-1");
                updateUnfollowTopic(unfollowTopic, messageDetail, context, idol.getName(), strText);
            }
        } else {
            // Nếu topic chưa tồn tại => tạo mới,
            // Nếu topic chưa theo dõi
            // Cập nhật lại unfollowTopic
            // Tại mới nếu unfollowTopic chưa có.
            topic = new Topic(idol.getAvatar(), idol.getName(), strText, messageDetail.getDatetime(), 3, topicID, true, false);
            if (isFollow(topic.getTopicID())) {
                topic.setFollow(true);
                updateTopic(topic, context);
            } else {
                Topic unfollowTopic = DatabaseHelper.getInstance(context).getTopic("-1");
                updateUnfollowTopic(unfollowTopic, messageDetail, context, idol.getName(), strText);
            }
            DatabaseHelper.getInstance(context).insertTopic(topic);
        }
        return topic;
    }

    //update unfollowTopic sau khi insert
    void updateUnfollowTopic(Topic unfollowTopic, MessageDetail messageDetail, Context context, String topicName, String strText) {
        if (unfollowTopic.getName() != null) {
            unfollowTopic.setLastMess(topicName + ": " + strText);
            unfollowTopic.setDate(messageDetail.getDatetime());
            unfollowTopic.setHasNewMessage(true);
            DatabaseHelper.getInstance(context).updateTopic(unfollowTopic);
        } else {
            unfollowTopic = new Topic("http://i.imgur.com/xFdNVDs.png", "Tin nhắn",
                    topicName + ": " + strText,
                    messageDetail.getDatetime(), 2, "-1", true, true);
            DatabaseHelper.getInstance(context).insertTopic(unfollowTopic);
        }
    }

    //----------------TOPIC
    public ArrayList<Topic> getListTopic(boolean isFollow, Context context, int loadFrom) {
        if (DatabaseHelper.getInstance(context).getListTopic(loadFrom, isFollow).size() == 0) {
            return null;
        }
        return DatabaseHelper.getInstance(context).getListTopic(loadFrom, isFollow);
    }

    public Topic getTopic(String topicID, Context context) {
        return DatabaseHelper.getInstance(context).getTopic(topicID);
    }

    public boolean updateTopic(Topic topic, Context context) {
        DatabaseHelper.getInstance(context).updateTopic(topic);
        return true;
    }

    public boolean deleteTopic(String topicID, Context context) {
//        Topic topic = DatabaseHelper.getInstance(context).getTopic(topicID);
//        if (topicID.equals("-1")) {
//            for (Topic t : DatabaseHelper.getInstance(context).getListTopic(false)) {
//                if (!t.isFollow()) {
//                    for (String str : splitTopicID(t.getTopicID())) {
//                        DatabaseHelper.getInstance(context).deleteAllMessage(str, t.getTopicID());
//                    }
//                    DatabaseHelper.getInstance(context).deleteTopic(t.getTopicID());
//                }
//            }
//            return DatabaseHelper.getInstance(context).deleteTopic("-1");
//        }
//        if (topic.isFollow()) {
//            for (String str : splitTopicID(topicID)) {
//                DatabaseHelper.getInstance(context).deleteAllMessage(str, topicID);
//            }
//            return DatabaseHelper.getInstance(context).deleteTopic(topicID);
//        }
//        if (!topic.isFollow()) {
//            for (String str : splitTopicID(topicID)) {
//                DatabaseHelper.getInstance(context).deleteAllMessage(str, topicID);
//            }
//            DatabaseHelper.getInstance(context).deleteTopic(topicID);
//            if (DatabaseHelper.getInstance(context).isEsixtUnfollowTopic()) {
//                return true;
//            }
//        }
//        return DatabaseHelper.getInstance(context).deleteTopic("-1");
        return true;
    }

    //update unfollowtopic sau khi delete topic
    void updateUnfollowTopic(Context context) {
        Topic unfollowTopic = DatabaseHelper.getInstance(context).getTopic("-1");
        Topic newestUnfollowTopic = DatabaseHelper.getInstance(context).getNewestUnfollowTopic();
        if (newestUnfollowTopic.getName() != null) {
            unfollowTopic.setDate(newestUnfollowTopic.getDate());
            unfollowTopic.setLastMess(newestUnfollowTopic.getName() + ": " + newestUnfollowTopic.getLastMess());
            DatabaseHelper.getInstance(context).updateTopic(unfollowTopic);
        }
    }

    //------------USER

//    public boolean insertUser(User user, Context context){
//        DatabaseHelper.getInstance(context).insertUser(user);
//        return true;
//    }
//
//    public User getUser(String userID, Context context) {
//        return DatabaseHelper.getInstance(context).getUser(userID);
//    }

    public User getCurrentUser(Context context) {
        return new User("5", "http://is2.mzstatic.com/image/thumb/Purple127/v4/95/75/d9/9575d99b-8854-11cc-25ef-4aa4b4bb6dc3/source/1200x630bb.jpg", "Tui");
    }

    public User getUser(String userID, Context context) {
        return DatabaseHelper.getInstance(context).getUser(userID);
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
        for (String id : followID) {
            if (senderID.equals(id)) {
                return true;
            }
        }
        return false;
    }

    //-------------ORTHER
    public void followIdol(String IdolID, String userID, Context context) {
        //Gọi lên server, userID theo dõi idolID
        followID.add(IdolID);
        Topic removedTopic = DatabaseHelper.getInstance(context).getTopic(IdolID + "_" + userID);
        if (removedTopic != null) {
            removedTopic.setFollow(true);
            DatabaseHelper.getInstance(context).updateTopic(removedTopic);
            if (!DatabaseHelper.getInstance(context).isEsixtUnfollowTopic()) {
                deleteTopic("-1", context);
            } else {
                updateUnfollowTopic(context);
            }
        }
    }

    public String[] splitTopicID(String topicID) {
        return topicID.split("_");
    }
}
