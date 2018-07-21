package com.sujit.scrambler;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.sujit.scrambler.legacy.AesCryptoUtils;

import static org.junit.jupiter.api.Assertions.*;    


public class SimpleAesCryptoTest {
    
    @Test
    @DisplayName("Simple Encryption Test with 16 bit Key")
    void testSimpleEncryptionWithPlainText16bitKey() {
        String plainText = "hello world! I am a simple program.";
        String plainTextKey = "Z7eT12HwqBnW37hY";
        String encryptedText = AesCryptoUtils.encryptWithPlainTextKey(plainText, plainTextKey);
        String decryptedText = AesCryptoUtils.decryptWithPlainTextKey(encryptedText, plainTextKey);
        assertEquals(plainText, decryptedText);
    }
    
    
    @Test
    @DisplayName("Simple Encryption Test with 15 bit Key - with expected failure")
    void testSimpleEncryptionWithPlainText15bitKey() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            String plainText = "hello world! I am a simple program.";
            String plainTextKey = "Z7eT12HwqBnW37j";
            String encryptedText = AesCryptoUtils.encryptWithPlainTextKey(plainText, plainTextKey);
        });
        assertEquals("Issue encountered while encrypting data. Invalid AES key length: 15 bytes", exception.getMessage());
    }
    
    @Test
    @Disabled
    void testDecryptionWithSaltIV() {
        String plainText = "Hi all, this is a compilcated world";
        String password = "Z7eT12HwqBnW37hY";
        /*String encryptedText = "IMK7BpL+TWkvyc+oT44NTtVAozKb8xjxEoN50UiU801PKIS6MyowMdn9/43MinUt";
        String saltString = "8a69b7a94227b54c";
        String initVector = "0a83e5e60c4ca4e9eac3f3c94c441300";
        String decryptedText = AesCryptoUtils.decryptWithSaltIV(encryptedText, password, saltString, initVector);
        assertEquals(plainText, decryptedText); */
       
        String encryptedText = AesCryptoUtils.encryptWithGCM(plainText, password);
        System.out.println(">>"+encryptedText); 
    }
     
}
