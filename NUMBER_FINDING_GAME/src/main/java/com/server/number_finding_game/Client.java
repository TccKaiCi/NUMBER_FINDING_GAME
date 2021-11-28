package com.server.number_finding_game;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    Socket socket = null;
    BufferedWriter out = null;
    BufferedReader in = null;
    BufferedReader stdin = null;

    public static void main(String[] args) throws UnknownHostException, IOException {
    Client client = new Client("localhost",8081,"hello");

    }

    public Client(String host, int port,String line) throws UnknownHostException, IOException {
        try {
            socket = new Socket(host, port);
            System.out.println("ClientBK accepted");
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.write(line);
                out.newLine();
                out.flush();
                line = in.readLine();
                System.out.println("ClientBK get: " + line);
                if (line.equals("bye")) {
                    System.out.println("Closing connection");
                    in.close();
                    out.close();
                    socket.close();
                }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}