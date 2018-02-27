package com.sujit.net.utils;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader; 
import java.io.PrintWriter;
import java.io.IOException; 

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageServer {

    private ServerSocket serverSocket;

    private volatile boolean serverSwitch;  // keep server accepting client connection till its true
    private final ExecutorService serverPool;

    public MessageServer() {
        serverSwitch = true;
        serverPool = Executors.newFixedThreadPool(5);
    }

    public void startMessageServer(int port) throws IOException {
        Runnable serverTask = ()-> {
            try{
                serverSocket = new ServerSocket(port);
                System.out.println("waiting for clients to connect....");
                while(serverSwitch){
                    Socket connectedSocket = serverSocket.accept();
                    serverPool.submit(new MessageService(connectedSocket));
                }
            }catch(IOException ioex){
                System.err.println("Unable to process client request");
                ioex.printStackTrace();
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    public void stopMessageServer() throws IOException {
        serverSwitch = false;
        serverPool.shutdown();
        //serverSocket.close();
    }
}