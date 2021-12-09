package com.BUS;

import com.DTO.NumberPoint;

import java.util.Stack;

public class Match {
    private String strIdRoom;
    private long longMatchTime;

    private Map map;
    private Stack<NumberPoint> stacks;

    public Match() {
        if (map == null) {
            map = new Map();
        }
        if (stacks == null) {
            stacks = new Stack<>();
        }
    }

    public Match(String strIdRoom, long longMatchTime) {
        this.strIdRoom = strIdRoom;
        this.longMatchTime = longMatchTime;
    }

    /**
     * Create map by follow
     */
    public void addPointToMap(NumberPoint point) {
//        check is NULL
        if (map == null) {
            map = new Map();
        }
        if (stacks == null) {
            stacks = new Stack<>();
        }

        map.addPoint(point);
        stacks.push(point);
    }

    /**
     * Create new map random, if it not exists
     *
     * @param startValue giá trị bắt đẩu
     * @param endValue   giá trị kết thúc
     */
    public void createRandomMap(int startValue, int endValue, int posXMax, int posXMin, int posYMax, int posYMin) {
//        check is NULL
        if (map == null) {
            map = new Map();
        }
        if (stacks == null) {
            stacks = new Stack<>();
        }

        map.setStartValue(startValue);
        map.setEndValue(endValue);

        map.createNew(posXMax, posXMin, posYMax, posYMin);

//        Put in to stack
        for (NumberPoint DTO : map.getList()) {
            stacks.push(DTO);
        }
    }

    /**
     * Create new map random, if it not exists
     *
     * @param startValue giá trị bắt đẩu
     * @param endValue   giá trị kết thúc
     */
    public void createRandomMap(int startValue, int endValue) {
//        check is NULL
        if (map == null) {
            map = new Map();
        }
        if (stacks == null) {
            stacks = new Stack<>();
        }

        map.setStartValue(startValue);
        map.setEndValue(endValue);

        map.createNew(770, 0, 470, 0);

//        Put in to stack
        for (NumberPoint DTO : map.getList()) {
            stacks.push(DTO);
        }
    }

    /**
     * Default is 1 to 100
     */
    public void createRandomMap() {
        createRandomMap(1, 100);
    }

    /**
     * If dont have next value return -1
     * <p style="color:yellow">Type NumberPoint</p>
     * @return Next value for choice
     */
    public NumberPoint getNextValue() {
        if (stacks.isEmpty()) {
            return null;
        }
        return stacks.pop();
    }

    /**
     * Display all point, map
     */
    public void display() {
        map.display();
    }

    public String getMapByJSon() {
        return map.getMapByJSon();
    }









    public String getStrIdRoom() {
        return strIdRoom;
    }

    public void setStrIdRoom(String strIdRoom) {
        this.strIdRoom = strIdRoom;
    }

    public long getLongMatchTime() {
        return longMatchTime;
    }

    public void setLongMatchTime(long longMatchTime) {
        this.longMatchTime = longMatchTime;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Stack<NumberPoint> getStacks() {
        return stacks;
    }

    public void setStacks(Stack<NumberPoint> stacks) {
        this.stacks = stacks;
    }
}
