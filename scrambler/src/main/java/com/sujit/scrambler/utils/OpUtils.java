package com.sujit.scrambler.utils;

import com.sujit.scrambler.agents.ScramblerMission;
import com.sujit.scrambler.agents.ScramblerMould;
import com.sujit.scrambler.electives.CryptoArchitecture;
import com.sujit.scrambler.electives.CryptoChoices;
import com.sujit.scrambler.electives.KeySize;
import com.sujit.scrambler.electives.SymmetricCryptoChoices;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Static utility class containing methods which are used by
 * the operative class to get user input and process them
 * to help agent classes to perform their actions
 */
public class OpUtils {

    private static Console console = System.console();

    /**
     * check if console is available or not
     * @return boolean
     */
    public static boolean isConsoleAvailable(){
        return (null==console)?false:true;
    }

    public static ScramblerMission getUserMission(){
        ScramblerMission scramblerMission = null;
        boolean isReadProperly = false;
        do{
            String mode = console.readLine("Enter 'E' for encryption or 'D' for decryption" +
                    " ['Q' for exit] : ");
            switch(mode) {
                case "d":
                case "D": scramblerMission = ScramblerMission.DECRYPT;isReadProperly=true;break;
                case "e":
                case "E": scramblerMission = ScramblerMission.ENCRYPT;isReadProperly=true;break;
                case "q":
                case "Q": return null;
                default: console.printf("Please select a valid option \n");
            }
            if(isReadProperly)
                console.printf("You have chosen '%s' operation.\n",scramblerMission.toString());
        } while (!isReadProperly);
        return scramblerMission;
    }

    public static CryptoArchitecture getUserArchitecture(){
        CryptoArchitecture cryptoArchitecture = null;
        boolean isReadProperly = false;
        do{
            String arch = console.readLine("Enter 'S' for symmetric or 'AS' for asymmetric" +
                    " ['Q' for exit] : ");
            if(arch.equalsIgnoreCase("s")||arch.equalsIgnoreCase("as")){
                if(arch.equalsIgnoreCase("S")){
                    cryptoArchitecture = CryptoArchitecture.SYMMETRIC;
                }else{
                    cryptoArchitecture = CryptoArchitecture.ASYMMETRIC;
                    console.printf("You have chosen '%s' architecture\n",cryptoArchitecture.name());
                    console.printf("This architecture has not been implemented in Scrambler yet!\n");
                    continue;
                }
                console.printf("You have chosen '%s' architecture\n",cryptoArchitecture.name());
                isReadProperly = true;
            }else{
                if(arch.equalsIgnoreCase("q")) return null;
                console.printf("'%s' is not a valid choice, please try again...\n",arch);
            }
        }while(!isReadProperly);
        return cryptoArchitecture;
    }

    public static KeySize getKeyLength(CryptoArchitecture cryptoArchitecture){
        KeySize keySize = null;
        boolean isReadProperly = false;
        do{
            StringBuilder prompt = new StringBuilder();
            prompt.append("Select from below key size options - \n");
            if(cryptoArchitecture.equals(CryptoArchitecture.SYMMETRIC)){
                prompt.append("\t['A' for 128 bit]\n").append("\t['B' for 256 bit]\n");
            }else{
                prompt.append("\t['C' for 2048 bit]\n");
            }
            prompt.append("\t['Q' for Exit]\n").append("Please enter your choice : ");
            String type = console.readLine(prompt.toString());
            if(type!=null || !type.isEmpty()){
                switch (type){
                    case "A":
                    case "a": if(cryptoArchitecture.equals(CryptoArchitecture.SYMMETRIC)) {
                                keySize = KeySize.BIT_16;
                                isReadProperly = true;
                              }
                              break;
                    case "B":
                    case "b": if(cryptoArchitecture.equals(CryptoArchitecture.SYMMETRIC)) {
                                keySize = KeySize.BIT_32;
                                isReadProperly = true;
                              }
                              break;
                    case "C":
                    case "c": if(cryptoArchitecture.equals(CryptoArchitecture.ASYMMETRIC)) {
                                keySize = KeySize.BIT_2048;
                                isReadProperly = true;
                              }
                              break;
                    case "Q":
                    case "q": return null;
                    default: console.printf("'%s' is not a valid choice, " +
                            "please try again...\n",type);
                }
            }
            if(keySize!=null){
                console.printf("You have chosen key length as %d bits\n",keySize.getBitLenth());
            }
        }while(!isReadProperly);
        return keySize;
    }

    public static CryptoChoices getUserCryptoChoice(
            CryptoArchitecture architecture, KeySize keySize){
        CryptoChoices cryptoChoices = null;
        boolean isReadProperly = false;
        StringBuilder userPrompt = new StringBuilder("Select from below cipher options - \n");
        if(architecture == CryptoArchitecture.SYMMETRIC){
            userPrompt.append("\t['D' for default AES]\n")
                      .append("\t['C' for AES with CBC]\n")
                      .append("\t['G' for AES with GCM]\n");
        }else{
            // space for asymmetric implementations
        }
        userPrompt.append("\t['Q' for Exit]\n").append("Please enter your choice : ");
        do{
            String type = console.readLine(userPrompt.toString());
            if(type!=null || !type.isEmpty()){
                if(architecture == CryptoArchitecture.SYMMETRIC){
                    switch (type){
                        case "D":
                        case "d":   {
                            cryptoChoices = SymmetricCryptoChoices.AES_DEFAULT;
                            isReadProperly = true;
                            break;
                        }
                        case "C":
                        case "c":   {
                            switch (keySize){
                                case BIT_16: cryptoChoices = SymmetricCryptoChoices.AES_CBC_128;break;
                                case BIT_32: cryptoChoices = SymmetricCryptoChoices.AES_CBC_256;break;
                            }
                            isReadProperly = true;
                            break;
                        }
                        case "G":
                        case "g":   {
                            switch (keySize){
                                case BIT_16: cryptoChoices = SymmetricCryptoChoices.AES_GCM_128;break;
                                case BIT_32: cryptoChoices = SymmetricCryptoChoices.AES_GCM_128;break;
                            }
                            isReadProperly = true;
                            break;
                        }
                        case "Q":
                        case "q":   return null;
                        default:    console.printf("'%s' is not a valid choice, please try again...\n",type);
                    }
                }else{
                    // space for asymmetric implementations
                }
            }
            if (cryptoChoices!=null) {
                console.printf("You have selected %s\n",cryptoChoices.toString());
            }
        }while(!isReadProperly);
        return cryptoChoices;
    }

