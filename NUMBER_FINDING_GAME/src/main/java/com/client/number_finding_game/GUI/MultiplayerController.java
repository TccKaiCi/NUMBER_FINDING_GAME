package com.client.number_finding_game.GUI;

import com.BUS.Match;
import com.DTO.NumberPoint;
import com.server.number_finding_game.Memory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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
        RecList = new ArrayList<>();
        match = new Match("R3", 3000);

        match.createRandomMap(1, 30, 790, 0, 510, 0);
        System.out.println(Memory.messenger);
//        todo
        try {
            JSONObject json = new JSONObject(Memory.messenger);

            JSONArray recs = json.getJSONArray("data");

            for (int i = 0; i < recs.length(); i++) {
                JSONObject rec = recs.getJSONObject(i);
                String id = rec.get("intValue").toString();

                System.out.println(id);
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
                    color = "0xffa500ff";
                else
                    color = "ffee04";
//                Check is nextValue
                if (model.getIntValue() == nextPoint.getIntValue()) {
//                    Check is chosen
                    if (match.getMap().isChosen(model.getIntValue())) {
//                        Point
                        getPoint();
//                    Change input
                        rectangle.setFill(Color.valueOf(color));
                        match.getMap().setColorByValue(model.getIntValue(), color);

//                    Print value select
//                      todo
                        System.out.println(label.getText()
                                + " ; "
                                + model.getIntValue()
                                + " ; "
                                + model.getStrChosenColor());
                        Memory.client.sendMessenger(label.getText()
                                + " ; "
                                + model.getIntValue()
                                + " ; "
                                + model.getStrChosenColor());
//                    Print next value
                        setLabelNumberFindingColor();
                    }
                }
            });


//            add to Panel/ UI
            pane.getChildren().add(stackPane);
        });

//         create next random
        setLabelNumberFindingColor();
    }

    public void setLabelNumberFindingColor() {
        nextPoint = match.getNextValue();
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

    // 16 ; 16 ; #hex_color
    public void setColorToNumber(String input) {
        String[] color = input.split(";");
        System.out.println("dadasdsa");
        for (Rectangle item : RecList) {
            System.out.println(item.getId());
            if (item.getId().equalsIgnoreCase("SP_" + color[0])) {
                System.out.println("trong if " + color[0] + color[1] + color[2]);
                item.setFill(Color.web(color[2]));
            }
        }
    }

    public void setBtn_back(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setBtn_setting(Event event){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(pane_main);
        btn_back.setOnMouseClicked(this::setBtn_back);
        btn_setting.setOnMouseClicked(this::setBtn_setting);
    }


}
