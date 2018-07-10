package com.analytics.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatesUtils {

    public static boolean isToday(String date){
        String s = date.substring(0,10);
        Log.println(Log.INFO,"----------------DATE:", s);
        Date today = new Date();
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(today);
        if(modifiedDate.equals(s)) return true;
        return false;
    }


    public static boolean isThisMonth(String date){
        String s = date.substring(0,7);
        Log.println(Log.INFO,"----------------DATE:", s);
        Date today = new Date();
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(today);
        if(modifiedDate.substring(0,7).equals(s)) return true;
        return false;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static boolean isThisWeek(String date){
        Calendar c = Calendar.getInstance();
        String s = date.substring(0,10);
        Log.println(Log.INFO,"----------------DATE:", s);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date source = format.parse(s);
            c.setTime(source);
            int week1 = c.getWeeksInWeekYear();
            Date today = new Date();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(today);
            int week2 = c2.getWeeksInWeekYear();
            Log.println(Log.INFO,"----------------WEEK1:", String.valueOf(week1));
            Log.println(Log.INFO,"----------------WEE2K:", String.valueOf(week2));
            if(week1 == week2) return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
