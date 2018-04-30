package com.application.pradyotprakash.newattendanceapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationActivity extends AppCompatActivity {

    private TextView senderName, senderMessage, messageOn;
    private CircleImageView mImage;
    private Toolbar student_notification_toolbar;
    private String dataMessage, dataFrom, dataDesignation, name, image, messageOnValue;
    private FirebaseFirestore mFirestore;
    private Uri senderImageURI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        student_notification_toolbar = findViewById(R.id.student_notification_toolbar);
        setSupportActionBar(student_notification_toolbar);
        getSupportActionBar().setTitle("Message Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dataMessage = getIntent().getStringExtra("message");
        dataFrom = getIntent().getStringExtra("from_user_id");
        dataDesignation = getIntent().getStringExtra("from_designation");
        messageOnValue = getIntent().getStringExtra("message_on");
        senderName = findViewById(R.id.sender_name);
        senderMessage = findViewById(R.id.sender_message);
        messageOn = findViewById(R.id.message_on);
        mImage = findViewById(R.id.sender_image);
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection(dataDesignation)
                .document(dataFrom)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                name = task.getResult().getString("name");
                                image = task.getResult().getString("image");
                                senderImageURI = Uri.parse(image);
                                senderName.setText(name);
                                senderMessage.setText(dataMessage);
                                messageOn.setText(messageOnValue);
                                RequestOptions placeHolderRequest = new RequestOptions();
                                placeHolderRequest.placeholder(R.mipmap.default_profile_picture);
                                try {
                                    Glide.with(NotificationActivity.this).setDefaultRequestOptions(placeHolderRequest).load(image).into(mImage);
                                } catch (Exception e) {
                                    Toast.makeText(NotificationActivity.this, "...", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(NotificationActivity.this, "Notification Is Deleted.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String retrieving_error = task.getException().getMessage();
                            Toast.makeText(NotificationActivity.this, "Error: " + retrieving_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
