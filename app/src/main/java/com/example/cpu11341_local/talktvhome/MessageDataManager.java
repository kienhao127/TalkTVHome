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

/**
 * Created by CPU11341-local on 9/11/2017.
 */

public class MessageDataManager {
    ArrayList<Topic> arrListTopic = new ArrayList<>();
    ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();
    DataListener dataListener;
    DateFormat dateFormat = new SimpleDateFormat("d/MM/yy HH:mm:ss");

    private static MessageDataManager instance = null;
    protected MessageDataManager() {
        arrMessDetail.add(new MessageDetail(1, 1, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
                "Tên event 1", "25/08/17 09:47:02", "http://talktv.vcdn.vn/talk/mobile/banner/ad_banner_75.jpg", "Mô tả", 1, "Xem chi tiết", "action_extra"));
        arrMessDetail.add(new MessageDetail(2, 2, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
                "Nhắc nhở 1", "25/09/17 09:47:02", "Nội dung nhắc nhở", 1, "Xem chi tiết", "action_extra"));
        arrMessDetail.add(new MessageDetail(3, 3, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
                "18/08/17 10:47:03", "Nội dung thông báo", false));

        arrMessDetail.add(new MessageDetail(3, 1, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"),
                "18/08/17 10:47:03", "Xin chào", false));
        arrMessDetail.add(new MessageDetail(3, 2, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"),
                "1/09/17 11:47:04", "Tôi là Thúy Chi", true));

        arrMessDetail.add(new MessageDetail(3, 1, new User(2, "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady"),
                "11/09/17 12:47:05", "Tôi là Trang Lady", false));

        arrListTopic.add( new Topic("https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV", "Nội dung thông báo", "18/08/17 10:47:03", 1, 0, false));
        arrListTopic.add( new Topic("http://i.imgur.com/xFdNVDs.png", "Tin nhắn", "Tên người dùng: tin nhắn cuối cùng", "11/09/17 12:47:05", 2, -1, false));
        arrListTopic.add( new Topic("http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi", "Tôi là Thúy Chi", "1/09/17 11:47:04", 3, 1, false));
        arrListTopic.add( new Topic("http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady", "Tôi là Trang Lady", "11/09/17 12:47:05", 3, 2, true));
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
                    try {
                        Date date = dateFormat.parse(topic.getDate());
                        topic.setDate(ElapsedTime.getRelativeTimeSpanString(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    arrTopic.add(topic);
                }
            }
        }
        else {
            for (Topic topic: arrListTopic){
                if (topic.getUserId() == 2){
                    try {
                        Date date = dateFormat.parse(topic.getDate());
                        topic.setDate(ElapsedTime.getRelativeTimeSpanString(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    arrTopic.add(topic);
                }
            }
        }
        return arrTopic;
    }

    public boolean insertMessage(MessageDetail messageDetail, boolean isSameChatFragment){
        arrMessDetail.add(messageDetail);
        updateTopic(messageDetail.getUser().getId(), messageDetail, isSameChatFragment);
        if (dataListener!=null){
            dataListener.onDataChanged(messageDetail.getUser().getId());
        }
        return true;
    }

    public boolean updateTopic(int senderID, MessageDetail messageDetail, boolean isSameChatFragment){
        String strDate = "";
        String strText = "";
        try {
            Date date = dateFormat.parse(messageDetail.getDatetime());
            strDate = ElapsedTime.getRelativeTimeSpanString(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (messageDetail.getType() == 4){
            strText = "Bạn: " + messageDetail.getText();
        } else {
            strText = messageDetail.getText();
        }
        for (int i=0; i<arrListTopic.size(); i++){
            Topic topic = arrListTopic.get(i);
            Log.i("senderID", String.valueOf(senderID));
            if (topic.getUserId() == senderID){
                topic.setLastMess(strText);
                topic.setDate(strDate);
                topic.setHasNewMessage(!isSameChatFragment);
                arrListTopic.remove(i);
                arrListTopic.add(2, topic);
                return true;
            }
        }
        arrListTopic.add(2, new Topic(messageDetail.getUser().getAvatar(), messageDetail.getUser().getName(), strText, strDate, 3, senderID, true));
        return true;
    }

    public interface DataListener{
        void onDataChanged(int senderID);
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

}
