package com.sujit.scrambler.engines;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class GaloisCounterAESEngine implements CryptoEngine {
	
	private final static Logger logger = LogManager.getLogger(GaloisCounterAESEngine.class.getName());
    
    private String initVector;
    private Cipher dCipher;
    private Cipher eCipher;
    
    private final int AES_KEY_SIZE;     // derived key length
    private final int SALT_SIZE;        // should be atleast 64 bits
    private final int IV_SIZE;          // initialization vector, should be atleast 96 bits
    private final int TAG_BIT_LENGTH;
    private final int ITERATION_COUNT;  // iteration count anything greater than 12288
    private final byte[] aadData;
    
    private static final String seedString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    public GaloisCounterAESEngine() {
        aadData = getRandomString(50).getBytes();
        AES_KEY_SIZE = 256;
        SALT_SIZE = 64;
        IV_SIZE = 96;
        TAG_BIT_LENGTH = 128;
        ITERATION_COUNT = 65536;
    }
    
    private String getRandomString(int lengthOfString){
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(lengthOfString);
        for( int i = 0; i < lengthOfString; i++ ) 
            sb.append(seedString.charAt(rnd.nextInt(seedString.length())));
        return sb.toString();
    }
    
    @Override
    public void configDecrypt(char[] plainTextKey, String... otherParams){
        
    }
    
    @Override
    public void configEncrypt(char[] plainTextKey){
        // Generating salt
        byte[] salt = new byte[SALT_SIZE] ;
        SecureRandom secRandom = new SecureRandom() ;
        secRandom.nextBytes(salt) ; // self-seeded randomizer for salt
        
        // Generating IV
        byte[] iv = new byte[IV_SIZE];
	    SecureRandom random = new SecureRandom();
	    random.nextBytes(iv);
        
	    /* 
	     * Generating key based on salt and plainTextKey.
	     * This is basically a PBKDF2 (Password based key derivation function). 
	     * 
	     * Encryption is then performed with that generated key and initilaization vector (IV).
	     */
        SecretKey secretKey = null ; 
        try {
    	    KeySpec spec = new PBEKeySpec(plainTextKey, salt, ITERATION_COUNT, AES_KEY_SIZE * 8);
    	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    	    SecretKey tempSecret = factory.generateSecret(spec);
    	    secretKey = new SecretKeySpec(tempSecret.getEncoded(),"AES");
    	    GCMParameterSpec gcmParamSpec = new GCMParameterSpec(TAG_BIT_LENGTH, iv) ;
            eCipher = Cipher.getInstance("AES/GCM/PKCS5Padding"); 
            eCipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParamSpec, new SecureRandom()); 
            eCipher.updateAAD(aadData);
        } catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
            logger.error("|-- Error while encrypting data - "+ex.getMessage(), ex);
        }
    }
    
    @Override
    public void encrypt(InputStream in, OutputStream out) {
        
    }
    
    @Override
	public void decrypt(InputStream encryptedStream, OutputStream out) {
    
    }
    
    

}