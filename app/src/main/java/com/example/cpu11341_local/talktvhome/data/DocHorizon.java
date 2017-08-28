package com.example.cpu11341_local.talktvhome.data;

import android.support.annotation.Nullable;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class DocHorizon {
    private String avatar;
    private int action_type;
    private String title;
    private int roomID;
    private String pageUrl;
    private int backendId;
    private int type;
    private int roomType;

    public DocHorizon(int action_type, int backendId, String pageUrl, String avatar, String title, int type) {
        this.avatar = avatar;
        this.title = title;
        this.action_type = action_type;
        this.backendId = backendId;
        this.pageUrl = pageUrl;
        this.type = type;
    }

    public DocHorizon(int action_type, int backendId, String avatar, String title, int type, int roomType, int roomId) {
        this.avatar = avatar;
        this.title = title;
        this.action_type = action_type;
        this.backendId = backendId;
        this.type = type;
        this.roomID = roomId;
        this.roomType = roomType;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getAction_type() {
        return action_type;
    }

    public String getTitle() {
        return title;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public int getBackendId() {
        return backendId;
    }

    public int getType() {
        return type;
    }

    public int getRoomType() {
        return roomType;
    }
}
