package com.BUS;

import com.DTO.NumberPoint;
import com.server.number_finding_game.Memory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Server and CLient
 * A match have map, id room, time
 */
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

        map.addPoint(point);
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

        return map.getMapByJSon(this.longMatchTime);
    }

    /**
     * @param jsonData String
     */
    public void setMapByJSon(String jsonData) {
        //      get data from server
        try {
            JSONObject json = new JSONObject(jsonData);
//            clear cache
            Memory.messenger = "";

            setLongMatchTime(Long.parseLong(json.get("longMatchTime").toString()));

//            set all point
            JSONArray recs = json.getJSONArray("data");

            for (int i = 0; i < recs.length(); i++) {
                JSONObject rec = recs.getJSONObject(i);
                NumberPoint point = new NumberPoint();

                point.setIntValue(Integer.parseInt(rec.get("intValue").toString()));
                point.setIntPosX(Integer.parseInt(rec.get("intPosX").toString()));
                point.setIntPosY(Integer.parseInt(rec.get("intPosY").toString()));
                point.setStrChosenColor(rec.get("strChosenColor").toString());
                point.setStrRare(rec.get("strRare").toString());

                this.addPointToMap(point);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
