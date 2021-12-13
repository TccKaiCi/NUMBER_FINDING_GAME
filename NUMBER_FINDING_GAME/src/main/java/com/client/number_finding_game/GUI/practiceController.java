package com.client.number_finding_game.GUI;

import com.BUS.Match;
import com.DTO.NumberPoint;
import com.client.number_finding_game.LoginForm;
import com.server.number_finding_game.Memory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class practiceController implements Initializable {
    @FXML
    private Pane pane2;
    @FXML
    private Label lblNumberFinding;
    @FXML
    private AnchorPane anchor;
    @FXML
    Button lbl_BackGame;

    private Match match;
    private NumberPoint nextPoint;
    private List<Rectangle> RecList;

    public static final String DEFAULT_COLOR = "#23f2eb";

    public void init(Pane pane) {
        RecList = new ArrayList<>();
        match = new Match("R3", 300);

        match.createRandomMap(4, 6);

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
                    color = "Yellow";
                else
                    color = "Red";
//                Check is nextValue
                if (model.getIntValue() == nextPoint.getIntValue()) {
//                    Check is chosen
                    if (match.getMap().isChosen(model.getIntValue())) {
                        match.getMap().setColorByValue(model.getIntValue(), color);
//                        Point
                        getPoint();
//                    Change input
                        rectangle.setFill(Color.valueOf(color));

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
                    pane2.setOpacity(0);
                    Timer cheManHinh = new Timer();
                    cheManHinh.scheduleAtFixedRate(new TimerTask() {
                        public int i = 1;
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                if (i == 4) {
                                    pane2.setOpacity(1);
                                    cheManHinh.cancel();
                                } else {
                                    i++;
                                }
                            });
                        }
                    }, 0, 1000);
                } else {
                    System.out.println("1 điểm");
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(pane2);

        lbl_BackGame.setOnAction(this::setBtn_practiceOnclick);
    }

    public void setBtn_practiceOnclick(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Practice play");
            stage.setResizable(false);

            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Waiting_room.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
