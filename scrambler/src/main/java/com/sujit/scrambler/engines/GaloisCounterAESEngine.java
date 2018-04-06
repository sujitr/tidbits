package com.sujit.scrambler.engines;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.io.OutputStream;

public class GaloisCounterAESEngine implements CryptoEngine {
    
    private String initVector;
    private Cipher dCipher;
    private Cipher eCipher;
    
    private final int AES_KEY_SIZE;
    private final int SALT_SIZE;
    private final int IV_SIZE;
    private final int TAG_BIT_LENGTH;
    private final byte[] aadData;
    
    static final String seedString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    public GaloisCounterAESEngine() {
        aadData = getRandomString(50).getBytes();
        AES_KEY_SIZE = 256;
        IV_SIZE = 96;
        TAG_BIT_LENGTH = 128;
    }
    
    private String getRandomString(int lengthOfString){
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for( int i = 0; i < len; i++ ) 
            sb.append(seedString.charAt(rnd.nextInt(seedString.length())));
        return sb.toString();
    }
    
    @Override
    public void configDecrypt(String plainTextKey){
        
    }
    
    @Override
    public void configEncrypt(String plainTextKey){
        // Generating salt
        byte[] salt = new byte[SALT_SIZE] ; // Should be atleast 64 bits
        SecureRandom secRandom = new SecureRandom() ;
        secRandom.nextBytes(salt) ; // self-seeded randomizer for salt
        
        // Generating IV
        byte[] iv = new byte[IV_SIZE];
	    SecureRandom random = new SecureRandom();
	    random.nextBytes(iv);
        
        // generating key based on IV and plainTextKey and then initiating the encryption cipher
        // this is basically a PBKDF2 (Password based key derivation function)
        SecretKey secretKey = null ;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    	    KeySpec spec = new PBEKeySpec(plainTextKey.toCharArray(), salt, 65536, 256 * 8);
    	    SecretKey tempSecret = factory.generateSecret(spec);
    	    secretKey = new SecretKeySpec(tempSecret.getEncoded(),"AES");
    	    GCMParameterSpec gcmParamSpec = new GCMParameterSpec(TAG_BIT_LENGTH, iv) ;
            eCipher = Cipher.getInstance("AES/GCM/PKCS5Padding"); 
            eCipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParamSpec, new SecureRandom());
        } catch(NoSuchAlgorithmException noSuchAlgoExc) {
            System.out.println("Key being request is for AES algorithm, but this cryptographic algorithm is not available in the environment "  + noSuchAlgoExc);
        }

        
        
    }
    
    @Override
    public void encrypt(InputStream in, OutputStream out) {
        
    }
    
    @Override
	public void decrypt(InputStream encryptedStream, OutputStream out) {
    
    }
    
    

}