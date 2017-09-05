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

    public Topic(String avatar, String name, String lastMess, String date, int action_type) {
        this.avatar = avatar;
        this.name = name;
        this.lastMess = lastMess;
        this.date = date;
        this.action_type = action_type;
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
}
