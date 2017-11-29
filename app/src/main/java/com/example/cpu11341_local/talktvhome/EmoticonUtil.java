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
    LinkedHashMap<String, Integer> recentEmoticons = new LinkedHashMap<>();

    protected EmoticonUtil(){
        emoticonsTitle.add(R.drawable.emoji_recent_focus);
        emoticonsTitle.add(R.drawable.smiley_0);
        emoticonsTitle.add(R.drawable.smiley_1);

        emoticonsList.add(recentEmoticons);

        LinkedHashMap<String, Integer> smileyEmoticons = new LinkedHashMap<>();
        smileyEmoticons.put(":-o:", R.drawable.smiley_3);
        smileyEmoticons.put(":-8:", R.drawable.smiley_4);
        smileyEmoticons.put(":((:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o1:", R.drawable.smiley_3);
        smileyEmoticons.put(":-81:", R.drawable.smiley_4);
        smileyEmoticons.put(":((1:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o2:", R.drawable.smiley_3);
        smileyEmoticons.put(":-82:", R.drawable.smiley_4);
        smileyEmoticons.put(":((2:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o3:", R.drawable.smiley_3);
        smileyEmoticons.put(":-83:", R.drawable.smiley_4);
        smileyEmoticons.put(":((3:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o4:", R.drawable.smiley_3);
        smileyEmoticons.put(":-84:", R.drawable.smiley_4);
        smileyEmoticons.put(":((4:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o5:", R.drawable.smiley_3);
        smileyEmoticons.put(":-85:", R.drawable.smiley_4);
        smileyEmoticons.put(":((5:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o6:", R.drawable.smiley_3);
        smileyEmoticons.put(":-86:", R.drawable.smiley_4);
        smileyEmoticons.put(":((6:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o7:", R.drawable.smiley_3);
        smileyEmoticons.put(":-87:", R.drawable.smiley_4);
        smileyEmoticons.put(":((7:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o8:", R.drawable.smiley_3);
        smileyEmoticons.put(":-88:", R.drawable.smiley_4);
        smileyEmoticons.put(":((8:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o9:", R.drawable.smiley_3);
        smileyEmoticons.put(":-89:", R.drawable.smiley_4);
        smileyEmoticons.put(":((9:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o10:", R.drawable.smiley_3);
        smileyEmoticons.put(":-810:", R.drawable.smiley_4);
        smileyEmoticons.put(":((10:", R.drawable.smiley_5);
        smileyEmoticons.put(":-o11:", R.drawable.smiley_3);
        smileyEmoticons.put(":-811:", R.drawable.smiley_4);
        smileyEmoticons.put(":((11:", R.drawable.smiley_5);
        smileyEmoticons.put(":1-o:", R.drawable.smiley_3);
        smileyEmoticons.put(":1-8:", R.drawable.smiley_4);
        smileyEmoticons.put(":1((:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o1:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-81:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((1:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o2:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-82:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((2:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o3:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-83:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((3:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o4:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-84:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((4:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o5:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-85:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((5:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o6:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-86:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((6:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o7:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-87:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((7:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o8:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-88:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((8:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o9:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-89:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((9:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o10:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-810:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((10:", R.drawable.smiley_5);
        smileyEmoticons.put(":2-o11:", R.drawable.smiley_3);
        smileyEmoticons.put(":2-811:", R.drawable.smiley_4);
        smileyEmoticons.put(":2((11:", R.drawable.smiley_5);
        emoticonsList.add(smileyEmoticons);

        LinkedHashMap<String, Integer> drinkEmoticons = new LinkedHashMap<>();
        drinkEmoticons.put(":):", R.drawable.smiley_6);
        drinkEmoticons.put(":-x:", R.drawable.smiley_7);
        drinkEmoticons.put(":-z:", R.drawable.smiley_8);
        drinkEmoticons.put(":-(:", R.drawable.smiley_9);
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
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        String regex = "\\:[^;: ]{1,3}\\:";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){

            String iconKey = matcher.group();
            Drawable drawable = null;
            Integer iconValue = 0;
            for (LinkedHashMap<String, Integer> emoticons : emoticonsList) {
                iconValue = emoticons.get(iconKey);
                if (iconValue != null){
                    drawable = context.getDrawable(iconValue);
                    drawable.setBounds(0, 0, (int) pxFromDp(context, 20), (int) pxFromDp(context, 20));
                    builder.setSpan(new ImageSpan(drawable), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                }
            }
        }
        return builder;
    }

    public float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
