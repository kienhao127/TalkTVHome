package com.example.cpu11341_local.talktvhome.data;

/**
 * Created by CPU11341-local on 11/8/2017.
 */

public class SimpleMessage extends MessageDetail {
    boolean isWarning;

    public SimpleMessage() {
    }

    public SimpleMessage(int type, User user, String text, long datetime, boolean isWarning) {
        super(type, user, text, datetime);
        this.isWarning = isWarning;
    }

    public boolean isWarning() {
        return isWarning;
    }

    public void setWarning(boolean warning) {
        isWarning = warning;
    }
}
