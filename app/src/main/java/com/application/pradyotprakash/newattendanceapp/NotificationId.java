package com.application.pradyotprakash.newattendanceapp;

import android.support.annotation.NonNull;

public class NotificationId {

    public String notificationId;

    public <T extends NotificationId> T withId(@NonNull final String id) {
        this.notificationId = id;
        return (T) this;
    }

}
