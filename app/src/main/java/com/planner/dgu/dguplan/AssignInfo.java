package com.planner.dgu.dguplan;

import java.util.ArrayList;
import java.util.List;

public class AssignInfo {

    public static ListViewAdapterM mAdapter;
    public static List<AssignInfo> asList = new ArrayList<>();

    public AssignInfo(){}
    public AssignInfo(String no, String subject, String name, String date, String handin, String open, String score){
        this.no = no;
        this.subject = subject;
        this.name = name;
        this.date = date;
        this.handin = handin;
        this.open = open;
        this.score = score;
    }

    public String no, subject, name, date, handin, open, score;
}
