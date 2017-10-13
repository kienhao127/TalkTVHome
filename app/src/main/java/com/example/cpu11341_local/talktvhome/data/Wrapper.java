package com.example.cpu11341_local.talktvhome.data;

import com.example.cpu11341_local.talktvhome.MainActivity;

/**
 * Created by CPU11341-local on 10/13/2017.
 */

public class Wrapper {
    Topic topic;
    MessageDetail messageDetail;

    public Wrapper(Topic topic, MessageDetail messageDetail){
        this.topic = topic;
        this.messageDetail = messageDetail;
    }

    public MessageDetail getMessageDetail() {
        return messageDetail;
    }

    public Topic getTopic() {
        return topic;
    }
}
