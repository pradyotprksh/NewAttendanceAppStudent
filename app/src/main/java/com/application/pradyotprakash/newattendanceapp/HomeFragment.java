package com.application.pradyotprakash.newattendanceapp;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mSubjectListView;
    private List<Home> subjectList;
    private List<NewNotes> newNoteslist;
    private List<NewNotification> newNotificationList;
    private StudentHomeRecyclerAdapter subjectRecyclerAdapter;
    private FirebaseFirestore mFirestore, mFirestore1, mFirestore2, mFirestore3, mFirestore4, mFirestore5, mFirestore6;
    private String classValue;
    private String todayDay;
    private TextView noClassBanner;
    private FirebaseAuth mAuth;
    private String student_id, fileLink;
    private int position = 0;
    private TextView noteName, noteDescription, noteUploadedOn, noteUploadedBy;
    private TextView noteName1, noteDescription1, noteUploadedOn1, noteUploadedBy1;
    private TextView message, from, on;
    private CircleImageView mImage;
    private TextView message1, from1, on1;
    private CircleImageView mImage1;
    private ConstraintLayout firstNote, secondNote, firstNotification, secondNotification;
    private long queueId;
    private DownloadManager downloadManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        classValue = StudentMainActivity.getClassValue();
        todayDay = getCurrentDay();
        mSubjectListView = view.findViewById(R.id.today_classlist);
        mSubjectListView.setVisibility(View.INVISIBLE);
        noClassBanner = view.findViewById(R.id.noClassBanner);
        noClassBanner.setVisibility(View.VISIBLE);
        subjectList = new ArrayList<>();
        subjectRecyclerAdapter = new StudentHomeRecyclerAdapter(subjectList, getContext());
        mSubjectListView.setHasFixedSize(true);
        mSubjectListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSubjectListView.setAdapter(subjectRecyclerAdapter);
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Timetable").document(classValue).collection(todayDay).orderBy("from").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        mSubjectListView.setVisibility(View.VISIBLE);
                        noClassBanner.setVisibility(View.INVISIBLE);
                        Home subjects = doc.getDocument().toObject(Home.class);
                        subjectList.add(subjects);
                        subjectRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        mFirestore3 = FirebaseFirestore.getInstance();
        mFirestore4 = FirebaseFirestore.getInstance();
        mFirestore2 = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        student_id = mAuth.getCurrentUser().getUid();
        newNoteslist = new ArrayList<>();
        noteName = view.findViewById(R.id.noteName);
        noteDescription = view.findViewById(R.id.noteDescription);
        noteUploadedOn = view.findViewById(R.id.noteUploadedOn);
        noteUploadedBy = view.findViewById(R.id.noteUploadedBy);
        noteName1 = view.findViewById(R.id.noteName1);
        noteDescription1 = view.findViewById(R.id.noteDescription1);
        noteUploadedOn1 = view.findViewById(R.id.noteUploadedOn1);
        noteUploadedBy1 = view.findViewById(R.id.noteUploadedBy1);
        noteName.setVisibility(View.INVISIBLE);
        noteDescription.setVisibility(View.INVISIBLE);
        noteUploadedOn.setVisibility(View.INVISIBLE);
        noteUploadedBy.setVisibility(View.INVISIBLE);
        noteName1.setVisibility(View.INVISIBLE);
        noteDescription1.setVisibility(View.INVISIBLE);
        noteUploadedOn1.setVisibility(View.INVISIBLE);
        noteUploadedBy1.setVisibility(View.INVISIBLE);
        if (getActivity() != null) {
            try {
                mFirestore2.collection("Student").document(student_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                mFirestore1 = FirebaseFirestore.getInstance();
                                mFirestore1.collection("Notes")
                                        .document(task.getResult().getString("branch"))
                                        .collection(task.getResult().getString("semester"))
                                        .document(task.getResult().getString("className"))
                                        .collection("Uploaded")
                                        .orderBy("uploadedOn", Query.Direction.DESCENDING)
                                        .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                                        NewNotes newNotes = doc.getDocument().toObject(NewNotes.class);
                                                        newNoteslist.add(newNotes);
                                                    }
                                                }
                                                for (position = 0; position < newNoteslist.size(); position++) {
                                                    if (position == 0) {
                                                        noteName.setVisibility(View.VISIBLE);
                                                        noteDescription.setVisibility(View.VISIBLE);
                                                        noteUploadedOn.setVisibility(View.VISIBLE);
                                                        noteUploadedBy.setVisibility(View.VISIBLE);
                                                        noteName.setText(newNoteslist.get(position).getTitle());
                                                        noteDescription.setText(newNoteslist.get(position).getDescription());
                                                        noteUploadedOn.setText(newNoteslist.get(position).getUploadedOn());
                                                        mFirestore3.collection("Faculty").document(newNoteslist.get(position).getUploadedBy()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    if (task.getResult().exists()) {
                                                                        noteUploadedBy.setText(task.getResult().getString("name"));
                                                                    }
                                                                }
                                                            }
                                                        });
                                                    }
                                                    if (position == 1) {
                                                        noteName1.setVisibility(View.VISIBLE);
                                                        noteDescription1.setVisibility(View.VISIBLE);
                                                        noteUploadedOn1.setVisibility(View.VISIBLE);
                                                        noteUploadedBy1.setVisibility(View.VISIBLE);
                                                        noteName1.setText(newNoteslist.get(position).getTitle());
                                                        noteDescription1.setText(newNoteslist.get(position).getDescription());
                                                        noteUploadedOn1.setText(newNoteslist.get(position).getUploadedOn());
                                                        mFirestore4.collection("Faculty").document(newNoteslist.get(position).getUploadedBy()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    if (task.getResult().exists()) {
                                                                        noteUploadedBy1.setText(task.getResult().getString("name"));
                                                                    }
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
            } catch (Exception e) {
                Toast.makeText(getContext(), "...", Toast.LENGTH_SHORT).show();
            }
        }
        firstNote = view.findViewById(R.id.firstNote);
        secondNote = view.findViewById(R.id.secondNote);
        firstNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirestore2.collection("Student").document(student_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                mFirestore1 = FirebaseFirestore.getInstance();
                                mFirestore1.collection("Notes")
                                        .document(task.getResult().getString("branch"))
                                        .collection(task.getResult().getString("semester"))
                                        .document(task.getResult().getString("className"))
                                        .collection("Uploaded")
                                        .orderBy("uploadedOn", Query.Direction.DESCENDING)
                                        .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                                        NewNotes newNotes = doc.getDocument().toObject(NewNotes.class);
                                                        newNoteslist.add(newNotes);
                                                    }
                                                }
                                                for (position = 0; position < newNoteslist.size(); position++) {
                                                    if (position == 0) {
                                                        fileLink = newNoteslist.get(position).getNoteLink();
                                                        fileLink = newNoteslist.get(position).getNoteLink();
                                                        downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileLink));
                                                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, newNoteslist.get(position).getName());
                                                        queueId = downloadManager.enqueue(request);
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
            }
        });
        secondNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirestore2.collection("Student").document(student_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                mFirestore1 = FirebaseFirestore.getInstance();
                                mFirestore1.collection("Notes")
                                        .document(task.getResult().getString("branch"))
                                        .collection(task.getResult().getString("semester"))
                                        .document(task.getResult().getString("className"))
                                        .collection("Uploaded")
                                        .orderBy("uploadedOn", Query.Direction.DESCENDING)
                                        .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                                        NewNotes newNotes = doc.getDocument().toObject(NewNotes.class);
                                                        newNoteslist.add(newNotes);
                                                    }
                                                }
                                                for (position = 0; position < newNoteslist.size(); position++) {
                                                    if (position == 1) {
                                                        fileLink = newNoteslist.get(position).getNoteLink();
                                                        fileLink = newNoteslist.get(position).getNoteLink();
                                                        downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileLink));
                                                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, newNoteslist.get(position).getName());
                                                        queueId = downloadManager.enqueue(request);
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
            }
        });
        message = view.findViewById(R.id.message_value);
        from = view.findViewById(R.id.sender_name);
        on = view.findViewById(R.id.message_on);
        mImage = view.findViewById(R.id.sender_list_image);
        message1 = view.findViewById(R.id.message_value1);
        from1 = view.findViewById(R.id.sender_name1);
        on1 = view.findViewById(R.id.message_on1);
        mImage1 = view.findViewById(R.id.sender_list_image1);
        mFirestore5 = FirebaseFirestore.getInstance();
        newNotificationList = new ArrayList<>();
        message.setVisibility(View.INVISIBLE);
        from.setVisibility(View.INVISIBLE);
        on.setVisibility(View.INVISIBLE);
        mImage.setVisibility(View.INVISIBLE);
        message1.setVisibility(View.INVISIBLE);
        from1.setVisibility(View.INVISIBLE);
        on1.setVisibility(View.INVISIBLE);
        mImage1.setVisibility(View.INVISIBLE);
        firstNotification = view.findViewById(R.id.firstNotification);
        secondNotification = view.findViewById(R.id.secondNotification);
        mFirestore5.collection("Student").document(student_id).collection("Notifications").orderBy("on", Query.Direction.DESCENDING).addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        NewNotification notification = doc.getDocument().toObject(NewNotification.class);
                        newNotificationList.add(notification);
                    }
                }
                for (position = 0; position < newNotificationList.size(); position++) {
                    if (position == 0) {
                        message.setVisibility(View.VISIBLE);
                        from.setVisibility(View.VISIBLE);
                        on.setVisibility(View.VISIBLE);
                        mImage.setVisibility(View.VISIBLE);
                        String messageValue = newNotificationList.get(position).getMessage();
                        if (messageValue.length() >= 15) {
                            messageValue = messageValue.substring(0, Math.min(message.length(), 10));
                            messageValue = messageValue + "...";
                            message.setText(messageValue);
                        } else {
                            message.setText(newNotificationList.get(position).getMessage());
                        }
                        from.setText(newNotificationList.get(position).getSenderName());
                        on.setText(newNotificationList.get(position).getOn());
                        CircleImageView mImageView = mImage;
                        Glide.with(getContext()).load(newNotificationList.get(position).getSenderImage()).into(mImageView);
                    }
                    if (position == 1) {
                        message1.setVisibility(View.VISIBLE);
                        from1.setVisibility(View.VISIBLE);
                        on1.setVisibility(View.VISIBLE);
                        mImage1.setVisibility(View.VISIBLE);
                        String message = newNotificationList.get(position).getMessage();
                        if (message.length() >= 15) {
                            message = message.substring(0, Math.min(message.length(), 10));
                            message = message + "...";
                            message1.setText(message);
                        } else {
                            message1.setText(newNotificationList.get(position).getMessage());
                        }
                        from1.setText(newNotificationList.get(position).getSenderName());
                        on1.setText(newNotificationList.get(position).getOn());
                        CircleImageView mImageView1 = mImage1;
                        Glide.with(getContext()).load(newNotificationList.get(position).getSenderImage()).into(mImageView1);
                    }
                }
            }
        });
