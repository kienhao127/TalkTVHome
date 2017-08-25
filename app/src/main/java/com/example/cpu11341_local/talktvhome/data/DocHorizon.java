package com.example.cpu11341_local.talktvhome.data;

import android.support.annotation.Nullable;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class DocHorizon {
    private String imgURL;
    private int action_type;
    private String title;
    private int roomID;
    private int categoryID;

    public DocHorizon(String imgURL, String title, @Nullable Integer roomID, @Nullable Integer categoryID) {
        this.imgURL = imgURL;
        this.title = title;
        if (roomID != null){
            this.action_type = 1;
            this.roomID = roomID;
        } else {
            this.action_type = 2;
            this.categoryID = categoryID;
        }
    }

    public String getImgURL() {
        return imgURL;
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

    public int getCategoryID() {
        return categoryID;
    }
}
