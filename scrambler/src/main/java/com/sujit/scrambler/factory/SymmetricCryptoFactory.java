package com.sujit.scrambler.factory;

public class SymmetricCryptoFactory extends CryptoFactory {
    public  CryptoEngine createCryptoEngine(CryptoChoices type) {
        CryptoEngine symmetricEngine = null;
        switch((SymmetricCryptoChoices)type) { 
            case AES_DEFAULT :
                symmetricEngine = new SimpleAESEngine();
                break;
            case AES_CBC :
            case AES_GCM :
            case AES_CTR : 
            default :
                System.out.println("No such product available in this factory"); 
                break;
        }
        return symmetricEngine;
    }
}