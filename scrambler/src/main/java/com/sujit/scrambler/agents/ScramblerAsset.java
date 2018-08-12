package com.sujit.scrambler.agents;

import com.sujit.scrambler.engines.CryptoEngine;
import com.sujit.scrambler.factory.CryptoFactory;
import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScramblerAsset {
    public static void scramble(ScramblerMould scramblerMould) throws IOException {
        CryptoFactory cryptoFactory = CryptoFactory
                .getCryptoFactory(scramblerMould.getCryptoArchitecture());
        CryptoEngine cryptoEngine = cryptoFactory
                .createCryptoEngine(scramblerMould.getCryptoChoices());

        FileInputStream in = FileUtils.openInputStream(scramblerMould.getInputFilePath());
        FileOutputStream out = FileUtils.openOutputStream(scramblerMould.getOutputFilePath());

        switch (scramblerMould.getScramblerMission()){
            case ENCRYPT:cryptoEngine.configEncrypt(scramblerMould.getPassword());
                         /* figure out how to get generated salt, iv & aad values if any. */
                         cryptoEngine.encrypt(in,out);
                         break;
            case DECRYPT:cryptoEngine.configDecrypt(scramblerMould.getPassword());
                         /* figure out how to pass salt, iv & aad values if any. */
                         cryptoEngine.decrypt(in,out);
                         break;
        }

    }
}
