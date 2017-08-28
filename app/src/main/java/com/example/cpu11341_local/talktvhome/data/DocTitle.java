package com.example.cpu11341_local.talktvhome.data;

/**
 * Created by CPU11341-local on 8/28/2017.
 */

public class DocTitle {
    private int actionType;
    private int backendId;
    private String pageUrl;
    private int type;
    private String title;

    public DocTitle(int actionType, int backendId, String pageUrl, int type, String title) {
        this.actionType = actionType;
        this.backendId = backendId;
        this.pageUrl = pageUrl;
        this.type = type;
        this.title = title;
    }

    public int getActionType() {
        return actionType;
    }

    public int getBackendId() {
        return backendId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
