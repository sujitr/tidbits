package com.sujit.scrambler.legacy;

import org.junit.jupiter.api.Test;

import com.sujit.scrambler.legacy.AesCryptoUtils;

import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Disabled;

public class AesCBCCryptoTest {
    @Test
    // @Disabled
    /**
     * This test method needs Java Unlimited Key Strength Jurisdiction extension installed in the target system.
     * Please disable it if its not available to avoid test failing. 
     */
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