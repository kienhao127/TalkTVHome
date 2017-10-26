package com.example.cpu11341_local.talktvhome.data;

import java.util.Date;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class MessageDetail {
    int type; //1: event, 2: remind, 3: message, 4:my message
    int id;
    User user;
    String title;
    long datetime;
    long eventDatetime;
    String imageURL;
    String text;
    int action_type; //1: go web, 2: go room
    String action_title;
    String action_extra;
    boolean isWarning;
    String topicID;

    public MessageDetail(){

    }

    //Event
    public MessageDetail(int type, User user, String title, long datetime, long eventDatetime, String imageURL, String text, int action_type, String action_title, String action_extra) {
        this.user = user;
        this.title = title;
        this.datetime = datetime;
        this.eventDatetime = eventDatetime;
        this.imageURL = imageURL;
        this.text = text;
        this.action_type = action_type;
        this.action_title = action_title;
        this.action_extra = action_extra;
        this.type = type;
    }

    //Remind
    public MessageDetail(int type, User user, String title, long datetime, long eventDatetime, String text, int action_type, String action_title, String action_extra) {
        this.user = user;
        this.title = title;
        this.datetime = datetime;
        this.eventDatetime = eventDatetime;
        this.text = text;
        this.action_type = action_type;
        this.action_title = action_title;
        this.action_extra = action_extra;
        this.type = type;
    }

    //Message
    public MessageDetail(int type, User user, long datetime, String text, boolean isWarning) {
        this.user = user;
        this.datetime = datetime;
        this.text = text;
        this.type = type;
        this.isWarning = isWarning;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public long getDatetime() {
        return datetime;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getText() {
        return text;
    }

    public int getAction_type() {
        return action_type;
    }

    public String getAction_title() {
        return action_title;
    }

    public String getAction_extra() {
        return action_extra;
    }

    public int getType() {
        return type;
    }

    public boolean isWarning() {
        return isWarning;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAction_type(int action_type) {
        this.action_type = action_type;
    }

    public void setAction_title(String action_title) {
        this.action_title = action_title;
    }

    public void setAction_extra(String action_extra) {
        this.action_extra = action_extra;
    }

    public void setWarning(boolean warning) {
        isWarning = warning;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public long getEventDatetime() {
        return eventDatetime;
    }

    public void setEventDatetime(long eventDatetime) {
        this.eventDatetime = eventDatetime;
    }
}
