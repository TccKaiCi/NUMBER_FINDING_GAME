package com.client.number_finding_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginForm extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("LoginStage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 438);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setResizable(false);
            stage.setTitle("Number finding game");
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();

    }
}