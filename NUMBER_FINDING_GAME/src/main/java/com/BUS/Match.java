package com.BUS;

import com.BUS.Map;
import com.DTO.NumberPoint;

import java.util.Collections;
import java.util.Stack;

import static java.sql.Types.NULL;

public class Match {
    private String strIdRoom;
    private String strRoomName;
    private long longMatchTime;

    private Map map;
    private Stack<Integer> stacks;

    public Match() {
    }

    /**
     * Tạo ra một map random
     * @param startValue giá trị bắt đẩu
     * @param endValue   giá trị kết thúc
     */
    public void createRandomMap(int startValue, int endValue) {
        map = new Map();
        stacks = new Stack<>();

        if (startValue != NULL) {
            map.setStartValue(startValue);
            map.setEndValue(endValue);
        } else {
            map.setStartValue(1);
            map.setEndValue(100);
        }

        map.createNew(770, 0, 470, 0);

        for (NumberPoint DTO : map.getList()) {
            stacks.push(DTO.getIntValue());
        }
    }

    public void createRandomMap() {
        createRandomMap(NULL, NULL);
    }

    public int getNextValue() {
        return stacks.pop();
    }

    public String getStrIdRoom() {
        return strIdRoom;
    }

    public void setStrIdRoom(String strIdRoom) {
        this.strIdRoom = strIdRoom;
    }

    public String getStrRoomName() {
        return strRoomName;
    }

    public void setStrRoomName(String strRoomName) {
        this.strRoomName = strRoomName;
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

    public Stack<Integer> getStacks() {
        return stacks;
    }

    public void setStacks(Stack<Integer> stacks) {
        this.stacks = stacks;
    }
}
