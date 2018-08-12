package com.sujit.scrambler.agents;


import com.sujit.scrambler.electives.CryptoArchitecture;
import com.sujit.scrambler.electives.CryptoChoices;
import com.sujit.scrambler.electives.KeySizes;
import java.io.File;

/**
 * A container for user specified choices upon which
 * the crypto engine will be forged.
 */
public class ScramblerMould {

    private ScramblerMission scramblerMission;
    private CryptoArchitecture cryptoArchitecture;
    private CryptoChoices cryptoChoices;
    private KeySizes keySize;
    private char[] password;
    private String saltString;
    private String initVectorString;
    private File inputFilePath;
    private File outputFilePath;

    public static class Builder {
        // required fields
        private final ScramblerMission mission;
        private final CryptoArchitecture arch;
        private final CryptoChoices choice;
        private final KeySizes size;
        private final char[] pwd;
        private final File inFile;
        private final File outFile;

        // optional fields
        private String salt;
        private String iv;

        public Builder(ScramblerMission mission, CryptoArchitecture arch,
                        CryptoChoices choice,
                        KeySizes size,
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
        inputFilePath = builder.inFile;
        outputFilePath = builder.outFile;
    }
}
