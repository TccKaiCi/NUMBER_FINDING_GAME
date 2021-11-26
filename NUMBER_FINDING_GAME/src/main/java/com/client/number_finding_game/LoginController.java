package com.client.number_finding_game;

import com.server.number_finding_game.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public Button btn_Login;
    @FXML
    public TextField tf_username;
    @FXML
    public PasswordField tf_password;
    @FXML
    public Pane login_background;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_Login.setOnAction(this::onClick);
    }

    public void onClick(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Waiting_room.fxml"));
            Parent root = fxmlLoader.load();
//            WaitingRoomController waitingRoomController = fxmlLoader.getController()
//            UserModel u = new UserModel(tf_username.getText(),tf_password.getText());
//            waitingRoomController.setUserModel(u);
            String SendingPack = "SIGNIN;" +tf_username.getText()+";"+tf_password.getText();
            Client a = new Client("localhost", 6000,SendingPack);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Number finding game");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}