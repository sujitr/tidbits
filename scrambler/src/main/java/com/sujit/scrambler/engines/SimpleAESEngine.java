package com.sujit.scrambler.engines;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.io.IOException; 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.CipherInputStream;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.binary.Base64InputStream;  

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sujit.scrambler.utils.CryptoUtils;

/**
 * Simple AES implementation, without any explicit cipher mode for blocks, 
 * without any explicit padding declaration, and without PBE KeySpec for
 * key length conformity. Provided key is directly used to generate AES key, so 
 * length of the provided key HAS TO BE either 16 byte or 32 byte.
 * 
 * By default EBC cipher mode is used, along with PKCS5Padding, which is inherently 
 * susceptible for replay attacks or known plaintext attacks. Please use with discretion.
 * 
 * @author Sujit
 * @since 2018
 */
public class SimpleAESEngine implements CryptoEngine {
	private final static Logger logger = LogManager.getLogger(SimpleAESEngine.class.getName());
	
    private Cipher dCipher;
    private Cipher eCipher;
    private final int MAX_FILE_BUF;
    
    public SimpleAESEngine(){
        MAX_FILE_BUF = 1024;
    }
    
    @Override
    public void configDecrypt(char[] plainTextKey, String... otherData){
        try {
			dCipher = Cipher.getInstance("AES");
			SecretKey secretKey = new SecretKeySpec(CryptoUtils.toBytes(plainTextKey), "AES");
			Arrays.fill(plainTextKey, '\u0000'); // clear sensitive data
			dCipher.init(Cipher.DECRYPT_MODE, secretKey);
		}catch(InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e){
		    logger.error("|- Error in configuring decryption {} ", e.getMessage(), e);
		}

    }
    
    @Override
    public void configEncrypt(char[] plainTextKey) {
        try {
			eCipher = Cipher.getInstance("AES");
			SecretKey secretKey = new SecretKeySpec(CryptoUtils.toBytes(plainTextKey), "AES");
			eCipher.init(Cipher.ENCRYPT_MODE, secretKey);
		}catch(InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e){
		    logger.error("|- Error in configuring encryption {} ",e.getMessage(), e);
		}
    }
    
    @Override
    public void encrypt(InputStream in, OutputStream out){
        int nread = 0;
        byte[] inbuf = new byte [MAX_FILE_BUF]; 
        /* you can also replace the below functionality 
            with a IOUtils copy from a CipherInputStream to Base64OutputStream */
        try (Base64OutputStream encryptedStream = new Base64OutputStream(out);) {
        	logger.debug("encrypting....");
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
            logger.debug("encryption of streams complete");
        } catch (IllegalBlockSizeException | BadPaddingException | IOException e) { 
            logger.error("error while encrypting streams {}", e);
        }

    }
    
    @Override
	public void decrypt(InputStream encryptedStream, OutputStream out) {
	   int nread = 0;
	   byte [] inbuf = new byte [MAX_FILE_BUF];
	   try(CipherInputStream cin = new CipherInputStream(new Base64InputStream(encryptedStream), dCipher);) { 
		   logger.debug("decrypting....");
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
            logger.debug("decryption of streams complete.");
	   } catch(IOException e) { 
	       logger.error("error while decrypting streams {}", e);
	   }
	}
}