package com.application.pradyotprakash.newattendanceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OtherSemesterDetails extends AppCompatActivity {

    private String currentSemester, branch, semesterValue;
    private android.support.v7.widget.Toolbar mToolbar;
    private AutoCompleteTextView semesterOption;
    private ImageView semesterSpinner;
    private static String[] semester = new String[]{};
    private RecyclerView mSubjectListView;
    private List<StudentSubjects> subjectList;
    private StudentSubjectRecyclerAdapter subjectRecyclerAdapter;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_semester_details);
        currentSemester = getIntent().getStringExtra("currentSemester");
        branch = getIntent().getStringExtra("branch");
        if (currentSemester.equals("Semester 2")) {
            semester = new String[]{"Semester 1"};
        } else if (currentSemester.equals("Semester 3")) {
            semester = new String[]{"Semester 1", "Semester 2"};
        } else if (currentSemester.equals("Semester 4")) {
            semester = new String[]{"Semester 1", "Semester 2", "Semester 3"};
        } else if (currentSemester.equals("Semester 5")) {
            semester = new String[]{"Semester 1", "Semester 2", "Semester 3", "Semester 4"};
        } else if (currentSemester.equals("Semester 6")) {
            semester = new String[]{"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5"};
        } else if (currentSemester.equals("Semester 7")) {
            semester = new String[]{"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6"};
        } else if (currentSemester.equals("Semester 8")) {
            semester = new String[]{"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6", "Semester 7"};
        } else {
            semester = new String[]{"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6", "Semester 7", "Semester 8"};
        }
        mToolbar = findViewById(R.id.faculty_selectsemester_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Previous Semester Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        semesterOption = findViewById(R.id.faculty_selectclass);
        semesterSpinner = findViewById(R.id.semester_spinner);
        ArrayAdapter<String> adapterSemester = new ArrayAdapter<>(OtherSemesterDetails.this, android.R.layout.simple_dropdown_item_1line, semester);
        semesterOption.setAdapter(adapterSemester);
        semesterSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semesterOption.showDropDown();
            }
        });
        mSubjectListView = findViewById(R.id.subjectList);
        subjectList = new ArrayList<>();
        subjectRecyclerAdapter = new StudentSubjectRecyclerAdapter(subjectList, OtherSemesterDetails.this);
        mSubjectListView.setHasFixedSize(true);
        mSubjectListView.setLayoutManager(new LinearLayoutManager(OtherSemesterDetails.this));
        mSubjectListView.setAdapter(subjectRecyclerAdapter);
        mFirestore = FirebaseFirestore.getInstance();
        semesterOption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                subjectList.clear();
                subjectRecyclerAdapter.notifyDataSetChanged();
                semesterValue = semesterOption.getText().toString();
                if (!TextUtils.isEmpty(semesterValue)) {
                    mFirestore.collection("Subject").document(branch).collection(semesterValue).addSnapshotListener(OtherSemesterDetails.this, new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                            for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                    StudentSubjects timetableClasses = documentChange.getDocument().toObject(StudentSubjects.class);
                                    subjectList.add(timetableClasses);
                                    subjectRecyclerAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}
