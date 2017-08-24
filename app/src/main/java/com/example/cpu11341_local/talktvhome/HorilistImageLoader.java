package com.example.cpu11341_local.talktvhome;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by CPU11341-local on 8/23/2017.
 */

public class HorilistImageLoader extends ImageLoader {

    private volatile static HorilistImageLoader instance;

    /** Returns singletone class instance */
    public static HorilistImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new HorilistImageLoader();
                }
            }
        }
        return instance;
    }
}