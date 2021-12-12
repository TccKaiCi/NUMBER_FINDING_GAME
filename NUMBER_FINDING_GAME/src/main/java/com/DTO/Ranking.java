package com.DTO;

import com.server.number_finding_game.Memory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Only for client
 */
public class Ranking {
    private String UID;
    private int Point;
    private String KetQua;
    private String Name;

    private List<Ranking> list;

    public Ranking() {
        list = new ArrayList<>();
    }
    public void getJsonRankTable(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
//            Memory.messenger = "";

            JSONArray recs = jsonObject.getJSONArray("data");

            for (int i = 0; i < recs.length(); i++) {
                JSONObject rec = recs.getJSONObject(i);
                Ranking rank = new Ranking();

//                rank.setIntValue(Integer.parseInt(rec.get("intValue").toString()));
                rank.setUID(rec.get("strUID").toString());
                rank.setPoint(Integer.parseInt(rec.get("intPoint").toString()));
                rank.setKetQua(rec.get("strKetQua").toString());
                rank.setName((rec.get("strNameInfor").toString()));

                list.add(rank);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void handleRank(){
        String[] UID;

        for (Ranking dto : list){

        }
    }

    public void display() {
        for (Ranking DTO : list) {
            System.out.println(DTO.toString());
        }
    }




    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public String getKetQua() {
        return KetQua;
    }

    public void setKetQua(String ketQua) {
        KetQua = ketQua;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "UID='" + UID + '\'' +
                ", Point=" + Point +
                ", KetQua='" + KetQua + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
