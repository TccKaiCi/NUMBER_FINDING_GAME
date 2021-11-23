package com.client.number_finding_game;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class practiceController implements Initializable {
    @FXML
    private Pane pane2;
    Random rand = new Random();
    List<Circle> circleList = new ArrayList<>();


    public void setPane2(Pane pane) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<=100; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < 100; i++) {
            Rectangle rectangle = new Rectangle(25,25);
            StackPane stackPane = new StackPane();
            Label label= new Label();
            label.setText(list.get(i).toString());
            stackPane.setId("SP_"+list.get(i));
            rectangle.setFill(Color.BLUE);

            stackPane.getChildren().addAll(rectangle,label);
            stackPane.setLayoutX(Math.random()*800);
            stackPane.setLayoutY(Math.random()*500);
            stackPane.setOnMouseClicked(mouseEvent -> {
                rectangle.setFill(Color.RED);
                System.out.println(label.getText());
            });
            if(stackPane.getId().equalsIgnoreCase("SP_1")){
                rectangle.setFill(Color.GREEN);
            }
            pane.getChildren().add(stackPane);

        }


    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPane2(pane2);

    }
}
