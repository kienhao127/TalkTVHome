package com.example.cpu11341_local.talktvhome.data;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class TabData {
    private int tabID;
    private String title;
    private int[] tabDocType;

    public TabData(int tabID, String title, int[] tabDocType) {
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

    public int[] getTabDocType() {
        return tabDocType;
    }

    public void setTabDocType(int[] tabDocType) {
        this.tabDocType = tabDocType;
    }
}
