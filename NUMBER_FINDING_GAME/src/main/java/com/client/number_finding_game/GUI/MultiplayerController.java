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
    Button btn_stop;
    @FXML
    ImageView btn_setting;

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

    public void getPoint(String color, String rare) {
        if ((color != Memory.userColor) && (rare.equals("Blind"))) {
            // blind
            pane_main.setOpacity(0);
            Timer cheManHinh = new Timer();
            cheManHinh.scheduleAtFixedRate(new TimerTask() {
                public int i = 1;

                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (i == 4) {
                            pane_main.setOpacity(1);
                            cheManHinh.cancel();
                        } else {
                            i++;
                        }
                    });
                }
            }, 0, 1000);

        } else {
            int i = 1;
            //NAME:COLOR:NAME2:COLOR2:NAME3:COLOR3
            for (Map.Entry<String, String> entry : Memory.otherUserInfor_Color.entrySet()) {
                if (Objects.equals(color, entry.getValue())) {
                    System.out.println("Chọn màu " + entry.getValue());
                    switch (i) {
                        case 1:
                            System.out.println("A");
                            congDiem(lbl_NumberScore, rare);
                            break;
                        case 2:
                            System.out.println("B");
                            congDiem(lbl_NumberScore1, rare);
                            break;
                        case 3:
                            System.out.println("C");
                            congDiem(lbl_NumberScore2, rare);
                            break;
                    }
                }
                i++;
            }
        }
    }

    public void congDiem(Label lbl, String rare) {
        int diem = Integer.parseInt(lbl.getText());
        System.out.println(diem + " la diem hien tai");

        if (Objects.equals(rare, "Lucky")) {
            diem += 3;
        } else {
            diem += 1;
        }
        lbl.setText(String.valueOf(diem));
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

    public void setBtn_stop(ActionEvent event) {
        // close
        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage1.close();


        countDown.cancel();
        timer.cancel();

//        exit lobby from server
        Memory.client.sendMessenger("exit");
        Platform.exit();
        System.exit(0);
    }

    public void setBtn_setting(Event event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(pane_main);
        btn_stop.setOnAction(this::setBtn_stop);
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
                        countDown.cancel();
                        timer.cancel();
                    } else {
                        lbl_time.setText((temp / 60) + ":" + (temp - (temp / 60) * 60));
                    }
                });
            }
        }, 0, 1000);

        int i = 1;
        for (Map.Entry<String, String> entry : Memory.otherUserInfor_Color.entrySet()) {
            switch (i) {
                case 1:
                    lbl_playerName.setText(entry.getKey());
                    break;
                case 2:
                    lbl_playerName1.setText(entry.getKey());
                    break;
                case 3:
                    lbl_playerName2.setText(entry.getKey());
                    break;
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
                    try {
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
//                        Type: Number:Color:Rare:UID
//                        0:1:2:3
                            case "FillColor":
                                Platform.runLater(() -> {
                                    setColorToNumber(Integer.parseInt(s[0]), s[1]);
                                    getPoint(s[1], s[2]);
                                });
                                break;
                        }
                    } catch (Exception ignored) {

                    }
                }

                Memory.messenger = "";
            } else {

            }
        }
    }


}
