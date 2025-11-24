package com.example.gpa_calculator;

import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:gpa_data.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS courses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                code TEXT,
                credit INTEGER,
                teacher1 TEXT,
                teacher2 TEXT,
                grade TEXT
            );
        """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
