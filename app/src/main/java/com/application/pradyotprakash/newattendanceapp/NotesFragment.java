package com.application.pradyotprakash.newattendanceapp;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
public class NotesFragment extends Fragment {

    private String semesterValue, classValue, branch;
    private FirebaseAuth mAuth;
    private static String user_id;
    private RecyclerView noteList;
    private List<NoteList> notesList;
    private NoteListRecyclerAdapter noteRecyclerAdapter;
    private FirebaseFirestore mFirestore, mFirestore1;

    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        mFirestore1 = FirebaseFirestore.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        noteList = view.findViewById(R.id.noteList);
        notesList = new ArrayList<>();
        noteRecyclerAdapter = new NoteListRecyclerAdapter(notesList, getContext());
        noteList.setHasFixedSize(true);
        noteList.setLayoutManager(new LinearLayoutManager(getContext()));
        noteList.setAdapter(noteRecyclerAdapter);
        notesList.clear();
        mFirestore1.collection("Student").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        branch = task.getResult().getString("branch");
                        semesterValue = task.getResult().getString("semester");
                        classValue = task.getResult().getString("className");
                        mFirestore.collection("Notes").document(branch).collection(semesterValue).document(classValue).collection("Uploaded").orderBy("uploadedOn").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                        String note_id = documentChange.getDocument().getId();
                                        NoteList noteList1 = documentChange.getDocument().toObject(NoteList.class).withId(note_id);
                                        notesList.add(noteList1);
                                        noteRecyclerAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(noteList.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(getContext(), R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        noteList.addItemDecoration(horizontalDecoration);
        return view;
    }

}
