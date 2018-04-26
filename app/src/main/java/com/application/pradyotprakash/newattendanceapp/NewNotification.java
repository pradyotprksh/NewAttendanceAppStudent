package com.application.pradyotprakash.newattendanceapp;

public class NewNotification {

    String from, message, on, designation, senderName, senderImage;

    public NewNotification(String from, String message, String on, String designation, String senderName, String senderImage) {
        this.from = from;
        this.message = message;
        this.on = on;
        this.designation = designation;
        this.senderName = senderName;
        this.senderImage = senderImage;
    }

    public NewNotification() {
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

}
