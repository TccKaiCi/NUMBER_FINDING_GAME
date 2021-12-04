package com.server.number_finding_game;

import com.BUS.UserAccountBUS;
import com.DAO.UserAccountDAO;
import com.DTO.UserAccountDTO;
import com.server.number_finding_game.ChatServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

// SERVER : Multi Server
// TIPE : Two-Way Communication (Client to Server, Server to Client)
// DESCRIPTION : 
// A simple server that will accept multi client connection and display everything the client says on the screen. 
// The Server can handle multiple clients simultaneously.
// The Server can sends all text received from any of the connected clients to all clients, 
// this means the Server has to receive and send, and the client has to send as well as receive.
// If the client user types "exit", the client will quit.
public class NewServer implements Runnable {

    private int port = 8081;
    private ServerSocket serverSocket = null;
    private Thread thread = null;
    private ChatServerThread clients[] = new ChatServerThread[50];
    private Map<String,Lobby> ListLobby = new HashMap<>();
    private UserAccountDTO[] UserList = new UserAccountDTO[90];

    Lobby tmp = new Lobby();
    private int clientCount = 0;
public static void main(String[] args){
    NewServer news = new NewServer();
}
    public NewServer() {
        try {
            serverSocket = new ServerSocket(port);
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
     * @param ID sdasda
     * @param input dsadsad
     */
    public synchronized void handle(SocketAddress ID, String input) throws Exception {
        UserAccountDTO dtotmp = new UserAccountDTO();
        UserAccountBUS bustmp = new UserAccountBUS();
        System.out.println("Server get from Client "+ID+" "+input);
        if (input.equals("exit")) {
            clients[findClient(ID)].send("exit");
            remove(ID);
        } else {
            if(input.equalsIgnoreCase("start")){
             clients[findClient(ID)].send("YourLob;"+clients[findClient(ID)].getLobbyID());
            }
            //Xử lí cú pháp
            if(input.contains(";")){
                String[] job=input.split(";");
                int lenght=job.length;
                switch (lenght){
                //SIGNIN
                    case 3:
                        if(job[0].equalsIgnoreCase("SIGNIN")) {
                            dtotmp.setStrUserName(job[1]);
                            dtotmp.setStrPassWord(job[2]);
                            if (bustmp.kiemTraDangNhap(dtotmp)) {
                                System.out.println("valid user");
                                clients[findClient(ID)].send("valid user");
                            }
                            dtotmp=new UserAccountDTO();
                        }
                    case 6:
                        //SIGNUP;UID;SIGNUP;UID;Username;Nameinf;Passwd;Gender
                        if (job[0].equalsIgnoreCase("SIGNUP")){
                            //set temp info
                            dtotmp.setStrUid(job[1]);//need delete when have auto increasing mechanic
                            dtotmp.setStrUserName(job[2]);
                            dtotmp.setStrNameInf(job[3]);
                            dtotmp.setStrPassWord(job[4]);
                            dtotmp.setStrGender(job[5]);
                            //need date time
                            //hash paswd
                            if(dtotmp.getStrPassWord()!=null){
                                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                                byte[] encodedhash = digest.digest(
                                        dtotmp.getStrPassWord().getBytes(StandardCharsets.UTF_8));
                                dtotmp.setStrPassWord(bytesToHex(encodedhash));
                                clients[findClient(ID)].send("Signin success");
                                System.out.println("HashPasswd: " + bytesToHex(encodedhash));
                            }
                            bustmp.them(dtotmp);
                        }
                }
            }
            //Phan luong
            Lobby temp=ListLobby.get(clients[findClient(ID)].getLobbyID());
            if(temp!=null)
            for (int i=0;i<3;i++){
                if (clients[i].getID() == ID) {
                    // if this client ID is the sender, just skip it
                    continue;
                }
                //chi nhung client co trong lobby moi nhan dc
                clients[findClient(temp.ThreadChat.get(i).getID())].send(input);
            }
        }
    }

    public synchronized void remove(SocketAddress ID) {
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
            addMemtoLobby(clients[clientCount]);
            clients[clientCount].setLobbyID(String.valueOf(ListLobby.size()));
            clients[clientCount].start();
            clientCount++;
        } else {
            System.out.println("Client refused : maximum " + clients.length + " reached.");
        }
    }

    /**
     * This set Lobby ID to when client start new thread it can send them to Client
     * @param member
     * @return
     */
    private void addMemtoLobby(ChatServerThread member){
        tmp.LobbyID = String.valueOf(ListLobby.size());
        tmp.ThreadChat.add(member);
        //well lobby reach fullsize we add them to new lobby, neead a fucntion remove them when they disconect
        if(tmp.ThreadChat.size()==3){
            tmp.isStart=true;
            ListLobby.put(tmp.LobbyID,tmp);
            tmp =new Lobby();
        }
    }
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
