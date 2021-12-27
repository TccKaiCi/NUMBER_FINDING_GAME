package com.server.number_finding_game;

import com.BUS.DetailMatchBUS;
import com.BUS.Match;
import com.BUS.UserAccountBUS;
import com.DTO.DetailMatchDTO;
import com.DTO.NumberPoint;
import com.DTO.UserAccountDTO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private final int port = 8081;
    private ServerSocket serverSocket = null;
    private Thread thread = null;
    private final ChatServerThread[] clients = new ChatServerThread[hardLimit];
    private int clientCount = 0;
    private List<Lobby> ListLobby;
    private int Startpoint = 1, Endpoint = 12;
    private int thoiGian = 100;
    HashMap<String, String> userStatus = new HashMap<>();
    UserAccountBUS accountBus;
    DetailMatchBUS detailMatchBUS;

    public HashMap<String, String> getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(HashMap<String, String> userStatus) {
        this.userStatus = userStatus;
    }

    public int getStartpoint() {
        return Startpoint;
    }

    public void setStartpoint(int startpoint) {
        Startpoint = startpoint;
    }

    public int getHardLimit() {
        return hardLimit;
    }

    public void setHardLimit(int hardLimit) {
        this.hardLimit = hardLimit;
    }

    public int getSoftLimit() {
        return softLimit;
    }

    public void setSoftLimit(int softLimit) {
        this.softLimit = softLimit;
    }

    public int getEndpoint() {
        return Endpoint;
    }

    public List<Lobby> getListLobby() {
        return ListLobby;
    }

    public void setListLobby(List<Lobby> listLobby) {
        ListLobby = listLobby;
    }

    public void setEndpoint(int endpoint) {
        Endpoint = endpoint;
    }

    public static void main(String[] args) {
        NewServer news = new NewServer();
    }

    public NewServer() {
        try {
            serverSocket = new ServerSocket(port);
            // khởi tạo
            ListLobby = new ArrayList<>();
            accountBus = new UserAccountBUS();
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


//                if room/ lobby is full 3/3 player or client
                if (findDirectLobby(ID).state.equalsIgnoreCase("isFull") && findDirectLobby(ID) != null) {
//                    Create idRoom and time
                    findDirectLobby(ID).Match = new Match(clients[findClient(ID)].getLobbyID(), thoiGian);
//                    Create random map
//                    Set up
                    findDirectLobby(ID).Match.createRandomMap(Startpoint, Endpoint, 790, 0, 510, 0);
                    findDirectLobby(ID).ListOwner = new HashMap<>();

//                    Get all UID and color
                    StringBuilder sb = new StringBuilder();
                    sb.append("UserColor;");
                    for (int i = 0; i < 3; i++) {
                        SocketAddress temp = findDirectLobby(ID).addr.get(i);

                        String color = switch (i) {
                            case 0 -> "Red";
                            case 1 -> "Yellow";
                            case 2 -> "Green";
                            default -> "";
                        };

                        //UID:COLOR:UID2:COLOR2:UID3:COLOR3:
                        sb.append("%s:%s:".formatted(accountBus.getNameInf_UID(clients[findClient(temp)].getUid()), color));
                    }

                    //        delete end ":"
                    sb.deleteCharAt(sb.length() - 1);

                    for (int i = 0; i < 3; i++) {
                        SocketAddress temp = findDirectLobby(ID).addr.get(i);

                        clients[findClient(temp)].send(sb.toString());

                        Thread.sleep(1000);

                        findDirectLobby(ID).ListOwner.put(clients[findClient(temp)].getUid(), 0);
                        clients[findClient(temp)].send(findDirectLobby(ID).Match.getMapByJSon());
                    }

                    Thread.sleep(500);
//                    Send next number to all player in lobby
                    NumberPoint DTO = findDirectLobby(ID).Match.getNextValue();
                    if (DTO == null) {
                        //todo
                        findDirectLobby(ID).state = "isEnd";
                    }
                    assert DTO != null;
                    String output = "NextNumber;"
                            + DTO.getIntValue() + ":" + DTO.getStrRare();
                    sendMessengerToAllInLobby(ID, output);
                    //                    server start count_down
                    Timer countDown = new Timer();
                    countDown.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            long temp = findDirectLobby(ID).Match.getLongMatchTime();
                            findDirectLobby(ID).Match.setLongMatchTime(temp - 1);


                            if (temp == 0) {
                                try {
                                    EndMatch(ID);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                countDown.cancel();
                            }
                        }
                    }, 0, 1000);

                }
            }
            // lam ham end, t di wc xiu
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
                            if (accountBus.kiemTraDangNhap(dtotmp)) {
                                boolean valid = true;
                                dtotmp = accountBus.getUserAccountByUserName(dtotmp);
                                clients[findClient(ID)].setUid(dtotmp.getStrUid());
                                for (int i = 0; i < clientCount; i++) {
                                    if (clients[i].getUid().equalsIgnoreCase(dtotmp.getStrUid())) {
                                        if (clients[i].getID() == ID) {
                                            continue;
                                        }
                                        clients[findClient(ID)].send("Account are sign in on other address");
                                        valid = false;
                                    }
                                }
                                if (valid) {
                                    userStatus.put(clients[findClient(ID)].getUid(), "online");
                                    clients[findClient(ID)].send("valid user");
                                    // return for client all infor user

                                    String sendmess = "Account;" +
                                            dtotmp.getStrUid() + ":" +
                                            dtotmp.getStrUserName() + ":" +
                                            dtotmp.getStrNameInf() + ":" +
                                            dtotmp.getStrPassWord() + ":" +
                                            dtotmp.getStrGender() + ":" +
                                            dtotmp.getStrDayOfBirth();

                                    // set uid

                                    Thread.sleep(1000);

                                    clients[findClient(ID)].send(sendmess);

                                }


                            }

                            dtotmp = new UserAccountDTO();
                        }
                        break;
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
                            //todo valid user
                            dtotmp.setStrUid(accountBus.getDefault());
                            //need date time
                            //hash paswd
                            if (!accountBus.kiemTraDangki(dtotmp)) {
                                if (dtotmp.getStrPassWord() != null) {
                                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                                    byte[] encodedhash = digest.digest(
                                            dtotmp.getStrPassWord().getBytes(StandardCharsets.UTF_8));
                                    dtotmp.setStrPassWord(bytesToHex(encodedhash));
                                    accountBus.them(dtotmp);
                                    if (accountBus.kiemTraDangNhap(dtotmp)) {
                                        clients[findClient(ID)].send("Success signup");
                                    } else {
                                        clients[findClient(ID)].send("Something gone wrong, cant signup this time");
                                    }
                                }
                            } else {
                                //todo
                                clients[findClient(ID)].send("ERR:username already have a account");
                            }
                        }
                        break;
                    case 2://edit acc
                        if (job[0].equalsIgnoreCase("Edit")) {
                            String[] edit = job[1].split(":");
                            UserAccountDTO usr = new UserAccountDTO();
                            usr = accountBus.getUserAccountByUID(edit[0]);
                            if (usr != null) {
                                if (usr.getStrPassWord().equalsIgnoreCase(edit[1])) ;
                                usr.setStrDayOfBirth(edit[3]);
                                usr.setStrGender(edit[4]);
                                usr.setStrNameInf(edit[2]);
                                accountBus.update(usr);
                                clients[findClient(ID)].send("EditSuccess");
                            }
                        }
                        if (job[0].equalsIgnoreCase("changepass")) {
                            String[] passwd = job[1].split(":");
                            UserAccountDTO usr = new UserAccountDTO();
                            usr = accountBus.getUserAccountByUID(passwd[0]);
                            if (usr != null) {
                                if (usr.getStrPassWord().equalsIgnoreCase(passwd[1])) ;
                                usr.setStrPassWord(Hash(passwd[2] + "+NumberFinding"));
                                accountBus.update(usr);
                                clients[findClient(ID)].send("EditSuccess");
                            }
                        }

                        //gui vs cu phap play nghia la dang trong tran
                        //Pickup;Number:Color:Rare:UID
                        if (job[0].equalsIgnoreCase("Pickup")) {
                            String[] arr = job[1].split(":");
//                            Check is the point chosen
//                            Input is value
                            if (findDirectLobby(ID).Match.getMap().isChosen(Integer.parseInt(arr[0]))) {
                                //todo
                                String UID = clients[findClient(ID)].getUid();

                                // tô mày
                                findDirectLobby(ID).Match.getMap().setColorByValue(Integer.parseInt(arr[0]), arr[1]);

//                                tính điểm cho user

                                // cộng điễm

                                // add to detail match
                                //todo
                                int prePoint = findDirectLobby(ID).ListOwner.get(UID);

                                if (Objects.equals(arr[2], "Lucky")) {
                                    findDirectLobby(ID).ListOwner.put(UID, prePoint + 3);
                                    System.out.println("3 điểm");
                                } else {
                                    if (Objects.equals(arr[2], "Blind")) {
//                                        todo Tuấn Anh che màn hình
                                    } else {
                                        System.out.println("1 điểm");
                                        findDirectLobby(ID).ListOwner.put(UID, prePoint + 1);
                                    }
                                }
                                // gửi cho các user còn lại
                                phanluong(ID, input);

                                Thread.sleep(100);

//                                Send and color the value number (Input)
                                String output = "FillColor;" + job[1];
                                //gửi tất cả user
                                sendMessengerToAllInLobby(ID, output);

                                Thread.sleep(100);

//                                Send next color to choice
                                NumberPoint DTO = findDirectLobby(ID).Match.getNextValue();
                                if (DTO == null) {
                                    // end game
                                    // dont have next number
                                    EndMatch(ID);
                                } else {
                                    output = "NextNumber;"
                                            + DTO.getIntValue() + ":" + DTO.getStrRare();
                                    sendMessengerToAllInLobby(ID, output);
                                }
                            }
                        } else {
                            if (job[0].equalsIgnoreCase("Ranking")) {
                                clients[findClient(ID)].send("RankingData;" + new DetailMatchBUS().initJsonRankTable());
                            }
                        }
                        //Ranking;Ranking

                        break;
                }
            }
        }
    }

    public void EndMatch(SocketAddress ID) throws Exception {
        Lobby tmp = findDirectLobby(ID);
//                    set id lobby
        java.sql.Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String lobbyIdRoom = "Lobby" + timestamp.getTime();

        sendMessengerToAllInLobby(ID, "ENDGAME;");

        int max = 0;
        for (int i = 0; i < 3; i++) {
            SocketAddress temp = tmp.addr.get(i);
            for (Map.Entry<String, Integer> entry : findDirectLobby(ID).ListOwner.entrySet()) {
                if (entry.getValue() >= max) {
                    max = entry.getValue();
                }
            }
        }

//                    Send map for all player in lobby
        for (int i = 0; i < 3; i++) {
            SocketAddress temp = tmp.addr.get(i);
            // tạo detail match
            DetailMatchDTO dto = new DetailMatchDTO();

            dto.setStrUid(clients[findClient(temp)].getUid());
            dto.setStrIdRoom(lobbyIdRoom);

//                        set color
            String color = switch (i) {
                case 0 -> "#ff0000";
                case 1 -> "#ff00eb";
                case 2 -> "#7eff45";
                default -> "";
            };
            dto.setStrPlayerColor(color);

            // lay diem tu hash map
            for (Map.Entry<String, Integer> entry : findDirectLobby(ID).ListOwner.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(dto.getStrUid())) {
                    // remove UID when match end from hashMap
                    // then get point
                    // bay gio la lam theo cahc cong cua m
                    dto.setIntPoint(entry.getValue());
                }
            }

            //todo
            if (max == 0) {
                dto.setStrKetQua("lose");
            } else {
                if (max == dto.getIntPoint()) {
                    dto.setStrKetQua("win");
                } else {
                    dto.setStrKetQua("lose");
                }
            }
            // add to bus detailmatch
            // write to database
            // not right now
            detailMatchBUS.add(dto);
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
            for (int j = 0; j < ListLobby.get(i).addr.size(); j++) {
                if (ListLobby.get(i).addr.get(j) == addr) {
                    return ListLobby.get(i);
                }
            }
        }
        return new Lobby();
    }

    public synchronized void remove(SocketAddress ID) {
        if (findClient(ID) != -1) {
            userStatus.put(clients[findClient(ID)].getUid(), "offline");
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

    private static String Hash(String str) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                str.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    public int getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(int thoiGian) {
        this.thoiGian = thoiGian;
    }
}
