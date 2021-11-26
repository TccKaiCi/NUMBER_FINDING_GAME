package com.server.number_finding_game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server extends Thread{
    Socket socket = null;
    ServerSocket server = null;
    BufferedWriter out = null;
    BufferedReader in = null;
    String defaultRes="invalid syntax";

    public static void main(String[] args) {

        Server a = new Server(6000);
    }

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server Started");
            System.out.println("Waiting for client ... ");

            socket = server.accept();
            System.out.println("ClientBK accepted");

            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String res="invalid syntax";
            while (true) {
                String line = in.readLine();
                if (line.equals("bye")) {
                    break;
                }
                System.out.println("Server get : " + line);
                if(line.contains(";")) {
                    String[] process = line.split(";");
                    int lenght = process.length;
                    System.out.println(lenght);
                    switch (lenght){
                        case 3: // validation signin
                        {
                            if(process[0].equals("SIGNIN")) {
                                //check thong tin trong database SIGNIN;username;passwd
                                try {
                                    ResultSet resultSet = new MySQLConnUtils().FindAccountInf("useraccount",process[1]);
                                    if(resultSet==null) break;
                                    resultSet.next();
                                    //getpass o day chua hash ca 2
                                    String passwd =resultSet.getString(2);
                                    if(process[2].equals(passwd)){
                                        System.out.println("valid user");
                                        res="valid user";
                                    }
                                    else {
                                        res="wrong password";
                                    }
                                } catch (SQLException e) {
                                    if(e.toString().equals("java.sql.SQLException: Illegal operation on empty result set.")){
                                        res ="Invalid user";
                                    }
                                }

                            }
                        }

                    }
                }

                out.write(res);
                out.newLine();
                out.flush();
                res=defaultRes;
            }

            System.out.println("Closing connection");
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }




}


