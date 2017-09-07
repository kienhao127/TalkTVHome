package com.example.cpu11341_local.talktvhome.data;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class Topic {
    String avatar;
    String name;
    String lastMess;
    String date;
    int action_type; //1: system, 2: not follow, 3: Chat
    int userId;

    public Topic(String avatar, String name, String lastMess, String date, int action_type, int userId) {
        this.avatar = avatar;
        this.name = name;
        this.lastMess = lastMess;
        this.date = date;
        this.action_type = action_type;
        this.userId = userId;
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

    public String getDate() {
        return date;
    }

    public int getAction_type() {
        return action_type;
    }

    public int getUserId() {
        return userId;
    }
}
