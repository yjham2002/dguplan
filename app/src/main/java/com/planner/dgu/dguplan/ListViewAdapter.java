package com.planner.dgu.dguplan;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    public Context mContext = null;
    public List<AttendInfo> mListData = new ArrayList<>();
    public int item_layout;
    public int addition = 0;

    public ListViewAdapter(Context mContext, int item_layout) {
        super();
        this.mContext = mContext;
        this.item_layout = item_layout;
    }

    public ListViewAdapter(Context mContext, int item_layout, int addition) {
        this.mContext = mContext;
        this.item_layout = item_layout;
        this.addition = addition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_attend, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AttendInfo mData = mListData.get(position);
        holder._subject.setText(mData.subject);
        holder._date.setText(mData.date);
        holder.cardview.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final AssignInfo mData = staticInfo.mAdapter.mListData.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView _subject;
        public TextView _date;
        public CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            _subject = (TextView)itemView.findViewById(R.id.subject);
            _date = (TextView)itemView.findViewById(R.id.date);
            cardview = (CardView)itemView.findViewById(R.id.cardview);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(AttendInfo addInfo){
        AttendInfo newAssign = new AttendInfo();
        newAssign.date = addInfo.date;
        newAssign.subject = addInfo.subject;
        mListData.add(newAssign);
    }

    public void dataChange(){
        this.notifyDataSetChanged();
    }

}