package com.example.cpu11341_local.talktvhome;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by CPU11341-local on 11/15/2017.
 */

public class EmoticonUtil {
    static LinkedHashMap<String, Integer> emoticons = new LinkedHashMap<String, Integer>();

    public static LinkedHashMap<String, Integer> getSmiley(){
        emoticons.put(":-o", R.drawable.smiley_3);
        emoticons.put(":-8", R.drawable.smiley_4);
        emoticons.put(":((", R.drawable.smiley_5);
        emoticons.put(":)", R.drawable.smiley_6);
        emoticons.put(":-x", R.drawable.smiley_7);
        emoticons.put(":-z", R.drawable.smiley_8);
        emoticons.put(":-(", R.drawable.smiley_9);
        return emoticons;
    }

    public static Spannable getSmiledText(String text, Context context) {
        getSmiley();
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        if (emoticons.size() > 0) {
            int index;
            for (index = 0; index < builder.length(); index++) {
                if (Character.toString(builder.charAt(index)).equals(":")) {
                    for (Map.Entry<String, Integer> entry : emoticons.entrySet()) {
                        int length = entry.getKey().length();
                        if (index + length > builder.length())
                            continue;
                        if (builder.subSequence(index, index + length).toString().equals(entry.getKey())) {
                            Drawable drawable = context.getDrawable(entry.getValue());
                            drawable.setBounds(0, 0, (int) pxFromDp(context, 20), (int) pxFromDp(context, 20));
                            builder.setSpan(new ImageSpan(drawable), index, index + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            index += length - 1;
                            break;
                        }
                    }
                }
            }
        }
        return builder;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
