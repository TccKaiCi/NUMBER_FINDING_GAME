package com.client.number_finding_game.Controllers;

import com.client.number_finding_game.LoginForm;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WaitingRoomController implements Initializable {
    @FXML
    private Button btn_single;
    @FXML
    private Button btn_multi;
    @FXML
    private Button btn_practice;
    @FXML
    private Button btn_account;
    @FXML
    private Button btn_quit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_practice.setOnAction(this::setBtn_practice);
    }

    public void setBtn_practice(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("PracticeStage.fxml"));
            Parent root = fxmlLoader.load();
//            WaitingRoomController waitingRoomController = fxmlLoader.getController();
//            UserModel u = new UserModel(tf_username.getText(),tf_password.getText());
//            waitingRoomController.setUserModel(u);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Practice play");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
