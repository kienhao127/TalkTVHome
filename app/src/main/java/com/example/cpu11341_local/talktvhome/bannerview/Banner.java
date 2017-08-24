package com.example.cpu11341_local.talktvhome.bannerview;;

/**
 * Created by CPU11341-local on 8/7/2017.
 */

public class Banner {
    private String imageURL;
    private int roomID;
    private int action_type;
    private String link;

    public Banner(String imageURL, int roomID) {
        this.imageURL = imageURL;
        this.roomID = roomID;
        this.action_type = 1;
    }
    public Banner(String imageURL, String link) {
        this.imageURL = imageURL;
        this.link = link;
        this.action_type = 2;
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
