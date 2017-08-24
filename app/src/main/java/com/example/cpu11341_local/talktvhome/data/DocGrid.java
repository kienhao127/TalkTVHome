package com.example.cpu11341_local.talktvhome.data;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class DocGrid {
    private String imageURL;
    private String channelName;
    private int action_type;

    public DocGrid(String imageURL, String channelName, int action_type) {
        this.imageURL = imageURL;
        this.channelName = channelName;
        this.action_type = action_type;
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
}
