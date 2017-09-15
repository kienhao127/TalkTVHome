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
    public static String getRelativeTimeSpanString(Date date) {
        Date currentTime = Calendar.getInstance().getTime();
        long different = currentTime.getTime() - date.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;

        DateFormat df = new SimpleDateFormat("d/MM/yy HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));

        if (elapsedDays < 1) {
            DateFormat sameDayDateFormat = new SimpleDateFormat("HH:mm");
            sameDayDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            return sameDayDateFormat.format(date);
        }
        if (elapsedDays <= 7 && elapsedDays >= 1) {
            DateFormat sameWeekDateFormat = new SimpleDateFormat("EEEE HH:mm");
            sameWeekDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            return sameWeekDateFormat.format(date);
        }

        if (elapsedDays > 7) {
            DateFormat dateFormat = new SimpleDateFormat("yy");
            if (dateFormat.format(currentTime).equals(dateFormat.format(date))) {
                DateFormat sameYearDateFormat = new SimpleDateFormat("d/MM HH:mm");
                sameYearDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
                return sameYearDateFormat.format(date);
            }
            else {
                DateFormat diffYearDateFormat = new SimpleDateFormat("d/MM/yy HH:mm");
                diffYearDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
                return diffYearDateFormat.format(date);
            }
        }
        return df.format(date);
    }
}
