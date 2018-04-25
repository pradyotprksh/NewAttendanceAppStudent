package com.application.pradyotprakash.newattendanceapp;

public class StudentsStatus extends StatusId{

    private String date;
    private String time;
    private String from;
    private String to;
    private String value;
    private String weekDay;
    private String semester;

    public StudentsStatus(String date, String time, String from, String to, String value, String weekDay, String semester) {
        this.date = date;
        this.time = time;
        this.from = from;
        this.to = to;
        this.value = value;
        this.weekDay = weekDay;
        this.semester = semester;
    }

    public StudentsStatus() {
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

