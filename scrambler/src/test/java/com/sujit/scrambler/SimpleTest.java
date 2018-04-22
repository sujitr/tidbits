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
import com.sujit.scrambler.engines.CryptoEngine;
import com.sujit.scrambler.factory.*;
import com.sujit.util.FileCompare;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.assertTrue;

public class SimpleTest {
	
	private final static Logger logger = LogManager.getLogger(SimpleTest.class.getName());
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Test
    public void testSimpleAESFileScrambleWith16ByteKey() throws IOException {
        CryptoFactory factory = CryptoFactory.getCryptoFactory(CryptoArchitecture.SYMMETRIC);
        CryptoEngine engine = factory.createCryptoEngine(SymmetricCryptoChoices.AES_DEFAULT);  
        String plainTextKey = "Z7eT12HwqBnW37hY"; // testing with 16 byte key
        engine.configEncrypt(plainTextKey.toCharArray());
        
        // Create temporary files for testing
        final File testFile = tempFolder.newFile("tempFile.txt");
        final File encryptedFile = tempFolder.newFile("encryptedFile.txt");
        final File decryptedFile = tempFolder.newFile("decryptedFile.txt");
        
        // Write something to input temporary file
        FileUtils.writeStringToFile(testFile, "hello world", "ISO-8859-1");
        logger.debug("Wrote to temporary file, with contents '{}' ", FileUtils.readFileToString(testFile,"ISO-8859-1"));
        
        FileInputStream in = FileUtils.openInputStream(testFile);
        FileOutputStream out = FileUtils.openOutputStream(encryptedFile);
        
        engine.encrypt(in,out);
        
        in.close(); out.close();
        logger.debug("Encrypted file is having content as '{}' ", FileUtils.readFileToString(encryptedFile,"ISO-8859-1"));
        
        engine.configDecrypt(plainTextKey.toCharArray());
        
        FileInputStream in2 = FileUtils.openInputStream(encryptedFile);
        FileOutputStream out2 = FileUtils.openOutputStream(decryptedFile);
        
        engine.decrypt(in2,out2);
        
        in2.close(); out2.close();
        logger.debug("Decrypted file content is '{}' ", FileUtils.readFileToString(decryptedFile,"ISO-8859-1"));
        logger.debug("comparing the input file with decrypted file...");
        BufferedInputStream str1 = new BufferedInputStream(new FileInputStream(testFile));
        BufferedInputStream str2 = new BufferedInputStream(new FileInputStream(decryptedFile));
        assertTrue(FileCompare.findDifference(str1 ,str2));
        
        str1.close();str2.close();
    }
}