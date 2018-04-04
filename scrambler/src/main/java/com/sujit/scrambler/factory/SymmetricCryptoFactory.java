package com.sujit.scrambler.factory;

import com.sujit.scrambler.electives.CryptoChoices;
import com.sujit.scrambler.electives.SymmetricCryptoChoices;
import com.sujit.scrambler.engines.CryptoEngine;
import com.sujit.scrambler.engines.SimpleAESEngine;

/**
 * Class to produce specific crypto implementation given
 * a choice of symmetric algorithm for encryption/decryption.
 */
public class SymmetricCryptoFactory extends CryptoFactory {
    public  CryptoEngine createCryptoEngine(CryptoChoices type) {
        CryptoEngine symmetricEngine = null;
        switch((SymmetricCryptoChoices)type) { 
            case AES_DEFAULT :
                symmetricEngine = new SimpleAESEngine();
                break;
            case AES_CBC :
            case AES_CTR :
            case AES_GCM :
            case AES_CCM :
            default :
                System.out.println("No such product available in this factory"); 
                break;
        }
        return symmetricEngine;
    }
}