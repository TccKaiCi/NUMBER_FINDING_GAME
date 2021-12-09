package com.client.number_finding_game.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertController implements Initializable {

    @FXML
    Button btn_submit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_submit.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();
        });
    }


}
