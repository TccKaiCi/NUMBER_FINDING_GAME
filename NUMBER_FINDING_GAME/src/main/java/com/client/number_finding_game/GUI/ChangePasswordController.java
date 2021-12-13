package com.client.number_finding_game.GUI;

import com.client.number_finding_game.LoginForm;
import com.server.number_finding_game.Memory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class ChangePasswordController implements Initializable {
    @FXML
    public TextField tf_1, tf_2, tf_3;
    @FXML
    public PasswordField Cp_OldPass, Cp_NewPass, Cp_RenewPass;
    @FXML
    public CheckBox cb_showhide;
    @FXML
    public Button CP_BtnCancel, CP_BtnSave;

    public static String OldPass = Memory.userAccountDTO.getStrPassWord();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setShowPassword();
        CP_BtnCancel.setOnAction(this::setCP_BtnCancel);
        CP_BtnSave.setOnAction(this::setCP_BtnSave);

        setButtonHover(CP_BtnCancel, "-fx-background-color: gray;", "-fx-background-color: lightgray;");
        setButtonHover(CP_BtnSave, "-fx-background-color: #FE6845;", "-fx-background-color: #FFA259;");
    }

    public void setButtonHover(Node node, String colorEnter, String colorExit) {
        node.setOnMouseEntered(mouseEvent -> {
            node.setStyle(colorEnter);
        });
        node.setOnMouseExited(mouseEvent -> {
            node.setStyle(colorExit);
        });
    }

    public void setShowPassword() {
        //Set init state
        tf_1.setManaged(false);
        tf_1.setVisible(false);
        tf_2.setManaged(false);
        tf_2.setVisible(false);
        tf_3.setManaged(false);
        tf_3.setVisible(false);

        //Bind properties;
        tf_1.managedProperty().bind(cb_showhide.selectedProperty());
        tf_1.visibleProperty().bind(cb_showhide.selectedProperty());

        Cp_OldPass.managedProperty().bind(cb_showhide.selectedProperty().not());
        Cp_OldPass.visibleProperty().bind(cb_showhide.selectedProperty().not());

        tf_2.managedProperty().bind(cb_showhide.selectedProperty());
        tf_2.visibleProperty().bind(cb_showhide.selectedProperty());

        Cp_NewPass.managedProperty().bind(cb_showhide.selectedProperty().not());
        Cp_NewPass.visibleProperty().bind(cb_showhide.selectedProperty().not());

        tf_3.managedProperty().bind(cb_showhide.selectedProperty());
        tf_3.visibleProperty().bind(cb_showhide.selectedProperty());

        Cp_RenewPass.managedProperty().bind(cb_showhide.selectedProperty().not());
        Cp_RenewPass.visibleProperty().bind(cb_showhide.selectedProperty().not());

        //Bind the textField and passwordField text values bidirectionally
        tf_1.textProperty().bindBidirectional(Cp_OldPass.textProperty());
        tf_2.textProperty().bindBidirectional(Cp_NewPass.textProperty());
        tf_3.textProperty().bindBidirectional(Cp_RenewPass.textProperty());
    }

    public void setCP_BtnCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setCP_BtnSave(ActionEvent event) {
        if (isInputValidate()) {
            OldPass = Cp_OldPass.getText();
            String SendingPack = "ChangePass;"
                    + Memory.userAccountDTO.getStrUid() + ":"
                    + OldPass + ":"
                    + Cp_NewPass.getText();

            Memory.client.sendMessenger(SendingPack);
            //todo nhan duoc "EditSuccess" thi bao thanh cong
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (Memory.messenger.contains("EditSuccess")) {
                        timer.cancel();
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Alert2.fxml"));
                            Parent parent = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.setResizable(false);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setTitle("Alert");
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        timer.cancel();
                    }
                });
            }
        }, 0, 100);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public boolean isInputValidate() {
        boolean check = true;
        boolean passCheck = Cp_NewPass.getText().isBlank();
        boolean repassCheck = Cp_RenewPass.getText().isBlank();
        if (passCheck || repassCheck || !Cp_RenewPass.getText().equalsIgnoreCase(Cp_NewPass.getText())) {
            System.out.println("hello");
            check = false;
        }
        return check;
    }

}
