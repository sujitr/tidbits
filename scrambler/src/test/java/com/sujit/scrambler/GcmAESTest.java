package com.sujit.scrambler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
 
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.sujit.scrambler.electives.CryptoArchitecture;
import com.sujit.scrambler.electives.SymmetricCryptoChoices;
import com.sujit.scrambler.engines.GaloisCounterAESEngine;
import com.sujit.scrambler.engines.CryptoEngine;
import com.sujit.scrambler.factory.*;
import com.sujit.util.FileCompare;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.assertTrue;

/**
 * Test class for GaloisCounterAESEngine.java
 */
public class GcmAESTest {
	
	private final static Logger logger = LogManager.getLogger(GcmAESTest.class.getName());
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Test
    /**
     * Test to check GCM encryption and decryption. Password length is not important here as 
     * the password will be expanded to fit engine key length size after applying 
     * salt and iteration count. 
     * 
     * @throws IOException
     */
    public void testGaloisCounterAESFileScramble_128BitEngine() throws IOException {
        CryptoFactory factory = CryptoFactory.getCryptoFactory(CryptoArchitecture.SYMMETRIC);
        CryptoEngine engine = factory.createCryptoEngine(SymmetricCryptoChoices.AES_GCM_128);  
        String plainTextKey = "mypass"; 
        
        // configure the encryption engine
        engine.configEncrypt(plainTextKey.toCharArray());
        
        // get engine parameters
        String saltString = ((GaloisCounterAESEngine)engine).getSaltString();
        String initVectorString = ((GaloisCounterAESEngine)engine).getInitVector();
        String aadString = ((GaloisCounterAESEngine)engine).getAadString();
        
        logger.debug("|- Generated SALT = {}", saltString);
        logger.debug("|- Generated IV = {}", initVectorString);
        logger.debug("|- Generated AAD = {}", aadString); 
        
        // Create temporary files for testing
        final File testFile = tempFolder.newFile("tempFile.txt");
        final File encryptedFile = tempFolder.newFile("encryptedFile.txt");
        final File decryptedFile = tempFolder.newFile("decryptedFile.txt");
        
        // Write something to input temporary file
        FileUtils.writeStringToFile(testFile, "hello world! GCM here.", "ISO-8859-1");
        logger.debug("Wrote to temporary file, with contents '{}' ", FileUtils.readFileToString(testFile,"ISO-8859-1"));
        
        // create streams from the files, to be used for encryption engine
        FileInputStream in = FileUtils.openInputStream(testFile);
        FileOutputStream out = FileUtils.openOutputStream(encryptedFile);
        
        // encrypt the data
        engine.encrypt(in,out);
        in.close(); out.close();
        logger.debug("Encrypted file is having content as '{}' ", FileUtils.readFileToString(encryptedFile,"ISO-8859-1"));

        // configure the decryption engine        
        engine.configDecrypt(plainTextKey.toCharArray(), initVectorString, saltString, aadString);
        
        // create streams from the files, to be used for decryption engine
        FileInputStream in2 = FileUtils.openInputStream(encryptedFile);
        FileOutputStream out2 = FileUtils.openOutputStream(decryptedFile);
        
        // decrypt the data
        engine.decrypt(in2,out2);
        in2.close(); out2.close();
        logger.debug("Decrypted file content is '{}' ", FileUtils.readFileToString(decryptedFile,"ISO-8859-1"));
        
        // comparing the original input file with decrypted file
        logger.debug("comparing the input file with decrypted file...");
        BufferedInputStream str1 = new BufferedInputStream(new FileInputStream(testFile));
        BufferedInputStream str2 = new BufferedInputStream(new FileInputStream(decryptedFile));
        assertTrue(FileCompare.findDifference(str1 ,str2));
        str1.close();str2.close();
    }
}