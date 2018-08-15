package com.sujit.scrambler.engines;   

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.binary.Base64InputStream;
import java.io.IOException;
import javax.crypto.CipherInputStream;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidParameterSpecException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.security.AlgorithmParameters;
import javax.crypto.spec.IvParameterSpec; 

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sujit.scrambler.electives.KeySize;
import com.sujit.scrambler.utils.CryptoUtils;

/**
 * Stronger implementation of AES with CBC cipher mode and PKCS5Padding.
 * This approach provides decent enough security and guards against rainbow attacks.
 * Please do not use same password key frequently.
 * 
 * @author Sujit
 * @since 2018
 */
public class CbcAESEngine implements CryptoEngine {
    
    private final static Logger logger = LogManager.getLogger(CbcAESEngine.class.getName());
    
    private String initVectorString;
    private String saltString;
    private Cipher dCipher;
    private Cipher eCipher;
    
    private final int AES_KEY_SIZE;     		// derived key length
    private final int SALT_SIZE;        			// should be at-least 64 bits
    private final int ITERATION_COUNT;  // iteration count anything greater than 12288
    
    
    public CbcAESEngine(KeySize k){
    	super();
        AES_KEY_SIZE = k.getBitLenth();
        SALT_SIZE = 64;
        ITERATION_COUNT = 65536;
    }

	@Override
	public void configDecrypt(char[] key, String... extraParameters) {
		this.saltString = extraParameters[0];
		this.initVectorString = extraParameters[1];
		byte[] salt = null;
		byte[] iv = null;  
		try {
		    salt = Hex.decodeHex(saltString.toCharArray());
		    iv = Hex.decodeHex(initVectorString.toCharArray()); 
		    SecretKey secretKey = CryptoUtils.generateSecretKey_AES(key, salt, ITERATION_COUNT, AES_KEY_SIZE);
    	    dCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    	    dCipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
    	    Arrays.fill(key, '\u0000'); // clear sensitive data
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException| DecoderException | InvalidAlgorithmParameterException e) {  
			logger.error("|-- Error while configuring decryption cipher  - {}",e.getMessage(), e); 
	    }
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
    	    SecretKey secretKey = CryptoUtils.generateSecretKey_AES(key, salt, ITERATION_COUNT, AES_KEY_SIZE);
    	    /* Initialize the encryption cipher object */
            eCipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
            eCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            AlgorithmParameters params = eCipher.getParameters();
            initVector = params.getParameterSpec(IvParameterSpec.class).getIV();
            initVectorString = Hex.encodeHexString(initVector);
            saltString = Hex.encodeHexString(salt);
            Arrays.fill(key, '\u0000'); // clear sensitive data
            logger.debug("|-- Configuration for CBC encryption engine complete. " +
					"Please make a note of generated 'Salt' and 'IV' values.");
            printEngineParameters();
	    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException| InvalidParameterSpecException  e) {
	        logger.error("|-- Error while configuring encryption cipher  - {}",e.getMessage(), e); 
	    }

	}

	@Override
	public void decrypt(InputStream encryptedStream, OutputStream out) {
		try(CipherInputStream cin = new CipherInputStream(new Base64InputStream(encryptedStream), dCipher);) { 
    		logger.info("decrypting....");
    		IOUtils.copy(cin,out);
    		logger.info("decryption of streams complete.");
    	} catch (IOException e) {
    		logger.error("|- Error while decrypting streams - {}", e.getMessage(), e);
		}
	}

	@Override
	public void encrypt(InputStream in, OutputStream encryptedStream) {
		logger.debug("encrypting....");
	    try(Base64OutputStream b64os = new Base64OutputStream(encryptedStream);
	    		CipherInputStream cin = new CipherInputStream(in, eCipher)){
	    	IOUtils.copy(cin, b64os);
	    	logger.debug("encryption of streams complete");
	    }catch (IOException e) { 
            logger.error("|- Error while encrypting streams - {}", e.getMessage(), e);
        } 
	}
	
	public String getInitVector(){
	    return initVectorString;
	}
	
	public String getSaltString(){
	    return saltString;
	}

	private void printEngineParameters(){
		StringBuilder sb = new StringBuilder();
		sb.append("\n\t Init Vector - ").append(initVectorString).append("\n")
				.append("\t Salt - ").append(saltString).append("\n");

		logger.info(sb.toString());
	}

}
