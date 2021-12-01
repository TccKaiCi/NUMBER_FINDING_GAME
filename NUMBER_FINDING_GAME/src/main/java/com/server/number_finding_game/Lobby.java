package com.server.number_finding_game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.SocketAddress;
import java.util.*;

public class Lobby extends Thread{
    public boolean isStart=false;
    public boolean isEnd=false;
    String LobbyID;
    List<ChatServerThread> ThreadChat = new ArrayList<>(3);
}
