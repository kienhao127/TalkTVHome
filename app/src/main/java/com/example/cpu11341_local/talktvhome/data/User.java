package com.example.cpu11341_local.talktvhome.data;

/**
 * Created by CPU11341-local on 9/6/2017.
 */

public class User {
    String avatar;
    int id;
    String name;
    boolean isFollow;

    public User(int id, String avatar, String name, boolean isFollow) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
        this.isFollow = isFollow;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isFollow() {
        return isFollow;
    }
}
