package com.application.pradyotprakash.newattendanceapp;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    private RecyclerView noteList;
    private List<EventList> notesList;
    private EventListRecyclerAdapter noteRecyclerAdapter;
    private FirebaseFirestore mFirestore;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        mFirestore = FirebaseFirestore.getInstance();
        noteList = view.findViewById(R.id.noteList);
        notesList = new ArrayList<>();
        noteRecyclerAdapter = new EventListRecyclerAdapter(notesList, getContext());
        noteList.setHasFixedSize(true);
        noteList.setLayoutManager(new LinearLayoutManager(getContext()));
        noteList.setAdapter(noteRecyclerAdapter);
        mFirestore.collection("Events").orderBy("uploadedOn").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        String note_id = documentChange.getDocument().getId();
                        EventList noteList1 = documentChange.getDocument().toObject(EventList.class).withId(note_id);
                        notesList.add(noteList1);
                        noteRecyclerAdapter.notifyDataSetChanged();
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
