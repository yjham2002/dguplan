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

public class ListViewAdapterM extends RecyclerView.Adapter<ListViewAdapterM.ViewHolder> {

    public Context mContext = null;
    public List<AssignInfo> mListData = new ArrayList<>();
    public int item_layout;
    public int addition = 0;

    public ListViewAdapterM(Context mContext, int item_layout) {
        super();
        this.mContext = mContext;
        this.item_layout = item_layout;
    }

    public ListViewAdapterM(Context mContext, int item_layout, int addition) {
        this.mContext = mContext;
        this.item_layout = item_layout;
        this.addition = addition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AssignInfo mData = mListData.get(position);
        holder._no.setText(mData.no);
        holder._subject.setText(mData.subject);
        if(mData.name.length() < 24) holder._name.setText(mData.name);
        else holder._name.setText(mData.name.substring(0, 23) + "...");
        holder._date.setText(mData.date);
        holder._handin.setText(mData.handin);
        holder._open.setText(mData.open);
        holder._score.setText(mData.score);
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
        public TextView _no;
        public TextView _subject;
        public TextView _name;
        public TextView _date;
        public TextView _handin;
        public TextView _open;
        public TextView _score;
        public CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            _no = (TextView)itemView.findViewById(R.id.no);
            _subject = (TextView)itemView.findViewById(R.id.subject);
            _name = (TextView)itemView.findViewById(R.id.name);
            _date = (TextView)itemView.findViewById(R.id.date);
            _handin = (TextView)itemView.findViewById(R.id.handin);
            _open = (TextView)itemView.findViewById(R.id.open);
            _score = (TextView)itemView.findViewById(R.id.score);
            cardview = (CardView)itemView.findViewById(R.id.cardview);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(AssignInfo addInfo){
        AssignInfo newAssign = new AssignInfo();
        newAssign.no = addInfo.no;
        newAssign.score = addInfo.score;
        newAssign.open = addInfo.open;
        newAssign.handin = addInfo.handin;
        newAssign.date = addInfo.date;
        newAssign.name = addInfo.name;
        newAssign.subject = addInfo.subject;
        mListData.add(newAssign);
    }

    public void dataChange(){
        this.notifyDataSetChanged();
    }

}