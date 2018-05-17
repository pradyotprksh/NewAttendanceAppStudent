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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FacultyMondaySubjectRecyclerAdapter extends RecyclerView.Adapter<FacultyMondaySubjectRecyclerAdapter.ViewHolder> {

    private List<FacultyMondaySubjects> subjectList;
    private Context context;
    private FirebaseAuth mAuth;
    private String userId, name;
    private FirebaseFirestore mFirestore;


    public FacultyMondaySubjectRecyclerAdapter(List<FacultyMondaySubjects> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;
    }

    @Override
    public FacultyMondaySubjectRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_monday_subject_list, parent, false);
        return new FacultyMondaySubjectRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FacultyMondaySubjectRecyclerAdapter.ViewHolder holder, final int position) {
        String facultyId = (subjectList.get(position).getSubjectTeacher());
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Faculty").document(facultyId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        name = task.getResult().getString("name");
                        holder.subjectTeacher.setText(name);
                    }
                }
            }
        });
        holder.timeValue.setText(subjectList.get(position).getFrom() + " : " + subjectList.get(position).getTo());
        holder.subjectCode.setText(subjectList.get(position).getSubjectCode());
        holder.subjectValue.setText(subjectList.get(position).getSubjectName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EachSubjectStudentDetails.class);
                        intent.putExtra("subjectId", subjectList.get(position).getSubjectCode());
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView timeValue, subjectCode, subjectValue, subjectTeacher;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            timeValue = mView.findViewById(R.id.time_value);
            subjectTeacher = mView.findViewById(R.id.subjectTeacher);
            subjectCode = mView.findViewById(R.id.subject_code);
            subjectValue = mView.findViewById(R.id.subject_value);
        }
    }
}
