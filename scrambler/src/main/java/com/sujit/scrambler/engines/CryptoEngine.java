package com.sujit.scrambler.engines;

import java.io.InputStream;
import java.io.OutputStream;

public interface CryptoEngine {
    public void configDecrypt(String key);
    public void configEncrypt(String key);
    public void decrypt(InputStream in, OutputStream out);
    public void encrypt(InputStream in, OutputStream out);
}