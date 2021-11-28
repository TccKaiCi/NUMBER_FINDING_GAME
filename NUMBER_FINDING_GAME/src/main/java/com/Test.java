package com;

import com.BUS.Match;
import com.DTO.NumberPoint;

import java.util.Collections;
import java.util.Stack;

public class Test {
    public static void main(String[] args) {
        Match match = new Match();

        match.createRandomMap(1, 10);

        match.getMap().display();

//        lấy ra
//        do {
//            int element = stacks.pop();
//            System.out.println("Removed Element: " + element);
//        } while (!stacks.empty());
    }
}
