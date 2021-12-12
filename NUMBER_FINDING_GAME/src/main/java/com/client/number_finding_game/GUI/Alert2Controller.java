package com.client.number_finding_game.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Alert2Controller implements Initializable {
    @FXML
    Button btn_submit3;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_submit3.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });
    }
}
