package com.sujit.scrambler.engines;

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
import java.io.IOException; 
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
import javax.crypto.CipherInputStream;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Hex; 
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.binary.Base64InputStream; 

public class SimpleAESEngine implements CryptoEngine {
    private Cipher dCipher;
    private Cipher eCipher;
    private final int MAX_FILE_BUF;
    
    public SimpleAESEngine(){
        MAX_FILE_BUF = 1024;
    }
    
    public void configDecrypt(String plainTextKey){
        try {
			dCipher = Cipher.getInstance("AES");
			SecretKey secretKey = new SecretKeySpec(plainTextKey.getBytes(), "AES");
			dCipher.init(Cipher.DECRYPT_MODE, secretKey);
		}catch(InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e){
		    e.printStackTrace();
		}

    }
    
    public void configEncrypt(String plainTextKey) {
        try {
			eCipher = Cipher.getInstance("AES");
			SecretKey secretKey = new SecretKeySpec(plainTextKey.getBytes(), "AES");
			eCipher.init(Cipher.ENCRYPT_MODE, secretKey);
		}catch(InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e){
		    e.printStackTrace();
		}
    }
    
    public void encrypt(InputStream in, OutputStream out){
        int nread = 0;
        byte[] inbuf = new byte [MAX_FILE_BUF]; 
        /* you can also replace the below functionality 
            with a IOUTils copy from a CipherInputStream to Base64OutputStream */
        try (Base64OutputStream encryptedStream = new Base64OutputStream(out);) {
            while((nread = in.read(inbuf))>0){
                byte[] trimbuf = new byte [nread];
                for (int i = 0; i < nread; i++)
                    trimbuf[i] = inbuf[i];
                byte[] tmp = eCipher.update(trimbuf);
                encryptedStream.write(tmp);
            }
            byte[] finalBuf = eCipher.doFinal();
            if(finalBuf !=null){
                encryptedStream.write(finalBuf); 
                encryptedStream.flush();
            }
        } catch (IllegalBlockSizeException | BadPaddingException | IOException e) { 
            e.printStackTrace();    
        }

    }
    
    
	public void decrypt(InputStream encryptedStream, OutputStream out) {
	   int nread = 0;
	   byte [] inbuf = new byte [MAX_FILE_BUF];
	   try(CipherInputStream cin = new CipherInputStream(new Base64InputStream(encryptedStream), dCipher);) { 
	       while ((nread = cin.read (inbuf)) > 0 )
            {
                // create a buffer to write with the exact number of bytes read. Otherwise a short read fills inbuf with 0x0
                byte [] trimbuf = new byte [nread];
                for (int i = 0; i < nread; i++)
                    trimbuf[i] = inbuf[i];
    
                // write out the size-adjusted buffer
                out.write (trimbuf);
            }
            out.flush();
            // IOUtils.copy(cin,out);  // this can be also used instead
	   } catch(IOException e) { 
	       e.printStackTrace(); 
	   }
	}
}