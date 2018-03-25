package com.sujit.util;

import java.util.Random;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.RandomAccessFile;
import java.nio.channels.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;
import java.io.IOException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Tests to check methods of FileCompare class.
 * Currently this class uses JUnit4 TemporaryFolder rules for executing the tests.
 * In future it needs to be moved to JUnit5 extension based mechanism.
 */
public class FileCompareTest {
    
    private static final Logger logger = LogManager.getLogger(FileCompareTest.class.getName());
    
    String originalFileName = "sourceFile.data";
    String copiedFileName = "copyFile.data";
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    @Rule
    public TestName testName = new TestName();
    
    File sourceFile;
    File copyFile;
    
    @Before
    public void setupFiles() throws IOException {
        logger.debug("Creating temporary files for comparison...");
        byte[] buffer = new byte[1024 * 1024 * 10];
        /*
         * Create and fill up the source file with random binary data
         */
        sourceFile = tempFolder.newFile(originalFileName);
        try (FileOutputStream fos = new FileOutputStream(sourceFile)) {
            Random random = new Random();
            for (int i = 0; i < 16; i++) {
                random.nextBytes(buffer);
                fos.write(buffer);
            }
        }
        /*
         * Copy the source file to another file for comparison purpose.
         * This can later be done by something else like network copy etc.
         */
        copyFile = tempFolder.newFile(copiedFileName);
        try (FileChannel in = new FileInputStream(sourceFile).getChannel();
             FileChannel out = new FileOutputStream(copyFile).getChannel()) {
            in.transferTo(0, in.size(), out);
        }
        logger.debug("source and destination files are created for comparison.");
    }
    
    @After
    public void clearFiles() throws IOException {
        if(sourceFile.exists()){
            sourceFile.delete();
        }
        if(copyFile.exists()){
            copyFile.delete(); 
        }
        logger.debug("source and destination files are deleted.");
    }
    
    
    @Test
    public void testSimpleFileCompare() throws IOException {
        logger.info("Currently executing : {}", testName.getMethodName());
        BufferedInputStream f1 = new BufferedInputStream(new FileInputStream(sourceFile));
        BufferedInputStream f2 = new BufferedInputStream(new FileInputStream(copyFile));
        FileCompare.findDifference(f1, f2);
    }
    
    @Test
    public void testMemoryMappedFileCompare() throws IOException {
        logger.info("Currently executing : {}", testName.getMethodName()); 
        RandomAccessFile f1 = new RandomAccessFile(sourceFile, "r");
        RandomAccessFile f2 = new RandomAccessFile(copyFile, "r");
        FileCompare.findDifferenceInMemory(f1,f2); 
    }
    
}
