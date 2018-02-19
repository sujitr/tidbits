package com.sujit.util;

import java.io.BufferedInputStream;
import java.io.IOException;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Utility class to compare two files.
 */
public class FileCompare {
   
    public FileCompare(){
        
    }
    
    /**
     * This method uses traditional match approach which compares byte sequence one after another. 
     * This method usually takes longer as bytes are read sequentially from the file system (via a buffer). 
     * 
     * @param fis1 - BufferedInputStream of first file
     * @param fis2 - BufferedInputStream of second file
     */
    public static void findDifference(BufferedInputStream fis1, BufferedInputStream fis2) throws IOException {
        long start = System.nanoTime();
        int b1 = 0, b2 = 0, pos = 1; 
        while(b1 != -1 && b2 != -1){
            if(b1 != b2){
                System.out.println("Files differ at position :"+pos);
            }
            pos++;
            b1 = fis1.read();
            b2 = fis2.read();
        }
        if(b1 != b2){
            System.out.println("Comparison complete. Files have different length");
        } else {
            System.out.println("Comparison complete. Files are identical.");
        }
        fis1.close();
        fis2.close();
        long end = System.nanoTime();
        System.out.println("Execution time: "+ (end - start)/1000000 + " ms");
    }
    
    /**
     * This method uses memory mapped files for comparison, which results is faster comparison. 
     * 
     * @param file1 - RandomAccessFile reference of first file
     * @param file2 - RandomAccessFile reference of second file
     */
    public static void findDifferenceInMemory(RandomAccessFile file1, RandomAccessFile file2) throws IOException {
        long start = System.nanoTime();
        FileChannel fch1 = file1.getChannel();
        FileChannel fch2 = file2.getChannel();
        if(fch1.size()!=fch2.size()){
           System.out.println("Files have different length. Not possible to compare.");
           return;
        }
        long size = fch1.size();
        ByteBuffer buf1 = fch1.map(FileChannel.MapMode.READ_ONLY, 0L, size);
        ByteBuffer buf2 = fch2.map(FileChannel.MapMode.READ_ONLY, 0L, size);
        for(int pos = 0; pos < size; pos++){
            if(buf1.get(pos) != buf2.get(pos)){
                System.out.println("Files differ at position: "+pos);
                return;
            }
        }
        System.out.println("Files are identical.");
        long end = System.nanoTime();
        System.out.println("Execution time: "+ (end - start)/1000000 + " ms");
    }
}
