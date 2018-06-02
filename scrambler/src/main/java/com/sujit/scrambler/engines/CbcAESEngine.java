package com.sujit.scrambler.engines;   

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.binary.Base64InputStream;
import java.io.IOException;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidParameterSpecException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.io.UnsupportedEncodingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.AlgorithmParameters;
import javax.crypto.spec.IvParameterSpec; 

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Stronger implementation of AES with CBC cipher mode and PKCS5Padding.
 * This approach provides decent enough security and guards against rainbow attacks.
 * Please do not use same password key frequently.
 * 
 * @author Sujit
 *
 */
public class CbcAESEngine implements CryptoEngine {
    
    private final static Logger logger = LogManager.getLogger(CbcAESEngine.class.getName());
    
    private String initVectorString;
    private String saltString;
    private Cipher dCipher;
    private Cipher eCipher;
    
    private final int AES_KEY_SIZE;     // derived key length
    private final int SALT_SIZE;        // should be atleast 64 bits
    private final int IV_SIZE;          // initialization vector, should be atleast 96 bits
    private final int ITERATION_COUNT;  // iteration count anything greater than 12288
    
    
    public CbcAESEngine(){
        AES_KEY_SIZE = 256;
        SALT_SIZE = 64;
        IV_SIZE = 96;
        ITERATION_COUNT = 65536;
    }

	@Override
	public void configDecrypt(char[] key, String... extraParameters) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void configEncrypt(char[] key) {
		// Generating salt
        byte[] salt = new byte[SALT_SIZE] ;
        SecureRandom secRandom = new SecureRandom() ;
        secRandom.nextBytes(salt) ; // self-seeded randomizer for salt
        
        // Initializing InitVector
        byte[] initVector = null;
        
        try {
	        /* Derive the key, given password and salt */
    	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    	    KeySpec spec = new PBEKeySpec(key, salt, ITERATION_COUNT, AES_KEY_SIZE);
    	    SecretKey tempSecret = factory.generateSecret(spec);
    	    SecretKey secretKey = new SecretKeySpec(tempSecret.getEncoded(),"AES");
    	    /* Initialize the encryption cipher object */
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            AlgorithmParameters params = cipher.getParameters();
            initVector = params.getParameterSpec(IvParameterSpec.class).getIV();
            initVectorString = Hex.encodeHexString(initVector);
            saltString = Hex.encodeHexString(salt);
	    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException| InvalidParameterSpecException  e) {
	        logger.error("|-- Error while configuring encryption cipher  - " + e.getMessage(), e); 
	    }

	}

	@Override
	public void decrypt(InputStream in, OutputStream out) {
		// TODO Auto-generated method stub

	}

	@Override
	public void encrypt(InputStream in, OutputStream encryptedStream) {
	    logger.debug("encrypting....");
		try(CipherOutputStream cout = new CipherOutputStream(new Base64OutputStream(encryptedStream), eCipher)){
		    IOUtils.copy(in,cout);
		    logger.debug("encryption of streams complete");
		} catch (IOException e) { 
            logger.error("error while encrypting streams {}", e);
        } 
	}
	
	public String getInitVector(){
	    return initVectorString;
	}
	
	public String getSaltString(){
	    return saltString;
	}

}
