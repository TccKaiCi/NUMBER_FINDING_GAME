package com.server.number_finding_game;

import com.BUS.Match;

import java.net.SocketAddress;
import java.util.*;

public class Lobby{
    public String state="isFree";
    public String DetailMatch;
    public Match Match;
    public String LobbyID;
    public List<SocketAddress> addr= new ArrayList<>(3);
}
