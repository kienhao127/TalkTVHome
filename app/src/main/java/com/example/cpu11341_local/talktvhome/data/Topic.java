package com.example.cpu11341_local.talktvhome.data;

import java.util.Date;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class Topic {
    User user;
    String lastMess;
    long date;
    int action_type; //1: system, 2: not follow, 3: Chat
    String topicID;
    boolean hasNewMessage;
    boolean isFollow;

    public Topic(User user, String lastMess, long date, int action_type, String topicID, boolean hasNewMessage, boolean isFollow) {
        this.user = user;
        this.lastMess = lastMess;
        this.date = date;
        this.action_type = action_type;
        this.topicID = topicID;
        this.hasNewMessage = hasNewMessage;
        this.isFollow = isFollow;
    }

    public Topic() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLastMess() {
        return lastMess;
    }

    public void setLastMess(String lastMess) {
        this.lastMess = lastMess;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getAction_type() {
        return action_type;
    }

    public void setAction_type(int action_type) {
        this.action_type = action_type;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public boolean isHasNewMessage() {
        return hasNewMessage;
    }

    public void setHasNewMessage(boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
