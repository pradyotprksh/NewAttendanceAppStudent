package com.application.pradyotprakash.newattendanceapp;

/**
 * Created by pradyotprakash on 01/03/18.
 */

public class Home {

    String from, to, subject;

    public Home(String from, String to, String subject) {
        this.from = from;
        this.to = to;
        this.subject = subject;
    }

    public Home() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
