package com.sujit.scrambler.agents;

import com.sujit.scrambler.electives.CryptoArchitecture;
import com.sujit.scrambler.electives.CryptoChoices;
import com.sujit.scrambler.electives.KeySizes;
import com.sujit.scrambler.electives.SymmetricCryptoChoices;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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

    public static void main(String[] args){

        ScramblerMission scramblerMission = null;
        CryptoArchitecture architecture = null;
        CryptoChoices cryptoChoices = null;
        KeySizes keySize = null;
        int keyLength = 0;
        char[] password;
        File inputFile = null;
        File outputFile = null;
        //Console console = System.console();
        if(null==console){
            System.out.println("Unable to get hold of console. Exiting...");
        }
        boolean isUserReady = false;
        do{
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
                    case "Q": return;
                    default: console.printf("Please select a valid option \n");
                }
                console.printf("You have chosen '%s' operation.\n",scramblerMission.toString());
            } while (!isReadProperly);
            /* First read the choice of crypto platform */
            isReadProperly = false;
            do{
                String arch = console.readLine("Enter 'S' for symmetric or 'AS' for asymmetric" +
                        " ['Q' for exit] : ");
                if(arch.equalsIgnoreCase("s")||arch.equalsIgnoreCase("as")){
                    if(arch.equalsIgnoreCase("S")){
                        architecture = CryptoArchitecture.SYMMETRIC;
                    }else{
                        architecture = CryptoArchitecture.ASYMMETRIC;
                        console.printf("You have chosen '%s' architecture\n",architecture.name());
                        console.printf("This architecture has not been implemented in Scrambler yet!\n");
                        continue;
                    }
                    console.printf("You have chosen '%s' architecture\n",architecture.name());
                    isReadProperly = true;
                }else{
                    if(arch.equalsIgnoreCase("q")) return;
                    console.printf("'%s' is not a valid choice, please try again...\n",arch);
                }
            }while(!isReadProperly);
            /* Second read the choice of key size, based upon choice of crypto platform */
            isReadProperly = false;
            do{
                StringBuilder prompt = new StringBuilder();
                prompt.append("Select from below key size options - \n");
                if(architecture.equals(CryptoArchitecture.SYMMETRIC)){
                    prompt.append("\t['A' for 128 bit]\n").append("\t['B' for 256 bit]\n");
                }else{
                    prompt.append("\t['C' for 2048 bit]\n");
                }
                prompt.append("\t['Q' for Exit]\n")
                        .append("Please enter your choice : ");
                String type = console.readLine(prompt.toString());
                if(type!=null || !type.isEmpty()){
                    switch (type){
                        case "A":
                        case "a": if(architecture.equals(CryptoArchitecture.SYMMETRIC)) {keyLength = 128;}
                            break;
                        case "B":
                        case "b": if(architecture.equals(CryptoArchitecture.SYMMETRIC)) {keyLength = 256;}
                            break;
                        case "C":
                        case "c": if(architecture.equals(CryptoArchitecture.ASYMMETRIC)) {keyLength = 2048;}
                            break;
                        case "Q":
                        case "q": return;
                        default: keyLength = 0;
                    }
                }
                if(keyLength == 0){
                    console.printf("'%s' is not a valid choice, please try again...\n",type);
                }else{
                    console.printf("You have chosen key length as %d bits\n",keyLength);
                    isReadProperly = true;
                }
            }while(!isReadProperly);
            /* Third read the choice of type of cipher implementation, based upon above two choices */
            isReadProperly = false;
            do{
                StringBuilder prompt = new StringBuilder();
                prompt.append("Select from below symmetric cipher options - \n")
                        .append("\t['D' for default AES]\n")
                        .append("\t['C' for AES with CBC]\n")
                        .append("\t['G' for AES with GCM]\n")
                        .append("\t['Q' for Exit]\n")
                        .append("Please enter your choice : ");
                String type = console.readLine(prompt.toString());
                if(type!=null || !type.isEmpty()){
                    switch (type){
                        case "D":
                        case "d": cryptoChoices = SymmetricCryptoChoices.AES_DEFAULT;break;
                        case "C":
                        case "c": cryptoChoices = (keyLength == 128)?
                                SymmetricCryptoChoices.AES_CBC_128:SymmetricCryptoChoices.AES_CBC_256;
                            break;
                        case "G":
                        case "g": cryptoChoices = (keyLength == 128)?
                                SymmetricCryptoChoices.AES_GCM_128:SymmetricCryptoChoices.AES_GCM_256;
                            break;
                        case "Q":
                        case "q": return;
                        default: cryptoChoices = null;
                    }
                }
                if(null == cryptoChoices){
                    console.printf("'%s' is not a valid choice, please try again...\n",type);
                }else{
                    console.printf("You have selected %s\n",cryptoChoices.toString());
                    isReadProperly = true;
                }
            }while(!isReadProperly);
            /* Fourth, ask user for password */
            isReadProperly = false;
            do{
                password = console.readPassword("Enter desired passphrase for crypto operation : ");
                if(password!=null && password.length >= 8) {
                    if(cryptoChoices.equals(SymmetricCryptoChoices.AES_DEFAULT)){
                        if(keyLength == 128 && password.length != 16){
                            console.printf("For AES Default, with %d length key, " +
                                    "you need to have 16 characters in your passphrase. " +
                                    "Please try again.\n", keyLength);
                            continue;
                        }else if(keyLength == 256 && password.length != 32){
                            console.printf("For AES Default, with %d length key, " +
                                    "you need to have 32 characters in your passphrase. " +
                                    "Please try again.\n", keyLength);
                            continue;
                        }
                    }
                    char[] repassword = console.readPassword("Please re-enter the passphrase : ");
                    if(repassword!=null && Arrays.equals(password, repassword)){
                        Arrays.fill(repassword,'\u0000');
                        isReadProperly=true;
                    }else{
                        console.printf("provided passphrases do not match, please retry. \n");
                    }
                }else{
                    console.printf("please use a passphrase longer than 7 characters. \n");
                }
            }while(!isReadProperly);
            /* Fifth, ask user for input file location */
            inputFile = getFileLocation("input", null);
            if(null==inputFile){
                return;
            }
            /* Sixth, ask user for output file location */
            outputFile = getFileLocation("output", inputFile);
            if(null==outputFile){
                return;
            }
            /* Seventh, ask user for final go ahead by displaying all the options chosen */
            console.printf("Your specified options are as listed below.\n");
            console.printf("Please review them to continue...\n");
            console.printf("======================================================\n");
            console.printf("Crypto Architecture : %s\n", architecture.name());
            console.printf("Key length : %d\n", keyLength);
            console.printf("Implementation choice : %s\n", cryptoChoices.toString());
            console.printf("Number of characters in passphrase : %d\n", password.length);
            console.printf("Input File : %s\n", inputFile.getAbsolutePath());
            console.printf("Output File : %s\n", outputFile.getAbsolutePath());
            console.printf("======================================================\n");
            isReadProperly = false;
            do{
                String action = console.readLine("Enter 'C' to continue with above settings, 'R'" +
                        " to re-enter them or 'Q' for exit] : ");
                switch(action){
                    case "c":
                    case "C": isReadProperly=true;isUserReady=true;break;
                    case "r":
                    case "R": isReadProperly=true;break;
                    case "q":
                    case "Q": return;
                    default: console.printf("Please select a valid choice. \n");
                }
            }while(!isReadProperly);
        }while(!isUserReady);
        /* code to actually call the engines and do actions */
        switch (keyLength){
            case 128:keySize = KeySizes.BIT_16;break;
            case 256:keySize = KeySizes.BIT_32;break;
        }
        ScramblerMould scramblerMould = new ScramblerMould
                .Builder(scramblerMission,architecture,cryptoChoices,
                keySize,password,inputFile,outputFile).build();

        try {
            ScramblerAsset.scramble(scramblerMould);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static File getFileLocation(String mode, File earlierfile){
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
                            console.printf("New prelim output file created.\n");
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
}
