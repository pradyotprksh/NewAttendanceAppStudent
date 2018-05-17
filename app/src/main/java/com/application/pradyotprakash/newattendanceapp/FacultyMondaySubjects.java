package com.application.pradyotprakash.newattendanceapp;

public class FacultyMondaySubjects extends SubjectId {

    String from, subjectCode, subjectName, classValue, to, weekDay, subjectTeacher;

    public FacultyMondaySubjects() {
    }

    public FacultyMondaySubjects(String from, String subjectCode, String subjectName, String classValue, String to, String weekDay, String subjectTeacher) {
        this.from = from;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.classValue = classValue;
        this.to = to;
        this.weekDay = weekDay;
        this.subjectTeacher = subjectTeacher;
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

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getClassValue() {
        return classValue;
    }

    public void setClassValue(String classValue) {
        this.classValue = classValue;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
}
