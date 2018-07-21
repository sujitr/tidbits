package com.sujit.scrambler.legacy;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.InvalidAlgorithmParameterException;
import java.util.Base64;
import java.io.UnsupportedEncodingException;
import javax.crypto.spec.GCMParameterSpec ;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKeyFactory; 
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.IvParameterSpec; 

import org.apache.commons.codec.binary.Hex; 
import org.apache.commons.codec.DecoderException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Utility class to provide different modes for AES encryption. It provides methods 
 * which uses simple plain text key based approach as well as advanced salt and 
 * initialization vector based approach to guard against dictionary and rainbow attacks.
 * 
 * @author
 *      Sujit
 */
public class AesCryptoUtils {
	
	private static final Logger logger = LogManager.getLogger(AesCryptoUtils.class.getName());
	
	public static Cipher cipher;
	public final static int SALT_LEN = 8;
	private final int KEYLEN_BITS = 128;
    private final int ITERATIONS = 65536;
    
	
	/**
	 * Method to decrypt a given AES encrypted string with a plaintext key.
	 * No explicit cipher mode is provided in this method, hence default EBC mode is 
	 * applied here. Also padding mode is the default PKCS5Padding.
	 * Please use this method with discretion because of its relative weakness against
	 * replay attacks.
	 * 
	 * @param encryptedText
	 *         String which has been encrypted with AES 
	 * 
	 * @param key
	 *         String having plaintext key used for encryption
	 * 
	 * @return 
	 *         String containing decrypted sequence of characters
	 */
	public static String decryptWithPlainTextKey(String encryptedText, String key) {
		String decryptedText = null;
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		byte[] decryptedByte = null;
		try {
			cipher = Cipher.getInstance("AES");
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			decryptedByte = cipher.doFinal(encryptedTextByte);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException| BadPaddingException e) {
			// e.printStackTrace();
			String errorMessage = "Issue encountered while decrypting data. "+e.getMessage();
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage); 
		}
		decryptedText = new String(decryptedByte);
		return decryptedText;
	}
	
	/**
	 * Method to encrypt a given string with a plaintext key to an encrypted string. 
	 * No explicit cipher mode is provided in this method, hence default EBC mode is 
	 * applied here. Also padding mode is the default PKCS5Padding.
	 * Please use this method with discretion because of its relative weakness against
	 * replay attacks. Plaintext blocks generates identical cipher text blocks with 
	 * EBC.
	 * 
	 * 
	 * @param plainText
	 *         String having the normal text which needs to be encrypted
	 * 
	 * @param key
	 *         String having plaintext key to be used for encryption
	 * 
	 * @return
	 *         String containing encrypted sequence of characters
	 */
	public static String encryptWithPlainTextKey(String plainText, String key) {
		byte[] plainTextByte = plainText.getBytes();
		byte[] encryptedByte = null;
		try {
			cipher = Cipher.getInstance("AES");
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encryptedByte = cipher.doFinal(plainTextByte);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException| BadPaddingException e) {
			// e.printStackTrace();
			String errorMessage = "Issue encountered while encrypting data. "+e.getMessage();
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage); 
		}
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}
	
	/**
	 * Method to encrypt a given string with a plain text password. The key for the encryption is 
	 * derived from a salt and using initilation vector. This approach is more resistant to attacks.
	 * 
	 * Please Note: If you are going for a 256 bit key, please make sure the proper JCE Jurisdiction 
	 * library is available in classpath.
	 * 
	 */
	public static String encryptWithSaltIV(String plainText, String password) {
	    byte[] plainTextByte = null;
	    byte[] encryptedByte = null;
	    byte[] iv = null;
	    /* Create secure random salt value */
	    byte[] salt = new byte[SALT_LEN];
	    SecureRandom random = new SecureRandom();
	    random.nextBytes(salt);
	    try {
	        plainTextByte = plainText.getBytes("UTF-8");
	        /* Derive the key, given password and salt. */
    	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    	    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);  //The magic numbers 65536 and 256 are the key derivation iteration count and the key size, respectively.
    	    SecretKey tempSecret = factory.generateSecret(spec);
    	    SecretKey secretKey = new SecretKeySpec(tempSecret.getEncoded(),"AES");
    	    /* Encrypt the message. */
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            AlgorithmParameters params = cipher.getParameters();
            iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            encryptedByte = cipher.doFinal(plainTextByte);
	    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException| BadPaddingException| InvalidKeySpecException| InvalidParameterSpecException| UnsupportedEncodingException e) {
	        String errorMessage = "Issue encountered while encrypting data. "+e.getMessage();
	        logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage); 
	    }
	    Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		logger.info("Generated SALT for this encryption : {}", Hex.encodeHexString(salt));
		logger.info("Generated Initialization Vector : {}", Hex.encodeHexString(iv));
		return encryptedText;
	}
	
	
	public static String decryptWithSaltIV(String encryptedText, String password, String saltString, String ivString) { 
	    String decryptedText = null;
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		byte[] decryptedByte = null;
		byte[] salt = null;
		byte[] iv = null;  
		try {
		    salt = Hex.decodeHex(saltString.toCharArray());
		    iv = Hex.decodeHex(ivString.toCharArray()); 
		    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
		    SecretKey tempSecret = factory.generateSecret(spec);
    	    SecretKey secretKey = new SecretKeySpec(tempSecret.getEncoded(),"AES");
    	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    	    cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
    	    decryptedByte = cipher.doFinal(encryptedTextByte);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException| BadPaddingException| InvalidKeySpecException| DecoderException | InvalidAlgorithmParameterException e) {  
	        String errorMessage = "Issue encountered while decrypting data. "+e.getMessage();
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage); 
	    }
		decryptedText = new String(decryptedByte);
		return decryptedText;
	} 
	
	
	public static String encryptWithGCM(String plainText, String password) {
	    byte[] plainTextByte = null;
	    byte[] encryptedByte = null;
	    byte[] aadData = "random".getBytes() ;
	    /* Create secure random salt value */
	    byte[] iv = new byte[SALT_LEN];
	    SecureRandom random = new SecureRandom();
	    random.nextBytes(iv);
	    try {
	        plainTextByte = plainText.getBytes("UTF-8");
	        /* Derive the key, given password and salt. */
    	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    	    KeySpec spec = new PBEKeySpec(password.toCharArray(), iv, 65536, 256);  // need to remove iv and get salt back here
    	    SecretKey tempSecret = factory.generateSecret(spec);
    	    SecretKey secretKey = new SecretKeySpec(tempSecret.getEncoded(),"AES");
    	    /* Encrypt the message. */
    	    GCMParameterSpec gcmParamSpec = new GCMParameterSpec(128, iv) ;
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding"); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParamSpec, new SecureRandom());  
            cipher.updateAAD(aadData);
            encryptedByte = cipher.doFinal(plainTextByte);
	    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException| BadPaddingException| InvalidKeySpecException| InvalidAlgorithmParameterException | UnsupportedEncodingException e) { 
            e.printStackTrace(); 
	    }
	    Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		logger.info("Generated IV for this encryption : {}", Hex.encodeHexString(iv));
		logger.info("Generated AAD : {}", Hex.encodeHexString(aadData));
		return encryptedText;
	}
	
}
