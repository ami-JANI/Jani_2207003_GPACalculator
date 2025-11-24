package com.example.gpa_calculator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {

    @FXML private Button myButton;
    @FXML private TextField totalCreditField;
    @FXML private TextField textField;

    @FXML
    private void openSecondPage() {
        try {
            DatabaseHelper.createTable();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("GPA-Calc.fxml"));
            Parent root = loader.load();

            SecondController controller = loader.getController();
            int totalCredit = Integer.parseInt(totalCreditField.getText());
            controller.setTotalCreditRequired(totalCredit);

            Stage stage = (Stage) myButton.getScene().getWindow();
            stage.setScene(new Scene(root, 540, 960));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearTextField() {
        textField.clear();
    }
}

// Ei file e first page theke next scene load kore
// DatabaseHelper diye table create kore
// user er total credit niye SecondController e pathai
