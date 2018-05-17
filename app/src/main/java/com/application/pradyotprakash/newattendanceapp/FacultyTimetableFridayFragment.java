package com.application.pradyotprakash.newattendanceapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class FacultyTimetableFridayFragment extends Fragment {

    private FirebaseFirestore mFirestore, mFirestore1;
    private String user_id;
    private FirebaseAuth mAuth;
    private RecyclerView mSubjectListView;
    private List<FacultyMondaySubjects> subjectList;
    private FacultyMondaySubjectRecyclerAdapter subjectRecyclerAdapter;

    public FacultyTimetableFridayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_faculty_timetable_friday, container, false);
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        mFirestore1 = FirebaseFirestore.getInstance();
        mFirestore1.collection("Student").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        mSubjectListView = view.findViewById(R.id.monday_subject);
                        subjectList = new ArrayList<>();
                        subjectRecyclerAdapter = new FacultyMondaySubjectRecyclerAdapter(subjectList, getContext());
                        mSubjectListView.setHasFixedSize(true);
                        mSubjectListView.setLayoutManager(new LinearLayoutManager(getContext()));
                        mSubjectListView.setAdapter(subjectRecyclerAdapter);
                        mFirestore = FirebaseFirestore.getInstance();
                        mFirestore.collection("Timetable").document(task.getResult().getString("className")).collection("Friday").orderBy("from").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                subjectRecyclerAdapter.notifyDataSetChanged();
                                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        String subject_id = doc.getDocument().getId();
                                        FacultyMondaySubjects subjects = doc.getDocument().toObject(FacultyMondaySubjects.class).withId(subject_id);
                                        subjectList.add(subjects);
                                        subjectRecyclerAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
        return view;
    }

}
