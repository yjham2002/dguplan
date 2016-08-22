package com.planner.dgu.dguplan;

import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import util.SFCallback;
import util.userDTO;
import weekview.MonthLoader;
import weekview.WeekView;
import weekview.WeekViewEvent;

public class MainActivity extends BaseActivity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, View.OnClickListener{

    private userDTO session;
    private WeekView mWeekView;

    @Override
    public void onClick(View v){
        switch(v.getId()){
            default: break;
        }
    }
    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
    }
    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
    }
    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = getEvents(newYear, newMonth);
        return events;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setMonthChangeListener(this);

        session = new userDTO(new SFCallback(){
            public void callback(){
                mWeekView.notifyDatasetChanged();
            }
        });
        session.execute();
    }

    public List<WeekViewEvent> getEvents(int newYear, int newMonth){
        List<WeekViewEvent> events = new ArrayList<>();
        Calendar cal = new GregorianCalendar(newYear, newMonth - 1, 1);

/*
        while (cal.get(Calendar.MONTH) == newMonth - 1) {
            int day = cal.get(Calendar.DAY_OF_WEEK);
            for(ClassInfo dclass : session.days.get(day)) {
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.YEAR, newYear);
                startTime.set(Calendar.MONTH, newMonth - 1);
                startTime.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
                startTime.set(Calendar.HOUR_OF_DAY, dclass.shour);
                startTime.set(Calendar.MINUTE, dclass.smin);

                Calendar endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, dclass.ehour);
                endTime.set(Calendar.MINUTE, dclass.emin);

                WeekViewEvent event = new WeekViewEvent(1, dclass.title, startTime, endTime);
                event.setColor(getResources().getColor(R.color.buttonPrimary));
                events.add(event);
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        */
        return events;
    }

}
