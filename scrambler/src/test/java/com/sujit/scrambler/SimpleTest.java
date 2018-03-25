package com.sujit.scrambler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
 
import junit.framework.Assert;
 
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.sujit.scrambler.factory.*;
import com.sujit.util.FileCompare;

public class SimpleTest {
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    @Test
    public void testSimpleAESFactoryProduct() throws IOException {
        CryptoFactory factory = CryptoFactory.getCryptoFactory(CryptoArchitecture.SYMMETRIC);
        CryptoEngine engine = factory.createCryptoEngine(SymmetricCryptoChoices.AES_DEFAULT);  
        String plainTextKey = "Z7eT12HwqBnW37hY";
        engine.configEncrypt(plainTextKey);
        
        // Create a temporary file.
        final File tempFile = tempFolder.newFile("tempFile.txt");
        final File tempFile2 = tempFolder.newFile("encryptedFile.txt");
        final File tempFile3 = tempFolder.newFile("decryptedFile.txt");
        
        // Write something to it.
        FileUtils.writeStringToFile(tempFile, "hello world");
        System.out.println(" ::::: >>>> :: "+FileUtils.readFileToString(tempFile,"ISO-8859-1"));
        
        FileInputStream in = FileUtils.openInputStream(tempFile);
        FileOutputStream out = FileUtils.openOutputStream(tempFile2);
        
        engine.encrypt(in,out);
        
        in.close(); out.close();
        System.out.println(" ::::: >>>> :: "+FileUtils.readFileToString(tempFile2,"ISO-8859-1"));
        
        engine.configDecrypt(plainTextKey);
        
        FileInputStream in2 = FileUtils.openInputStream(tempFile2);
        FileOutputStream out2 = FileUtils.openOutputStream(tempFile3);
        
        engine.decrypt(in2,out2);
        
        in2.close(); out2.close();
        System.out.println(" ::::: >>>> :: "+FileUtils.readFileToString(tempFile3,"ISO-8859-1"));
        
        BufferedInputStream str1 = new BufferedInputStream(new FileInputStream(tempFile));
        BufferedInputStream str2 = new BufferedInputStream(new FileInputStream(tempFile3));
        Assert.assertTrue(FileCompare.findDifference(str1 ,str2));
        
        str1.close();str2.close();
        
    }
}