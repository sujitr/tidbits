package com.sujit.scrambler.electives; 

/**
 * Products which are available from SymmetricCryptoFactory.
 * 
 * AES_DEFAULT  : This is the default implementation of AES, without any explicit mention of 
 *                block mode of padding scheme. So, by default, EBC block mode is selected 
 *                along with default PKCS5Padding.
 * 
 * AES_CBC      : Here block mode is CBC and it uses PKCS5Padding. Apart from that this implementation 
 *                also uses Salt and Initialization Vector to create/derive the key from a 
 *                given plaintext password.
 * 
 * AES_CTR      :
 * AES_GCM      :
 * AES_CCM      
 */
public enum SymmetricCryptoChoices implements CryptoChoices {
    AES_DEFAULT, 
    AES_CBC_128, 
    AES_CBC_256, 
    AES_GCM_128, 
    AES_GCM_256, 
    AES_CTR, 
    AES_CCM
}