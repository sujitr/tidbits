package com.sujit.scrambler;

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

public class AesDecryptor {
    public static void main( String[] args ) {
    	String keyValue = "0234567891234567";
    	String base64EncodedTextFilePath = "C:\\Users\\Sujit\\Desktop\\test-enc.txt";
    	String outputFilePath = "C:\\Users\\Sujit\\Desktop\\decrypted.zip";
    	String encryptedTextChunked = null;
    	File xFile = new File(base64EncodedTextFilePath);
    	System.out.println("|-- reading the encrypted base64 encoded text in the text file specified...");
    	try {
			FileReader fr = new FileReader(xFile);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder sb = new StringBuilder();
			String s;
			while((s = br.readLine()) != null) {
				sb.append(s);
			}
			fr.close(); 
			br.close();
			encryptedTextChunked = sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	System.out.println("|-- encrypted base64 text string fetch complete...attempting to decode...");
    	String encryptedText = Joiner.on("").join(Splitter.on("\n").trimResults().split(encryptedTextChunked));
    	String decryptedText = AesCryptoUtils.decrypt(encryptedText, keyValue);
    	byte[] decodedBuffer = Base64.decodeBase64(decryptedText);
    	System.out.println("|-- decoded....writing contents in zip file...");
    	try{
    		OutputStream oos = new FileOutputStream(outputFilePath);
    		oos.write(decodedBuffer);
    		oos.close();
    	}catch(FileNotFoundException fex){
    		fex.printStackTrace();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	System.out.println("|-- done");
    }
}
