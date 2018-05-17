package com.application.pradyotprakash.newattendanceapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class OnlyFacultySubjectPagerViewAdapter extends FragmentPagerAdapter {

    public OnlyFacultySubjectPagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FacultyTimetableMondayFragment mondayFragment = new FacultyTimetableMondayFragment();
                return mondayFragment;
            case 1:
                FacultyTimetableTuesdayFragment tuesdayFragment = new FacultyTimetableTuesdayFragment();
                return tuesdayFragment;
            case 2:
                FacultyTimetableWednesdayFragment wednesdayFragment = new FacultyTimetableWednesdayFragment();
                return wednesdayFragment;
            case 3:
                FacultyTimetableThursdayFragment thursdayFragment = new FacultyTimetableThursdayFragment();
                return thursdayFragment;
            case 4:
                FacultyTimetableFridayFragment fridayFragment = new FacultyTimetableFridayFragment();
                return fridayFragment;
            case 5:
                FacultyTimetableSaturdayFragment saturdayFragment = new FacultyTimetableSaturdayFragment();
                return saturdayFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }
}

