package com.sujit.scrambler.factory;

import com.sujit.scrambler.electives.CryptoArchitecture;
import com.sujit.scrambler.electives.CryptoChoices;
import com.sujit.scrambler.engines.CryptoEngine;

public abstract class CryptoFactory {
    
    private static final SymmetricCryptoFactory symmetricFactory = new SymmetricCryptoFactory();
    private static final AsymmetricCryptoFactory asymmetricFactory = new AsymmetricCryptoFactory();    
    
    public static CryptoFactory getCryptoFactory(CryptoArchitecture arch) {
        CryptoFactory factory = null;
        switch (arch) {
            case SYMMETRIC : 
                factory = symmetricFactory;
                break;
            case ASSYMETRIC :
                factory = asymmetricFactory;
        }
        return factory;
    }
    
    public abstract CryptoEngine createCryptoEngine(CryptoChoices engineChoice); 
}