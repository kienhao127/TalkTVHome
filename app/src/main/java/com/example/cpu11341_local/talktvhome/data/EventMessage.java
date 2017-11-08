package com.example.cpu11341_local.talktvhome.data;

/**
 * Created by CPU11341-local on 11/8/2017.
 */

public class EventMessage extends MessageDetail {
    String title;
    long eventDatetime;
    String imageURL;
    int action_type; //1: go web, 2: go room
    String action_title;
    String action_extra;

    public EventMessage(){

    }
    public EventMessage(int type, User user, String text, long datetime, String title, long eventDatetime, String imageURL, int action_type, String action_title, String action_extra) {
        super(type, user, text, datetime);
        this.title = title;
        this.eventDatetime = eventDatetime;
        this.imageURL = imageURL;
        this.action_type = action_type;
        this.action_title = action_title;
        this.action_extra = action_extra;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getEventDatetime() {
        return eventDatetime;
    }

    public void setEventDatetime(long eventDatetime) {
        this.eventDatetime = eventDatetime;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getAction_type() {
        return action_type;
    }

    public void setAction_type(int action_type) {
        this.action_type = action_type;
    }

    public String getAction_title() {
        return action_title;
    }

    public void setAction_title(String action_title) {
        this.action_title = action_title;
    }

    public String getAction_extra() {
        return action_extra;
    }

    public void setAction_extra(String action_extra) {
        this.action_extra = action_extra;
    }
}
