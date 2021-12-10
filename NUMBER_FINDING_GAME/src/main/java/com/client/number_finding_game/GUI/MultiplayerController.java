package com.client.number_finding_game.GUI;

import com.BUS.Match;
import com.DTO.NumberPoint;
import com.server.number_finding_game.Memory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

import javafx.stage.Stage;
import org.json.*;
import org.w3c.dom.events.MouseEvent;

public class MultiplayerController implements Initializable {
    @FXML
    Pane pane_main;
    @FXML
    Label lbl_findingNum;
    @FXML
    ImageView btn_back, btn_setting;

    private Match match;
    private NumberPoint nextPoint;
    private List<Rectangle> RecList;

    public static final String DEFAULT_COLOR = "#23f2eb";

    public void init(Pane pane) {
        if (nextPoint == null) {
            nextPoint = new NumberPoint();
        }

        RecList = new ArrayList<>();
        match = new Match("R2321321313", 3000);

//      get data from server
        try {
            JSONObject json = new JSONObject(Memory.messenger);
            Memory.messenger = "";

            JSONArray recs = json.getJSONArray("data");

            for (int i = 0; i < recs.length(); i++) {
                JSONObject rec = recs.getJSONObject(i);
                NumberPoint point = new NumberPoint();

                point.setIntValue(Integer.parseInt(rec.get("intValue").toString()));
                point.setIntPosX(Integer.parseInt(rec.get("intPosX").toString()));
                point.setIntPosY(Integer.parseInt(rec.get("intPosY").toString()));
                point.setStrChosenColor(rec.get("strChosenColor").toString());
                point.setStrRare(rec.get("strRare").toString());

                match.addPointToMap(point);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
//                This can be change for player input
                String color;
                if (model.getIntValue() % 2 == 0)
                    color = "#ff0000";
                else
                    color = "#ff00eb";
//                Check is nextValue
                if (model.getIntValue() == nextPoint.getIntValue()) {
//                    Check is chosen
                    if (match.getMap().isChosen(model.getIntValue())) {
//                    Print value select
//                        Pickup;Value:color:rare:uid
                        Memory.client.sendMessenger("Pickup;"
                                + model.getIntValue() + ":" + color
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

    public void getPoint() {
//        is have next number
        if (nextPoint == null) {
        } else {
//            check Rare number
            if (Objects.equals(nextPoint.getStrRare(), "Lucky")) {
                System.out.println("3 điểm");
            } else {
                if (Objects.equals(nextPoint.getStrRare(), "Blind")) {
//                    Tuấn Anh làm
                } else {
                    System.out.println("1 điểm");
                }
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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

        MyTask myTask = new MyTask();
        Timer timer = new Timer();
        timer.schedule(myTask, 0, 1);
    }

    public class MyTask extends TimerTask {
        @Override
        public void run() {
            if (!Memory.messenger.equalsIgnoreCase("")) {
                String tmp = Memory.messenger;

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
                        });
                        break;
                }
                Memory.messenger = "";
            } else {

            }
        }
    }


}
