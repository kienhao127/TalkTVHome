package com.example.cpu11341_local.talktvhome;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.text.DateFormat;
import android.icu.util.TimeZone;
import android.text.format.DateUtils;
import android.util.Log;

import java.util.Date;

/**
 * Created by CPU11341-local on 9/13/2017.
 */
public class ElapsedTime {
    public static String getRelativeTimeSpanString(long date) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long different = currentTime - date;

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;

        SimpleDateFormat df = new SimpleDateFormat("d/MM/yy HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        if (elapsedDays < 1) {
            SimpleDateFormat  sameDayDateFormat = new SimpleDateFormat("HH:mm");
            sameDayDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            return sameDayDateFormat.format(date);
        }
        if (elapsedDays <= 7 && elapsedDays >= 1) {
            SimpleDateFormat sameWeekDateFormat = new SimpleDateFormat("EEEE HH:mm");
            sameWeekDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            return sameWeekDateFormat.format(date);
        }

        if (elapsedDays > 7) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            if (dateFormat.format(currentTime).equals(dateFormat.format(date))) {
                SimpleDateFormat sameYearDateFormat = new SimpleDateFormat("d/MM HH:mm");
                sameYearDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
                return sameYearDateFormat.format(date);
            }
            else {
                SimpleDateFormat diffYearDateFormat = new SimpleDateFormat("d/MM/yy HH:mm");
                diffYearDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
                return diffYearDateFormat.format(date);
            }
        }
        return df.format(date);
    }

    public static int getDayOfDate(long date){
        SimpleDateFormat df = new SimpleDateFormat("d");
        df.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        return Integer.valueOf(df.format(date));
    }
}
