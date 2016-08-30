package com.planner.dgu.dguplan;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.*;
import android.support.v4.app.*;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

import java.util.Calendar;

import util.userDTO;

public class fragment_sub_02 extends Fragment{

    private RecyclerView mRecycle;
    private TextView _asCnt;
    private int atCnt = 0;
    private SQLiteDatabase database;
    private String dbName = "DGUPLAN";
    private String attendTable =
            "create table if not exists ATTEND(" +
                    "`id` integer primary key autoincrement, " +
                    "`subject` text, " +
                    "`date` text);";

    public void createDatabase(){
        database = getActivity().openOrCreateDatabase(dbName, android.content.Context.MODE_PRIVATE, null);
    }

    public void createTable(){
        try{
            database.execSQL(attendTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectData(){
        Calendar cal = Calendar.getInstance();
        String sql = "select distinct `subject`, `date` from ATTEND order by `id` asc";
        Cursor result = database.rawQuery(sql, null);
        result.moveToFirst();
        AttendInfo.asList.clear();
        while(!result.isAfterLast()){
            AttendInfo.asList.add(new AttendInfo(result.getString(0), result.getString(1)));
            result.moveToNext();
        }
        result.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_sub_02, container, false);

        _asCnt = (TextView) rootView.findViewById(R.id.ascount2);
        mRecycle = (RecyclerView) rootView.findViewById(R.id.listView2);
        AttendInfo.mAdapter = new ListViewAdapter(rootView.getContext(), R.layout.listview_attend);
        mRecycle.setAdapter(AttendInfo.mAdapter);
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mRecycle.setItemAnimator(new DefaultItemAnimator());

        createDatabase();
        createTable();
        selectData();

        for(AttendInfo insert : AttendInfo.asList) AttendInfo.mAdapter.addItem(insert);
        AttendInfo.mAdapter.dataChange();

        _asCnt.setText("지각/결석으로 예상되는 " + AttendInfo.asList.size() + "건의 강의");

        return rootView;
    }

}
