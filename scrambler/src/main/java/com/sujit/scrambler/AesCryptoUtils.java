package com.sujit.scrambler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * Utility class to provide different modes for AES encryption. It provides methods 
 * which uses simple plaintext key based approach as well as advanced salt and 
 * initialization vector based approach to guard against dictionary and rainbow attacks.
 * 
 * @author
 *      Sujit
 */
public class AesCryptoUtils {
	
	public static Cipher cipher;
	
	/**
	 * Method to decrypt a given AES encrypted string with a plaintext key.
	 * No explicit cipher mode is provided in this method, hence default EBC mode is 
	 * applied here. 
	 * Also padding mode is the default PKCS5Padding.
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
			System.err.println(errorMessage);
			throw new IllegalArgumentException(errorMessage); 
		}
		decryptedText = new String(decryptedByte);
		return decryptedText;
	}
	
	/**
	 * Method to encrypt a given string with a plaintext key to an encrypted string. 
	 * No explicit cipher mode is provided in this method, hence default EBC mode is 
	 * applied here. 
	 * Also padding mode is the default PKCS5Padding.
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
			System.err.println(errorMessage);
			throw new IllegalArgumentException(errorMessage); 
		}
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}
	
}
