package com.example.gpa_calculator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:gpa.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS course (
                    title TEXT,
                    code TEXT PRIMARY KEY,
                    credit INTEGER,
                    grade TEXT,
                    teacher1 TEXT,
                    teacher2 TEXT
                );
                """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ignored) {}
    }

    public static void insertCourse(Course course) {
        String sql = "INSERT INTO course(title, code, credit, grade, teacher1, teacher2) VALUES(?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getTitle());
            pstmt.setString(2, course.getCode());
            pstmt.setInt(3, course.getCredit());
            pstmt.setString(4, course.getGrade());
            pstmt.setString(5, course.getTeacher1());
            pstmt.setString(6, course.getTeacher2());

            pstmt.executeUpdate();
        } catch (SQLException ignored) {}
    }

    public static void updateCourse(Course updatedCourse) {
        String sql = """
                UPDATE course SET title=?, credit=?, grade=?, teacher1=?, teacher2=?
                WHERE code=?
                """;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedCourse.getTitle());
            pstmt.setInt(2, updatedCourse.getCredit());
            pstmt.setString(3, updatedCourse.getGrade());
            pstmt.setString(4, updatedCourse.getTeacher1());
            pstmt.setString(5, updatedCourse.getTeacher2());
            pstmt.setString(6, updatedCourse.getCode());

            pstmt.executeUpdate();
        } catch (SQLException ignored) {}
    }

    public static void deleteCourse(String code) {
        String sql = "DELETE FROM course WHERE code=?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            pstmt.executeUpdate();
        } catch (SQLException ignored) {}
    }

    public static List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Course(
                        rs.getString("title"),
                        rs.getString("code"),
                        rs.getInt("credit"),
                        rs.getString("teacher1"),
                        rs.getString("teacher2"),
                        rs.getString("grade")
                ));
            }

        } catch (SQLException ignored) {}

        return list;
    }
}

/*
Ei file e sqlite database create, course insert, course update (code dhore),
course delete (code dhore), ebong sob course fetch korar logic ase.
Course code ke unique dhore sob CRUD operation kora hoy.
*/
