package com.example.cpu11341_local.talktvhome.bannerview;;

/**
 * Created by CPU11341-local on 8/7/2017.
 */

public class Banner {
    private String imageURL;
    private int roomID;
    private int action_type;
    private String link;
    private int adID;

    public Banner(String imageURL, int roomID, int adID, int action_type) {
        this.imageURL = imageURL;
        this.roomID = roomID;
        this.action_type = action_type;
        this.adID = adID;
    }
    public Banner(String imageURL, String link, int adID, int action_type) {
        this.imageURL = imageURL;
        this.link = link;
        this.action_type = action_type;
        this.adID = adID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getAction_type() {
        return action_type;
    }

    public String getLink() {
        return link;
    }
}
