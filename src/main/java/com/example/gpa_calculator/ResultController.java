package com.example.gpa_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

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
        teacherCol.setCellValueFactory(new PropertyValueFactory<>("teacherNames"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));

        resultLabel.setStyle("-fx-font-size: 26px; -fx-text-fill: white;");
        resultLabel.setWrapText(true);
    }

    public void showResults(List<Course> courseList) {

        courseTable.getItems().addAll(courseList);

        double totalGP = 0;
        int totalCredits = 0;

        for (Course c : courseList) {
            totalGP += c.getCredit() * gradeToPoint(c.getGrade());
            totalCredits += c.getCredit();
        }

        double gpa = totalGP / totalCredits;

        resultLabel.setText("Your GPA: " + String.format("%.2f", gpa));
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
// Ei file e result screen er table setup, course list table e show kore
// sob course er grade + credit diye GPA calculate kore label e display kore