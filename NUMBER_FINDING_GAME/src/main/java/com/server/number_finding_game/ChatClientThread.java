package com.server.number_finding_game;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread extends Thread {

    private Socket socket = null;
    private NewClient newClient = null;
    private DataInputStream dis = null;

    public ChatClientThread(NewClient _New_client, Socket _socket) {
        newClient = _New_client;
        socket = _socket;
        open();
        start();
    }

    public void open() {
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error getting input stream : " + e.getMessage());
            newClient.stop();
        }
    }

    public void close() {
        try {
            dis.close();
        } catch (IOException e) {
            System.out.println("Error closing input stream : " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                newClient.handleMessage(dis.readUTF());
            }
        } catch (IOException e) {
            newClient.stop();
        }
    }
}
