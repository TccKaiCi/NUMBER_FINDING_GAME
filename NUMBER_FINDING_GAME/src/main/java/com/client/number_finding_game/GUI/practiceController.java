package com.client.number_finding_game.GUI;

import com.BUS.Match;
import com.DTO.NumberPoint;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

public class practiceController implements Initializable {
    @FXML
    private Pane pane2;
    @FXML
    private Label lblNumberFinding;

    private Match match;
    private NumberPoint nextPoint;

    public void init(Pane pane) {
        match = new Match("R3", 300);

        match.createRandomMap(14, 17);

//        create list label
        match.getMap().getList().forEach(model -> {
            Rectangle rectangle = new Rectangle(25, 25);
            StackPane stackPane = new StackPane();
            Label label = new Label();

            label.setText(String.valueOf(model.getIntValue()));
            stackPane.setId("SP_" + model.getIntValue());
            rectangle.setFill(Color.valueOf("6fcffa"));

            stackPane.getChildren().addAll(rectangle, label);

            stackPane.setLayoutX(model.getIntPosX());
            stackPane.setLayoutY(model.getIntPosY());

//            Event click
            stackPane.setOnMouseClicked(mouseEvent -> {
//                This can be change for player color
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
//                    Change color
                        rectangle.setFill(Color.valueOf(color));
                        match.getMap().setColorByValue(model.getIntValue(), color);

//                    Print value select
                        System.out.println(label.getText()
                                + " : "
                                + model.getIntValue()
                                + " : "
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
            lblNumberFinding.setText("NULL");
        } else {
//            check Rare number
            if (Objects.equals(nextPoint.getStrRare(), "Lucky")) {
                lblNumberFinding.setTextFill(Color.valueOf("#ffed00"));
            } else {
                if (Objects.equals(nextPoint.getStrRare(), "Blind")) {
                    lblNumberFinding.setTextFill(Color.valueOf("#aa77ff"));
                } else {
                    lblNumberFinding.setTextFill(Color.valueOf("#000000"));
                }
            }

//            set value
            lblNumberFinding.setText(String.valueOf(nextPoint.getIntValue()));
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
                    pane2.setVisible(false);

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                    pane2.setVisible(true);
                } else {
                    System.out.println("1 điểm");
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(pane2);
    }
}
