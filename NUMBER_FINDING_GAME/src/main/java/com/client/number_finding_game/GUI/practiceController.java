package com.client.number_finding_game.GUI;

import com.BUS.Match;
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
    private Match match;
    private int intNextValue;

    public void init(Pane pane) {
        match = new Match();

        match.createRandomMap();

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

            stackPane.setOnMouseClicked(mouseEvent -> {
//                This can be change for player color
                String color;
                if (model.getIntValue() % 2 == 0)
                    color = "0xffa500ff";
                else
                    color = "ffee04";
//                Check is nextValue
                if (model.getIntValue() == intNextValue) {
//                    Check is chosen
                    if (match.getMap().isChosen(model.getIntValue())) {
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
                        intNextValue = match.getNextValue();
                        System.out.println(intNextValue);
                    }
                }
            });

            pane.getChildren().add(stackPane);
        });

//         create next random
        intNextValue = match.getNextValue();
        System.out.println(intNextValue);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(pane2);
    }
}
