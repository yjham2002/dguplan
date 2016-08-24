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
        return getEvents(newYear, newMonth);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setMonthChangeListener(this);
        mWeekView.goToHour(9.0);
        Calendar fix = Calendar.getInstance();
        fix.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        mWeekView.goToDate(fix);

    }

    public List<WeekViewEvent> getEvents(int newYear, int newMonth){
        int i = 0, j = 0;
        List<WeekViewEvent> events = new ArrayList<>();
        for(i = 0; i < IntroActivity.session.days.get(0).size(); i++){ // horizontal shift
            WeekViewEvent temp = null;
            for(j = 0; j < IntroActivity.session.days.size(); j++){ // vertical shift
                ClassInfo wve = IntroActivity.session.days.get(j).get(i);
                if (wve.isValid) {
                    if(temp == null) {
                        temp = IntroActivity.session.days.get(j).get(i).toSimpleEvent(newYear, newMonth);
                        temp.setColor(getResources().getColor(R.color.colorPrimary));
                    }
                    else if(wve.title.equals(temp.title)) {
                        Calendar endTime = (Calendar) temp.getStartTime().clone();
                        endTime.set(Calendar.HOUR_OF_DAY, wve.ehour);
                        endTime.set(Calendar.MINUTE, wve.emin);
                        temp.setEndTime(endTime);
                    }else{
                        /*
                        for(int week = 1; week <= 5; week++){
                            WeekViewEvent diff = temp.clone();
                            Calendar nStart = diff.getStartTime();
                            Calendar nEnd = diff.getEndTime();
                            nStart.set(Calendar.WEEK_OF_MONTH, week);
                            nEnd.set(Calendar.WEEK_OF_MONTH, week);
                            diff.setStartTime(nStart);
                            diff.setEndTime(nEnd);
                            events.add(diff);
                        }*/
                        events.add(temp);
                        temp = IntroActivity.session.days.get(j).get(i).toSimpleEvent(newYear, newMonth);
                        temp.setColor(getResources().getColor(R.color.colorPrimary));
                    }
                }
                if(temp != null && !wve.title.equals(temp.title)) {
                    /*
                    for(int week = 1; week <= 5; week++){
                        WeekViewEvent diff = temp.clone();
                        Calendar nStart = diff.getStartTime();
                        Calendar nEnd = diff.getEndTime();
                        nStart.set(Calendar.WEEK_OF_MONTH, week);
                        nEnd.set(Calendar.WEEK_OF_MONTH, week);
                        diff.setStartTime(nStart);
                        diff.setEndTime(nEnd);
                        events.add(diff);
                    }*/
                    events.add(temp);
                    temp = null;
                }
            }
        }

        return events;
    }

}
