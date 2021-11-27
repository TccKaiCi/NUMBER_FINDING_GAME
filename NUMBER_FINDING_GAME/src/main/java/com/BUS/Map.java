package com.BUS;

import com.DTO.NumberPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    public List<NumberPoint> list;
    public int startValue, endValue;

    public Map() {
        list = new ArrayList<>();
    }

    public void createNew(int posXMax, int posXMin, int posYMax, int posYMin) {
        Random rand = new Random();

        for (int i = startValue; i <= endValue; i++) {
            NumberPoint model = new NumberPoint();

            model.setValue(i);
            do {
                model.setPosX(rand.nextInt((posXMax - posXMin) + 1) + posXMin);
                model.setPosY(rand.nextInt((posYMax - posYMin) + 1) + posYMin);
            } while (!isNearly(model));

            model.setChosenColor("6fcffa");

            list.add(model);
        }
    }

    public void display() {
        list.forEach(model -> {
            System.out.println(model.toString());
        });
    }

    public boolean isNearly(NumberPoint value) {
        for (NumberPoint model : list) {
            // a^2 + b^2 / 2
            int duongCheo = (int) Math.sqrt(1152) / 2;
            int khoanCach = (int) Math.sqrt(
                    (value.getPosX() - model.getPosX()) * (value.getPosX() - model.getPosX()) +
                            (value.getPosY() - model.getPosY()) * (value.getPosY() - model.getPosY()));

            if (khoanCach <= duongCheo + 20) {
                return false;
            }
        }
        return true;
    }

    public void setColorByValue(int value, String color) {
        for (NumberPoint model : list) {
            if (model.getValue() == value) {
                model.setChosenColor(color);
            }
        }
    }

    public boolean isChosen(int value) {
        for (NumberPoint model : list) {
            if (model.getValue() == value) {
                if (model.getChosenColor() == "6fcffa" ) {
                    return false;
                }
            }
        }

        return true;
    }

}
