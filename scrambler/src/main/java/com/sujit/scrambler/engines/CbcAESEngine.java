package com.sujit.scrambler.engines;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Stronger implementation of AES with CBC cipher mode and PKCS5Padding.
 * This approach provides decent enough security and guards against rainbow attacks.
 * Please do not use same password key frequently.
 * 
 * @author Sujit
 *
 */
public class CbcAESEngine implements CryptoEngine {

	@Override
	public void configDecrypt(char[] key, String... extraParameters) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void configEncrypt(char[] key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void decrypt(InputStream in, OutputStream out) {
		// TODO Auto-generated method stub

	}

	@Override
	public void encrypt(InputStream in, OutputStream out) {
		// TODO Auto-generated method stub

	}

}
