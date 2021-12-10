package com.server.number_finding_game;

import com.BUS.DetailMatchBUS;
import com.BUS.Match;
import com.BUS.UserAccountBUS;
import com.DAO.UserAccountDAO;
import com.DTO.DetailMatchDTO;
import com.DTO.NumberPoint;
import com.DTO.UserAccountDTO;
import com.server.number_finding_game.ChatServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.*;

// SERVER : Multi Server
// TIPE : Two-Way Communication (Client to Server, Server to Client)
// DESCRIPTION : 
// A simple server that will accept multi client connection and display everything the client says on the screen. 
// The Server can handle multiple clients simultaneously.
// The Server can sends all text received from any of the connected clients to all clients, 
// this means the Server has to receive and send, and the client has to send as well as receive.
// If the client user types "exit", the client will quit.
public class NewServer implements Runnable {
    public int softLimit = 40;
    public int hardLimit = 45;
    private int port = 8081;
    private ServerSocket serverSocket = null;
    private Thread thread = null;
    private ChatServerThread clients[] = new ChatServerThread[hardLimit];
    private int clientCount = 0;
    private List<Lobby> ListLobby;

    UserAccountBUS bustmp;
    DetailMatchBUS detailMatchBUS;


    public static void main(String[] args) {
        NewServer news = new NewServer();
    }

