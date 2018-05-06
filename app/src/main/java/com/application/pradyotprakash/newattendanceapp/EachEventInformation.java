package com.application.pradyotprakash.newattendanceapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class EachEventInformation extends AppCompatActivity {

    private String title, description, uploadedOn, uploadedBy, imageLink, eventId;
    private TextView eventTitle, eventDescrption, eventUploadedBy, eventUploadedOn;
    private ImageView eventImage;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_event_information);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        uploadedOn = getIntent().getStringExtra("uploadedOn");
        uploadedBy = getIntent().getStringExtra("uploadedBy");
        imageLink = getIntent().getStringExtra("imageLink");
        eventId = getIntent().getStringExtra("eventId");
        Toolbar mToolbar = findViewById(R.id.uploadNotesToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(title);
        eventTitle = findViewById(R.id.eventTitle);
        eventDescrption = findViewById(R.id.eventDescription);
        eventUploadedBy = findViewById(R.id.uploadedBy);
        eventUploadedOn = findViewById(R.id.uploadedOn);
        eventImage = findViewById(R.id.eventImage);
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Faculty").document(uploadedBy).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        eventUploadedOn.setText("Event Date: " + uploadedOn);
                        eventUploadedBy.setText("Uploaded By: " + task.getResult().getString("name"));
                        eventTitle.setText(title);
                        eventDescrption.setText(description);
                        RequestOptions placeHolderRequest = new RequestOptions();
                        placeHolderRequest.placeholder(R.drawable.no_event_image);
                        Glide.with(EachEventInformation.this).setDefaultRequestOptions(placeHolderRequest).load(imageLink).into(eventImage);
                    }
                }
            }
        });
    }
}
