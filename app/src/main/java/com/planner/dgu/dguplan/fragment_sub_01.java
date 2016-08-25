package com.planner.dgu.dguplan;

import android.os.*;
import android.support.v4.app.*;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

import util.userDTO;

public class fragment_sub_01 extends Fragment{

    private RecyclerView mRecycle;
    private TextView _asCnt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_sub_01, container, false);

        _asCnt = (TextView) rootView.findViewById(R.id.ascount);
        mRecycle = (RecyclerView) rootView.findViewById(R.id.listView);
        AssignInfo.mAdapter = new ListViewAdapterM(rootView.getContext(), R.layout.listview_item);
        mRecycle.setAdapter(AssignInfo.mAdapter);
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mRecycle.setItemAnimator(new DefaultItemAnimator());

        for(AssignInfo insert : AssignInfo.asList) AssignInfo.mAdapter.addItem(insert);

        AssignInfo.mAdapter.dataChange();

        _asCnt.setText("총 " + (userDTO.realCnt) + "건의 과제 중 " + userDTO.handinCnt + "건 제출");

        return rootView;
    }

}
