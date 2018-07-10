package com.analytics.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Log;

import com.analytics.model.Stats;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatsCount {

    public static long countAllEvents(List<Stats> stats) {
        long res = 0;
        for (Stats token: stats) {
            res++;
        }
        return res;
    }

    public static long countAllEventsByEventName(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getEventname().equals(eventName)) res++;
        }
        return res;
    }

    public static long countTodayEventsByEventName(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getEventname().equals(eventName)) {
                if(isToday(token.getCreatedAt())) res++;
            }
        }
        return res;
    }

    public static long countWeekEventsByEventName(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getEventname().equals(eventName)) {
                if(isThisWeek(token.getCreatedAt())) res++;
            }
        }
        return res;
    }

    public static long countMonthEventsByEventName(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getEventname().equals(eventName)) {
                if(isThisMonth(token.getCreatedAt())) res++;
            }
        }
        return res;
    }

    private static boolean isToday(String date){
        String s = date.substring(0,10);
        Log.println(Log.INFO,"----------------DATE:", s);
        Date today = new Date();
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(today);
        if(modifiedDate.equals(s)) return true;
        return false;
    }


    private static boolean isThisMonth(String date){
        String s = date.substring(0,7);
        Log.println(Log.INFO,"----------------DATE:", s);
        Date today = new Date();
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(today);
        if(modifiedDate.substring(0,7).equals(s)) return true;
        return false;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static boolean isThisWeek(String date){
        Calendar c = Calendar.getInstance();
        String s = date.substring(0,10);
        Log.println(Log.INFO,"----------------DATE:", s);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        try {
            Date source = format.parse(s);
            c.setTime(source);
            int week1 = c.getWeeksInWeekYear();
            c.setTime(new Date());
            int week2 = c.getWeeksInWeekYear();
            if(week1 == week2) return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
