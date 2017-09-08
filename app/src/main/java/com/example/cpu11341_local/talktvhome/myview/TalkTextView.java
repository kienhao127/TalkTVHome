package com.example.cpu11341_local.talktvhome.myview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by CPU11341-local on 9/8/2017.
 */

public class TalkTextView extends TextView {
    public TalkTextView(Context context) {
        super(context);
        init();
    }

    public TalkTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TalkTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TalkTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        Typeface typeface;
        this.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Roboto-Regular.ttf"));
    }
}
