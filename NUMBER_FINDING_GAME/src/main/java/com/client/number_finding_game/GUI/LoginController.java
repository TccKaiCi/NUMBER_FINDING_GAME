package com.client.number_finding_game.GUI;

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
import javafx.stage.Stage;

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
    public Label lbl_Error, lbl_name, lbl_pass, lbl_1, lbl_2, lbl_3, lbl_4, lbl_5, lbl_6;
    @FXML
    public DatePicker dk_dob;
    @FXML
    public RadioButton dk_male, dk_female, dk_other;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #D6B5A7; -fx-border-color: #000;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #AD8E93; -fx-border-color: #000;";
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        initRadioGroup();
    }

    public void onClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Waiting_room.fxml"));
            Parent root = fxmlLoader.load();
            String SendingPack = "SIGNIN;" + tf_username.getText() + ";" + pf_password.getText();
            if (validate(tf_username.getText())) {
                Memory.client.Connect();
                Memory.client.sendMessenger(SendingPack);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Number finding game");
                stage.setResizable(false);
                //need add function prevent signin when password wrong
                if (Memory.messenger.equalsIgnoreCase("Valid user")) {
                    stage.setScene(new Scene(root));
                    stage.show();
                    System.out.println(Memory.messenger);
                } else {
                    //todo tuananh
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
        ToggleGroup toggleGroup = new ToggleGroup();
        dk_male.setToggleGroup(toggleGroup);
        dk_female.setToggleGroup(toggleGroup);
        dk_other.setToggleGroup(toggleGroup);

        //listen to changes in selected toggle
        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> System.out.println(newVal + " was selected"));
    }



    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}