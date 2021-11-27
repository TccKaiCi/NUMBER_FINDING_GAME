package com.client.number_finding_game.Controllers;

import com.client.number_finding_game.LoginForm;
import com.server.number_finding_game.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    public PasswordField pf_password;
    @FXML
    public Pane login_background;
    @FXML
    public PasswordField pf_RetypePass;
    @FXML
    public Button btn_Register;
    @FXML
    public Label lbl_Retype;
    @FXML
    public Button btn_Back;
    @FXML
    public Label lbl_Error;
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #D6B5A7; -fx-border-color: #000;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #AD8E93; -fx-border-color: #000;";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_Login.setOnAction(this::onClick);
        btn_Register.setOnAction(this::setBtn_RegisterOnClick);
        btn_Back.setOnAction(this::setBtn_BackOnClick);
        btn_Login.setOnMouseEntered(e-> btn_Login.setStyle(HOVERED_BUTTON_STYLE));
        btn_Login.setOnMouseExited(e-> btn_Login.setStyle(IDLE_BUTTON_STYLE));
        btn_Register.setOnMouseEntered(e-> btn_Register.setStyle(HOVERED_BUTTON_STYLE));
        btn_Register.setOnMouseExited(e-> btn_Register.setStyle(IDLE_BUTTON_STYLE));
        btn_Back.setOnMouseEntered(e-> btn_Back.setStyle(HOVERED_BUTTON_STYLE));
        btn_Back.setOnMouseExited(e-> btn_Back.setStyle(IDLE_BUTTON_STYLE));

    }

    public void onClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Waiting_room.fxml"));
            Parent root = fxmlLoader.load();
//            WaitingRoomController waitingRoomController = fxmlLoader.getController()
//            UserModel u = new UserModel(tf_username.getText(),tf_password.getText());
//            waitingRoomController.setUserModel(u);
            String SendingPack = "SIGNIN;" + tf_username.getText() + ";" + pf_password.getText();
            Client a = new Client("localhost", 6000, SendingPack);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Number finding game");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBtn_RegisterOnClick(ActionEvent event){
        btn_Login.setVisible(false);
        btn_Login.setDisable(true);
        lbl_Retype.setVisible(true);
        lbl_Retype.setDisable(false);
        pf_RetypePass.setVisible(true);
        pf_RetypePass.setDisable(false);
        btn_Back.setVisible(true);
        btn_Back.setDisable(false);
        btn_Register.setText("Submit");
    }
    public void setBtn_BackOnClick(ActionEvent event){
        btn_Login.setVisible(true);
        btn_Login.setDisable(false);
        lbl_Retype.setVisible(false);
        lbl_Retype.setDisable(true);
        pf_RetypePass.setVisible(false);
        pf_RetypePass.setDisable(true);
        btn_Back.setVisible(false);
        btn_Back.setDisable(true);
        btn_Register.setText("CREATE NEW ACCOUNT");
    }

}