package com.example.cpu11341_local.talktvhome.data;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class TabData {
    private int tabID;
    private String title;
    private doctype[] tabDocType;

    public TabData(int tabID, String title, doctype[] tabDocType) {
        this.tabID = tabID;
        this.title = title;
        this.tabDocType = tabDocType;
    }

    public int getTabID() {
        return tabID;
    }

    public void setTabID(int tabID) {
        this.tabID = tabID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public doctype[] getTabDocType() {
        return tabDocType;
    }

    public void setTabDocType(doctype[] tabDocType) {
        this.tabDocType = tabDocType;
    }
}
