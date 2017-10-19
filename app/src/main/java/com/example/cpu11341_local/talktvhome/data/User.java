package com.example.cpu11341_local.talktvhome.data;

/**
 * Created by CPU11341-local on 9/6/2017.
 */

public class User {
    String avatar;
    String id;
    String name;

    public User(String id, String avatar, String name) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
    }

    public User(){

    }

    public String getAvatar() {
        return avatar;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
