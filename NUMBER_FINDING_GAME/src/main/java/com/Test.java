package com;

import com.BUS.Match;
import com.DTO.NumberPoint;

public class Test {
    public static void main(String[] args) {
        Match match;
        match = new Match("R3", 300);

        match.createRandomMap(1, 10, 1020, 200, 600, 60);


        System.out.println( match.getMapByJSon());
    }

}
