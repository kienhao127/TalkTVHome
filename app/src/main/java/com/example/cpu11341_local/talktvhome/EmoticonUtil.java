package com.example.cpu11341_local.talktvhome;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
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
    private static EmoticonUtil instance = null;
    public ArrayList<LinkedHashMap<String, Integer>> emoticonsList = new ArrayList<>();
    public ArrayList<Integer> emoticonsTitle = new ArrayList<>();

    protected EmoticonUtil(){
        emoticonsTitle.add(R.drawable.smiley_0);
        emoticonsTitle.add(R.drawable.smiley_1);

        LinkedHashMap<String, Integer> smileyEmoticons = new LinkedHashMap<>();
        smileyEmoticons.put(":-o", R.drawable.smiley_3);
        smileyEmoticons.put(":-8", R.drawable.smiley_4);
        smileyEmoticons.put(":((", R.drawable.smiley_5);
        emoticonsList.add(smileyEmoticons);

        LinkedHashMap<String, Integer> drinkEmoticons = new LinkedHashMap<>();
        drinkEmoticons.put(":)", R.drawable.smiley_6);
        drinkEmoticons.put(":-x", R.drawable.smiley_7);
        drinkEmoticons.put(":-z", R.drawable.smiley_8);
        drinkEmoticons.put(":-(", R.drawable.smiley_9);
        emoticonsList.add(drinkEmoticons);
    }

    public static EmoticonUtil getInstance() {
        if (instance == null) {
            try {
                instance = new EmoticonUtil();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public LinkedHashMap<String, Integer> getEmoticons(int catID){
        return emoticonsList.get(catID);
    }

    public Spannable getSmiledText(String text, Context context) {
        ArrayList<Integer> starts = new ArrayList<>();
        ArrayList<Integer> ends = new ArrayList<>();
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
            for (LinkedHashMap<String, Integer> emoticons : emoticonsList) {
                try {
                    drawable = context.getDrawable(emoticons.get(iconString));
                } catch (NullPointerException e) {

                }
            }
            if (drawable != null) {
                drawable.setBounds(0, 0, (int) pxFromDp(context, 20), (int) pxFromDp(context, 20));
                builder.setSpan(new ImageSpan(drawable), starts.get(i), ends.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }

    public float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
