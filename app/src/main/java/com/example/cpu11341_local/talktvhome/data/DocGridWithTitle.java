package com.example.cpu11341_local.talktvhome.data;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 8/21/2017.
 */

public class DocGridWithTitle {
    private String title;
    private ArrayList<DocGrid> arrDocGrid;

    public DocGridWithTitle(String title, ArrayList<DocGrid> arrDocGrid) {
        this.title = title;
        this.arrDocGrid = arrDocGrid;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<DocGrid> getarrDocGrid() {
        return arrDocGrid;
    }
}
