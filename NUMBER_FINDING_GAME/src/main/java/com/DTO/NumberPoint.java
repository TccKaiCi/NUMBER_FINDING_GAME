package com.DTO;


public class NumberPoint {
    public int value;
    public int posX;
    public int posY;

    public String chosenColor;
    public String loaiSo;

    public NumberPoint() {
    }

    public NumberPoint(int value, int posX, int posY, String chosenColor, String loaiSo) {
        this.value = value;
        this.posX = posX;
        this.posY = posY;
        this.chosenColor = chosenColor;
        this.loaiSo = loaiSo;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(String chosenColor) {
        this.chosenColor = chosenColor;
    }

    public String getLoaiSo() {
        return loaiSo;
    }

    public void setLoaiSo(String loaiSo) {
        this.loaiSo = loaiSo;
    }

    @Override
    public String toString() {
        return "numberPointModel{" +
                "value=" + value +
                ", posX=" + posX +
                ", posY=" + posY +
                ", chosenColor=" + chosenColor +
                ", loaiSo='" + loaiSo + '\'' +
                '}';
    }
}
