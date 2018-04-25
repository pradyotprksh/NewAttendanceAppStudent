package com.application.pradyotprakash.newattendanceapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Created by pradyotprakash on 01/03/18.
 */

public class StudentHomeRecyclerAdapter extends RecyclerView.Adapter<StudentHomeRecyclerAdapter.ViewHolder> {

    private List<Home> subjectList;
    private Context context;
    private FirebaseFirestore mFirestore;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mFirestore = FirebaseFirestore.getInstance();
        holder.subject.setText(subjectList.get(position).getSubjectName());
        String facultyId = (subjectList.get(position).getSubjectTeacher());
        mFirestore.collection("Faculty").document(facultyId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        holder.subjectTeacher.setText(task.getResult().getString("name"));
                    }
                }
            }
        });
        holder.subjectCode.setText(subjectList.get(position).getSubjectCode());
        String from = subjectList.get(position).getFrom();
        String to = subjectList.get(position).getTo();
        holder.timeValue.setText(from + " : " + to);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EachSubjectStudentDetails.class);
                intent.putExtra("subjectId", subjectList.get(position).getSubjectCode());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView subject, subjectCode, timeValue, subjectTeacher;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            subject = mView.findViewById(R.id.subject_value);
            subjectCode = mView.findViewById(R.id.subject_code);
            timeValue = mView.findViewById(R.id.time_value);
            subjectTeacher = mView.findViewById(R.id.subject_teacher);
        }
    }
}
