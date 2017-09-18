package com.example.cpu11341_local.talktvhome.data;

import java.util.Date;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class Topic {
    String avatar;
    String name;
    String lastMess;
    long date;
    int action_type; //1: system, 2: not follow, 3: Chat
    int userId;
    boolean hasNewMessage;

    public Topic(String avatar, String name, String lastMess, long date, int action_type, int userId, boolean hasNewMessage) {
        this.avatar = avatar;
        this.name = name;
        this.lastMess = lastMess;
        this.date = date;
        this.action_type = action_type;
        this.userId = userId;
        this.hasNewMessage = hasNewMessage;
    }


    public boolean isHasNewMessage() {
        return hasNewMessage;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getLastMess() {
        return lastMess;
    }

    public long getDate() {
        return date;
    }

    public int getAction_type() {
        return action_type;
    }

    public int getUserId() {
        return userId;
    }

    public void setHasNewMessage(boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }

    public void setLastMess(String lastMess) {
        this.lastMess = lastMess;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
