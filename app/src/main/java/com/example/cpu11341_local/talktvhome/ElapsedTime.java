package com.example.cpu11341_local.talktvhome;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.text.DateFormat;
import android.icu.util.TimeZone;
import android.text.format.DateUtils;

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

        DateFormat df = new SimpleDateFormat("d/MM/yy HH:mm");

        if (elapsedDays < 1) {
            DateFormat sameDayDateFormat = new SimpleDateFormat("HH:mm");
            return sameDayDateFormat.format(date);
        }
        if (elapsedDays <= 7 && elapsedDays >= 1) {
            DateFormat sameWeekDateFormat = new SimpleDateFormat("EEEE HH:mm");
            return sameWeekDateFormat.format(date);
        }

        if (elapsedDays > 7) {
            DateFormat dateFormat = new SimpleDateFormat("yy");
            if (dateFormat.format(currentTime).equals(dateFormat.format(date))) {
                DateFormat sameYearDateFormat = new SimpleDateFormat("d/MM HH:mm");
                return sameYearDateFormat.format(date);
            }
            else {
                DateFormat diffYearDateFormat = new SimpleDateFormat("d/MM/yy HH:mm");
                return diffYearDateFormat.format(date);
            }
        }
        return df.format(date);
    }

    public static int getDayOfDate(long date){
        DateFormat df = new SimpleDateFormat("d");
        return Integer.valueOf(df.format(date));
    }
}
