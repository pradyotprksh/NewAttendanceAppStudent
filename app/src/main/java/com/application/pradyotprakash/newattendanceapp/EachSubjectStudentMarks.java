package com.application.pradyotprakash.newattendanceapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EachSubjectStudentMarks extends AppCompatActivity {

    private String studentId, subjectCode, semester;
    private FirebaseFirestore mFirestore, mFirestore1, mFirestore2, mFirestore3;
    private TextView externalMarks, internal1Marks, internal2Marks, internal3Marks, textView1, textView2, textView3, textView4, textView5, internalAverage, textView6, totalMarks;
    private Integer internal1, internal2, internal3, external, average, totalMarksValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_subject_student_marks);
        studentId = getIntent().getStringExtra("studentId");
        subjectCode = getIntent().getStringExtra("subjectCode");
        semester = getIntent().getStringExtra("semester");
        Toolbar mToolbar = findViewById(R.id.adminSetupToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Marks");
        externalMarks = findViewById(R.id.externalMarks);
        internal1Marks = findViewById(R.id.interanl1Marks);
        internal2Marks = findViewById(R.id.interanl2Marks);
        internal3Marks = findViewById(R.id.interanl3Marks);
        internalAverage = findViewById(R.id.internalAverageValue);
        totalMarks = findViewById(R.id.totalMarks);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView3);
        textView3 = findViewById(R.id.textView4);
        textView4 = findViewById(R.id.textView5);
        textView5 = findViewById(R.id.textView6);
        textView6 = findViewById(R.id.textView7);
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore1 = FirebaseFirestore.getInstance();
        mFirestore2 = FirebaseFirestore.getInstance();
        mFirestore3 = FirebaseFirestore.getInstance();
        mFirestore1
                .collection("Student")
                .document(studentId)
                .collection(semester)
                .document("Marks")
                .collection(subjectCode)
                .document("Marks")
                .collection("Internal")
                .document("Internal 1")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        if (task.getResult().getString("marksObtained").equals("AB") || Integer.valueOf(task.getResult().getString("marksObtained")) < 15) {
                            internal1Marks.setTextColor(Color.rgb(244, 67, 54));
                            textView2.setTextColor(Color.rgb(244, 67, 54));
                        }
                        if (task.getResult().getString("marksObtained").equals("AB")) {
                            internal1 = 0;
                        } else {
                            internal1 = Integer.valueOf(task.getResult().getString("marksObtained"));
                        }
                        internal1Marks.setText(task.getResult().getString("marksObtained") + " out of " + task.getResult().getString("totalMarks"));
                    } else {
                        internal1 = 0;
                        internal1Marks.setText("Marks Not Entered");
                    }
                    mFirestore2
                            .collection("Student")
                            .document(studentId)
                            .collection(semester)
                            .document("Marks")
                            .collection(subjectCode)
                            .document("Marks")
                            .collection("Internal")
                            .document("Internal 2")
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    if (task.getResult().getString("marksObtained").equals("AB") || Integer.valueOf(task.getResult().getString("marksObtained")) < 15) {
                                        internal2Marks.setTextColor(Color.rgb(244, 67, 54));
                                        textView3.setTextColor(Color.rgb(244, 67, 54));
                                    }
                                    if (task.getResult().getString("marksObtained").equals("AB")) {
                                        internal2 = 0;
                                    } else {
                                        internal2 = Integer.valueOf(task.getResult().getString("marksObtained"));
                                    }
                                    internal2Marks.setText(task.getResult().getString("marksObtained") + " out of " + task.getResult().getString("totalMarks"));
                                } else {
                                    internal2 = 0;
                                    internal2Marks.setText("Marks Not Entered");
                                }
                                mFirestore3
                                        .collection("Student")
                                        .document(studentId)
                                        .collection(semester)
                                        .document("Marks")
                                        .collection(subjectCode)
                                        .document("Marks")
                                        .collection("Internal")
                                        .document("Internal 3")
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {
                                                if (task.getResult().getString("marksObtained").equals("AB") || Integer.valueOf(task.getResult().getString("marksObtained")) < 15) {
                                                    internal3Marks.setTextColor(Color.rgb(244, 67, 54));
                                                    textView4.setTextColor(Color.rgb(244, 67, 54));
                                                }
                                                if (task.getResult().getString("marksObtained").equals("AB")) {
                                                    internal3 = 0;
                                                } else {
                                                    internal3 = Integer.valueOf(task.getResult().getString("marksObtained"));
                                                }
                                                internal3Marks.setText(task.getResult().getString("marksObtained") + " out of " + task.getResult().getString("totalMarks"));
                                            } else {
                                                internal3 = 0;
                                                internal3Marks.setText("Marks Not Entered");
                                            }
                                            mFirestore
                                                    .collection("Student")
                                                    .document(studentId)
                                                    .collection(semester)
                                                    .document("Marks")
                                                    .collection(subjectCode)
                                                    .document("Marks")
                                                    .collection("External")
                                                    .document("Marks")
                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().exists()) {
                                                            if (task.getResult().getString("marksObtained").equals("AB") || Integer.valueOf(task.getResult().getString("marksObtained")) < 35) {
                                                                externalMarks.setTextColor(Color.rgb(244, 67, 54));
                                                                textView1.setTextColor(Color.rgb(244, 67, 54));
                                                            }
                                                            if (task.getResult().getString("marksObtained").equals("AB")) {
                                                                external = 0;
                                                            } else {
                                                                external = Integer.valueOf(task.getResult().getString("marksObtained"));
                                                            }
                                                            external = Integer.valueOf(task.getResult().getString("marksObtained"));
                                                            externalMarks.setText(task.getResult().getString("marksObtained") + " out of " + task.getResult().getString("totalMarks"));
                                                        } else {
                                                            external = 0;
                                                            externalMarks.setText("Marks Not Entered");
                                                        }
                                                        if (internal1 >= internal2 && internal1 >= internal3) {
                                                            if (internal2 >= internal3) {
                                                                average = (internal1 + internal2) / 2;
                                                                internalAverage.setText(String.valueOf(average));
                                                            } else {
                                                                average = (internal1 + internal3) / 2;
                                                                internalAverage.setText(String.valueOf(average));
                                                            }
                                                        } else if (internal2 >= internal1 && internal2 >= internal3) {
                                                            if (internal1 >= internal3) {
                                                                average = (internal2 + internal1) / 2;
                                                                internalAverage.setText(String.valueOf(average));
                                                            } else {
                                                                average = (internal2 + internal3) / 2;
                                                                internalAverage.setText(String.valueOf(average));
                                                            }
                                                        } else if (internal3 >= internal1 && internal3 >= internal2) {
                                                            if (internal1 >= internal2) {
                                                                average = (internal3 + internal1) / 2;
                                                                internalAverage.setText(String.valueOf(average));
                                                            } else {
                                                                average = (internal3 + internal2) / 2;
                                                                internalAverage.setText(String.valueOf(average));
                                                            }
                                                        }
                                                        if (average < 15) {
                                                            internalAverage.setTextColor(Color.rgb(244, 67, 54));
                                                            textView5.setTextColor(Color.rgb(244, 67, 54));
                                                        }
                                                        totalMarksValue = external + average;
                                                        totalMarks.setText(String.valueOf(totalMarksValue));
                                                        if (totalMarksValue < 50 || external < 35) {
                                                            totalMarks.setTextColor(Color.rgb(244, 67, 54));
                                                            textView6.setTextColor(Color.rgb(244, 67, 54));
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}
