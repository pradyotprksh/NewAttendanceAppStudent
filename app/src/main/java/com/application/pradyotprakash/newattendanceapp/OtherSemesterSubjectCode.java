package com.application.pradyotprakash.newattendanceapp;

public class OtherSemesterSubjectCode {

    private String subjectCode, subjectTeacher;

    public OtherSemesterSubjectCode() {
    }

    public OtherSemesterSubjectCode(String subjectCode, String subjectTeacher) {
        this.subjectCode = subjectCode;
        this.subjectTeacher = subjectTeacher;
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

}
