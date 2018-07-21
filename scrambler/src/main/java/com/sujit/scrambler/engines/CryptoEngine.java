package com.sujit.scrambler.engines;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

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
     * @param key 
     * 		    plaintext key provided by the user to set up decryption
     * @param extraParameters
     *  	 	other extra parameters like salt and initialization vector as needed. This
     *  		is a varargs parameter enabling multiple param values. Please ensure
     *  		proper sequencing while reading them. Should match with sequence 
     *  		of call.  
     */
    public void configDecrypt(char[] key, String...  extraParameters);
    
    /**
     * Method to set up the encryption process. It may help 
     * in forming a secure key or salt or initialization 
     * vector as required.
     * 
     * @param key
     *          plaintext key provided by the user to set up encryption
     */
    public void configEncrypt(char[] key);
    
    /**
     * Method to decrypt a given inputstream, after decryption has been
     * set up with the user provided key.
     * 
     * @param in
     *          input stream object which needs to be decrypted
     * @param out
     *          output stream object to which decrypted data is written
     *          out
     */
    public void decrypt(InputStream in, OutputStream out);
    
    /**
     * Method to encrypet a given inputstream, after encryption has been
     * set up with the user provided key.
     * 
     * @param in
     *          input stream object which needs to encrypted
     * @param out
     *          output stream object to which decrypted data is written
     *          out
     */
    public void encrypt(InputStream in, OutputStream out);
    
}