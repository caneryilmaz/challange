package com.cnrylmz.challengemobilist.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Caner on 08.05.2019.
 */

public final class DateUtil {

    public static String covertTimeToText(String createdAt) {

        String convTime = null;

        String prefix = "";
        String suffix = "Ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pasTime = dateFormat.parse(createdAt);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " Seconds " + suffix;
            } else if (minute < 60) {
                convTime = minute + " Minutes " + suffix;
            } else if (hour < 24) {
                convTime = hour + " Hours " + suffix;
            } else if (day >= 7) {
                if (day > 30) {
                    convTime = (day / 30) + " Months " + suffix;
                } else if (day > 360) {
                    convTime = (day / 360) + " Years " + suffix;
                } else {
                    convTime = (day / 7) + " Week " + suffix;
                }
            } else if (day < 7) {
                convTime = day + " Days " + suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;

    }

    public static float toNumber(Date now) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(now);
        int hour = c.get(Calendar.HOUR_OF_DAY);// 0-23
        int minute = c.get(Calendar.MINUTE);// 0-59

        return toNumber(hour, minute);
    }

    private static float toNumber(int hour, int minute) {
        return hour + minute / 60f;
    }

}