package com.server.number_finding_game;

import java.io.*;
import java.net.Socket;
import java.util.Map;

// Client for Server4
public class NewClient implements Runnable {
    public String CurLobbyID = ""; //uid
    private final String serverName = "localhost";
    private final int serverPort = 8081;
    private Socket socket = null;
    private Thread thread = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private ChatClientThread client = null;

    public static void main(String[] args) {
        NewClient client = new NewClient();
        client.Connect();
//        client.sendMessenger("start");
    }

    public String getCurLobbyID() {
        return CurLobbyID;
    }

    public void setCurLobbyID(String cur) {
        this.CurLobbyID = cur;
    }

    //    is connect tho server???
    public boolean Connect() {
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Client started on port " + socket.getLocalPort() + "...");
            System.out.println("Connected to server " + socket.getRemoteSocketAddress());
            dis = new DataInputStream(System.in);
            dos = new DataOutputStream(socket.getOutputStream());
            client = new ChatClientThread(this, socket);
            thread = new Thread(this);
            thread.start();
            return true;
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }

    @Override
    public void run() {
        while (thread != null) {
            try {
                System.out.print("Message to server : ");
                dos.writeUTF(dis.readLine());
                dos.flush();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Error : " + e.getMessage());
                }
            } catch (IOException e) {
                System.out.println("Sending error : " + e.getMessage());
                stop();
            }
        }
    }

    //    Other client send messenger to here
    public void handleMessage(String message) {

        if (message.equals("exit")) {
            stop();
        } else {
//
            if (message.equalsIgnoreCase("RESET") || message.equalsIgnoreCase("END")
                    || message.equalsIgnoreCase("Host win") || message.equalsIgnoreCase("Client win")) {
                Memory.statusMessenger = message;
                System.out.println("Tin nhan trang thai duoc dua vao hang doi: " + Memory.statusMessenger);
            } else {
                if ((!message.equalsIgnoreCase(" "))) {
//            say that we have a messenger
                    Memory.playerMessenger = true;
//            /127.0.0.1:50194 says : 0 1 2
                    Memory.messenger = message;
                    System.out.println("Client " + socket.getLocalPort() + " nhan duoc " + Memory.messenger);
                    if (message.contains(";")) {
                        String[] job = message.split(";");
//                            xu ly tac vu
                        switch (job.length) {
                            case 2: {
                                if (job[0].equalsIgnoreCase("YourLob")) {
                                    setCurLobbyID(job[1]);
                                    System.out.println("Current Lobby " + getCurLobbyID());
                                } else {
                                    if (job[0].equalsIgnoreCase("Account")) {
                                        String[] arr = job[1].split(":");
                                        Memory.userAccountDTO.setStrUid(arr[0]);
                                        Memory.userAccountDTO.setStrUserName(arr[1]);
                                        Memory.userAccountDTO.setStrNameInf(arr[2]);
                                        Memory.userAccountDTO.setStrPassWord(arr[3]);
                                        Memory.userAccountDTO.setStrGender(arr[4]);
                                        Memory.userAccountDTO.setStrDayOfBirth(arr[5]);
                                    } else {
                                        // UserColor;Color
                                        if (job[0].equalsIgnoreCase("UserColor")) {
                                            //NAME:COLOR:NAME2:COLOR2:NAME3:COLOR3
                                            //0:1:2:3:4:5
                                            String[] arr = job[1].split(":");
                                            Memory.otherUserInfor_Color.put(arr[0], arr[1]);
                                            Memory.otherUserInfor_Color.put(arr[2], arr[3]);
                                            Memory.otherUserInfor_Color.put(arr[4], arr[5]);

                                            for (Map.Entry<String, String> entry : Memory.otherUserInfor_Color.entrySet() ) {
                                                if (entry.getKey().equalsIgnoreCase(Memory.userAccountDTO.getStrNameInf())) {
                                                    Memory.userColor = entry.getValue();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (message.equalsIgnoreCase("test")) {
                        sendMessenger("; " + getCurLobbyID());
                    }
                }
            }
        }
    }

    //    Stop the connection
    public void stop() {
        try {
            thread = null;
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing : " + e.getMessage());
        }
        client.close();
    }

    public String getServer() {
        return String.valueOf(socket.getRemoteSocketAddress());
    }

    //    Try to find other servver
    public boolean findServer() {
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Client started on port " + socket.getLocalPort() + "...");
            System.out.println("Connected to server " + socket.getRemoteSocketAddress());
            System.out.println("Disconnect to server");
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void sendMessenger(String line) {
        Memory.messenger = line;
        try {
            dos.writeUTF(Memory.messenger);
            dos.flush();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error : " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Sending error : " + e.getMessage());
            stop();
        }
    }

    public void sendStatusMessenger() {
        try {
            dos.writeUTF(Memory.statusMessenger);
            dos.flush();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error : " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Sending error : " + e.getMessage());
            stop();
        }
    }
}
