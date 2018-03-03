package com.sujit.scrambler;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; 

public class SimpleAesCryptoTest {
    
    @Test
    void testSimpleEncryptionWithPlainText16bitKey() {
        String plainText = "hello world! I am a simple program.";
        String plainTextKey = "Z7eT12HwqBnW37hY";
        String encryptedText = AesCryptoUtils.encryptWithPlainTextKey(plainText, plainTextKey);
        String decryptedText = AesCryptoUtils.decryptWithPlainTextKey(encryptedText, plainTextKey);
        assertEquals(plainText, decryptedText);
    }
    
    
    @Test
    void testSimpleEncryptionWithPlainText15bitKey() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            String plainText = "hello world! I am a simple program.";
            String plainTextKey = "Z7eT12HwqBnW37j";
            String encryptedText = AesCryptoUtils.encryptWithPlainTextKey(plainText, plainTextKey);
        });
        assertEquals("Issue encountered while encrypting data. Invalid AES key length: 15 bytes", exception.getMessage());
    }
     
}
