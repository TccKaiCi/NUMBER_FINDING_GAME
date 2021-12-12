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

        long temp = 60023;

        System.out.println(temp / 60 +"phut");
        System.out.println( temp- (temp / 60)* 60+"giay");


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
