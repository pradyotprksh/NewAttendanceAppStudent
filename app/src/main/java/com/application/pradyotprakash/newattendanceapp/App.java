package com.application.pradyotprakash.newattendanceapp;

import android.app.Application;
import android.content.Intent;

/**
 * Created by pradyotprakash on 04/03/18.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        startService(new Intent(this, MyService.class));
    }

}
