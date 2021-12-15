package com.client.number_finding_game.GUI;

import com.DTO.UserAccountDTO;
import com.client.number_finding_game.LoginForm;
import com.server.number_finding_game.Memory;
import com.server.number_finding_game.NewClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {
    @FXML
    public Button btn_Login, btn_Register, btn_Back, btn_submit;
    @FXML
    public TextField tf_username, dk_name, dk_username;
    @FXML
    public PasswordField pf_password, dk_pass, dk_retype;
    @FXML
    public Label lbl_Error, lbl_name, lbl_pass, lbl_1, lbl_2, lbl_3, lbl_4, lbl_5, lbl_6, dk_passError, dk_emailError ;
    @FXML
    public DatePicker dk_dob;
    @FXML
    public RadioButton dk_male, dk_female, dk_other;
    ToggleGroup toggleGroup = new ToggleGroup();
    public static final String IDLE_BUTTON_STYLE = "-fx-background-color: #A7DA46; ";
    public static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #4E9525; ";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Memory.client.Connect();
        btn_Login.setOnAction(this::onClick);
        btn_Register.setOnAction(this::setBtn_RegisterOnClick);
        btn_Back.setOnAction(this::setBtn_BackOnClick);
        btn_Login.setOnMouseEntered(e -> btn_Login.setStyle(HOVERED_BUTTON_STYLE));
        btn_Login.setOnMouseExited(e -> btn_Login.setStyle(IDLE_BUTTON_STYLE));
        btn_Register.setOnMouseEntered(e -> btn_Register.setStyle(HOVERED_BUTTON_STYLE));
        btn_Register.setOnMouseExited(e -> btn_Register.setStyle(IDLE_BUTTON_STYLE));
        btn_Back.setOnMouseEntered(e -> btn_Back.setStyle(HOVERED_BUTTON_STYLE));
        btn_Back.setOnMouseExited(e -> btn_Back.setStyle(IDLE_BUTTON_STYLE));
        btn_submit.setOnMouseEntered(e -> btn_submit.setStyle(HOVERED_BUTTON_STYLE));
        btn_submit.setOnMouseExited(e -> btn_submit.setStyle(IDLE_BUTTON_STYLE));
        btn_submit.setOnAction(this::setBtn_submit);
        initRadioGroup();
    }

    public void onClick(ActionEvent event) {
        try {
            String SendingPack = "SIGNIN;" + tf_username.getText() + ";" + pf_password.getText();
            if (validate(tf_username.getText()) || !pf_password.getText().equals("")) {
                Memory.client.sendMessenger(SendingPack);
                System.out.println(SendingPack);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Number finding game");
                stage.setResizable(false);
                //need add function prevent signin when password wrong
                if (Memory.messenger.equalsIgnoreCase("valid user")) {
                    while (!Memory.messenger.contains("Account")){
                        System.out.printf("");
                    }
                    FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Waiting_room.fxml"));
                    Parent root = fxmlLoader.load();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    setVi_TRUE_Dis_FALSE(lbl_Error);
                }
            } else {
                lbl_Error.setText("Tên đăng nhập là đại chỉ email");
                setVi_TRUE_Dis_FALSE(lbl_Error);
                tf_username.setStyle("-fx-border-color: red");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBtn_submit(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        String toogleGroupValue = selectedRadioButton.getText();
        if (!toogleGroupValue.isBlank() && isInputValidate() ) {
            if (validate(dk_username.getText())) {
                if (dk_pass.getText().equals(dk_retype.getText())) {
                    String SendingPack = "";
                    SendingPack = "SIGNUP;"
                            + dk_username.getText() + ";"
                            + dk_name.getText() + ";"
                            + dk_pass.getText() + ";"
                            + toogleGroupValue + ";"
                            + dk_dob.getValue();
                    Memory.client.sendMessenger(SendingPack);
                    System.out.println(SendingPack);
                    dk_passError.setVisible(false);
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Alert.fxml"));
                        Parent parent = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(parent));
                        stage.setResizable(false);
                        stage.initModality(Modality.APPLICATION_MODAL);
//                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setTitle("submit success");
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    VisibleSignT_Re_F();
                } else {
                    dk_passError.setVisible(true);
                }
                dk_emailError.setVisible(false);
            } else {
                dk_emailError.setVisible(true);
            }
        } else {
            System.out.println("DK ko thah cong");
        }
    }


    public void setBtn_RegisterOnClick(ActionEvent event) {
        Node[] signInPart = {btn_Login, lbl_name, lbl_pass, tf_username, pf_password, btn_Register};
        setVi_FALSE_Dis_TRUE(signInPart);

        Node[] registerPart = {lbl_1, lbl_2, lbl_3, lbl_4, lbl_5, lbl_6,
                dk_retype, dk_dob, dk_pass, dk_name, dk_username, dk_male, dk_female, dk_other, btn_Back, btn_submit};
        setVi_TRUE_Dis_FALSE(registerPart);
    }

    public void setBtn_BackOnClick(ActionEvent event) {
        Node[] signInPart = {btn_Login, lbl_name, lbl_pass, tf_username, pf_password, btn_Register};
        setVi_TRUE_Dis_FALSE(signInPart);

        Node[] registerPart = {lbl_1, lbl_2, lbl_3, lbl_4, lbl_5, lbl_6,
                dk_retype, dk_dob, dk_pass, dk_name, dk_username, dk_male, dk_female, dk_other, btn_Back, btn_submit};
        setVi_FALSE_Dis_TRUE(registerPart);

        btn_Register.setText("Tạo tài khoản mới");
    }

    public void VisibleSignT_Re_F() {
        Node[] signInPart = {btn_Login, lbl_name, lbl_pass, tf_username, pf_password, btn_Register};
        setVi_TRUE_Dis_FALSE(signInPart);

        Node[] registerPart = {lbl_1, lbl_2, lbl_3, lbl_4, lbl_5, lbl_6,
                dk_retype, dk_dob, dk_pass, dk_name, dk_username, dk_male, dk_female, dk_other, btn_Back, btn_submit};
        setVi_FALSE_Dis_TRUE(registerPart);

        btn_Register.setText("Tạo tài khoản mới");
    }

    public void setVi_TRUE_Dis_FALSE(Node[] node) {
        for (Node items : node) {
            items.setDisable(false);
            items.setVisible(true);
        }
    }

    public void setVi_TRUE_Dis_FALSE(Node node) {
        node.setDisable(false);
        node.setVisible(true);
    }

    public void setVi_FALSE_Dis_TRUE(Node[] node) {
        for (Node items : node) {
            items.setDisable(true);
            items.setVisible(false);
        }
    }

    public void initRadioGroup() {
        dk_male.setToggleGroup(toggleGroup);
        dk_female.setToggleGroup(toggleGroup);
        dk_other.setToggleGroup(toggleGroup);
        //listen to changes in selected toggle
        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> System.out.println(newVal + " was selected"));
    }

    //return true if nothing empty or right email type - else return false
    public boolean isInputValidate() {
        boolean check = true;
        boolean nameCheck = dk_name.getText().isBlank();
        boolean usernameCheck = dk_username.getText().isBlank();
        boolean dobCheck = false;
        try {
            dobCheck = dk_dob.getValue().toString().isBlank();
        } catch (NullPointerException e) {
            dobCheck = true;
        }
        boolean passCheck = dk_pass.getText().isBlank();
        boolean repassCheck = dk_pass.getText().isBlank();
        if (nameCheck || usernameCheck || dobCheck || passCheck || repassCheck) {
            check = false;
        }
        return check;
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}