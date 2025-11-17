package com.example.gpa_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/gpa_calculator/hello-view.fxml"));

        if (root == null) {
            System.out.println("FXML not found!");
            return;
        }
        int height = 960;
        int width = height * 9 / 16;
        Scene scene = new Scene(root, width, height);
        stage.setTitle("GPA Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
