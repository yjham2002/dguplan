package com.planner.dgu.dguplan;

import android.util.Log;

import java.util.Calendar;

import weekview.WeekViewEvent;

public class ClassInfo {
    public static final int NULLPTR = 100;
    public static final String NULLCLASS = "#";

    public String title = "", location = "", rawtime = "";
    public int wday = NULLPTR, shour = NULLPTR, smin = NULLPTR, ehour = NULLPTR, emin = NULLPTR;

    public ClassInfo(String title, String location, String rawtime, int wday){
        this.title = title;
        this.location = location;
        this.rawtime = rawtime;
        this.wday = wday;
        rawToMean();
        Log.e("Class Info Added >>", "title : " + title + " / location : " + location + " / rawtime :" + rawtime);
    }
    public void rawToMean(){
        if(rawtime.length() < 10) return;
        shour = Integer.parseInt(rawtime.substring(0, 2).trim());
        smin = Integer.parseInt(rawtime.substring(3, 5).trim());
        ehour = Integer.parseInt(rawtime.substring(8, 10).trim());
        emin = Integer.parseInt(rawtime.substring(11, 13).trim());
        Log.e("Class Info - Convert >>", "raw = " + rawtime + " / (" + shour +"):(" + smin + ") / (" + ehour + "):(" + emin + ")" );
    }
    public WeekViewEvent toSimpleEvent(int newYear, int newMonth){
        if(wday == NULLPTR) return null;
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.YEAR, newYear);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.DAY_OF_WEEK, wday);
        startTime.set(Calendar.HOUR_OF_DAY, shour);
        startTime.set(Calendar.MINUTE, smin);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, ehour);
        endTime.set(Calendar.MINUTE, emin);
        return new WeekViewEvent(0, "[" + title + "]\n" + location, startTime, endTime);
    }
}
