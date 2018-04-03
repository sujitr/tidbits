package com.sujit.scrambler.electives; 

/**
 * Choice of the encryption architecture, for which an implementation from 
 * the factory would be made available.
 * 
 * Two choices are -
 * SYMMETRIC :  for symmetric encryption/decryption algorithm based cryptography. 
 *              These algorithms, can either operate in block mode (which works on fixed-size blocks of data) 
 *              or stream mode (which works on bits or bytes of data). They are commonly used for applications 
 *              like data encryption, file encryption and encrypting transmitted data in communication networks 
 * (like TLS, emails, instant messages, etc.). 
 * 
 * ASYMMETRIC : for asymmetric encryption/decryption algorithm based cryptography. 
 *              Unlike symmetric algorithms, which use the same key for both encryption and decryption operations,
 *              asymmetric algorithms use two separate keys for these two operations. These algorithms are used for 
 *              computing digital signatures and key establishment protocols. 
 */
public enum CryptoArchitecture {
    SYMMETRIC, ASYMMETRIC
}
