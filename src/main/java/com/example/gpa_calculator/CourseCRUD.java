package com.example.gpa_calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CourseCRUD {

    public static void insertCourse(Course c) {
        String sql = "INSERT INTO courses(title, code, credit, teacher1, teacher2, grade) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getTitle());
            ps.setString(2, c.getCode());
            ps.setInt(3, c.getCredit());
            ps.setString(4, c.getTeacher1());
            ps.setString(5, c.getTeacher2());
            ps.setString(6, c.getGrade());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateCourse(Course c) {
        String sql = "UPDATE courses SET title=?, credit=?, teacher1=?, teacher2=?, grade=? WHERE code=?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getTitle());
            ps.setInt(2, c.getCredit());
            ps.setString(3, c.getTeacher1());
            ps.setString(4, c.getTeacher2());
            ps.setString(5, c.getGrade());
            ps.setString(6, c.getCode());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteCourse(String code) {
        String sql = "DELETE FROM courses WHERE code=?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ObservableList<Course> getAllCourses() {
        ObservableList<Course> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM courses";

        try (Connection conn = DatabaseHelper.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
