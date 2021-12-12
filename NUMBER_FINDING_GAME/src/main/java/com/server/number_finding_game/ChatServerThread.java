package com.server.number_finding_game;

import com.server.number_finding_game.Memory;
import com.server.number_finding_game.NewServer;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ChatServerThread extends Thread {
    private String LobbyID = "";
    private String uid = "";
    private NewServer newServer = null;
    private Socket socket = null;
    private SocketAddress ID = null;

    private BufferedInputStream bis = null;
    private DataInputStream dis = null;
    private BufferedOutputStream bos = null;
    private DataOutputStream dos = null;

    public ChatServerThread(NewServer _New_server, Socket _socket) {
        super();
        newServer = _New_server;
        socket = _socket;
        ID = socket.getRemoteSocketAddress();
    }

    public SocketAddress getID() {
        return ID;
    }

    public String getLobbyID() {
        return LobbyID;
    }

    public void setLobbyID(String Lobby) {
        this.LobbyID = Lobby;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void send(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException e) {
            System.out.println("Client " + socket.getRemoteSocketAddress() + " error sending : " + e.getMessage());
            newServer.remove(ID);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Client " + socket.getRemoteSocketAddress() + " connected to server...");

            bis = new BufferedInputStream(socket.getInputStream());
            dis = new DataInputStream(bis);
            bos = new BufferedOutputStream(socket.getOutputStream());
            dos = new DataOutputStream(bos);

            while (true) {
                //gui cho player biet no dang o lobby nao, reset sau khi lobby isEnd
                newServer.handle(ID, dis.readUTF());
            }
        } catch (IOException e) {
            newServer.remove(ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        System.out.println("Client " + socket.getRemoteSocketAddress() + " disconnect from server...");
        socket.close();
        dis.close();
        dos.close();
    }
}
