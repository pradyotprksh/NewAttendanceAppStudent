package com.application.pradyotprakash.newattendanceapp;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationActivity extends AppCompatActivity {

    private TextView senderName, senderMessage, messageOn;
    private CircleImageView mImage;
    private Toolbar student_notification_toolbar;
    private String dataMessage, dataFrom, dataDesignation, name, image, messageOnValue;
    private FirebaseFirestore mFirestore;
    private Uri senderImageURI = null;
    private LikeButton starBtn;
    private FirebaseFirestore mFirestore1, mFirestore2;
    private String user_id, notificationId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        student_notification_toolbar = findViewById(R.id.student_notification_toolbar);
        setSupportActionBar(student_notification_toolbar);
        getSupportActionBar().setTitle("Message Details");
        dataMessage = getIntent().getStringExtra("message");
        dataFrom = getIntent().getStringExtra("from_user_id");
        dataDesignation = getIntent().getStringExtra("from_designation");
        messageOnValue = getIntent().getStringExtra("message_on");
        notificationId = getIntent().getStringExtra("notificationId");
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
        starBtn = findViewById(R.id.star_button);
        mFirestore1 = FirebaseFirestore.getInstance();
        mFirestore2 = FirebaseFirestore.getInstance();
        starBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                HashMap<String, Object> likedNotification = new HashMap<>();
                likedNotification.put("liked", "true");
                mFirestore1.collection("Student").document(user_id).collection("Notifications").document(notificationId).update(likedNotification).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            String image_error = task.getException().getMessage();
                            Toast.makeText(NotificationActivity.this, "Error: " + image_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                HashMap<String, Object> likedNotification = new HashMap<>();
                likedNotification.put("liked", FieldValue.delete());
                mFirestore1.collection("Student").document(user_id).collection("Notifications").document(notificationId).update(likedNotification).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            String image_error = task.getException().getMessage();
                            Toast.makeText(NotificationActivity.this, "Error: " + image_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        try {
            mFirestore1.collection("Student").document(user_id).collection("Notifications").document(notificationId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            try {
                                String liked = task.getResult().getString("liked");
                                if (liked.equals("true")) {
                                    starBtn.setLiked(true);
                                } else {
                                    starBtn.setLiked(false);
                                }
                            } catch (Exception e) {
                                starBtn.setLiked(false);
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
        }
    }

}
