package com.example.cpu11341_local.talktvhome.data;

import android.support.annotation.Nullable;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class DocGrid {
    private String imageURL;
    private String channelName;
    private int action_type;
    private Integer roomID;
    private Integer offlineVideoID;

    public DocGrid(String imageURL, String channelName, @Nullable Integer roomID, @Nullable Integer offlineVideoID) {
        this.imageURL = imageURL;
        this.channelName = channelName;
        if (roomID != null) {
            this.action_type = 1;
            this.roomID = roomID;
        }else {
            this.action_type = 2;
            this.offlineVideoID = offlineVideoID;
        }
    }



    public String getImageURL() {
        return imageURL;
    }

    public String getChannelName() {
        return channelName;
    }

    public int getAction_type() {
        return action_type;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getOfflineVideoID() {
        return offlineVideoID;
    }
}
