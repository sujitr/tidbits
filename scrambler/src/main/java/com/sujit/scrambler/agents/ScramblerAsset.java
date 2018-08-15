package com.sujit.scrambler.agents;

import com.sujit.scrambler.engines.CryptoEngine;
import com.sujit.scrambler.factory.CryptoFactory;
import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class to perform activity based on the action chosen
 * by the user in order to get the job done.
 */
public class ScramblerAsset {
    /**
     * Method to call the engine's mode of action based on users choice
     *
     * @param scramblerMould
     * @throws IOException
     */
    public static void mobilize(ScramblerMould scramblerMould) throws IOException {
        CryptoFactory cryptoFactory = CryptoFactory
                .getCryptoFactory(scramblerMould.getCryptoArchitecture());
        CryptoEngine cryptoEngine = cryptoFactory
                .createCryptoEngine(scramblerMould.getCryptoChoices());

        FileInputStream in = FileUtils.openInputStream(scramblerMould.getInputFilePath());
        FileOutputStream out = FileUtils.openOutputStream(scramblerMould.getOutputFilePath());

        switch (scramblerMould.getScramblerMission()){
            case ENCRYPT:cryptoEngine.configEncrypt(scramblerMould.getPassword());
                         cryptoEngine.encrypt(in,out);
                         break;
            case DECRYPT:cryptoEngine.configDecrypt(scramblerMould.getPassword(),
                            scramblerMould.getSaltString(),
                            scramblerMould.getInitVectorString(),
                            scramblerMould.getAadString());
                         cryptoEngine.decrypt(in,out);
                         break;
        }

    }
}
