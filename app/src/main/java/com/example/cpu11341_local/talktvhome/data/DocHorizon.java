package com.example.cpu11341_local.talktvhome.data;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class DocHorizon {
    private String imgURL;
    private int action_type;
    private String title;

    public DocHorizon(String imgURL, int action_type, String title) {
        this.imgURL = imgURL;
        this.action_type = action_type;
        this.title = title;
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
}
