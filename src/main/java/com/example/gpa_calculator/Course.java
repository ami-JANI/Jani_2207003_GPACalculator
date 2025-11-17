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

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public int getCredit() {
        return credit;
    }

    public String getGrade() {
        return grade;
    }

    public String getTeacherNames() {
        if (teacher1 == null || teacher1.isEmpty()) return teacher2;
        if (teacher2 == null || teacher2.isEmpty()) return teacher1;
        return "1. " + teacher1 + "\n2. " + teacher2;
    }
}

// Ei file e Course class er mddhe course er title, code, credit, teacher der naam,
// grade r moto data store kora jay. Get diye data fetch kora jay,
// ebong getTeacherNames() diye duijon teacher ke ek sathe show kora jay
