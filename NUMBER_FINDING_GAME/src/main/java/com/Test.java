package com;

import com.BUS.Match;
import com.DTO.NumberPoint;
import com.client.number_finding_game.GUI.MultiplayerController;

import java.sql.Timestamp;
import java.util.TimerTask;


public class Test {
    public static void main(String[] args) {
//        MyTask myTask = new MyTask();
//        Timer timer = new Timer();
//        timer.schedule(myTask, 0, 10);

        String s = "A;b:C";
        String[] arr= s.split(";");
        System.out.println(arr.length);

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
