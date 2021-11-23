package com.client.number_finding_game;

import javafx.scene.paint.Color;

public class numberPointModel {
    String value;
    double posX;
    double posY;

    public numberPointModel() {
    }

    public numberPointModel(String value, double posX, double posY) {
        this.value = value;
        this.posX = posX;
        this.posY = posY;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }


    @Override
    public String toString() {
        return "NumberPointMODEL{" + "value=" + value + ", posX=" + posX + ", posY=" + posY +"}";
    }
}
