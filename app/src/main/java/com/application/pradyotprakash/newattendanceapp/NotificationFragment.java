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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private RecyclerView mNotificationListView;
    private List<Notification> notificationList;
    private NotificationRecyclerActivity notificationRecyclerAdapter;
    private FirebaseFirestore mFirestore;
    private String user_id;
    private FirebaseAuth mAuth;
    private TextView noNotificationBanner;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        noNotificationBanner = view.findViewById(R.id.noNotificationBanner);
        noNotificationBanner.setVisibility(View.VISIBLE);
        mNotificationListView = view.findViewById(R.id.notification_list);
        mNotificationListView.setVisibility(View.GONE);
        notificationList = new ArrayList<>();
        notificationRecyclerAdapter = new NotificationRecyclerActivity(notificationList, getContext());
        mNotificationListView.setHasFixedSize(true);
        mNotificationListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNotificationListView.setAdapter(notificationRecyclerAdapter);
        notificationList.clear();
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Student").document(user_id).collection("Notifications").orderBy("on", Query.Direction.DESCENDING).addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        mNotificationListView.setVisibility(View.VISIBLE);
                        noNotificationBanner.setVisibility(View.GONE);
                        String notification_id = doc.getDocument().getId();
                        Notification notification = doc.getDocument().toObject(Notification.class).withId(notification_id);
                        notificationList.add(notification);
                        notificationRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(mNotificationListView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(getContext(), R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        mNotificationListView.addItemDecoration(horizontalDecoration);
        return view;
    }

}
