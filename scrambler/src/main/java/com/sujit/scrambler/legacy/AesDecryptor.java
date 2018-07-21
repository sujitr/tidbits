package com.sujit.scrambler.legacy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
/**
 * This class takes a file containing AES encrypted text as input and attempts to 
 * extract & recreate the target file whose bytes first has been Base64 encoded
 * and then AES encrypted using a key. 
 * 
 * This key has to be 16 bytes (16 characters long) for this class. This is a simple
 * implementation of the encryption (of AES) with only a plaintext password. This is not 
 * a very strong system in face of known dictionary attacks for guessing the password.
 * For normal use cases this might suffice, but use with discretion.
 * 
 * After decrypting the text it creates the resulting file in the path provided.
 * <br><b>Please note, this class is not just an AES encrypted text to plain text 
 * class. It has custom manipulation.</b>
 * 
 * @author 
 *      Sujit
 *
 */
public class AesDecryptor {
    public static void main( String[] args ) {
    	// Parameters and key values
    	String keyValue = "0234567891234567";
    	String base64EncodedTextFilePath = "C:\\Users\\Sujit\\Desktop\\test-enc.txt";
    	String outputFilePath = "C:\\Users\\Sujit\\Desktop\\decrypted.zip";
    	String encryptedTextChunked = null;
    	File xFile = new File(base64EncodedTextFilePath);
    	System.out.println("|-- reading the encrypted base64 encoded text in the text file specified...");
    	try(BufferedReader br = new BufferedReader(new FileReader(xFile))) {
			StringBuilder sb = new StringBuilder();
			String s;
			while((s = br.readLine()) != null) {
				sb.append(s);
			}
			encryptedTextChunked = sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	System.out.println("|-- encrypted base64 text string fetch complete...attempting to decode...");
    	/*
    	 * This part expects the given encrypted string consists of chunked 76 character columns, which 
    	 * is needed for transporting the huge Base64 encoded strings via transport mechanisms like email.
    	 * So, after receiving such a chunked data, it first needs to be converted back into a single string before
    	 * attempting AES decryption. 
    	 */
    	String encryptedText = Joiner.on("").join(Splitter.on("\n").trimResults().split(encryptedTextChunked));
    	// decrypting the data
    	String decryptedText = AesCryptoUtils.decryptWithPlainTextKey(encryptedText, keyValue);
    	// decoding on Base64
    	byte[] decodedBuffer = Base64.decodeBase64(decryptedText);
    	System.out.println("|-- decoded....writing contents in output file...");
    	// writing the byte contents into the output file for reconstruction
    	try(OutputStream oos = new FileOutputStream(outputFilePath)){
    		oos.write(decodedBuffer);
    	}catch(FileNotFoundException fex){
    		fex.printStackTrace();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	System.out.println("|-- done");
    }
}
