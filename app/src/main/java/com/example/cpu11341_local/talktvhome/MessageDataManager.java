package com.example.cpu11341_local.talktvhome;

import android.content.Context;
import android.util.Log;

import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.data.User;
import com.example.cpu11341_local.talktvhome.helper.DatabaseHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by CPU11341-local on 9/11/2017.
 */

public class MessageDataManager {
    public DataListener dataListener;
    private static MessageDataManager instance = null;

    protected MessageDataManager() throws ParseException {
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

    //-----------RECENT EMOTICON
    public void insertRecentEmoticon(String key, int value, Context context){
        long t = System.currentTimeMillis();
        if (!DatabaseHelper.getInstance(context).updateRecentEmoticon(key, t)){
            DatabaseHelper.getInstance(context).insertRecentEmoticon(key, value, t);
            deleteRecentEmoticon(context);
        }
    }

    public void deleteRecentEmoticon(Context context){
        int deleteRow = DatabaseHelper.getInstance(context).totalRowInRecentEmoticonTable() - 45;
        if (deleteRow >= 1) {
            DatabaseHelper.getInstance(context).deleteRecentEmoticon(deleteRow);
        }
    }

    public LinkedHashMap<String, Integer> getListRecentEmoticon(Context context){
        return DatabaseHelper.getInstance(context).getListRecentEmoticon();
    }
    //-----------MESSAGE
    public ArrayList<MessageDetail> getListMessageFromDB(String topicID, Context context, int loadMoreFrom) {
        ArrayList<MessageDetail> arrMessageDetailOfSender = DatabaseHelper.getInstance(context).getListMessage(topicID, loadMoreFrom);
        return arrMessageDetailOfSender;
    }

    public void deleteMessage(int id, Context context) {
        DatabaseHelper.getInstance(context).deleteMessage(id);
    }

    public Topic insertMessage(MessageDetail messageDetail, Context context) {
        DatabaseHelper.getInstance(context).insertMessage(messageDetail);
        insertUser(messageDetail.getUser(), context);

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
        // Tạo mới nếu unfollowTopic chưa có.
        if (topic.getUser() != null) {
            topic.setLastMess(strText);
            topic.setDate(messageDetail.getDatetime());
            topic.setHasNewMessage(true);
            DatabaseHelper.getInstance(context).updateTopic(topic);
            if (!topic.isFollow()) {
                updateUnfollowTopic(messageDetail, context, idol.getName(), strText);
            }
        } else {
            // Nếu topic chưa tồn tại => tạo mới,
            // Nếu topic chưa theo dõi
            // Cập nhật lại unfollowTopic
            // Tạo mới nếu unfollowTopic chưa có.
            topic = new Topic(idol, strText, messageDetail.getDatetime(), 3, topicID, true, false);
            if (messageDetail.getUser().getId() == "0"){
                topic.setFollow(true);
            } else {
                if (isFollow(topic.getTopicID(), context)) {
                    topic.setFollow(true);
                    updateTopic(topic, context);
                } else {
                    updateUnfollowTopic(messageDetail, context, idol.getName(), strText);
                }
            }
            DatabaseHelper.getInstance(context).insertTopic(topic);
        }
        return topic;
    }

    //update unfollowTopic sau khi insert
    void updateUnfollowTopic(MessageDetail messageDetail, Context context, String topicName, String strText) {
        Topic unfollowTopic = DatabaseHelper.getInstance(context).getTopic("-1");
        if (unfollowTopic.getUser() != null) {
            unfollowTopic.setLastMess(topicName + ": " + strText);
            unfollowTopic.setDate(messageDetail.getDatetime());
            unfollowTopic.setHasNewMessage(true);
            DatabaseHelper.getInstance(context).updateTopic(unfollowTopic);
        } else {
            User tempUser = new User ("-1", "http://i.imgur.com/xFdNVDs.png", "Tin nhắn");
            DatabaseHelper.getInstance(context).insertUser(tempUser);
            unfollowTopic = new Topic(tempUser,
                    topicName + ": " + strText,
                    messageDetail.getDatetime(), 2, "-1", true, true);
            DatabaseHelper.getInstance(context).insertTopic(unfollowTopic);
        }
    }

    //----------------TOPIC
    public ArrayList<Topic> getListTopic(boolean isFollow, Context context, int loadFrom) {
        return DatabaseHelper.getInstance(context).getListTopic(loadFrom, isFollow, 60);
    }

    public ArrayList<Topic> loadMoreTopic(boolean isFollow, Context context, int loadFrom) {
        return DatabaseHelper.getInstance(context).getListTopic(loadFrom, isFollow, 60);
    }

    public Topic getTopic(String topicID, Context context) {
        return DatabaseHelper.getInstance(context).getTopic(topicID);
    }

    public boolean updateTopic(Topic topic, Context context) {
        return DatabaseHelper.getInstance(context).updateTopic(topic);
    }

    public boolean deleteTopic(String topicID, Context context) {
        Topic topic = DatabaseHelper.getInstance(context).getTopic(topicID);
        if (topicID.equals("-1")) {
            for (Topic t : DatabaseHelper.getInstance(context).getListTopic(false)) {
                DatabaseHelper.getInstance(context).deleteAllMessage(t.getTopicID());
                DatabaseHelper.getInstance(context).deleteTopic(t.getTopicID());
            }
            return DatabaseHelper.getInstance(context).deleteTopic("-1");
        }
        if (topic.isFollow()) {
            DatabaseHelper.getInstance(context).deleteAllMessage(topicID);
            return DatabaseHelper.getInstance(context).deleteTopic(topicID);
        }
        if (!topic.isFollow()) {
            DatabaseHelper.getInstance(context).deleteAllMessage(topicID);
            DatabaseHelper.getInstance(context).deleteTopic(topicID);
            updateUnfollowTopic(context);
            if (DatabaseHelper.getInstance(context).isEsixtUnfollowTopic()) {
                return true;
            }
        }
        return DatabaseHelper.getInstance(context).deleteTopic("-1");
    }

    //update unfollowtopic sau khi delete topic
    void updateUnfollowTopic(Context context) {
        Topic unfollowTopic = DatabaseHelper.getInstance(context).getTopic("-1");
        Topic newestUnfollowTopic = DatabaseHelper.getInstance(context).getNewestUnfollowTopic();
        if (newestUnfollowTopic.getUser() != null) {
            unfollowTopic.setDate(newestUnfollowTopic.getDate());
            unfollowTopic.setLastMess(newestUnfollowTopic.getUser().getName() + ": " + newestUnfollowTopic.getLastMess());
            DatabaseHelper.getInstance(context).updateTopic(unfollowTopic);
        }
    }

    //------------USER

    public boolean insertUser(User user, Context context){
        DatabaseHelper.getInstance(context).insertUser(user);
        return true;
    }
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

    public boolean isFollow(String topicID, Context context) {
        Topic topic = getTopic(topicID, context);
        if (topic.getUser() !=null && topic.isFollow()){
            return true;
        }
        String idolID = splitTopicID(topicID)[0];
        //Thiếu hàm gọi lên server kiểm tra idol đã được theo dõi chưa
        //Nếu có return true;
        //Ngược lại, return false;
        return false;
    }

    //-------------ORTHER
    public void followIdol(String IdolID, String userID, Context context) {
        //Gọi lên server, userID theo dõi idolID
        Topic removedTopic = DatabaseHelper.getInstance(context).getTopic(IdolID + "_" + userID);
        if (removedTopic.getUser() != null) {
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
