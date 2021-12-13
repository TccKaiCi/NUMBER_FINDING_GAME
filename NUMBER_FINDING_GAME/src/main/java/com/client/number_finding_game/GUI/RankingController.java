package com.client.number_finding_game.GUI;

import com.DTO.Ranking;
import com.server.number_finding_game.Memory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.client.number_finding_game.GUI.WaitingRoomController;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RankingController implements Initializable {
    @FXML
    Button btn_back;
    @FXML
    VBox vbox;
    @FXML
    ScrollPane scroll;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_back.setOnAction(this::setBtn_back);
        String[] arr = Memory.messenger.split(";");
        if (arr[0].contains("RankingData")) {
            Ranking listRank = new Ranking();
            listRank.getJsonRankTable(arr[1]);
            listRank = listRank.handleRank();
            listRank.sortPoint();

            for (Ranking dto : listRank.getList()) {
                HBox playerPane = new HBox();
                Label uid = new Label(dto.getUID());
                Label name = new Label(dto.getName());
                Label point = new Label(String.valueOf(dto.getPoint()));
                Label winRate;
                if (dto.getWinRate() == null) {
                    winRate = new Label("0,0");
                } else {
                    winRate = new Label(dto.getWinRate());
                }
                Insets insets = new Insets(11, 0, 0, 5);
                uid.setPadding(insets);
                name.setPadding(insets);
                point.setPadding(insets);
                winRate.setPadding(insets);
                uid.setPrefWidth(130);
                uid.setPrefHeight(28);
                name.setPrefWidth(155);
                name.setPrefHeight(28);
                point.setPrefWidth(70);
                point.setPrefHeight(28);
                winRate.setPrefWidth(81);
                winRate.setPrefHeight(28);
                uid.setFont(new Font(19));
                name.setFont(new Font(19));
                point.setFont(new Font(19));
                winRate.setFont(new Font(19));
                uid.setTextFill(Color.BLACK);
                name.setTextFill(Color.BLACK);
                point.setTextFill(Color.RED);
                winRate.setTextFill(Color.BLACK);

                playerPane.getChildren().addAll(uid, name, point, winRate);
                playerPane.setPrefHeight(50);
                playerPane.setPrefWidth(300);
                playerPane.setStyle("-fx-background-color: CYAN; -fx-background-radius: 2;");
                HBox.setMargin(playerPane, new Insets(30, 30, 0, 30));
                vbox.getChildren().add(playerPane);
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));

            }
            scroll.setContent(vbox);


        }
    }

    public void setBtn_back(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


}
