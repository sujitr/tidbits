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



public class AesCryptoUtils {
	
	public static Cipher cipher;
	
	public static String decrypt(String encryptedText, String key) {
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
			e.printStackTrace();
		}
		decryptedText = new String(decryptedByte);
		return decryptedText;
	}
	
	public static String encrypt(String plainText, String key) {
		byte[] plainTextByte = plainText.getBytes();
		byte[] encryptedByte = null;
		try {
			cipher = Cipher.getInstance("AES");
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encryptedByte = cipher.doFinal(plainTextByte);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException| BadPaddingException e) {
			e.printStackTrace();
		}
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}
	
}
