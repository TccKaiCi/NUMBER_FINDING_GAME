package com.client.number_finding_game.GUI;

import com.BUS.Match;
import com.DTO.NumberPoint;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.*;
import org.w3c.dom.events.MouseEvent;

public class MultiplayerController implements Initializable {
    @FXML
    Pane pane_main;
    @FXML
    Label lbl_findingNum, lbl_time, lbl_playerName, lbl_NumberScore, lbl_playerName1, lbl_NumberScore1, lbl_playerName2, lbl_NumberScore2;
    @FXML
    ImageView btn_back, btn_setting;

    private Match match;
    private NumberPoint nextPoint;
    private List<Rectangle> RecList;
    private Timer countDown, timer;


    public static final String DEFAULT_COLOR = "#23f2eb";

    public void init(Pane pane) {
        if (nextPoint == null) {
            nextPoint = new NumberPoint();
        }

        RecList = new ArrayList<>();
        match = new Match();

        match.setMapByJSon(Memory.messenger);


//        create list label
        match.getMap().getList().forEach(model -> {
            Rectangle rectangle = new Rectangle(25, 25);
            StackPane stackPane = new StackPane();
            Label label = new Label();

            label.setText(String.valueOf(model.getIntValue()));
            rectangle.setId("SP_" + model.getIntValue());


            rectangle.setFill(Color.web(DEFAULT_COLOR));

            stackPane.getChildren().addAll(rectangle, label);
            RecList.add(rectangle);

            stackPane.setLayoutX(model.getIntPosX());
            stackPane.setLayoutY(model.getIntPosY());

//            Event click
            stackPane.setOnMouseClicked(mouseEvent -> {
//                Check is nextValue
                if (model.getIntValue() == nextPoint.getIntValue()) {
//                    Check is chosen
                    if (match.getMap().isChosen(model.getIntValue())) {
//                    Print value select
//                        Pickup;Value:color:rare:uid
                        Memory.client.sendMessenger("Pickup;"
                                + model.getIntValue() + ":" + Memory.userColor
                                + ":" + model.getStrRare()
                                + ":" + Memory.userAccountDTO.getStrUid());
                    }
                }
            });


//            add to Panel/ UI
            pane.getChildren().add(stackPane);
        });
    }

    public void setLabelNumberFindingColor() {
//        is have next number
        if (nextPoint == null) {
            lbl_findingNum.setText("NULL");
        } else {
//            check Rare number
            if (Objects.equals(nextPoint.getStrRare(), "Lucky")) {
                lbl_findingNum.setTextFill(Color.valueOf("#ffed00"));
            } else {
                if (Objects.equals(nextPoint.getStrRare(), "Blind")) {
                    lbl_findingNum.setTextFill(Color.valueOf("#aa77ff"));
                } else {
                    lbl_findingNum.setTextFill(Color.valueOf("#000000"));
                }
            }

//            set value
            lbl_findingNum.setText(String.valueOf(nextPoint.getIntValue()));
        }
    }

    public void getPoint(String color) {
        //10:Red
//            check Rare number
        if (color.equals(Memory.userColor)) {
            int diem = Integer.parseInt(lbl_NumberScore.getText());
            if (Objects.equals(nextPoint.getStrRare(), "Lucky")) {
                lbl_NumberScore.setText(String.valueOf((diem + 3)));
                System.out.println("3 điểm");
            } else {
                if (Objects.equals(nextPoint.getStrRare(), "Blind")) {
//                   todo Tuấn Anh làm
                } else {
                    lbl_NumberScore.setText(String.valueOf((diem + 1)));
                    System.out.println("1 điểm");
                }
            }
        } else {
            // other user
            int i = 1;
            for (Map.Entry<String, String> entry : Memory.otherUserInfor_Color.entrySet()) {
                if (color.equals(entry.getValue())) {
                    int diem = Integer.parseInt(lbl_NumberScore.getText());
                    if (Objects.equals(nextPoint.getStrRare(), "Lucky")) {
                        if (i == 1) {
                            lbl_NumberScore1.setText(String.valueOf((diem + 3)));
                        } else {
                            lbl_NumberScore2.setText(String.valueOf((diem + 3)));
                        }
                    } else {
                        if (Objects.equals(nextPoint.getStrRare(), "Blind")) {
//                   todo Tuấn Anh làm
                        } else {
                            if (i == 1) {
                                lbl_NumberScore1.setText(String.valueOf((diem + 1)));
                            } else {
                                if (i == 2) {
                                    lbl_NumberScore2.setText(String.valueOf((diem + 1)));
                                }
                            }
                        }
                    }
                }
                i++;
            }
        }
    }


    /**
     * 16 : #hex_color
     */
    public void setColorToNumber(int value, String color) {
        for (Rectangle item : RecList) {
            if (item.getId().equalsIgnoreCase("SP_" + value)) {
                item.setFill(Color.web(color));
            }
        }
    }

    public void setBtn_back(Event event) {
//        quay trở lại waitting room
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Waiting_room.fxml"));
            Parent parent = null;

            parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("submit success");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // close
        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage1.close();

//        exit lobby from server
        Memory.client.stop();
    }

    public void setBtn_setting(Event event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(pane_main);
        btn_back.setOnMouseClicked(this::setBtn_back);
        btn_setting.setOnMouseClicked(this::setBtn_setting);

        lbl_playerName.setText(Memory.userAccountDTO.getStrNameInf());

        timer = new Timer();
        timer.schedule(new MyTask(), 0, 1);

        countDown = new Timer();
        countDown.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    long temp = match.getLongMatchTime();
                    match.setLongMatchTime(temp - 1);

                    if (temp == 0) {
                        lbl_time.setText("0");
                    } else {
                        lbl_time.setText((temp / 60) + ":" + (temp - (temp / 60) * 60));
                    }
                });
            }
        }, 0, 1000);

        int i = 1;
        for (Map.Entry<String, String> entry : Memory.otherUserInfor_Color.entrySet()) {
            if (i == 1) {
                lbl_playerName1.setText(entry.getKey());
            } else {
                if (i == 2) {
                    lbl_playerName2.setText(entry.getKey());
                }
            }
            i++;
        }
    }

    public class MyTask extends TimerTask {
        @Override
        public void run() {
            if (!Memory.messenger.equalsIgnoreCase("")) {
                String tmp = Memory.messenger;

                if (Memory.messenger == "ENDGAME;") {
                    System.out.println("ENDGAME;");
                    countDown.cancel();
                    timer.cancel();
                } else {
//                Pickup;1:color
                    String[] arr = tmp.split(";");
                    String[] s = arr[1].split(":");


//                Get X in X;a:b....etc
                    switch (arr[0]) {
//                    setLabelNumberFindingColor
//                    value:rare
//                    NextNumber;10:Lucky
                        case "NextNumber":
                            Platform.runLater(() -> {
                                if (nextPoint == null) {
                                    nextPoint = new NumberPoint();
                                }
                                System.out.println(arr[1]);
                                nextPoint.setIntValue(Integer.parseInt(s[0]));
                                nextPoint.setStrRare(s[1]);
                                setLabelNumberFindingColor();
                            });
                            break;
//                        Type: Number:Color
                        case "FillColor":
                            Platform.runLater(() -> {
                                setColorToNumber(Integer.parseInt(s[0]), s[1]);
                                getPoint(s[1]);
                            });
                            break;
                    }
                }
                Memory.messenger = "";
            } else {

            }
        }
    }


}
