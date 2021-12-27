package com.BUS;

import com.DTO.NumberPoint;

import java.util.*;

/**
 * For server and client
 * Map có 1 dãy Number liên tiếp nhau, sắp xếp lộn xộn trên hiển thị
 */
public class Map {
    private List<NumberPoint> list;
    private int startValue, endValue;
    private final String DEFAULT_COLOR = "#23f2eb";

    public Map() {
        list = new ArrayList<>();
    }

    /**
     * Create random Number on X:<br>
     * <p style="color : yellow">X  Max to min</p>
     * <p style="color : yellow">Y  Max to min</p>
     */
    public void createNew(int posXMax, int posXMin, int posYMax, int posYMin) {
//         create list number
        for (int i = startValue; i <= endValue; i++) {
            NumberPoint model = new NumberPoint();

            model.setIntValue(i);
            model.setStrChosenColor(DEFAULT_COLOR);

            if (i % 10 == 0) {
                model.setStrRare("Lucky");
            } else {
                if (i % 5 == 0) {
                    model.setStrRare("Blind");
                } else {
                    model.setStrRare("Normal");
                }
            }

//             find a place for it
            do {
                model.randomPosition(posXMax, posXMin, posYMax, posYMin);
            } while (isNearly(model));

            list.add(model);
        }

        Collections.shuffle(list);
    }

    public void display() {
        list.forEach(model -> {
            System.out.println(model.toString());
        });
    }

    /**
     * Check 1 point to all list
     *
     * @param value
     * @return <b>True is the 2 number nearly</b>
     */
    public boolean isNearly(NumberPoint value) {
        for (NumberPoint model : list) {
            if (value.isNearly(model)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Set color by Value Number
     *
     * @param value The number's Value
     * @param color The Color You Want
     */
    public void setColorByValue(int value, String color) {
        for (NumberPoint model : list) {
            if (model.getIntValue() == value) {
                model.setStrChosenColor(color);
            }
        }
    }

    /**
     * Try to check is the number already chosen
     * <b>Color default is: "6fcffa", you can edit it from this code</b>
     *
     * @param value Value Number of the Number
     * @return <b>true if it is choice</b>
     */
    public boolean isChosen(int value) {
        for (NumberPoint model : list) {
            if (model.getIntValue() == value) {
                if (Objects.equals(model.getStrChosenColor(), DEFAULT_COLOR)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Only Json
     *
     * @return
     */
    public String getMapByJSon(long longMatchTime) {
        StringBuilder sb = new StringBuilder();

//        open json
        sb.append("{\n" +
                " \"longMatchTime\" : " + longMatchTime + " ,\n" +
                "  \"data\": [");

        list.forEach(model -> {
            sb.append("{\n" +
                    "      \"intValue\": " + model.getIntValue() + " ,\n" +
                    "      \"intPosX\": " + model.getIntPosX() + ",\n" +
                    "      \"intPosY\": " + model.getIntPosY() + ",\n" +
                    "      \"strChosenColor\" : \"" + model.getStrChosenColor() + "\",\n" +
                    "      \"strRare\" : \"" + model.getStrRare() + "\"\n" +
                    "    },");
        });

//        delete end ","
        sb.deleteCharAt(sb.length() - 1);
//        close json
        sb.append("\n" +
                " \n]" +
                "}");
        return sb.toString();
    }

    /**
     * Add point to map
     * @param model must have full data, not allow null
     */
    public boolean addPoint(NumberPoint model) {
        return list.add(model);
    }


    public List<NumberPoint> getList() {
        return list;
    }

    public void setList(List<NumberPoint> list) {
        this.list = list;
    }

    public int getStartValue() {
        return startValue;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }

    public int getEndValue() {
        return endValue;
    }

    public void setEndValue(int endValue) {
        this.endValue = endValue;
    }
}
