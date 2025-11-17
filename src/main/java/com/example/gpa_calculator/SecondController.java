package com.example.gpa_calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecondController {

    @FXML private TextField courseTitleField;
    @FXML private TextField courseCodeField;
    @FXML private TextField courseCreditField;
    @FXML private TextField teacher1Field;
    @FXML private TextField teacher2Field;
    @FXML private ComboBox<String> gradeCombo;
    @FXML private Button nextCourseBtn;
    @FXML private Button calculateBtn;

    private int TOTAL_CREDIT;
    private static int CreditSum = 0;

    private static final List<Course> courseList = new ArrayList<>();

    @FXML
    public void initialize() {
        gradeCombo.getItems().addAll("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "D", "F");
        updateButtonStates();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Course Added");
        alert.setHeaderText(null);
        alert.setContentText("Course added successfully!");
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onNextCourse() {
        try {
            String title = courseTitleField.getText().trim();
            String code = courseCodeField.getText().trim();
            String teacher1 = teacher1Field.getText().trim();
            String teacher2 = teacher2Field.getText().trim();
            String grade = gradeCombo.getValue();
            String creditText = courseCreditField.getText().trim();

            if (title.isEmpty() || code.isEmpty() || teacher1.isEmpty() || creditText.isEmpty() || grade == null) {
                showErrorAlert("Error, try again!");
                return;
            }

            int credit;
            try {
                credit = Integer.parseInt(creditText);
                if (credit <= 0) {
                    showErrorAlert("Credit must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                showErrorAlert("Credit must be a number.");
                return;
            }

            courseList.add(new Course(title, code, credit, teacher1, teacher2, grade));
            CreditSum += credit;

            showSuccessAlert();
            clearFields();
            updateButtonStates();

        } catch (Exception e) {
            showErrorAlert("Try again.");
        }
    }

    @FXML
    private void onCalculate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gpa_calculator/result-page.fxml"));
            Parent root = loader.load();

            ResultController ctrl = loader.getController();
            ctrl.showResults(courseList);

            Stage stage = (Stage) nextCourseBtn.getScene().getWindow();
            stage.setScene(new Scene(root, 540, 960));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        courseTitleField.clear();
        courseCodeField.clear();
        courseCreditField.clear();
        teacher1Field.clear();
        teacher2Field.clear();
        gradeCombo.setValue(null);
    }

    private void updateButtonStates() {
        nextCourseBtn.setDisable(CreditSum >= TOTAL_CREDIT);
        calculateBtn.setDisable(CreditSum != TOTAL_CREDIT);
    }

    public void setTotalCreditRequired(int totalCredit) {
        this.TOTAL_CREDIT = totalCredit;
        updateButtonStates();
    }
}

// Ei file e user er course input newa, validation kora, course list e add kora,
// success or error alert dekhano, collected credit check kora, button enable/disable,
// and finally result page e data pathano
