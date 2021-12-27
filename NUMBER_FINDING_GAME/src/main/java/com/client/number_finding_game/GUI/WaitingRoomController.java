package com.client.number_finding_game.GUI;

import com.DTO.Ranking;
import com.client.number_finding_game.LoginForm;
import com.server.number_finding_game.Memory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoomController implements Initializable {
    @FXML
    private Button btn_multi, btn_practice, btn_ranking, btn_account, btn_quit;
    @FXML
    Label lbl_name;



    private static final DropShadow hoverEffect = new DropShadow();
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #A7DA46; ";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #4E9525; ";
    Timer timer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_multi.setOnAction(this::setBtn_multiOnClick);
        btn_practice.setOnAction(this::setBtn_practiceOnclick);
        btn_account.setOnAction(this::setBtn_accountOnClick);
        btn_quit.setOnAction(this::setBtn_quit);
        btn_ranking.setOnAction(this::setBtn_ranking);
        setHoverEffect();
        Node[] node = {btn_multi, btn_practice, btn_ranking, btn_account, btn_quit};
        setButtonAnimate(node);

        Memory.rankingDTO.getPoint();

        Timer countDown = new Timer();
        countDown.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    lbl_name.setText(Memory.userAccountDTO.getStrNameInf());
                });
            }
        }, 0, 1000);
    }

    public void setHoverEffect() {
        hoverEffect.setColor(Color.web("#FFE8D6"));
        hoverEffect.setRadius(35);
        hoverEffect.setWidth(40);
        hoverEffect.setHeight(40);
    }

    public void setButtonAnimate(Node[] node) {
        for (Node item : node) {
            item.setOnMouseEntered(mouseEvent -> {
                item.setEffect(hoverEffect);
                item.setStyle(HOVERED_BUTTON_STYLE);
            });
            item.setOnMouseExited(mouseEvent -> {
                item.setEffect(null);
                item.setStyle(IDLE_BUTTON_STYLE);
            });
            item.setOnMousePressed(mouseEvent -> item.setStyle(HOVERED_BUTTON_STYLE));
            item.setOnMouseReleased(mouseEvent -> item.setStyle(IDLE_BUTTON_STYLE));
        }
    }

    public void setBtn_practiceOnclick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("PracticeStage.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Practice play");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBtn_ranking(ActionEvent event) {
        Memory.client.sendMessenger("Ranking;Ranking");

        Timer timer123 = new Timer();
        timer123.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    String[] arr = Memory.messenger.split(";");
                    if (arr[0].equals("RankingData")) {
                        timer123.cancel();
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("testRank.fxml"));
                            Parent parent = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.setResizable(false);
                            stage.initModality(Modality.APPLICATION_MODAL);
//                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setTitle("testRank");
                            stage.show();
//                            Ranking ranking = new Ranking();
//
//                            ranking.getJsonRankTable(arr[1]);
//                            Ranking listRank = ranking.handleRank();
//                            listRank.display();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        timer123.cancel();
                    }
                });
            }
        }, 0, 100);
    }

    public void setBtn_accountOnClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("EditAccount.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Edit Account Info");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBtn_multiOnClick(ActionEvent event) {
        Memory.client.sendMessenger("start");

        //show loading animation
        btn_multi.setText("Đang tìm đối thủ...");
        Node[] node = {btn_multi, btn_practice, btn_quit, btn_account, btn_ranking};
        setDisable(node, true);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (checkLobby(Memory.messenger)) {
                        timer.cancel();
                        Node[] node = {btn_multi, btn_practice, btn_quit, btn_account, btn_ranking};
                        setDisable(node, false);
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Multiplayer.fxml"));
                            Parent parent = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.setResizable(false);
                            stage.initModality(Modality.APPLICATION_MODAL);
//                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setTitle("Multiplayer");
                            stage.show();


                            Stage stage2 = (Stage) btn_multi.getScene().getWindow();
                            stage2.close();
                            setDisable(node, true);
                            timer.cancel();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }, 0, 100);
    }

    public void setDisable(Node[] node, boolean value) {
        for (Node item : node) {
            item.setDisable(value);
        }
    }

    public boolean checkLobby(String mess) {
        return mess.contains("data");
    }

    public void setBtn_quit(ActionEvent event) {
        System.exit(0);
    }
}
