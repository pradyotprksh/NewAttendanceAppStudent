package com.application.pradyotprakash.newattendanceapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class TimetableActivity extends AppCompatActivity {

    private TextView monday, tuesday, wednesday, thursday, friday, saturday;
    private ViewPager timetablePager;
    private OnlyFacultySubjectPagerViewAdapter mPagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        monday = findViewById(R.id.timetable_monday);
        tuesday = findViewById(R.id.timetable_tuesday);
        wednesday = findViewById(R.id.timetable_wednesday);
        thursday = findViewById(R.id.timetable_thursday);
        friday = findViewById(R.id.timetable_friday);
        saturday = findViewById(R.id.timetable_saturday);
        timetablePager = findViewById(R.id.timetablePager);
        mPagerViewAdapter = new OnlyFacultySubjectPagerViewAdapter(getSupportFragmentManager());
        timetablePager.setAdapter(mPagerViewAdapter);
        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timetablePager.setCurrentItem(0);
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timetablePager.setCurrentItem(1);
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timetablePager.setCurrentItem(2);
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timetablePager.setCurrentItem(3);
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timetablePager.setCurrentItem(4);
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timetablePager.setCurrentItem(5);
            }
        });
        timetablePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void changeTabs(int position) {
        if (position == 0) {
            monday.setTextColor(getColor(R.color.textTabBright));
            monday.setTextSize(22);

            tuesday.setTextColor(getColor(R.color.textTabLight));
            tuesday.setTextSize(16);

            wednesday.setTextColor(getColor(R.color.textTabLight));
            wednesday.setTextSize(16);

            thursday.setTextColor(getColor(R.color.textTabLight));
            thursday.setTextSize(16);

            friday.setTextColor(getColor(R.color.textTabLight));
            friday.setTextSize(16);

            saturday.setTextColor(getColor(R.color.textTabLight));
            saturday.setTextSize(16);
        }

        if (position == 1) {
            tuesday.setTextColor(getColor(R.color.textTabBright));
            tuesday.setTextSize(22);

            monday.setTextColor(getColor(R.color.textTabLight));
            monday.setTextSize(16);

            wednesday.setTextColor(getColor(R.color.textTabLight));
            wednesday.setTextSize(16);

            thursday.setTextColor(getColor(R.color.textTabLight));
            thursday.setTextSize(16);

            friday.setTextColor(getColor(R.color.textTabLight));
            friday.setTextSize(16);

            saturday.setTextColor(getColor(R.color.textTabLight));
            saturday.setTextSize(16);
        }

        if (position == 2) {
            wednesday.setTextColor(getColor(R.color.textTabBright));
            wednesday.setTextSize(22);

            tuesday.setTextColor(getColor(R.color.textTabLight));
            tuesday.setTextSize(16);

            monday.setTextColor(getColor(R.color.textTabLight));
            monday.setTextSize(16);

            thursday.setTextColor(getColor(R.color.textTabLight));
            thursday.setTextSize(16);

            friday.setTextColor(getColor(R.color.textTabLight));
            friday.setTextSize(16);

            saturday.setTextColor(getColor(R.color.textTabLight));
            saturday.setTextSize(16);
        }

        if (position == 3) {
            thursday.setTextColor(getColor(R.color.textTabBright));
            thursday.setTextSize(22);

            tuesday.setTextColor(getColor(R.color.textTabLight));
            tuesday.setTextSize(16);

            wednesday.setTextColor(getColor(R.color.textTabLight));
            wednesday.setTextSize(16);

            monday.setTextColor(getColor(R.color.textTabLight));
            monday.setTextSize(16);

            friday.setTextColor(getColor(R.color.textTabLight));
            friday.setTextSize(16);

            saturday.setTextColor(getColor(R.color.textTabLight));
            saturday.setTextSize(16);
        }

        if (position == 4) {
            friday.setTextColor(getColor(R.color.textTabBright));
            friday.setTextSize(22);

            tuesday.setTextColor(getColor(R.color.textTabLight));
            tuesday.setTextSize(16);

            wednesday.setTextColor(getColor(R.color.textTabLight));
            wednesday.setTextSize(16);

            thursday.setTextColor(getColor(R.color.textTabLight));
            thursday.setTextSize(16);

            monday.setTextColor(getColor(R.color.textTabLight));
            monday.setTextSize(16);

            saturday.setTextColor(getColor(R.color.textTabLight));
            saturday.setTextSize(16);
        }

        if (position == 5) {
            saturday.setTextColor(getColor(R.color.textTabBright));
            saturday.setTextSize(22);

            tuesday.setTextColor(getColor(R.color.textTabLight));
            tuesday.setTextSize(16);

            wednesday.setTextColor(getColor(R.color.textTabLight));
            wednesday.setTextSize(16);

            thursday.setTextColor(getColor(R.color.textTabLight));
            thursday.setTextSize(16);

            friday.setTextColor(getColor(R.color.textTabLight));
            friday.setTextSize(16);

            monday.setTextColor(getColor(R.color.textTabLight));
            monday.setTextSize(16);
        }
    }
}
