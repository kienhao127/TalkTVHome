package com.example.cpu11341_local.talktvhome.data;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class MessageDetail {
    String avatar;
    String title;
    String datetime;
    String imageURL;
    String Description;
    String action_title;
    int action_type;
    String action_extra;
    String message;

    public MessageDetail(String title, String datetime, String imageURL, String description, String action_title, int action_type, String action_extra) {
        this.title = title;
        this.datetime = datetime;
        this.imageURL = imageURL;
        Description = description;
        this.action_title = action_title;
        this.action_type = action_type;
        this.action_extra = action_extra;
    }

    public MessageDetail(String avatar, String datetime, String message) {
        this.avatar = avatar;
        this.datetime = datetime;
        this.message = message;
    }

    public MessageDetail(String title, String datetime, String description, String action_title, int action_type, String action_extra) {
        this.title = title;
        this.datetime = datetime;
        Description = description;
        this.action_title = action_title;
        this.action_type = action_type;
        this.action_extra = action_extra;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getTitle() {
        return title;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return Description;
    }

    public String getAction_title() {
        return action_title;
    }

    public int getAction_type() {
        return action_type;
    }

    public String getAction_extra() {
        return action_extra;
    }

    public String getMessage() {
        return message;
    }
}
