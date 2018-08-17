package com.sujit.scrambler.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Static utility class containing common methods used in all
 * cryptographic engines and configurations.
 * 
 * @author Sujit
 * @since 2018
 *
 */
public class CryptoUtils {
	/* Disabling instance creation and class extension */
	private CryptoUtils() {
		/* insurance in case the constructor is accidentally invoked from within the class */
		throw new AssertionError("This class is not intended to be instantiated!");
	}
	
	public static final String ALGO_AES = "AES";
	public static final int SALT_SIZE = 64;
	public static final int ITERATION_COUNT = 65536;
	public static final String SALT_TAG = "Salt";
	public static final String IV_TAG = "Initialization Vector";
	public static final String AAD_TAG = "AAD";

	
	
	/**
	 * Utility method to generate SecretKey from user provided password/passphrase. 
	 * This approach uses PBKDF2 (Password Based Key Derivation Function). 
	 * PBKDFs are computed by applying multiple iterations to a user-supplied password 
	 * using a pseudorandom function (prf) and an additional salt. 
	 *   
	 * @param key
	 * 	character array containing user provided password / passphrase
	 * @param salt
	 * 	byte array containing salt value 
	 * @param iterationCount
	 * 	integer denoting number of iterations on prf, minimum 10000 recommended
	 * @param keySize
	 * 	integer denoting intended length of the derived key (128 bits or 256 bits)
	 * @return SecretKey
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static SecretKey generateSecretKey_AES(char[] key, byte[] salt, int iterationCount, int keySize) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	    KeySpec spec = new PBEKeySpec(key, salt, iterationCount, keySize);
	    SecretKey tempSecret = factory.generateSecret(spec);
	    SecretKey secretKey = new SecretKeySpec(tempSecret.getEncoded(),"AES");
		return secretKey;
	}
	
	/**
     * Utility method for converting character array to byte array.
     * It is used to change input passwords to byte array whenever needed.
     * @param chars
     * 		password as character array
     * @return
     * 		byte array of the password
     */
    public static byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }
    
    /**
     * Determines if cryptography restrictions apply.
     * Restrictions apply if the value of {@link Cipher#getMaxAllowedKeyLength(String)} returns a value 
     * smaller than {@link Integer#MAX_VALUE} if there are any restrictions according to the JavaDoc of the method.
     * This method is used with the transform <code>"AES/CBC/PKCS5Padding"</code> as this is an often 
     * used algorithm that is <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#impl">
     * an implementation requirement for Java SE</a>.
     * 
     * <a href="https://stackoverflow.com/questions/7953567/checking-if-unlimited-cryptography-is-available/7954769#7954769">
     * Source of this method</a>
     * 
     * @return <code>true</code> if restrictions apply, <code>false</code> otherwise
     */
    public static boolean restrictedCryptography() {
        try {
            return Cipher.getMaxAllowedKeyLength("AES/CBC/PKCS5Padding") < Integer.MAX_VALUE;
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalStateException("The transform \"AES/CBC/PKCS5Padding\" is not available"
            		+ " (the availability of this algorithm is mandatory for Java SE implementations)", e);
        }
    }
}
