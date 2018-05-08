package com.application.pradyotprakash.newattendanceapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentMainActivity extends AppCompatActivity {

    private Toolbar student_main_toolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore studentMainFirestore;
    private String user_id;
    private TextView studentMainName;
    private CircleImageView studentMainImage;
    private BottomNavigationView studentMainBottomNavigation;
    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private NotesFragment notesFragment;
    private DetailsFragment detailsFragment;
    private EventFragment eventFragment;
    private static String classValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        student_main_toolbar = findViewById(R.id.student_notification_toolbar);
        setSupportActionBar(student_main_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        studentMainName = findViewById(R.id.student_main_name);
        studentMainImage = findViewById(R.id.student_main_image);
        mAuth = FirebaseAuth.getInstance();
        studentMainFirestore = FirebaseFirestore.getInstance();
        studentMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToSetup();
            }
        });
        studentMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToSetup();
            }
        });
        student_main_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToSetup();
            }
        });
        studentMainBottomNavigation = findViewById(R.id.studentBottomNavigation);
        studentMainBottomNavigation.setEnabled(false);
        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        notesFragment = new NotesFragment();
        detailsFragment = new DetailsFragment();
        eventFragment = new EventFragment();
        studentMainBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btm_home:
                        replaceFragment(homeFragment);
                        return true;
                    case R.id.btm_notification:
                        replaceFragment(notificationFragment);
                        return true;
                    case R.id.btm_notes:
                        replaceFragment(notesFragment);
                        return true;
                    case R.id.btm_events:
                        replaceFragment(eventFragment);
                        return true;
                    case R.id.btm_details:
                        replaceFragment(detailsFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        } else {
            user_id = mAuth.getCurrentUser().getUid();
            studentMainFirestore.collection("Student").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
                            sendToSetup();
                        } else {
                            String name = task.getResult().getString("name");
                            classValue = task.getResult().getString("className");
                            studentMainName.setText("Welcome, " + name);
                            String image = task.getResult().getString("image");
                            RequestOptions placeHolderRequest = new RequestOptions();
                            placeHolderRequest.placeholder(R.mipmap.default_profile_picture);
                            Glide.with(StudentMainActivity.this).setDefaultRequestOptions(placeHolderRequest).load(image).into(studentMainImage);
                            studentMainBottomNavigation.setEnabled(true);
                        }
                    } else {
                        String retrieving_error = task.getException().getMessage();
                        Toast.makeText(StudentMainActivity.this, "Error: " + retrieving_error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static String getClassValue() {
        return classValue;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout_btn:
                logOut();
                return true;
            case R.id.action_setting_btn:
                sendToSetup();
                return true;
            default:
                return false;
        }
    }

    private void sendToSetup() {
        Intent intentSetup = new Intent(StudentMainActivity.this, StudentSetupActivity.class);
        startActivity(intentSetup);
    }

    private void logOut() {
        LayoutInflater li = LayoutInflater.from(StudentMainActivity.this);
        View promptsView = li.inflate(R.layout.prompts1, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                StudentMainActivity.this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Map<String, Object> tokenRemoveMap = new HashMap<>();
                                tokenRemoveMap.put("token_id", FieldValue.delete());
                                studentMainFirestore.collection("Student").document(user_id).update(tokenRemoveMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mAuth.signOut();
                                        sendToLogin();
                                    }
                                });
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void sendToLogin() {
        Intent intent = new Intent(StudentMainActivity.this, StudentLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void replaceFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.studentMainContainer, fragment);
        fragmentTransaction.commit();
    }

}
