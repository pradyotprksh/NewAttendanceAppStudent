package com.application.pradyotprakash.newattendanceapp;

/**
 * Created by pradyotprakash on 01/03/18.
 */

public class Home {

    String from, to, subjectName, subjectCode, subjectTeacher;

    public Home(String from, String to, String subjectName, String subjectCode, String subjectTeacher) {
        this.from = from;
        this.to = to;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.subjectTeacher = subjectTeacher;
    }

    public Home() {
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(String subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
