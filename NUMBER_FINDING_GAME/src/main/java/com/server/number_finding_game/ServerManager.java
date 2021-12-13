package com.server.number_finding_game;

import java.util.Scanner;

public class ServerManager {
    private static NewServer newServer;
    public static void main(String[] args){
        newServer=new NewServer();
        Scanner scanner =new Scanner(System.in);
        System.out.println("Status: Hien thi user status");
        String s=scanner.nextLine();

        while (true){
            if (s.equals("status")) {
                displayStatus();
                continue;
            }
        }
    }
  public static void displayStatus(){
      System.out.println("Status Account:\n");
     newServer.userStatus.forEach((k, v) -> System.out.println(k + " : " + v));
  }
}
