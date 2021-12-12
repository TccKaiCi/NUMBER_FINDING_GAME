package com.client.number_finding_game.GUI;

import com.DTO.UserAccountDTO;
import com.client.number_finding_game.LoginForm;
import com.server.number_finding_game.Memory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditAccountController implements Initializable {
    @FXML
    public Button Edt_btnChangePass, Edt_btnCancel, Edt_btnSave;
    @FXML
    public TextField Edt_usrName, Edt_fullName;
    @FXML
    public RadioButton Edt_RBMale, Edt_RBFemale, Edt_RBOthers;
    @FXML
    public DatePicker Edt_Dob;
    ToggleGroup toggleGroup = new ToggleGroup();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initRadioGroup();
        Edt_btnChangePass.setOnAction(this::setEdt_btnChangePass);
        Edt_btnCancel.setOnAction(this::setEdt_btnCancel);
        Edt_btnSave.setOnAction(this::setEdt_btnSave);
        Edt_Dob.setOnAction(this::getDatePicker);
        Edt_usrName.setText(Memory.userAccountDTO.getStrUserName());
        Edt_fullName.setText(Memory.userAccountDTO.getStrNameInf());
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(Memory.userAccountDTO.getStrDayOfBirth());
        Edt_Dob.setValue(localDate);
        String gender=Memory.userAccountDTO.getStrGender();
        switch (gender) {
            case "Nam" -> Edt_RBMale.setSelected(true);
            case "Nữ" -> Edt_RBFemale.setSelected(true);
            case "Khác" -> Edt_RBOthers.setSelected(true);
        }
        setButtonHover(Edt_btnChangePass, "-fx-background-color: gray;", "-fx-background-color: lightgray;");
        setButtonHover(Edt_btnCancel,  "-fx-background-color: #4E9525;", "-fx-background-color: #A7DA46;");
        setButtonHover(Edt_btnSave, "-fx-background-color: #FE6845;", "-fx-background-color: #FFA259;");
    }

    public void setButtonHover(Node node, String colorEnter, String colorExit){
        node.setOnMouseEntered(mouseEvent -> { node.setStyle(colorEnter); });
        node.setOnMouseExited(mouseEvent -> { node.setStyle(colorExit); });
    }

    public void setEdt_btnChangePass(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("ChangePassword.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Edit Account Info");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initRadioGroup() {

        Edt_RBMale.setToggleGroup(toggleGroup);
        Edt_RBFemale.setToggleGroup(toggleGroup);
        Edt_RBOthers.setToggleGroup(toggleGroup);

        //listen to changes in selected toggle
        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> System.out.println(newVal + " was selected"));
    }

    public void setEdt_btnCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setEdt_btnSave(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Alert.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            Memory.userAccountDTO.setStrUid(Memory.userAccountDTO.getStrUid());
            Memory.userAccountDTO.setStrUserName(Edt_usrName.getText());
            Memory.userAccountDTO.setStrNameInf(Edt_fullName.getText());
            Memory.userAccountDTO.setStrDayOfBirth(Edt_Dob.getValue().toString());
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            String toogleGroupValue = selectedRadioButton.getText();
            Memory.userAccountDTO.setStrGender(toogleGroupValue);
           String SendingPack ="Edit;"
                    +Memory.userAccountDTO.getStrUid()+":"
                    +Memory.userAccountDTO.getStrUserName()+":"
                    +Memory.userAccountDTO.getStrNameInf()+":"
                    +Memory.userAccountDTO.getStrDayOfBirth()+":"
                    +Memory.userAccountDTO.getStrGender();
           Memory.client.sendMessenger(SendingPack);
            //todo nhan duoc "EditSuccess" moi bao, check gmail
            stage.setTitle("Edit success");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void getDatePicker(ActionEvent event) {
        System.out.println(Edt_Dob.getValue());
    }

}
