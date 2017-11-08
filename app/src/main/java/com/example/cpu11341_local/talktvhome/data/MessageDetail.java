package com.example.cpu11341_local.talktvhome.data;

import java.util.Date;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class MessageDetail {
    int type; //1: event, 2: remind, 3: message, 4:my message
    int id;
    User user;
    String text;
    long datetime;
    String topicID;

    public MessageDetail(){

    }

    public MessageDetail(int type, User user, String text, long datetime) {
        this.type = type;
        this.user = user;
        this.text = text;
        this.datetime = datetime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }
}
