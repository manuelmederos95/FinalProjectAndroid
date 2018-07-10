package com.analytics.utils;

import com.analytics.model.Stats;

import java.util.List;

import static com.analytics.utils.DatesUtils.isThisMonth;
import static com.analytics.utils.DatesUtils.isThisWeek;
import static com.analytics.utils.DatesUtils.isToday;

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

    public static long countAllCategoriesByCategory(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getCategory().equals(eventName)) res++;
        }
        return res;
    }

    public static long countAllActionsByAction(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getAction().equals(eventName)) res++;
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

    public static long countTodayCategoriesByCategory(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getCategory().equals(eventName)) {
                if(isToday(token.getCreatedAt())) res++;
            }
        }
        return res;
    }

    public static long countTodayActionsByAction(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getAction().equals(eventName)) {
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

    public static long countWeekCategoriesByCategory(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getCategory().equals(eventName)) {
                if(isThisWeek(token.getCreatedAt())) res++;
            }
        }
        return res;
    }

    public static long countWeekActionsByAction(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getAction().equals(eventName)) {
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

    public static long countMonthCategoriesByCategory(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getCategory().equals(eventName)) {
                if(isThisMonth(token.getCreatedAt())) res++;
            }
        }
        return res;
    }

    public static long countMonthActionsByAction(List<Stats> stats, String eventName) {
        long res = 0;
        for (Stats token: stats) {
            if(token.getAction().equals(eventName)) {
                if(isThisMonth(token.getCreatedAt())) res++;
            }
        }
        return res;
    }


}