    public static char[] getPassword(CryptoChoices cryptoChoices, KeySize keySize){
        boolean isReadProperly = false;
        char[] password,repassword;
        do{
            password = console.readPassword("Enter passphrase for crypto operation : ");
            if(password!=null && password.length >= 8) {
                if(cryptoChoices.equals(SymmetricCryptoChoices.AES_DEFAULT)){
                    if((keySize == KeySize.BIT_16 && password.length != 16) ||
                            (keySize == KeySize.BIT_32 && password.length != 32)) {
                        console.printf("For AES Default, with %d bit key, " +
                                "you need to have %d characters in your passphrase. " +
                                "Please try again.\n", keySize.getBitLenth(),keySize.getBitLenth());
                        continue;
                    }
                }
                console.flush();
                repassword = console.readPassword("Please re-enter the passphrase : ");
                if(repassword!=null && Arrays.equals(password, repassword)){
                    Arrays.fill(repassword,'\u0000');
                    isReadProperly=true;
                }else{
                    console.printf("provided passphrases do not match, please retry. \n");
                }
            }else{
                console.printf("please use a passphrase with atleast 8 characters. \n");
            }
        }while(!isReadProperly);
        return password;
    }

    public static File getFileLocation(String mode, File earlierfile){
        boolean isReadProperly = false;
        File file = null;
        do{
            String filePath = console.readLine("Please enter the path of the "
                    + mode +" file ['Q' to exit] : ");
            if(filePath.equalsIgnoreCase("q")){
                return null;
            }
            file = new File(filePath);
            if(file.exists() && file.isFile() && file.canRead()){
                if(mode.equals("output") && file.equals(earlierfile)){
                    console.printf("You have selected same file as input file. " +
                            "Please select another file for output.\n");
                }else{
                    console.printf("You have selected file %s having size %d bytes.\n",
                            file.getName(),file.length());
                    isReadProperly = true;
                }
            }else{
                if(mode.equals("input")){
                    console.printf("Please enter correct file path. " +
                            "What you have entered either does not exists, or not a file" +
                            " or not having read permissions. Check and try again.\n");
                }else{
                    console.printf("given output file does not exist, " +
                            "attempting to create one...\n");
                    /* check if the parent folder exists and then create the new file */
                    if(file.getParentFile().exists() && file.getParentFile().canWrite()){
                        try {
                            file.createNewFile();
                            console.printf("New preliminary output file created.\n");
                            isReadProperly = true;
                        } catch (IOException e) {
                            console.printf("unable to create new output file due to " +
                                    ": "+e.getLocalizedMessage());
                            return null;
                        }
                    }else{
                        console.printf("please check provided output folder. It does not " +
                                "seem to exist or does'nt have write permissions.\n");
                    }
                }
            }
        }while(!isReadProperly);
        return file;
    }

    public static String getAdditionalData(String query){
        boolean isReadProperly = false;
        String result = null;
        do {
            result = console.readLine("Please enter the value for '%s' :",query);
            console.printf("You have entered : %s \n",result);
            String confirm = console.readLine("press [C] to confirm, [R] to re-enter or [Q] to quit: ");
            switch (confirm){
                case "c":
                case "C":isReadProperly = true;break;
                case "r":
                case "R":break;
                case "q":
                case "Q":return null;
                default: console.printf("\nPlease select a valid choice. %s is not a valid option\n",confirm);
            }
        } while (!isReadProperly);
        return result;
    }

    public static Boolean getUserConfirmation(ScramblerMould scramblerMould){
        console.printf("Your specified options are as listed below.\n");
        console.printf("Please review them to continue...\n");
        console.printf("===============================================================\n");
        console.printf("Operation : %s\n",
                scramblerMould.getScramblerMission().toString());
        console.printf("Crypto Architecture : %s\n",
                scramblerMould.getCryptoArchitecture().name());
        console.printf("Key length : %d\n",
                scramblerMould.getKeySize().getBitLenth());
        console.printf("Implementation choice : %s\n",
                scramblerMould.getCryptoChoices().toString());
        console.printf("Number of characters in passphrase : %d\n",
                scramblerMould.getPassword().length);
        console.printf("Input File : %s\n",
                scramblerMould.getInputFilePath().getAbsolutePath());
        console.printf("Output File : %s\n",
                scramblerMould.getOutputFilePath().getAbsolutePath());
        console.printf("===============================================================\n");
        boolean isReadProperly = false;
        Boolean response = Boolean.FALSE;
        do {
            String action = console.readLine("Enter 'C' to continue with above settings, 'R'" +
                    " to re-enter them or 'Q' for exit] : ");
            switch (action) {
                case "c":
                case "C":
                    isReadProperly = true;
                    response = true;
                    break;
                case "r":
                case "R":
                    isReadProperly = true;
                    response = false;
                    break;
                case "q":
                case "Q":
                    return null;
                default:
                    console.printf("Please select a valid choice. \n");
            }
        } while (!isReadProperly);
        return response;
    }
}
