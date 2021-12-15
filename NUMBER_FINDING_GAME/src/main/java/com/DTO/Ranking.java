package com.DTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Only for client
 */
public class Ranking {
    private String UID;
    private int Point;
    private String KetQua;
    private String Name;
    public int SumPoint;
    public String WinRate;

    private List<Ranking> list;


    public Ranking() {
        list = new ArrayList<>();
    }

    public void getJsonRankTable(String json) {
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

    /**
     * Noob coder @prohzgod
     * @return
     */
    public Ranking handleRank() {
        HashMap<String, Integer> mapPoint = new HashMap<String, Integer>();

        //Tong diem player || return map<uid, diem>
        for (int i = 0; i < list.size(); i++) {
            mapPoint.merge(list.get(i).UID, list.get(i).Point, Integer::sum);
        }

        // Ty le thang/tong
        HashMap<String, Double> mapAllMatch = new HashMap<>();
        HashMap<String, Double> mapWinMatch = new HashMap<>();
        double temp = 0;
        double temp2 = 0;
        for (int i = 0; i < list.size(); i++) {
            for (Map.Entry<String, Integer> item : mapPoint.entrySet()) {
                if (list.get(i).UID.equals(item.getKey())) {
                    temp = 1;
                    mapAllMatch.merge(item.getKey(), temp, Double::sum);
                    if (list.get(i).KetQua.equals("win")) {
                        temp2 = 1;
                        mapWinMatch.merge(item.getKey(), temp2, Double::sum);
                    }
                }
            }
        }

        HashMap<String, String> mapWinRate = new HashMap<>();
        for (Map.Entry<String, Double> item : mapAllMatch.entrySet()) {
            for (Map.Entry<String, Double> item2 : mapWinMatch.entrySet()) {
                if (Objects.equals(item.getKey(), item2.getKey())) {
                    DecimalFormat numberFormat = new DecimalFormat("#.00");
                    mapWinRate.put(item.getKey(), numberFormat.format(((item2.getValue() / item.getValue()) * 100)));
                    // System.out.println("Ti le thang: "+(item2.getValue()/ item.getValue()) * 100);
                }
            }
        }

        //  UID     NAME    POINT   WINRATE
        // NAME     WINRATE
        Ranking bus = new Ranking();

        for (Map.Entry<String, Integer> item : mapPoint.entrySet()) {
            Ranking dto = new Ranking();

            dto.setUID(item.getKey());
            dto.setPoint(item.getValue());
            dto.setName(this.getName_UID(dto.getUID()));

            // winrate
            for (Map.Entry<String, String> winRate : mapWinRate.entrySet()) {
                if (dto.getUID() == winRate.getKey()) {
                    dto.setWinRate(winRate.getValue());
                }
            }
            bus.list.add(dto);
        }
        return bus;
    }

    public void sortPoint() {
        Collections.sort(list, Ranking::pointGiamDan);
    }

    public static int pointGiamDan(Ranking a, Ranking b){
        int x = a.getPoint();
        int y = b.getPoint();
        if ( x > y ) {
            return -1;
        }
        else {
            if (x == y ) {
                return 0;
            }
            else {
                return 1;
            }
        }
    }

    public String getName_UID(String name) {
        for (Ranking item : list) {
            if (Objects.equals(name, item.getUID())) {
                return item.getName();
            }
        }
        return null;
    }

    public void display() {
        for (Ranking DTO : list) {
            System.out.println(DTO.toString());
//            System.out.println(DTO.UID);
        }
    }

    public int getSumPoint() {
        return SumPoint;
    }

    public void setSumPoint(int sumPoint) {
        SumPoint = sumPoint;
    }

    public String getWinRate() {
        return WinRate;
    }

    public void setWinRate(String winRate) {
        WinRate = winRate;
    }

    public List<Ranking> getList() {
        return list;
    }

    public void setList(List<Ranking> list) {
        this.list = list;
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
                ", WinRate='" + WinRate + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
