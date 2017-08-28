package com.example.cpu11341_local.talktvhome.data;

import android.support.annotation.Nullable;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class DocGrid {
    private int actionType;
    private String thumbnail;
    private int viewers;
    private String name;
    private int backendId;
    private String title;
    private int type;
    private int roomType;
    private int roomId;

    public DocGrid(int actionType, String thumbnail, int viewers, String name, int backendId, String title, int type, int roomType, int roomId) {
        this.actionType = actionType;
        this.thumbnail = thumbnail;
        this.viewers = viewers;
        this.name = name;
        this.backendId = backendId;
        this.title = title;
        this.type = type;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public int getActionType() {
        return actionType;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getViewers() {
        return viewers;
    }

    public String getName() {
        return name;
    }

    public int getBackendId() {
        return backendId;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public int getRoomType() {
        return roomType;
    }

    public int getRoomId() {
        return roomId;
    }
}
