package com.planner.dgu.dguplan;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.RectF;
import android.os.*;
import android.support.v4.app.*;
import android.util.Log;
import android.view.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import weekview.MonthLoader;
import weekview.WeekView;
import weekview.WeekViewEvent;

public class fragment_main extends Fragment implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, View.OnClickListener{

    private boolean isAlreadyDid = false;
    private List<WeekViewEvent> dataList = new ArrayList<>();
    private WeekView mWeekView;
    private SQLiteDatabase database;
    private Handler h = new Handler();
    private String dbName = "DGUPLAN";
    private String createTable =
            "create table if not exists CLASSES(" +
                    "`id` integer primary key autoincrement, " +
                    "`subject` text, " +
                    "`location` text, " +
                    "`rawtime` text, " +
                    "`wday` integer);";
    private String dropTable =
            "drop table if exists CLASSES;";

    public boolean isUnique(String subject, String location, String rawtime, int wday){
        SQLiteStatement s = database.compileStatement( "select count(*) from CLASSES where `wday`=" + wday + " and `subject`='" + subject + "' and `rawtime`='" + rawtime + "' and `location`='" + location + "'; " );
        long count = s.simpleQueryForLong();
        if(count <= 0) return true;
        else return false;
    }

    private void insertData(String subject, String location, String rawtime, int wday){
        if(!isUnique(subject, location, rawtime, wday)) return;
        Log.e("Inserted", subject + " / " + location + " / " + rawtime + " / " + wday);
        database.beginTransaction();
        try{
            String sql = "insert into CLASSES(`subject`, `location`, `rawtime`, `wday`) values ('" + subject + "', '" + location + "', '" + rawtime + "', " + wday + ");";
            database.execSQL(sql);
            database.setTransactionSuccessful();
        }catch(Exception e){
            Log.e("Insertion Failed", "###############################################");
        }finally{
            database.endTransaction();
        }
    }

    public void createDatabase(){
        database = getActivity().openOrCreateDatabase(dbName, android.content.Context.MODE_PRIVATE, null);
    }

    public void dropTable(){
        try{
            database.execSQL(dropTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable(){
        try{
            database.execSQL(createTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        List<WeekViewEvent> list = getEvents(newYear, newMonth);
        if(!isAlreadyDid) {
            h.post(dbInsert);
            isAlreadyDid = true;
        }
        return list;
    }

    Runnable dbInsert = new Runnable() {
        @Override
        public void run() {
            dropTable();
            createTable();
            for(WeekViewEvent temp : dataList){
                Log.e("dataList", Integer.toString(dataList.size()));
                insertData(temp.title, temp.loc, temp.getRawTime(), temp.getStartTime().get(Calendar.DAY_OF_WEEK));
            }
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        mWeekView.goToHour(9.0);
        Calendar fix = Calendar.getInstance();
        fix.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        mWeekView.goToDate(fix);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mWeekView = (WeekView) rootView.findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setMonthChangeListener(this);
        mWeekView.goToHour(9.0);
        Calendar fix = Calendar.getInstance();
        fix.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        mWeekView.goToDate(fix);

        createDatabase();

        return rootView;
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
                        dataList.add(temp);
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
                    dataList.add(temp);
                    temp = null;
                }
            }
        }

        return events;
    }

}