    public NewServer() {
        try {
            serverSocket = new ServerSocket(port);
            // khởi tạo
            ListLobby = new ArrayList<>();
            bustmp = new UserAccountBUS();
            detailMatchBUS = new DetailMatchBUS();

            System.out.println("Server started on port " + serverSocket.getLocalPort() + "...");
            System.out.println("Waiting for client...");
            thread = new Thread(this);
            thread.start();
        } catch (IOException e) {
            System.out.println("Can not bind to port : " + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (thread != null) {
            try {
                // wait until client socket connecting, then add new thread
                addThreadClient(serverSocket.accept());
            } catch (IOException e) {
                System.out.println("Server accept error : " + e);
                stop();
            }
        }
    }

    public void stop() {
        if (thread != null) {
            thread = null;
        }
    }

    private int findClient(SocketAddress ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Xu li cu phap
     *
     * @param ID    sdasda
     * @param input dsadsad
     */
    public synchronized void handle(SocketAddress ID, String input) throws Exception {
        if (clientCount > softLimit) {
            clients[findClient(ID)].send("Server is very busy now");
            clients[findClient(ID)].send("exit");
            remove(ID);
            return;
        }
        // khởi tạo
        UserAccountDTO dtotmp = new UserAccountDTO();

        System.out.println("Server get from Client " + ID + " " + input);
        if (input.equals("exit")) {
            clients[findClient(ID)].send("exit");
            remove(ID);
        } else {
            if (input.equalsIgnoreCase("start")) {
                String idLobby = findLobbyFree(ID);
                clients[findClient(ID)].setLobbyID(idLobby);

                clients[findClient(ID)].send("YourLob;" + clients[findClient(ID)].getLobbyID());

//                lobby
                Lobby tmp = findDirectLobby(ID);

//                if room/ lobby is full 3/3 player or client
                if (tmp.state.equalsIgnoreCase("isFull") && tmp != null) {
//                    Create idRoom and time
                    tmp.Match = new Match(clients[findClient(ID)].getLobbyID(), 300);
//                    Create random map
//                    Set up
                    tmp.Match.createRandomMap(1, 20, 790, 0, 510, 0);

//                    set id lobby
                    java.sql.Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String lobbyIdRoom = "Lobby" + timestamp.getTime();

//                    Send map for all player in lobby
                    for (int i = 0; i < 3; i++) {
                        SocketAddress temp = tmp.addr.get(i);
                        // tạo detail match
                        DetailMatchDTO dto = new DetailMatchDTO();

                        dto.setStrUid(clients[findClient(temp)].getUid());
                        dto.setStrIdRoom(lobbyIdRoom);

//                        set color
                        String color = "";
                        switch (i) {
                            case 0:
                                color = "#ff0000";
                                break;
                            case 1:
                                color = "#ff00eb";
                                break;
                            case 2:
                                color = "#7eff45";
                                break;
                        }
                        dto.setStrPlayerColor(color);
                        dto.setIntPoint(0);
                        dto.setStrKetQua("");

                        // add to bus detailmatch
                        // write to database
                        detailMatchBUS.add(dto);

                        clients[findClient(temp)].send(tmp.Match.getMapByJSon());
                    }


                    Thread.sleep(1000);
//                    Send next number to all player in lobby
                    NumberPoint DTO = findDirectLobby(ID).Match.getNextValue();
                    String output = "NextNumber;"
                            + DTO.getIntValue() + ":" + DTO.getStrRare();
                    sendMessengerToAllInLobby(ID, output);
                }
            }
            //Xử lí cú pháp
            if (input.contains(";")) {
                String[] job = input.split(";");
                int lenght = job.length;
                switch (lenght) {
                    //SIGNIN;tendangnhap;matkhau
                    case 3:
                        if (job[0].equalsIgnoreCase("SIGNIN")) {
                            dtotmp.setStrUserName(job[1]);
                            dtotmp.setStrPassWord(job[2] + "+NumberFinding");
                            System.out.println(dtotmp.getStrPassWord());
                            if (dtotmp.getStrPassWord() != null) {
                                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                                byte[] encodedhash = digest.digest(
                                        dtotmp.getStrPassWord().getBytes(StandardCharsets.UTF_8));
                                dtotmp.setStrPassWord(bytesToHex(encodedhash));
                                System.out.println(dtotmp.getStrPassWord());
                            }
                            if (bustmp.kiemTraDangNhap(dtotmp)) {
                                System.out.println("valid user");
                                clients[findClient(ID)].send("valid user");

                                // return for client all infor user
                                dtotmp = bustmp.getUserAccountByUserName(dtotmp);
                                String sendmess = "Account;" +
                                        dtotmp.getStrUid() + ":" +
                                        dtotmp.getStrUserName() + ":" +
                                        dtotmp.getStrNameInf() + ":" +
                                        dtotmp.getStrPassWord() + ":" +
                                        dtotmp.getStrGender() + ":" +
                                        dtotmp.getStrDayOfBirth();

                                // set uid
                                clients[findClient(ID)].setUid(dtotmp.getStrUid());

                                Thread.sleep(1000);

                                clients[findClient(ID)].send(sendmess);
                            }

                            dtotmp = new UserAccountDTO();
                        }
                    case 6:
                        //SIGNUP;UID;SIGNUP;Username;Nameinf;Passwd;Gender
                        if (job[0].equalsIgnoreCase("SIGNUP")) {
                            //set temp info
                            //need delete when have auto increasing mechanic
                            dtotmp.setStrUserName(job[1]);
                            dtotmp.setStrNameInf(job[2]);
                            dtotmp.setStrPassWord(job[3] + "+NumberFinding");
                            dtotmp.setStrGender(job[4]);
                            dtotmp.setStrDayOfBirth(job[5]);
                            dtotmp.setStrUid(bustmp.getDefault());
                            //need date time
                            //hash paswd
                            if (dtotmp.getStrPassWord() != null) {
                                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                                byte[] encodedhash = digest.digest(
                                        dtotmp.getStrPassWord().getBytes(StandardCharsets.UTF_8));
                                dtotmp.setStrPassWord(bytesToHex(encodedhash));
                                clients[findClient(ID)].send("Signup success");
                                bustmp.them(dtotmp);
                                if (bustmp.kiemTraDangNhap(dtotmp)) {
                                    clients[findClient(ID)].send("Success signup");
                                } else {
                                    clients[findClient(ID)].send("Something gone wrong, cant signup this time");
                                }
                            }

                        }
                    case 2:
                        //gui vs cu phap play nghia la dang trong tran
                        ;//Pickup;Number:Color
                        if (job[0].equalsIgnoreCase("Pickup")) {
                            String[] arr = job[1].split(":");
//                            Check is the point chosen
//                            Input is value
                            if (findDirectLobby(ID).Match.getMap().isChosen(Integer.parseInt(arr[0]))) {
                                findDirectLobby(ID).Match.getMap().setColorByValue(Integer.parseInt(arr[0]), arr[1]);

//                                tính điểm cho user

                                // gửi cho các user còn lại
                                phanluong(ID, input);

                                Thread.sleep(1000);

//                                Send and color the value number (Input)
                                String output = "FillColor;" + arr[0] + ":" + arr[1];
                                //gửi tất cả user
                                sendMessengerToAllInLobby(ID, output);

                                Thread.sleep(1000);

//                                Send next color to choice
                                NumberPoint DTO = findDirectLobby(ID).Match.getNextValue();
                                output = "NextNumber;"
                                        + DTO.getIntValue() + ":" + DTO.getStrRare();
                                sendMessengerToAllInLobby(ID, output);
                            }
                        }

                }
            }
        }
    }

    public void phanluong(SocketAddress ID, String input) {

        if (!clients[findClient(ID)].getLobbyID().equalsIgnoreCase("")) {
            for (int i = 0; i < clientCount; i++) {
                if (clients[i].getLobbyID().equalsIgnoreCase(clients[findClient(ID)].getLobbyID())) {
                    if (clients[i].getID() == ID) {
//                         if this client ID is the sender, just skip it
                        continue;
                    }
                    if (!input.equalsIgnoreCase("start"))
                        clients[i].send(input);
                }
            }
        }
    }

    public void sendMessengerToAllInLobby(SocketAddress ID, String input) {

        if (!clients[findClient(ID)].getLobbyID().equalsIgnoreCase("")) {
            for (int i = 0; i < clientCount; i++) {
                if (clients[i].getLobbyID().equalsIgnoreCase(clients[findClient(ID)].getLobbyID())) {
                    if (!input.equalsIgnoreCase("start"))
                        clients[i].send(input);
                }
            }
        }
    }

    public synchronized void removeFromLobby(ChatServerThread client) {
        int remove = Integer.parseInt(client.getLobbyID());
        ListLobby.get(remove).addr.remove(client.getID());
    }

    public synchronized String findLobbyFree(SocketAddress addr) {
        String res = "";
        Lobby lobby = new Lobby();
        if (ListLobby.size() == 0) {
            Lobby lobbyPrime = new Lobby();
            lobbyPrime.LobbyID = String.valueOf(ListLobby.size());
            ListLobby.add(lobbyPrime);
        }
        for (int i = 0; i < ListLobby.size(); i++) {
            if (ListLobby.get(i).addr.size() == 3 && i == ListLobby.size() - 1) {
                lobby.LobbyID = String.valueOf(ListLobby.size());
                lobby.addr.add(addr);
                ListLobby.add(lobby);
                res = lobby.LobbyID;
                System.out.println("ListLobby==3");
                lobby = new Lobby();
                return res;
            }
            //bo dieu kien isfull = isstart neu co lam lobby 2 nguoi hoac waiting time

            if (ListLobby.get(i).addr.size() < 3 && !ListLobby.get(i).state.equalsIgnoreCase("isFull")) {
                ListLobby.get(i).addr.add(addr);
                res = String.valueOf(i);
                System.out.println("listLobby<3");
                if (ListLobby.get(i).addr.size() == 3) {
                    ListLobby.get(i).state = "isFull";
                    System.out.println(ListLobby.get(i).state);
                }
                lobby = new Lobby();
                return res;
            }

        }
        return res;
    }

    public Lobby findDirectLobby(SocketAddress addr) {
        for (int i = 0; i < ListLobby.size(); i++) {
            for (int j = 0; j < 3; j++) {
                if (ListLobby.get(i).addr.get(j) == addr) {
                    return ListLobby.get(i);
                }
            }
        }
        return new Lobby();
    }

    public synchronized void remove(SocketAddress ID) {
        if (!clients[findClient(ID)].getLobbyID().equalsIgnoreCase(""))
            removeFromLobby(clients[findClient(ID)]);
        int index = findClient(ID);
        if (index >= 0) {
            ChatServerThread threadToTerminate = clients[index];
            System.out.println("Removing client thread " + ID + " at " + index);
            if (index < clientCount - 1) {
                for (int i = index + 1; i < clientCount; i++) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            try {
                threadToTerminate.close();
            } catch (IOException e) {
                System.out.println("Error closing thread : " + e.getMessage());
            }
        }
    }

    private void addThreadClient(Socket socket) {
        if (clientCount < clients.length) {
            clients[clientCount] = new ChatServerThread(this, socket);
            clients[clientCount].start();
            clientCount++;
        } else {
            System.out.println("Client refused : maximum " + clients.length + " reached.");
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
