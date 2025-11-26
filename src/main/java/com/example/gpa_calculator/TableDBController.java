package com.example.gpa_calculator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class TableDBController {

    @FXML private TableView<Course> courseTable;

    @FXML private TableColumn<Course, String> titleCol;
    @FXML private TableColumn<Course, String> codeCol;
    @FXML private TableColumn<Course, Integer> creditCol;
    @FXML private TableColumn<Course, String> teacher1Col;
    @FXML private TableColumn<Course, String> teacher2Col;
    @FXML private TableColumn<Course, String> gradeCol;

    @FXML private TextField titleField;
    @FXML private TextField codeField;
    @FXML private TextField creditField;
    @FXML private TextField teacher1Field;
    @FXML private TextField teacher2Field;
    @FXML private TextField gradeField;

    private final Label gpaLabel = new Label();

    @FXML
    public void initialize() {
        DatabaseHelper.createTable();

        titleCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        codeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCode()));
        creditCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCredit()).asObject());
        teacher1Col.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTeacher1()));
        teacher2Col.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTeacher2()));
        gradeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGrade()));

        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                titleField.setText(newSel.getTitle());
                codeField.setText(newSel.getCode());
                creditField.setText(String.valueOf(newSel.getCredit()));
                teacher1Field.setText(newSel.getTeacher1());
                teacher2Field.setText(newSel.getTeacher2());
                gradeField.setText(newSel.getGrade());
            }
        });

        gpaLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        addGpaLabelToBottomIfPossible();

        refreshTable();
    }


    private void addGpaLabelToBottomIfPossible() {
        try {
            if (courseTable.getParent() instanceof VBox vbox && !vbox.getChildren().contains(gpaLabel)) {
                vbox.getChildren().add(gpaLabel);
            }
        } catch (Exception ignored) {}
    }

    @FXML
    private void onAddClicked() {
        String title = titleField.getText().trim();
        String code = codeField.getText().trim();
        String creditText = creditField.getText().trim();
        String t1 = teacher1Field.getText().trim();
        String t2 = teacher2Field.getText().trim();
        String grade = gradeField.getText().trim();

        if (title.isEmpty() || code.isEmpty() || creditText.isEmpty() || grade.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill Title, Code, Credit and Grade.");
            return;
        }

        int credit;
        try {
            credit = Integer.parseInt(creditText);
            if (credit <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Credit must be a positive integer.");
            return;
        }

        Course c = new Course(code, title, credit, t1, t2, grade);
        CourseCRUD.insertCourse(c);
        refreshTable();
        clearFields();
    }

    @FXML
    private void onUpdateClicked() {
        Course sel = courseTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert(Alert.AlertType.INFORMATION, "Select a row to update.");
            return;
        }

        String title = titleField.getText().trim();
        String code = codeField.getText().trim();
        String creditText = creditField.getText().trim();
        String t1 = teacher1Field.getText().trim();
        String t2 = teacher2Field.getText().trim();
        String grade = gradeField.getText().trim();

        if (title.isEmpty() || code.isEmpty() || creditText.isEmpty() || grade.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill Title, Code, Credit and Grade.");
            return;
        }

        int credit;
        try {
            credit = Integer.parseInt(creditText);
            if (credit <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Credit must be a positive integer.");
            return;
        }

        sel.setTitle(title);
        sel.setCode(code);
        sel.setCredit(credit);
        sel.setTeacher1(t1);
        sel.setTeacher2(t2);
        sel.setGrade(grade);

        CourseCRUD.updateCourse(sel);
        refreshTable();
        clearFields();
    }

    @FXML
    private void onDeleteClicked() {
        Course sel = courseTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert(Alert.AlertType.INFORMATION, "Select a row to delete.");
            return;
        }
        CourseCRUD.deleteCourse(sel.getCode());
        refreshTable();
    }

    private void refreshTable() {
        ObservableList<Course> list = CourseCRUD.getAllCourses();
        courseTable.setItems(list);
        calculateGPA(list);
    }

    private void calculateGPA(ObservableList<Course> list) {
        double totalPoints = 0;
        double totalCredits = 0;

        for (Course c : list) {
            double gp = gradeToPoint(c.getGrade());
            totalPoints += gp * c.getCredit();
            totalCredits += c.getCredit();
        }

        double gpa = (totalCredits == 0) ? 0 : totalPoints / totalCredits;
        gpaLabel.setText(String.format("GPA: %.2f", gpa));
    }

    private double gradeToPoint(String grade) {
        if (grade == null) return 0;
        switch (grade.trim().toUpperCase()) {
            case "A+": return 4.0;
            case "A": return 3.75;
            case "A-": return 3.50;
            case "B+": return 3.25;
            case "B": return 3.0;
            case "B-": return 2.75;
            case "C+": return 2.50;
            case "C": return 2.25;
            case "D": return 2.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }

    private void clearFields() {
        titleField.clear();
        codeField.clear();
        creditField.clear();
        teacher1Field.clear();
        teacher2Field.clear();
        gradeField.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert a = new Alert(type);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}
