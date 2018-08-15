package com.sujit.scrambler.agents;


import com.sujit.scrambler.electives.CryptoArchitecture;
import com.sujit.scrambler.electives.CryptoChoices;
import com.sujit.scrambler.electives.KeySize;
import java.io.File;

/**
 * A container for user specified choices upon which
 * the crypto engine will be forged.
 */
public class ScramblerMould {

    private ScramblerMission scramblerMission;
    private CryptoArchitecture cryptoArchitecture;
    private CryptoChoices cryptoChoices;
    private KeySize keySize;
    private char[] password;
    private String saltString;
    private String initVectorString;
    private String aadString;
    private File inputFilePath;
    private File outputFilePath;

    public ScramblerMission getScramblerMission() {
        return scramblerMission;
    }

    public CryptoArchitecture getCryptoArchitecture() {
        return cryptoArchitecture;
    }

    public CryptoChoices getCryptoChoices() {
        return cryptoChoices;
    }

    public KeySize getKeySize() {
        return keySize;
    }

    public char[] getPassword() {
        return password;
    }

    public String getSaltString() {
        return saltString;
    }

    public String getInitVectorString() {
        return initVectorString;
    }

    public String getAadString() {
        return  aadString;
    }

    public File getInputFilePath() {
        return inputFilePath;
    }

    public File getOutputFilePath() {
        return outputFilePath;
    }

    public static class Builder {
        // required fields
        private final ScramblerMission mission;
        private final CryptoArchitecture arch;
        private final CryptoChoices choice;
        private final KeySize size;
        private final char[] pwd;
        private final File inFile;
        private final File outFile;

        // optional fields
        private String salt;
        private String iv;
        private String aad;

        public Builder(ScramblerMission mission, CryptoArchitecture arch,
                        CryptoChoices choice,
                        KeySize size,
                        char[] pwd,
                        File inFile,
                        File outFile) {
            this.mission = mission;
            this.arch = arch;
            this.choice = choice;
            this.size = size;
            this.pwd = pwd;
            this.inFile = inFile;
            this.outFile = outFile;
        }

        public Builder salt(String value){
            salt = value;
            return this;
        }

        public Builder initVector(String value){
            iv = value;
            return this;
        }

        public Builder aadString(String value){
            aad = value;
            return this;
        }

        public ScramblerMould build(){
            return new ScramblerMould(this);
        }
    }

    private ScramblerMould(Builder builder){
        scramblerMission = builder.mission;
        cryptoArchitecture = builder.arch;
        cryptoChoices = builder.choice;
        keySize = builder.size;
        password = builder.pwd;
        saltString = builder.salt;
        initVectorString = builder.iv;
        aadString = builder.aad;
        inputFilePath = builder.inFile;
        outputFilePath = builder.outFile;
    }
}
