package com.example.c195;

import DBConnection.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LogInForm.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        DBConnection.startConnection();
        launch();
        try {
            DBConnection.closeConnection();
        } catch (Exception e){

        }
    }
}