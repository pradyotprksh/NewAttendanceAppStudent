package com.application.pradyotprakash.newattendanceapp;

import android.support.annotation.NonNull;

public class StatusId {
    public String statusId;

    public <T extends StatusId> T withId(@NonNull final String id) {
        this.statusId = id;
        return (T) this;
    }
}
