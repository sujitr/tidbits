package com.sujit.net.utils;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class MessageClient {
    private Socket clientSocket;
    private PrintWriter socketWriter;
    private BufferedReader socketReader;
    
    public void startServerConnection(String ipAddress, int port) throws IOException {
        clientSocket = new Socket(ipAddress, port);
        socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
    
    public String sendMessage(String msg) throws IOException {
        socketWriter.println(msg);
        String response = socketReader.readLine();
        return response;
    }
    
    public void stopConnection() throws IOException {
        socketWriter.close();
        socketReader.close();
        clientSocket.close(); 
    }
}