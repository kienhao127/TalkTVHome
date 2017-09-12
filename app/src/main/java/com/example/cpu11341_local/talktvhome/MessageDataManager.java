package com.example.cpu11341_local.talktvhome;

import android.util.Log;

import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.data.User;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/11/2017.
 */

public class MessageDataManager {
    ArrayList<Topic> arrListTopic = new ArrayList<>();
    ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();
    DataListener dataListener;

    private static MessageDataManager instance = null;
    protected MessageDataManager() {
        arrMessDetail.add(new MessageDetail(1, 1, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
                "Tên event 1", "17/08/25 09:47:02", "http://talktv.vcdn.vn/talk/mobile/banner/ad_banner_75.jpg", "Mô tả", 1, "Xem chi tiết", "action_extra"));
        arrMessDetail.add(new MessageDetail(2, 2, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
                "Nhắc nhở 1", "17/09/25 09:47:02", "Nội dung nhắc nhở", 1, "Xem chi tiết", "action_extra"));
        arrMessDetail.add(new MessageDetail(3, 3, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
                "18/08/25 10:47:03", "Nội dung thông báo", false));

        arrMessDetail.add(new MessageDetail(3, 1, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"),
                "18/08/25 10:47:03", "Xin chào", false));
        arrMessDetail.add(new MessageDetail(3, 2, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"),
                "18/08/25 11:47:04", "Tôi là Thúy Chi", true));

        arrMessDetail.add(new MessageDetail(3, 1, new User(2, "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady"),
                "19/08/25 12:47:05", "Tôi là Trang Lady", false));

        arrListTopic.add( new Topic("https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV", "Tin nhắn cuối cùng", "Hôm qua", 1, 0, false));
        arrListTopic.add( new Topic("http://i.imgur.com/xFdNVDs.png", "Tin nhắn", "Tên người dùng: tin nhắn cuối cùng", "Hôm qua", 2, -1, false));
        arrListTopic.add( new Topic("http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi", "Tin nhắn cuối cùng", "Hôm qua", 3, 1, false));
        arrListTopic.add( new Topic("http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady", "Tin nhắn cuối cùng", "Hôm qua", 3, 2, true));
    }

    public static MessageDataManager getInstance() {
        if(instance == null) {
            Log.i("ASDSA", "ASD");
            instance = new MessageDataManager();
        }
        return instance;
    }

    public ArrayList<MessageDetail> getListMessage(int senderID){
        ArrayList<MessageDetail> arrMessageDetailOfSender = new ArrayList<>();
        for (MessageDetail messageDetail : arrMessDetail){
            if (messageDetail.getUser().getId() == senderID){
                arrMessageDetailOfSender.add(messageDetail);
            }
        }
        return arrMessageDetailOfSender;
    }

    public ArrayList<Topic> getListTopic(boolean isFollow){
        ArrayList<Topic> arrTopic = new ArrayList<>();
        if (isFollow){
            for (Topic topic: arrListTopic){
                if (topic.getUserId() != 2){
                    arrTopic.add(topic);
                }
            }
        }
        else {
            for (Topic topic: arrListTopic){
                if (topic.getUserId() == 2){
                    arrTopic.add(topic);
                }
            }
        }
        return arrTopic;
    }

    public boolean insertMessage(MessageDetail messageDetail){
        arrMessDetail.add(messageDetail);
        updateTopic(messageDetail.getUser().getId(), messageDetail);
        if (dataListener!=null){
            dataListener.onDataChanged();
        }
        return true;
    }

    public boolean updateTopic(int senderID, MessageDetail messageDetail){
        for(Topic topic:arrListTopic){
            if (topic.getUserId() == senderID){
                topic.setLastMess(messageDetail.getText());
                topic.setDate(messageDetail.getDatetime());
                topic.setHasNewMessage(true);
            }
        }
        return true;
    }

    public interface DataListener{
        void onDataChanged();
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }
}
