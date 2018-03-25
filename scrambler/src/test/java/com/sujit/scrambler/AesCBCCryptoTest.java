package com.sujit.scrambler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AesCBCCryptoTest {
    @Test
    void testDecryptionWithSaltIV() {
        String plainText = "Hi all, this is a compilcated world";
        String password = "Z7eT12HwqBnW37hY";
        String encryptedText = "IMK7BpL+TWkvyc+oT44NTtVAozKb8xjxEoN50UiU801PKIS6MyowMdn9/43MinUt";
        String saltString = "8a69b7a94227b54c";
        String initVector = "0a83e5e60c4ca4e9eac3f3c94c441300";
        String decryptedText = AesCryptoUtils.decryptWithSaltIV(encryptedText, password, saltString, initVector);
        assertEquals(plainText, decryptedText); 
    }
}