package com.server.number_finding_game;

import java.net.SocketAddress;
import java.util.Scanner;

public class ServerManager {
    private static NewServer newServer;

    public static void main(String[] args) {
        newServer = new NewServer();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Status: Hien thi user status");
        String s = scanner.nextLine();

        while (!s.equalsIgnoreCase("stop")) {
            if (s.equalsIgnoreCase("status")) {
                displayStatus();
                s = scanner.nextLine();
            }
            if (s.equalsIgnoreCase("SetUp")) {
                int x,y;
                do {
                    System.out.println("Đặt điểm khởi đầu");
                    s = scanner.nextLine();
                } while (isInteger(s) && s != null);
                x = Integer.parseInt(s);

                do {
                    System.out.println("Đặt điểm kết thúc");
                    s = scanner.nextLine();
                } while (isInteger(s) && s != null);
                y = Integer.parseInt(s);

                if (x > y) {
                    newServer.setStartpoint(x);
                    newServer.setEndpoint(y);
                } else {
                    System.out.println("vui long nhập bắt đầu > kết thúc");
                }

            }
            if ((s.equalsIgnoreCase("Lobby"))) {
                if (newServer.getListLobby() == null) {
                    System.out.println();
                }
                for (Lobby lob : newServer.getListLobby()) {
                    System.out.println("Lobby" + lob.LobbyID + "\n");
                    for (SocketAddress soa : lob.addr) {
                        System.out.println("Player: " + soa.toString());
                    }
                }
            }
        }
    }

    public static void displayStatus() {
        System.out.println("Status Account:\n");
        newServer.userStatus.forEach((k, v) -> System.out.println(k + " : " + v));
    }

    public static boolean isInteger(String var) {
        try {
            Integer.parseInt(var);
            return true;

            // return false when "var" can't be converted to integer
            // NumberFormatException will return null
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
