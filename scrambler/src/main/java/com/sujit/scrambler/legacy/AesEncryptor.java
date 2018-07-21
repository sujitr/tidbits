package com.sujit.scrambler.legacy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * This class takes a file as an input, first converts its bytes into a Base64 string and
 * then encrypts the Base64 encoded string using AES encryption standard and a 
 * given key. 
 * 
 * This key has to be 16 bytes (16 characters long) for this class. This is a simple
 * implementation of the encryption (of AES) with only a plaintext password. This is not 
 * a very strong system in face of known dictionary attacks for guessing the password.
 * For normal use cases this might suffice, but use with discretion.
 * 
 * After encrypting it writes the chunked string into a file as provided.
 * <br><b>Please note, this class is not just a plain text to AES encrypted 
 * text class. It has custom manipulation.</b>
 * 
 * @author 
 *      Sujit
 *
 */
public class AesEncryptor {

	public static void main(String[] args) {
		// Parameters and key values
		String inputFilePath = "";
		String encryptedOutputFilePath = "";
		String keyValue = "";
		String base64String = null;
		String encryptedBase64EncodedText = null;
		
		//reading the file into the byte array
		File zfile = new File(inputFilePath);
		byte[] buffer = null;
		try(InputStream ios = new FileInputStream(zfile)) {
			buffer = IOUtils.toByteArray(ios);
		}catch(IOException ex){
			ex.printStackTrace();
		} 
		// convert the read byte array from file to a Base64 encoded string
		base64String = Base64.encodeBase64String(buffer);
		// encrypt that Base64 encoded string using AES
		encryptedBase64EncodedText = AesCryptoUtils.encryptWithPlainTextKey(base64String, keyValue);
		/*
		 * In case of very large Base64 encoded strings its required to 
		 * break the string into 76 character line chunks which helps to fit 
		 * the entire string in a transport mechanism like email. Otherwise
		 * one huge Base64 encoded string will not fit into an email body. 
		 */
		String chunkedData = Joiner.on("\n").join(Splitter.fixedLength(76).split(encryptedBase64EncodedText));
		// write the encrypted data into a file
		try(FileWriter fw = new FileWriter(new File(encryptedOutputFilePath))){
			fw.write(chunkedData);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
