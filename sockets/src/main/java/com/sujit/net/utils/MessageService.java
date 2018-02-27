package com.sujit.net.utils;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader; 
import java.io.PrintWriter;
import java.io.IOException; 
import java.util.concurrent.Callable;

public class MessageService implements Callable {
    
    Socket clientSocket;
    private PrintWriter socketWriter;
    private BufferedReader socketReader;

    public MessageService(Socket socket){
        clientSocket = socket;
    }

    @Override
    public Object call() throws Exception {
        socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String message;
        while((message = socketReader.readLine())!=null){
            if(".".equals(message)){
                socketWriter.println("good bye!");
                break;
            }
            socketWriter.println(message);
        }
        return null;
    }
    
}