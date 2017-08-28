package com.example.cpu11341_local.talktvhome.data;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 8/21/2017.
 */

public class DocGridWithTitle {
    private DocTitle docTitle;
    private ArrayList<DocGrid> arrDocGrid;

    public DocGridWithTitle(DocTitle docTitle, ArrayList<DocGrid> arrDocGrid) {
        this.docTitle = docTitle;
        this.arrDocGrid = arrDocGrid;
    }

    public DocTitle getDocTitle() {
        return docTitle;
    }

    public ArrayList<DocGrid> getarrDocGrid() {
        return arrDocGrid;
    }
}
