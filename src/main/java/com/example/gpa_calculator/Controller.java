package com.example.gpa_calculator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> titleCol;
    @FXML private TableColumn<Course, String> codeCol;
    @FXML private TableColumn<Course, Integer> creditCol;
    @FXML private TableColumn<Course, String> teacher1Col;
    @FXML private TableColumn<Course, String> teacher2Col;
    @FXML private TableColumn<Course, String> gradeCol;

    @FXML private TextField titleField, codeField, creditField, teacher1Field, teacher2Field, gradeField;

    @FXML
    public void initialize() {
        DatabaseHelper.createTable();

        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        codeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCode()));
        creditCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCredit()).asObject());
        teacher1Col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTeacher1()));
        teacher2Col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTeacher2()));
        gradeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGrade()));

        refreshTable();
        setupRowSelection();
    }


    private void setupRowSelection() {
        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                titleField.setText(newV.getTitle());
                codeField.setText(newV.getCode());
                creditField.setText(String.valueOf(newV.getCredit()));
                teacher1Field.setText(newV.getTeacher1());
                teacher2Field.setText(newV.getTeacher2());
                gradeField.setText(newV.getGrade());
            }
        });
    }


    @FXML
    private void addCourse() {
        Course c = new Course(
                titleField.getText(),
                codeField.getText(),
                Integer.parseInt(creditField.getText()),
                teacher1Field.getText(),
                teacher2Field.getText(),
                gradeField.getText()
        );

        CourseCRUD.insertCourse(c);
        refreshTable();
        clearFields();
    }

    @FXML
    private void updateCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Course updated = new Course(
                titleField.getText(),
                codeField.getText(),
                Integer.parseInt(creditField.getText()),
                teacher1Field.getText(),
                teacher2Field.getText(),
                gradeField.getText()
        );

        CourseCRUD.updateCourse(updated);
        refreshTable();
    }


    @FXML
    private void deleteCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        CourseCRUD.deleteCourse(selected.getCode());
        refreshTable();
        clearFields();
    }


    private void refreshTable() {
        ObservableList<Course> list = FXCollections.observableArrayList(DatabaseHelper.getAllCourses());
        courseTable.setItems(list);
    }

    private void clearFields() {
        titleField.clear();
        codeField.clear();
        creditField.clear();
        teacher1Field.clear();
        teacher2Field.clear();
        gradeField.clear();
    }
}
