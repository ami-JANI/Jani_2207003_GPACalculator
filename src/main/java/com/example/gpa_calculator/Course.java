package com.example.gpa_calculator;

public class Course {

    private String title;
    private String code;
    private int credit;
    private String teacher1;
    private String teacher2;
    private String grade;

    public Course(String title, String code, int credit, String teacher1, String teacher2, String grade) {
        this.title = title;
        this.code = code;
        this.credit = credit;
        this.teacher1 = teacher1;
        this.teacher2 = teacher2;
        this.grade = grade;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }

    public String getTeacher1() { return teacher1; }
    public void setTeacher1(String teacher1) { this.teacher1 = teacher1; }

    public String getTeacher2() { return teacher2; }
    public void setTeacher2(String teacher2) { this.teacher2 = teacher2; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
