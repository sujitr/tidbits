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
    
    private final int AES_KEY_SIZE;
    private final int SALT_SIZE;
    private final int IV_SIZE;
    private final int TAG_BIT_LENGTH;
    private final byte[] aadData;
    
    private static final String seedString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    public GaloisCounterAESEngine() {
        aadData = getRandomString(50).getBytes();
        AES_KEY_SIZE = 256;
        SALT_SIZE = 64;
        IV_SIZE = 96;
        TAG_BIT_LENGTH = 128;
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
	     * generating key based on IV and plainTextKey and then initiating the encryption cipher
	     * this is basically a PBKDF2 (Password based key derivation function). Encryption is
	     * performed with that generated key.
	     */
        SecretKey secretKey = null ;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    	    KeySpec spec = new PBEKeySpec(plainTextKey, salt, 65536, AES_KEY_SIZE * 8);
    	    SecretKey tempSecret = factory.generateSecret(spec);
    	    secretKey = new SecretKeySpec(tempSecret.getEncoded(),"AES");
    	    GCMParameterSpec gcmParamSpec = new GCMParameterSpec(TAG_BIT_LENGTH, iv) ;
            eCipher = Cipher.getInstance("AES/GCM/PKCS5Padding"); 
            eCipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParamSpec, new SecureRandom());
        } catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
            System.out.println("Key being request is for AES algorithm, but this cryptographic algorithm is not available in the environment "  + ex);
        }
    }
    
    @Override
    public void encrypt(InputStream in, OutputStream out) {
        
    }
    
    @Override
	public void decrypt(InputStream encryptedStream, OutputStream out) {
    
    }
    
    

}