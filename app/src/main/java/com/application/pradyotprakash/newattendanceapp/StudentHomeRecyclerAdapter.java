package com.application.pradyotprakash.newattendanceapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pradyotprakash on 01/03/18.
 */

public class StudentHomeRecyclerAdapter extends RecyclerView.Adapter<StudentHomeRecyclerAdapter.ViewHolder> {

    private List<Home> subjectList;
    private Context context;

    public StudentHomeRecyclerAdapter(List<Home> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_today_classes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.subject.setText(subjectList.get(position).getSubject());
        holder.from.setText(subjectList.get(position).getFrom());
        holder.to.setText(subjectList.get(position).getTo());
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView subject, from, to;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            subject = mView.findViewById(R.id.subject_value);
            from = mView.findViewById(R.id.from_value);
            to = mView.findViewById(R.id.to_value);
        }
    }
}
