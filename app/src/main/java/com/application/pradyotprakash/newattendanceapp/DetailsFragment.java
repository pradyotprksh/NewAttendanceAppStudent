package com.application.pradyotprakash.newattendanceapp;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private CircleImageView studentImage;
    private TextView studentName, studentUsn, studentBranch, studentSemester, studentProctor, studentClass, studentClassTeacher;
    private Uri studentImageURI = null;
    private String name, className, proctorId, proctorName, branch, semester;
    private static String studentId;
    private FirebaseFirestore studentInformationFirestore, proctorInformationFirestore, mFirestore, mFirestore1, mFirestore2;
    private RecyclerView mSubjectListView;
    private List<StudentSubjects> subjectList;
    private StudentSubjectRecyclerAdapter subjectRecyclerAdapter;
    private FirebaseAuth mAuth;
    private Button otherSemesterDetails;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        mAuth = FirebaseAuth.getInstance();
        studentId = mAuth.getCurrentUser().getUid();
        studentImage = view.findViewById(R.id.student_image);
        studentName = view.findViewById(R.id.student_name);
        studentUsn = view.findViewById(R.id.student_usn);
        studentBranch = view.findViewById(R.id.student_branch);
        studentSemester = view.findViewById(R.id.student_semester);
        studentProctor = view.findViewById(R.id.student_proctor);
        studentClass = view.findViewById(R.id.student_class);
        studentClassTeacher = view.findViewById(R.id.student_class_teacher);
        otherSemesterDetails = view.findViewById(R.id.other_semester_details);
        studentInformationFirestore = FirebaseFirestore.getInstance();
        proctorInformationFirestore = FirebaseFirestore.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore1 = FirebaseFirestore.getInstance();
        mSubjectListView = view.findViewById(R.id.subjectList);
        subjectList = new ArrayList<>();
        subjectRecyclerAdapter = new StudentSubjectRecyclerAdapter(subjectList, getContext());
        mSubjectListView.setHasFixedSize(true);
        mSubjectListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSubjectListView.setAdapter(subjectRecyclerAdapter);
        mFirestore2 = FirebaseFirestore.getInstance();
        otherSemesterDetails.setEnabled(false);
        studentInformationFirestore.collection("Student").document(studentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        name = task.getResult().getString("name");
                        String usn = task.getResult().getString("usn");
                        branch = task.getResult().getString("branch");
                        className = task.getResult().getString("className");
                        semester = task.getResult().getString("semester");
                        String image = task.getResult().getString("image");
                        studentImageURI = Uri.parse(image);
                        studentName.setText(name);
                        studentUsn.setText(usn);
                        studentBranch.setText(branch);
                        studentSemester.setText(semester);
                        studentClass.setText(className);
                        RequestOptions placeHolderRequest = new RequestOptions();
                        placeHolderRequest.placeholder(R.mipmap.default_profile_picture);
                        try {
                            Glide.with(getActivity()).setDefaultRequestOptions(placeHolderRequest).load(image).into(studentImage);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "...", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            proctorId = task.getResult().getString("proctor");
                            proctorInformationFirestore.collection("Faculty").document(proctorId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().exists()) {
                                            proctorName = task.getResult().getString("name");
                                            studentProctor.setText("Proctor Name: " + proctorName);
                                        }
                                    }
                                }
                            });
                        } catch (Exception e) {
                            studentProctor.setText("Proctor Not Assigned Yet");
                        }
                        mFirestore.collection("Class").document(branch).collection(semester).document(className).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        String classTeacherId = task.getResult().getString("classTeacher");
                                        mFirestore1.collection("Faculty").document(classTeacherId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().exists()) {
                                                        String classTeacher = task.getResult().getString("name");
                                                        studentClassTeacher.setText("Class Teacher: " + classTeacher);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                        mFirestore2.collection("Subject").document(branch).collection(semester).addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                subjectRecyclerAdapter.notifyDataSetChanged();
                                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        StudentSubjects subjects = doc.getDocument().toObject(StudentSubjects.class);
                                        subjectList.add(subjects);
                                        subjectRecyclerAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                        otherSemesterDetails.setEnabled(true);
                    } else {
                        Toast.makeText(getContext(), "Ask The Student To Fill In The Details.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String retrieving_error = task.getException().getMessage();
                    Toast.makeText(getContext(), "Error: " + retrieving_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(mSubjectListView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(getContext(), R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        mSubjectListView.addItemDecoration(horizontalDecoration);
        otherSemesterDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (semester.equals("Semester 1")) {
//                    Toast.makeText(getContext(), "You are in the first semester.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(getContext(), OtherSemesterDetails.class);
//                    intent.putExtra("currentSemester", semester);
//                    startActivity(intent);
//                }
                Intent intent = new Intent(getContext(), OtherSemesterDetails.class);
                intent.putExtra("currentSemester", semester);
                intent.putExtra("branch", branch);
                startActivity(intent);
            }
        });
        return view;
    }

}
