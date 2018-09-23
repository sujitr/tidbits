package com.sujit.scrambler.agents;

import com.sujit.scrambler.electives.CryptoArchitecture;
import com.sujit.scrambler.electives.CryptoChoices;
import com.sujit.scrambler.electives.KeySize;
import com.sujit.scrambler.electives.SymmetricCryptoChoices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.sujit.scrambler.utils.OpUtils.*;
import static com.sujit.scrambler.utils.CryptoUtils.*;

import java.io.Console;
import java.io.File;
import java.io.IOException;

/**
 * This class executes the scrambler for a user. This class is meant
 * to be called from the JAR. It takes input from user about the
 * various choices for the task and then calls the appropriate engines.
 * 
 * Typically the choices which user needs to mention for any operation she 
 * wishes to perform are as below 
 *      <ul>
 *          <li>Whether its encryption or decryption operation</li>
 *          <li>Whats the architecture, symmetric or asymmetric</li>
 *          <li>Whats the preferred key size</li>
 *          <li>Whats the algo for the architecture</li>
 *          <li>Password / Passphrase</li>
 *          <li>If its a decryption operation then salt, iv & aad values, if applicable</li>
 *          <li>Input file path</li>
 *          <li>Output file path</li>
 *      </ul>
 * <br>
 * 
 *
 * @author Sujit
 * @since 2018
 */
public class ScramblerOperative {

    private final static Logger logger = LogManager.getLogger(ScramblerOperative.class);

    public static void main(String[] args) {

        ScramblerMission scramblerMission;
        ScramblerMould scramblerMould;
        CryptoArchitecture architecture;
        CryptoChoices cryptoChoices;
        KeySize keySize;
        char[] password;
        String saltString = null;
        String initVectorString = null;
        String aadString = null;
        File inputFile;
        File outputFile;
        if (!isConsoleAvailable()) {
            System.out.println("Unable to get hold of a console. Exiting...");
            return;
        }
        boolean isUserReady = false;
        do {
            /* first read the mission objective */
            scramblerMission = getUserMission();
            checkForExit(scramblerMission);
            /* second read the choice of crypto platform */
            architecture = getUserArchitecture();
            checkForExit(architecture);
            /* third read the choice of key size, based upon choice of crypto platform */
            keySize = getKeyLength(architecture);
            checkForExit(keySize);
            /* fourth read the choice of type of cipher implementation, based upon above two choices */
            cryptoChoices = getUserCryptoChoice(architecture, keySize);
            checkForExit(cryptoChoices);
            /* fifth, ask user for password & other details */
            password = getPassword(cryptoChoices, keySize);
            checkForExit(password);
            /* if its a decryption operation, ask for additional details */
            if(scramblerMission == ScramblerMission.DECRYPT &&
                    cryptoChoices != SymmetricCryptoChoices.AES_DEFAULT){
                saltString = getAdditionalData(SALT_TAG);
                checkForExit(saltString);
                initVectorString = getAdditionalData(IV_TAG);
                checkForExit(initVectorString);
                if(cryptoChoices == SymmetricCryptoChoices.AES_GCM_128 ||
                        cryptoChoices == SymmetricCryptoChoices.AES_GCM_256){
                    aadString = getAdditionalData(AAD_TAG);
                    checkForExit(aadString);
                }
            }
            /* sixth, ask user for input file location */
            inputFile = getFileLocation("input", null);
            checkForExit(inputFile);
            /* seventh, ask user for output file location */
            outputFile = getFileLocation("output", inputFile);
            checkForExit(outputFile);
            /* eight, ask user for final go ahead by displaying all the options chosen */
            if(scramblerMission == ScramblerMission.ENCRYPT){
                scramblerMould = new ScramblerMould
                        .Builder(scramblerMission, architecture, cryptoChoices,
                        keySize, password, inputFile, outputFile).build();
            }else{
                scramblerMould = new ScramblerMould
                        .Builder(scramblerMission, architecture, cryptoChoices,
                        keySize, password, inputFile, outputFile)
                        .salt(saltString).initVector(initVectorString).aadString(aadString)
                        .build();
            }

            /* remove this conformation part also in the utility class, and pass the mould object to confirm  */
            Boolean response = getUserConfirmation(scramblerMould);
            checkForExit(response);
            isUserReady = response.booleanValue();
        } while (!isUserReady);
        /* code to actually call the engines and do actions */
        logger.info("attempting to perform the requested operation, please wait...\n");
        try {
            ScramblerAsset.mobilize(scramblerMould);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("operation completed. Have a great day!\n");
    }

    private static void checkForExit(Object ob){
        if(null == ob)
            System.exit(0);
    }
}
