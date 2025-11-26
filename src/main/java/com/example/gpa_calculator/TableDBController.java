package com.example.gpa_calculator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class TableDBController {

    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> codeCol;
    @FXML private TableColumn<Course, String> titleCol;
    @FXML private TableColumn<Course, Integer> creditCol;
    @FXML private TableColumn<Course, String> teacher1Col;
    @FXML private TableColumn<Course, String> teacher2Col;
    @FXML private TableColumn<Course, String> gradeCol;

    @FXML private TextField codeField, titleField, creditField, teacher1Field, teacher2Field, gradeField;
    @FXML private Button addBtn, updateBtn, deleteBtn;
    @FXML private Label statusLabel;

    private final ObservableList<Course> courses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        DatabaseHelper.createTable();
        setupTableColumns();
        courseTable.setItems(courses);
        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) fillFields(newSel);
        });

        loadAllCourses();
    }

    private void setupTableColumns() {
        codeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCode()));
        titleCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        creditCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCredit()).asObject());
        teacher1Col.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTeacher1()));
        teacher2Col.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTeacher2()));
        gradeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGrade()));
    }

    private void loadAllCourses() {
        setStatus("Loading courses...");
        Platform.runLater(() -> {
            List<Course> loaded = CourseCRUD.getAllCourses();
            courses.setAll(loaded);
            setStatus("Loaded " + loaded.size() + " course(s).");
        });
    }

    private void fillFields(Course c) {
        codeField.setText(c.getCode());
        titleField.setText(c.getTitle());
        creditField.setText(String.valueOf(c.getCredit()));
        teacher1Field.setText(c.getTeacher1());
        teacher2Field.setText(c.getTeacher2());
        gradeField.setText(c.getGrade());
    }

    @FXML
    private void onAddClicked() {
        try {
            String code = codeField.getText().trim();
            String title = titleField.getText().trim();
            String teacher1 = teacher1Field.getText().trim();
            String teacher2 = teacher2Field.getText().trim();
            String grade = gradeField.getText().trim();
            int credit = Integer.parseInt(creditField.getText().trim());

            if (code.isEmpty() || title.isEmpty() || teacher1.isEmpty() || grade.isEmpty()) {
                setStatus("All fields must be filled.");
                return;
            }

            Course course = new Course(code, title, credit, teacher1, teacher2, grade);
            CourseCRUD.insertCourse(course);

            courses.add(course);
            courseTable.getSelectionModel().select(course);
            clearFields();
            setStatus("Course added successfully.");

        } catch (NumberFormatException e) {
            setStatus("Credit must be a number.");
        } catch (Exception e) {
            setStatus("Error adding course: " + e.getMessage());
        }
    }

    @FXML
    private void onUpdateClicked() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setStatus("Select a course to update.");
            return;
        }

        try {
            selected.setCode(codeField.getText().trim());
            selected.setTitle(titleField.getText().trim());
            selected.setTeacher1(teacher1Field.getText().trim());
            selected.setTeacher2(teacher2Field.getText().trim());
            selected.setGrade(gradeField.getText().trim());
            selected.setCredit(Integer.parseInt(creditField.getText().trim()));

            CourseCRUD.updateCourse(selected);

            int index = courses.indexOf(selected);
            courses.set(index, selected);
            courseTable.getSelectionModel().select(selected);
            setStatus("Course updated successfully.");

        } catch (NumberFormatException e) {
            setStatus("Credit must be a number.");
        } catch (Exception e) {
            setStatus("Error updating course: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteClicked() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setStatus("Select a course to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete course " + selected.getCode() + " - " + selected.getTitle() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Confirm delete");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                try {
                    CourseCRUD.deleteCourse(selected.getCode());
                    courses.remove(selected);
                    clearFields();
                    setStatus("Course deleted successfully.");
                } catch (Exception e) {
                    setStatus("Error deleting course: " + e.getMessage());
                }
            }
        });
    }

    private void clearFields() {
        codeField.clear();
        titleField.clear();
        creditField.clear();
        teacher1Field.clear();
        teacher2Field.clear();
        gradeField.clear();
    }

    private void setStatus(String message) {
        Platform.runLater(() -> statusLabel.setText(message));
    }
}
