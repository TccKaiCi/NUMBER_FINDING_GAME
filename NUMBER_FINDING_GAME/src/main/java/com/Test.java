package com;

import com.BUS.Match;
import com.DTO.NumberPoint;

public class Test {
    public static void main(String[] args) {
        Match match;
        match = new Match("R3", 300);

        match.createRandomMap(14, 17);

        match.display();

        System.out.println( match.getNextValue().getIntValue());
    }

}