//        mFirestore6 = FirebaseFirestore.getInstance();
//        firstNotification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFirestore6.collection("Student").document(student_id).collection("Notifications").orderBy("on", Query.Direction.DESCENDING).addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
//                            if (doc.getType() == DocumentChange.Type.ADDED) {
//                                NewNotification notification = doc.getDocument().toObject(NewNotification.class);
//                                newNotificationList.add(notification);
//                            }
//                        }
//                        for (position = 0; position < newNotificationList.size(); position++) {
//                            if (position == 0) {
//                                Intent notificationIndent = new Intent(getContext(), NotificationActivity.class);
//                                notificationIndent.putExtra("message", newNotificationList.get(position).getMessage());
//                                notificationIndent.putExtra("from_user_id", newNotificationList.get(position).getFrom());
//                                notificationIndent.putExtra("from_designation", newNotificationList.get(position).getDesignation());
//                                notificationIndent.putExtra("message_on", newNotificationList.get(position).getOn());
//                                startActivity(notificationIndent);
//                            }
//                        }
//                    }
//                });
//            }
//        });
//        secondNotification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFirestore6.collection("Student").document(student_id).collection("Notifications").orderBy("on", Query.Direction.DESCENDING).addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
//                            if (doc.getType() == DocumentChange.Type.ADDED) {
//                                NewNotification notification = doc.getDocument().toObject(NewNotification.class);
//                                newNotificationList.add(notification);
//                            }
//                        }
//                        for (position = 0; position < newNotificationList.size(); position++) {
//                            if (position == 1) {
//                                Intent notificationIndent = new Intent(getContext(), NotificationActivity.class);
//                                notificationIndent.putExtra("message", newNotificationList.get(position).getMessage());
//                                notificationIndent.putExtra("from_user_id", newNotificationList.get(position).getFrom());
//                                notificationIndent.putExtra("from_designation", newNotificationList.get(position).getDesignation());
//                                notificationIndent.putExtra("message_on", newNotificationList.get(position).getOn());
//                                startActivity(notificationIndent);
//                            }
//                        }
//                    }
//                });
//            }
//        });
        return view;
    }

    public String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        return "Wrong Day";
    }

}

