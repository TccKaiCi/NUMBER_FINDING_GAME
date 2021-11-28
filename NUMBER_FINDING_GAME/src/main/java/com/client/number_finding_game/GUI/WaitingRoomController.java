package com.client.number_finding_game.GUI;

import com.client.number_finding_game.LoginForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        btn_practice.setOnAction(this::setBtn_practiceOnclick);
        btn_account.setOnAction(this::setBtn_accountOnClick);
    }

    public void setBtn_practiceOnclick(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("PracticeStage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Practice play");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setBtn_accountOnClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("EditAccount.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Edit Account Info");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
