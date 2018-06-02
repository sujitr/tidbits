package com.sujit.scrambler.engines;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
    
    private String initVectorString;
    private String saltString;
    private Cipher dCipher;
    private Cipher eCipher;
    
    private final int AES_KEY_SIZE;     // derived key length
    private final int SALT_SIZE;        // should bea atleast 64 bits
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
        String suppliedInitVector = otherParams[0];
        String suppliedSalt = otherParams[1];
    }
    
    @Override
    public void configEncrypt(char[] plainTextKey){
    	logger.info("|-- Configuring GCM encryption engine...");
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
	     * Encryption is then performed with that generated key and initialization vector (IV).
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
            logger.info("|-- Configuration for GCM encryption engine complete. Please make a note of generated 'Salt' and 'IV' values.");
        } catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
            logger.error("|-- Error while configuring GCM engine - "+ex.getMessage(), ex);
        } finally{
        	initVectorString = Hex.encodeHexString(iv);
        	saltString = Hex.encodeHexString(salt);
        }
    }
    
    @Override
    public void encrypt(InputStream in, OutputStream out) {
    	try (Base64OutputStream encryptedStream = new Base64OutputStream(out);
    			CipherInputStream cin = new CipherInputStream(in, eCipher)) {
    		logger.info("encrypting....");
    		IOUtils.copy(cin, encryptedStream);
    		encryptedStream.flush();
    		logger.info("encryption of streams complete");
    	} catch (IOException e) {
    		logger.error("error while encrypting streams {}", e);
		}
    }
    
    @Override
	public void decrypt(InputStream encryptedStream, OutputStream out) {
    	try(CipherInputStream cin = new CipherInputStream(new Base64InputStream(encryptedStream), dCipher);) { 
    		logger.info("decrypting....");
    		IOUtils.copy(cin,out);
    		logger.info("decryption of streams complete.");
    	} catch (IOException e) {
    		logger.error("error while decrypting streams {}", e);
		}
    }
    
    /**
     * Method to return the generated salt and iv values for encryption
     */
    public void getEncryptionParameters(){
    	
    }
}