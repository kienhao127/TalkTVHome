package com.example.cpu11341_local.talktvhome;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        ArrayList<Integer> starts = new ArrayList<>();
        ArrayList<Integer> ends = new ArrayList<>();
        getSmiley();
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        String regex = "\\:[^;: ][^;: ]?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            starts.add(matcher.start());
            ends.add(matcher.end());
        }
        for (int i=0; i < starts.size(); i++){
            String iconString = text.substring(starts.get(i), ends.get(i));
            Drawable drawable = null;
            try{
                drawable = context.getDrawable(emoticons.get(iconString));
            } catch (NullPointerException e){
                
            }
            if (drawable != null) {
                drawable.setBounds(0, 0, (int) pxFromDp(context, 20), (int) pxFromDp(context, 20));
                builder.setSpan(new ImageSpan(drawable), starts.get(i), ends.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
