package com.sujit.scrambler.engines;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface outlining base functionalities of 
 * a cryptographic product. 
 * 
 * Implementers would need to provide specific 
 * algorithm based definition for these 
 * functionalities.
 * 
 * @author Sujit
 * @since 2018
 */
public interface CryptoEngine {
    /**
     * Method to set up the decryption process. It may help 
     * in forming a secure key or salt or initialization 
     * vector as required.
     * 
     * @param String
     *      plaintext key provided by the user to set up decryption
     */
    public void configDecrypt(String key);
    
    /**
     * Method to set up the encryption process. It may help 
     * in forming a secure key or salt or initialization 
     * vector as required.
     * 
     * @param String
     *          plaintext key provided by the user to set up encryption
     */
    public void configEncrypt(String key);
    
    /**
     * Method to decrypt a given inputstream, after decryption has been
     * set up with the user provided key.
     * 
     * @param InputStream
     *          input stream object which needs to be decrypted
     * @param OutputStream
     *          output stream object to which decrypted data is written
     *          out
     */
    public void decrypt(InputStream in, OutputStream out);
    
    /**
     * Method to encrypet a given inputstream, after encryption has been
     * set up with the user provided key.
     * 
     * @param InputStream
     *          input stream object which needs to encrypted
     * @param OutputStream
     *          output stream object to which decrypted data is written
     *          out
     */
    public void encrypt(InputStream in, OutputStream out);
}