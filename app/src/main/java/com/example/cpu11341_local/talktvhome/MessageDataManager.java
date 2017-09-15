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
import java.util.Date;
import java.util.LinkedHashMap;
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

    private static MessageDataManager instance = null;
    protected MessageDataManager() throws ParseException {
        arrMessDetail.add(new MessageDetail(1, 1, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
                "Tên event 1", dateFormat.parse("25/08/17 09:47:02"), "http://talktv.vcdn.vn/talk/mobile/banner/ad_banner_75.jpg", "Mô tả", 1, "Xem chi tiết", "action_extra"));
        arrMessDetail.add(new MessageDetail(2, 2, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
                "Nhắc nhở 1", dateFormat.parse("25/09/17 09:47:02"), "Nội dung nhắc nhở", 1, "Xem chi tiết", "action_extra"));
        arrMessDetail.add(new MessageDetail(3, 3, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
                dateFormat.parse("18/08/17 10:47:03"), "Nội dung thông báo", false));
        linkedHashMapMsgDetail.put(0, new ArrayList<>(arrMessDetail));
        arrMessDetail.clear();

        arrMessDetail.add(new MessageDetail(3, 1, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"),
                dateFormat.parse("18/08/17 10:47:03"), "Xin chào", false));
        arrMessDetail.add(new MessageDetail(3, 2, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"),
                dateFormat.parse("1/09/17 11:47:04"), "Tôi là Thúy Chi", true));
        linkedHashMapMsgDetail.put(1, new ArrayList<>(arrMessDetail));
        arrMessDetail.clear();

        arrMessDetail.add(new MessageDetail(3, 1, new User(2, "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady"),
                dateFormat.parse("11/09/17 12:47:05"), "Tôi là Trang Lady", false));
        linkedHashMapMsgDetail.put(2, new ArrayList<>(arrMessDetail));
        arrMessDetail.clear();

        linkedHashMapTopic.put(0, new Topic("https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV", "Nội dung thông báo", dateFormat.parse("18/08/17 10:47:03"), 1, 0, false));
        linkedHashMapTopic.put(1, new Topic("http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi", "Tôi là Thúy Chi", dateFormat.parse("1/09/17 11:47:04"), 3, 1, false));
        linkedHashMapTopic.put(2, new Topic("http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady", "Tôi là Trang Lady", dateFormat.parse("11/09/17 12:47:05"), 3, 2, true));
        linkedHashMapTopic.put(-1, new Topic("http://i.imgur.com/xFdNVDs.png", "Tin nhắn", "Tên người dùng: tin nhắn cuối cùng", dateFormat.parse("11/09/17 12:47:05"), 2, -1, false));
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

    public ArrayList<MessageDetail> getListMessage(int senderID){
        ArrayList<MessageDetail> arrMessageDetailOfSender = new ArrayList<>();
        for (MessageDetail messageDetail : linkedHashMapMsgDetail.get(senderID)){
            arrMessageDetailOfSender.add(messageDetail);
        }
        return arrMessageDetailOfSender;
    }

    public ArrayList<Topic> getListTopic(boolean isFollow) {
        ArrayList<Topic> arrTopic = new ArrayList<>();

        for (Topic topic : linkedHashMapTopic.values()){
            if (isFollow(topic.getUserId()) == isFollow){
                topic.setDate(topic.getDate());
                arrTopic.add(topic);
            }
        }
        return arrTopic;
    }

    public boolean insertMessage(MessageDetail messageDetail, boolean isSameChatFragment){
        if (linkedHashMapMsgDetail.get(messageDetail.getUser().getId()) != null){
            linkedHashMapMsgDetail.get(messageDetail.getUser().getId()).add(messageDetail);
        } else {
            arrMessDetail.add(messageDetail);
            linkedHashMapMsgDetail.put(messageDetail.getUser().getId(), arrMessDetail);
        }
        updateTopic(messageDetail.getUser().getId(), messageDetail, isSameChatFragment);
        if (dataListener!=null){
            dataListener.onDataChanged(messageDetail.getUser().getId());
        }
        return true;
    }

    public boolean updateTopic(int senderID, MessageDetail messageDetail, boolean isSameChatFragment){
        String strText = "";

        if (messageDetail.getType() == 4){
            strText = "Bạn: " + messageDetail.getText();
        } else {
            strText = messageDetail.getText();
        }

        Topic topic = linkedHashMapTopic.get(senderID);
        if (topic != null){
            topic.setLastMess(strText);
            topic.setDate(messageDetail.getDatetime());
            topic.setHasNewMessage(!isSameChatFragment);
            return true;
        }
        linkedHashMapTopic.put(senderID, new Topic(messageDetail.getUser().getAvatar(), messageDetail.getUser().getName(), strText, messageDetail.getDatetime(), 3, senderID, true));
        return true;
    }

    public interface DataListener{
        void onDataChanged(int senderID);
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
}
