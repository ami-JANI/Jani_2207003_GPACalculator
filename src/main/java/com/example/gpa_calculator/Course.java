package com.example.gpa_calculator;

public class Course {

    private int id;
    private String title;
    private String code;
    private int credit;
    private String teacher1;
    private String teacher2;
    private String grade;

    public Course(int id, String title, String code, int credit,
                  String teacher1, String teacher2, String grade) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.credit = credit;
        this.teacher1 = teacher1;
        this.teacher2 = teacher2;
        this.grade = grade;
    }

    public Course(String title, String code, int credit,
                  String teacher1, String teacher2, String grade) {
        this.title = title;
        this.code = code;
        this.credit = credit;
        this.teacher1 = teacher1;
        this.teacher2 = teacher2;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public String getTeacher1() {
        return teacher1;
    }

    public String getTeacher2() {
        return teacher2;
    }

    public String getGrade() {
        return grade;
    }

    public String getTeacherNames() {
        if ((teacher1 == null || teacher1.isEmpty()) &&
                (teacher2 == null || teacher2.isEmpty()))
            return "";

        if (teacher1 == null || teacher1.isEmpty())
            return teacher2;

        if (teacher2 == null || teacher2.isEmpty())
            return teacher1;

        return "1. " + teacher1 + "\n2. " + teacher2;
    }
}

// Ei file e Course class er moddhe DB er jonno id, title, code, credit,
// teacher1, teacher2, save hoy
// Ekta constructor DB theke data load er jonno, arekta insert er jonno
