package com.application.pradyotprakash.newattendanceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentSetupActivity extends AppCompatActivity {

    private CircleImageView studentSetupImage;
    private Uri studentMainImageURI = null;
    private EditText studentName, studentUsn, studentClass;
    private AutoCompleteTextView studentBranch, studentSemester;
    private Button studentSetupBtn;
    private StorageReference mStudentStorageReference;
    private FirebaseAuth mAuth;
    private ProgressBar studentSetupProgress;
    private FirebaseFirestore studentSetupFirestore;
    private String user_id;
    private boolean isChanged = false;
    private ImageView branchSpinner, semesterSpinner;
    private static final String[] branch = new String[]{"Bio Technology Engineering", "Civil Engineering", "Computer Science Engineering", "Electrical & Electronics Engineering", "Electronics & Comm. Engineering", "Information Science & Engineering", "Mechanical Engineering"};
    private static final String[] semester = new String[]{"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_setup);
        Toolbar mToolbar = findViewById(R.id.studentSetupToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Your Details");
        studentSetupProgress = findViewById(R.id.student_setup_progress);
        studentSetupImage = findViewById(R.id.student_setup_image);
        studentSetupBtn = findViewById(R.id.student_setup_btn);
        studentName = findViewById(R.id.student_setup_name);
        studentUsn = findViewById(R.id.student_setup_usn);
        studentClass = findViewById(R.id.student_setup_class);
        studentBranch = findViewById(R.id.student_setup_branch);
        studentBranch.setThreshold(1);
        studentSemester = findViewById(R.id.student_setup_semester);
        studentSemester.setThreshold(1);
        branchSpinner = findViewById(R.id.branch_spinner);
        semesterSpinner = findViewById(R.id.semester_spinner);
        ArrayAdapter<String> adapterBranch = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, branch);
        studentBranch.setAdapter(adapterBranch);
        ArrayAdapter<String> adapterSemester = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, semester);
        studentSemester.setAdapter(adapterSemester);
        branchSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentBranch.showDropDown();
            }
        });
        semesterSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentSemester.showDropDown();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        mStudentStorageReference = FirebaseStorage.getInstance().getReference();
        studentSetupFirestore = FirebaseFirestore.getInstance();
        studentSetupProgress.setVisibility(View.VISIBLE);
        studentSetupBtn.setEnabled(false);
        studentName.setEnabled(false);
        studentUsn.setEnabled(false);
        studentClass.setEnabled(false);
        studentBranch.setEnabled(false);
        studentSemester.setEnabled(false);
        branchSpinner.setEnabled(false);
        semesterSpinner.setEnabled(false);
        studentSetupFirestore.collection("Student").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        String name = task.getResult().getString("name");
                        String usn = task.getResult().getString("usn");
                        String className = task.getResult().getString("className");
                        String branch = task.getResult().getString("branch");
                        String semester = task.getResult().getString("semester");
                        String image = task.getResult().getString("image");
                        studentMainImageURI = Uri.parse(image);
                        studentName.setText(name);
                        studentUsn.setText(usn);
                        studentClass.setText(className);
                        studentBranch.setText(branch);
                        studentSemester.setText(semester);
                        RequestOptions placeHolderRequest = new RequestOptions();
                        placeHolderRequest.placeholder(R.mipmap.default_profile_picture);
                        Glide.with(StudentSetupActivity.this).setDefaultRequestOptions(placeHolderRequest).load(image).into(studentSetupImage);
                    } else {
                        Toast.makeText(StudentSetupActivity.this, "Fill All The Data and Upload a Profile Image.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(StudentSetupActivity.this, "Once The Data is Uploaded You Cannot Change It Again. So Check all the Details Before Uploading..", Toast.LENGTH_LONG).show();
                        studentName.setEnabled(true);
                        studentUsn.setEnabled(true);
                        studentClass.setEnabled(true);
                        studentBranch.setEnabled(true);
                        studentSemester.setEnabled(true);
                        branchSpinner.setEnabled(true);
                        semesterSpinner.setEnabled(true);
                    }
                } else {
                    String retrieving_error = task.getException().getMessage();
                    Toast.makeText(StudentSetupActivity.this, "Error: " + retrieving_error, Toast.LENGTH_SHORT).show();
                }
                studentSetupProgress.setVisibility(View.INVISIBLE);
                studentSetupBtn.setEnabled(true);
            }
        });
        studentSetupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentSetupActivity.this, "Once The Data is Uploaded You Cannot Change It Again. So Check all the Details Before Uploading..", Toast.LENGTH_LONG).show();
                final String student_name = studentName.getText().toString();
                final String student_usn = studentUsn.getText().toString().toUpperCase();
                final String student_class = studentClass.getText().toString().toUpperCase();
                final String student_branch = studentBranch.getText().toString();
                final String student_semester = studentSemester.getText().toString();
                boolean usnCheck = Pattern.matches("[0-9]SG[0-9][0-9][A-Z][A-Z][0-9][0-9][0-9]", student_usn);
                boolean classCheck = Pattern.matches("[A-Z][0-9][0-9][0-9]", student_class);
                if (!TextUtils.isEmpty(student_name) && !TextUtils.isEmpty(student_usn) && !TextUtils.isEmpty(student_class) && !TextUtils.isEmpty(student_branch) && !TextUtils.isEmpty(student_semester) && studentMainImageURI != null) {
                    if (usnCheck) {
                        if (classCheck) {
                            studentSetupProgress.setVisibility(View.VISIBLE);
                            if (isChanged) {
                                StorageReference image_path = mStudentStorageReference.child("student_profile_images").child(user_id + ".jpg");
                                image_path.putFile(studentMainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            storeFireStore(task, student_name, student_usn, student_class, student_branch, student_semester);
                                        } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(StudentSetupActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                            studentSetupProgress.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                            } else {
                                storeFireStore(null, student_name, student_usn, student_class, student_branch, student_semester);
                            }
                        } else {
                            Toast.makeText(StudentSetupActivity.this, "Invalid Class Number.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(StudentSetupActivity.this, "Invalid USN.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(StudentSetupActivity.this, "Fill all the details and add a profile image also.", Toast.LENGTH_LONG).show();
                }
            }
        });
        studentSetupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ((ContextCompat.
                            checkSelfPermission(StudentSetupActivity.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                            (ContextCompat.
                                    checkSelfPermission(StudentSetupActivity.this,
                                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(StudentSetupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        ActivityCompat.requestPermissions(StudentSetupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        cropImage();
                    }
                } else {
                    cropImage();
                }
            }
        });
    }

    private void storeFireStore(Task<UploadTask.TaskSnapshot> task, String student_name, String student_usn, String student_class, String student_branch, String student_semester) {
        Uri download_uri;
        if (task != null) {
            download_uri = task.getResult().getDownloadUrl();
        } else {
            download_uri = studentMainImageURI;
        }
        String token_id = FirebaseInstanceId.getInstance().getToken();
        HashMap<String, String> studentMap = new HashMap<>();
        studentMap.put("name", student_name);
        studentMap.put("usn", student_usn);
        studentMap.put("className", student_class);
        studentMap.put("branch", student_branch);
        studentMap.put("semester", student_semester);
        studentMap.put("image", download_uri.toString());
        studentMap.put("token_id", token_id);
        studentSetupFirestore.collection("Student").document(user_id).set(studentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(StudentSetupActivity.this, "Check All The Details Before Leaving This Page.", Toast.LENGTH_LONG).show();
                } else {
                    String image_error = task.getException().getMessage();
                    Toast.makeText(StudentSetupActivity.this, "Error: " + image_error, Toast.LENGTH_SHORT).show();
                }
                studentSetupProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                studentMainImageURI = result.getUri();
                studentSetupImage.setImageURI(studentMainImageURI);
                isChanged = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void cropImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(StudentSetupActivity.this);
    }
}
