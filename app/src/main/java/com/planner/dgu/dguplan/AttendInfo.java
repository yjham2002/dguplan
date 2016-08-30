package com.planner.dgu.dguplan;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class AttendInfo {

    public static ListViewAdapter mAdapter;
    public static List<AttendInfo> asList = new ArrayList<>();

    public AttendInfo(){}
    public AttendInfo(String subject, String date){
        this.subject = subject;
        this.date = date;
    }

    public String subject, date;
}
