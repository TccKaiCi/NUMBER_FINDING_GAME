package com;

import com.BUS.Match;
import com.DTO.NumberPoint;
import com.client.number_finding_game.GUI.MultiplayerController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;


public class Test {
    public static void main(String[] args) {
        HashMap<String,Integer> Scoreboard= new HashMap<>();
        Scoreboard.put("13231",1);
        Scoreboard.put("13231",Scoreboard.get("13231")+1) ;
        System.out.println(Scoreboard.get("13231"));
    }

    public static class MyTask extends TimerTask {
        public int a = 1;

        @Override
        public void run() {
            a++;
            System.out.println(a);
        }
    }
}
