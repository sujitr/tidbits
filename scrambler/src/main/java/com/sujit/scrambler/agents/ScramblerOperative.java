package com.sujit.scrambler.agents;

import com.sujit.scrambler.electives.CryptoArchitecture;
import com.sujit.scrambler.electives.CryptoChoices;
import com.sujit.scrambler.electives.KeySize;
import static com.sujit.scrambler.utils.OpUtils.*;

import java.io.Console;
import java.io.File;
import java.io.IOException;

/**
 * This class executes the scrambler for a user. This class is meant
 * to be called from the JAR. It takes input from user about the
 * various choices for the task and then calls the appropriate engines.
 *
 * @author Sujit
 * @since 2018
 */
public class ScramblerOperative {

    private static Console console = System.console();

    public static void main(String[] args) {

        ScramblerMission scramblerMission;
        CryptoArchitecture architecture;
        CryptoChoices cryptoChoices;
        KeySize keySize;
        char[] password;
        File inputFile;
        File outputFile;
        if (isConsoleAvailable()) {
            System.out.println("Unable to get hold of console. Exiting...");
        }
        boolean isUserReady = false;
        do {
            /* first read the mission objective */
            scramblerMission = getUserMission();
            checkForQuit(scramblerMission);
            /* second read the choice of crypto platform */
            architecture = getUserArchitecture();
            /* third read the choice of key size, based upon choice of crypto platform */
            keySize = getKeyLength(architecture);
            /* fourth read the choice of type of cipher implementation, based upon above two choices */
            cryptoChoices = getUserCryptoChoice(architecture, keySize);
            /* fifth, ask user for password */
            password = getPassword(cryptoChoices, keySize);
            /* sixth, ask user for input file location */
            inputFile = getFileLocation("input", null);
            if (null == inputFile) {
                return;
            }
            /* seventh, ask user for output file location */
            outputFile = getFileLocation("output", inputFile);
            if (null == outputFile) {
                return;
            }
            /* eight, ask user for final go ahead by displaying all the options chosen */
            console.printf("Your specified options are as listed below.\n");
            console.printf("Please review them to continue...\n");
            console.printf("===============================================================\n");
            console.printf("Operation : %s\n", scramblerMission.toString());
            console.printf("Crypto Architecture : %s\n", architecture.name());
            console.printf("Key length : %d\n", keySize.getBitLenth());
            console.printf("Implementation choice : %s\n", cryptoChoices.toString());
            console.printf("Number of characters in passphrase : %d\n", password.length);
            console.printf("Input File : %s\n", inputFile.getAbsolutePath());
            console.printf("Output File : %s\n", outputFile.getAbsolutePath());
            console.printf("===============================================================\n");
            boolean isReadProperly = false;
            do {
                String action = console.readLine("Enter 'C' to continue with above settings, 'R'" +
                        " to re-enter them or 'Q' for exit] : ");
                switch (action) {
                    case "c":
                    case "C":
                        isReadProperly = true;
                        isUserReady = true;
                        break;
                    case "r":
                    case "R":
                        isReadProperly = true;
                        break;
                    case "q":
                    case "Q":
                        return;
                    default:
                        console.printf("Please select a valid choice. \n");
                }
            } while (!isReadProperly);
        } while (!isUserReady);
        /* code to actually call the engines and do actions */
        console.printf("\n...attempting to perform the requested operation, please wait...\n");
        ScramblerMould scramblerMould = new ScramblerMould
                .Builder(scramblerMission, architecture, cryptoChoices,
                keySize, password, inputFile, outputFile).build();

        try {
            ScramblerAsset.mobilize(scramblerMould);
        } catch (IOException e) {
            e.printStackTrace();
        }
        console.printf("\noperation completed. Have a great day!\n");
    }

    private static void checkForQuit(Object ob){
        if(null == ob)
            System.exit(0);
    }
}
