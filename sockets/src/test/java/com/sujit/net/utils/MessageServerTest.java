package com.sujit.net.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*; 


class MessageServerTest {
    
    static MessageServer messageServer; 
    
    @BeforeAll
    static void setup() throws IOException {
        messageServer = new MessageServer();
        System.out.println("Starting up server...");
        try {
            messageServer.startMessageServer(5634);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started");
    }
    
    @AfterAll
    static void teardown() throws IOException {
        System.out.println("Shutting down server...");
        try {
            messageServer.stopMessageServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server shut down");
    }
    
    
    @Test
    @DisplayName("Test for first client")
    void testMessageEcho1() throws IOException {
        MessageClient messageClient = new MessageClient();
        messageClient.startServerConnection("127.0.0.1",5634);
        String response1 = messageClient.sendMessage("hello message-server");
        String response2 = messageClient.sendMessage("have a great day");
        String response3 = messageClient.sendMessage(".");
        assertEquals("hello message-server",response1);
        assertEquals("have a great day",response2);
        assertEquals("good bye!",response3);
    } 
    
    @Test
    @DisplayName("Test for second client")
    void testMessageEcho2() throws IOException {
        MessageClient messageClient = new MessageClient();
        messageClient.startServerConnection("127.0.0.1",5634);
        String response1 = messageClient.sendMessage("hi are you there");
        String response2 = messageClient.sendMessage("sound like its a just a ping");
        String response3 = messageClient.sendMessage(".");
        assertEquals("hi are you there",response1);
        assertEquals("sound like its a just a ping",response2);
        assertEquals("good bye!",response3);
    }
    
}
