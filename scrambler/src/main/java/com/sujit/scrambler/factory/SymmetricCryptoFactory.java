package com.sujit.scrambler.factory;

import com.sujit.scrambler.electives.CryptoChoices;
import com.sujit.scrambler.electives.KeySize;
import com.sujit.scrambler.electives.SymmetricCryptoChoices;
import com.sujit.scrambler.engines.CbcAESEngine;
import com.sujit.scrambler.engines.GaloisCounterAESEngine;
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
            case AES_DEFAULT:
                symmetricEngine = new SimpleAESEngine();
                break;
            case AES_CBC_128 :
            	symmetricEngine = new CbcAESEngine(KeySize.BIT_16);
            	break;
            case AES_CBC_256 :
            	symmetricEngine = new CbcAESEngine(KeySize.BIT_32);
            	break;
            case AES_CTR :
            case AES_GCM_128 :
                symmetricEngine = new GaloisCounterAESEngine(KeySize.BIT_16);
            	break;
            case AES_GCM_256 :
                symmetricEngine = new GaloisCounterAESEngine(KeySize.BIT_32);
            	break;
            case AES_CCM :
            default :
                System.out.println("No such product available in this factory"); 
                break;
        }
        return symmetricEngine;
    }
}