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
    AES_DEFAULT { public String toString() { return "AES Default with chosen key"; }},
    AES_CBC_128 { public String toString() { return "AES CBC with 128 bit Key derived by PBKDF"; }},
    AES_CBC_256 { public String toString() { return "AES CBC with 256 bit Key derived by PBKDF"; }},
    AES_GCM_128 { public String toString() { return "AES GCM with 128 bit Key derived by PBKDF"; }},
    AES_GCM_256 { public String toString() { return "AES GCM with 256 bit Key derived by PBKDF"; }},
    AES_CTR { public String toString() { return "AES CTR"; }},
    AES_CCM { public String toString() { return "AES CCM"; }}
}