package com.application.pradyotprakash.newattendanceapp;

import android.support.annotation.NonNull;

/**
 * Created by pradyotprakash on 08/03/18.
 */

public class SubjectId {
    public String subjectId;

    public <T extends SubjectId> T withId(@NonNull final String id) {
        this.subjectId = id;
        return (T) this;
    }
}
