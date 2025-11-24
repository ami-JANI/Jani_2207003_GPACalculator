package com.example.gpa_calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class CourseCRUD {

    public static void insertCourse(Course c) {
        String sql = "INSERT INTO courses(title, code, credit, teacher1, teacher2, grade) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getTitle());
            ps.setString(2, c.getCode());
            ps.setInt(3, c.getCredit());
            ps.setString(4, c.getTeacher1());
            ps.setString(5, c.getTeacher2());
            ps.setString(6, c.getGrade());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) c.setId(rs.getInt(1));

        } catch (Exception e) {
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
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("code"),
                        rs.getInt("credit"),
                        rs.getString("teacher1"),
                        rs.getString("teacher2"),
                        rs.getString("grade")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void deleteCourse(int id) {
        String sql = "DELETE FROM courses WHERE id=?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCourse(Course c) {
        String sql = """
            UPDATE courses SET
                title=?, code=?, credit=?, teacher1=?, teacher2=?, grade=?
            WHERE id=?
        """;

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getTitle());
            ps.setString(2, c.getCode());
            ps.setInt(3, c.getCredit());
            ps.setString(4, c.getTeacher1());
            ps.setString(5, c.getTeacher2());
            ps.setString(6, c.getGrade());
            ps.setInt(7, c.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
