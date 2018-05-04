package com.application.pradyotprakash.newattendanceapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class OtherSemesterStudentSubjectRecyclerAdapter extends RecyclerView.Adapter<OtherSemesterStudentSubjectRecyclerAdapter.ViewHolder>{

    private List<OtherSemesterSubjectCode> subjectList;
    private Context context;
    private String studentId;
    private FirebaseAuth mAuth;

    public OtherSemesterStudentSubjectRecyclerAdapter(List<OtherSemesterSubjectCode> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;
    }

    @Override
    public OtherSemesterStudentSubjectRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_student, parent, false);
        return new OtherSemesterStudentSubjectRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OtherSemesterStudentSubjectRecyclerAdapter.ViewHolder holder, final int position) {
        mAuth = FirebaseAuth.getInstance();
        studentId = mAuth.getCurrentUser().getUid();
        holder.subjectValue.setText(subjectList.get(position).getSubjectCode());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherSemesterEachSubjectStudentDetails.class);
                intent.putExtra("subjectId", subjectList.get(position).getSubjectCode());
                intent.putExtra("subjectTeacher", subjectList.get(position).getSubjectTeacher());
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
        private TextView subjectValue;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            subjectValue = mView.findViewById(R.id.subject_value);
        }
    }
}
