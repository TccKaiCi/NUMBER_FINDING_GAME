package com.client.number_finding_game.GUI;

import com.BUS.Map;
import com.BUS.Match;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

public class practiceController implements Initializable {
    @FXML
    private Pane pane2;
    Random rand = new Random();
    List<Circle> circleList = new ArrayList<>();


    public void setPane2(Pane pane) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < 100; i++) {
            Rectangle rectangle = new Rectangle(25, 25);
            StackPane stackPane = new StackPane();
            Label label = new Label();
            label.setText(list.get(i).toString());
            stackPane.setId("SP_" + list.get(i));
            rectangle.setFill(Color.BLUE);

            stackPane.getChildren().addAll(rectangle, label);
            stackPane.setLayoutX(Math.random() * 800);
            stackPane.setLayoutY(Math.random() * 500);
            stackPane.setOnMouseClicked(mouseEvent -> {
                rectangle.setFill(Color.RED);
                System.out.println(label.getText());
            });

            if(stackPane.getId().equalsIgnoreCase("SP_1")){
                rectangle.setFill(Color.DARKGOLDENROD);

            }
            pane.getChildren().add(stackPane);
        }
    }

    Match match;

    public void init(Pane pane) {
        match = new Match();

        match.createRandomMap();


        match.getMap().getList().forEach(model -> {
            Rectangle rectangle = new Rectangle(25, 25);
            StackPane stackPane = new StackPane();
            Label label = new Label();

            label.setText(String.valueOf(model.getIntValue()));
            stackPane.setId("SP_" + model.getIntValue() );
            rectangle.setFill(Color.valueOf("6fcffa"));

            stackPane.getChildren().addAll(rectangle, label);

            stackPane.setLayoutX(model.getIntPosX());
            stackPane.setLayoutY(model.getIntPosY());

            stackPane.setOnMouseClicked(mouseEvent -> {
                String color;
                if (model.getIntValue() % 2 == 0)
                    color = "0xffa500ff";
                else
                    color = "ffee04";

                if (match.getMap().isChosen(model.getIntValue())) {
                    rectangle.setFill(Color.valueOf(color));
                    match.getMap().setColorByValue(model.getIntValue(), color);
                    System.out.println(label.getText()
                            + " : "
                            + model.getIntValue()
                            + " : "
                            + model.getStrChosenColor());
                }
            });

            pane.getChildren().add(stackPane);
        });

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        setPane2(pane2);
        init(pane2);
    }
}
