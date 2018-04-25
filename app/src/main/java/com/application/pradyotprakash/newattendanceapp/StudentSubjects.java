package com.application.pradyotprakash.newattendanceapp;

public class StudentSubjects {

    private String branch, semester, subjectCode, subjectName, subjectTeacher;

    public StudentSubjects(String branch, String semester, String subjectCode, String subjectName, String subjectTeacher) {
        this.branch = branch;
        this.semester = semester;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectTeacher = subjectTeacher;
    }

    public StudentSubjects() {
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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

    public String getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(String subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }
}
