package com.example.gpa_calculator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ResultController {

    @FXML private Label resultLabel;
    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> codeCol;
    @FXML private TableColumn<Course, String> titleCol;
    @FXML private TableColumn<Course, Integer> creditCol;
    @FXML private TableColumn<Course, String> teacherCol;
    @FXML private TableColumn<Course, String> gradeCol;

    @FXML
    public void initialize() {
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<>("teacher1")); // or combined
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));

        resultLabel.setStyle("-fx-font-size: 26px; -fx-text-fill: white;");
        resultLabel.setWrapText(true);

        loadCoursesFromDB();
    }

    private void loadCoursesFromDB() {
        ObservableList<Course> courses = CourseCRUD.getAllCourses();
        courseTable.setItems(courses);

        double totalGP = 0;
        int totalCredits = 0;

        for (Course c : courses) {
            totalGP += c.getCredit() * gradeToPoint(c.getGrade());
            totalCredits += c.getCredit();
        }

        if (totalCredits > 0) {
            double gpa = totalGP / totalCredits;
            resultLabel.setText("Your GPA: " + String.format("%.2f", gpa));
        } else {
            resultLabel.setText("No courses found.");
        }
    }

    private double gradeToPoint(String g) {
        return switch (g) {
            case "A+" -> 4.0;
            case "A" -> 3.75;
            case "A-" -> 3.50;
            case "B+" -> 3.25;
            case "B" -> 3.0;
            case "B-" -> 2.75;
            case "C+" -> 2.50;
            case "C" -> 2.25;
            case "D" -> 2.00;
            default -> 0.0;
        };
    }
}

// Ei file e result screen e DB theke sob course fetch kore,
// TableView te show kore, GPA calculate kore resultLabel e display kore.
