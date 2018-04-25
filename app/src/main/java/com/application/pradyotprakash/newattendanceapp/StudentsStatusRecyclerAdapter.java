package com.application.pradyotprakash.newattendanceapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class StudentsStatusRecyclerAdapter extends RecyclerView.Adapter<StudentsStatusRecyclerAdapter.ViewHolder> {

    private List<StudentsStatus> statusList;
    private Context context;
    private FirebaseFirestore mFirestore, mFirestore1;
    private String currentValue;

    public StudentsStatusRecyclerAdapter(Context context, List<StudentsStatus> statusList) {
        this.statusList = statusList;
        this.context = context;
    }

    @Override
    public StudentsStatusRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendance_status_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentsStatusRecyclerAdapter.ViewHolder holder, final int position) {
        final String statusId = statusList.get(position).statusId;
        String weekDay = statusList.get(position).getWeekDay();
        String date = statusList.get(position).getDate();
        String day = date + " " + weekDay;
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore1 = FirebaseFirestore.getInstance();
        holder.dateValue.setText(day);
        if (statusList.get(position).getValue().equals("Present")) {
            holder.statusValue.setText("Present");
            holder.statusValue.setTextColor(Color.BLUE);
        } else {
            holder.statusValue.setText("Absent");
            holder.statusValue.setTextColor(Color.rgb(244, 67, 54));
        }
        holder.timeValue.setText(statusList.get(position).getTime());
        String from = statusList.get(position).getFrom();
        String to = statusList.get(position).getTo();
        String classTime = from + " - " + to;
        holder.classTimeValue.setText(classTime);
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView dateValue, statusValue, timeValue, classTimeValue;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            dateValue = mView.findViewById(R.id.dateValue);
            statusValue = mView.findViewById(R.id.statusValue);
            timeValue = mView.findViewById(R.id.timeValue);
            classTimeValue = mView.findViewById(R.id.classTimeValue);

        }
    }
}
