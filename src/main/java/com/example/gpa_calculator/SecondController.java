package com.example.gpa_calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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

    @FXML
    public void initialize() {
        gradeCombo.getItems().addAll("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "D", "F");
        updateButtonStates();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
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
                showErrorAlert("Please fill all fields and select a grade!");
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

            Course course = new Course(code, title, credit, teacher1, teacher2, grade);
            CourseCRUD.insertCourse(course);


            CreditSum += credit;
            showSuccessAlert("Course added successfully!");
            clearFields();
            updateButtonStates();

        } catch (Exception e) {
            showErrorAlert("Error while adding course. Try again.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onCalculate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gpa_calculator/tabledb.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) nextCourseBtn.getScene().getWindow();
            stage.setScene(new Scene(root, 540, 960));

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error opening table page.");
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

// Ei file e user input theke course create kore validation kore,
// Database e insert kore CourseCRUD use kore,
// success/error alert dekha, credit sum check kore button enable/disable,
// result page e navigate kore.
