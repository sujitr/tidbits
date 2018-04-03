package com.sujit.scrambler.factory;

import com.sujit.scrambler.electives.CryptoArchitecture;
import com.sujit.scrambler.electives.CryptoChoices;
import com.sujit.scrambler.engines.CryptoEngine;

/**
 * Abstract factory class which, given an architecture, produces factory instance for
 * manufacturing products of that architecture family.
 */
public abstract class CryptoFactory {
    
    /* for symmetric cryptography line of products */
    private static final SymmetricCryptoFactory symmetricFactory = new SymmetricCryptoFactory();
    /* for asymmetric cryptography line of products */
    private static final AsymmetricCryptoFactory asymmetricFactory = new AsymmetricCryptoFactory();    
    
    /**
     * Method to get the factory based on a given architecture choice.
     * 
     * @param CryptoArchitecture
     *          architecture of the encryption/decryption algorithm (whether symmetric or asymmetric)
     * 
     * @return CryptoFactory
     *          instance of appropriate factory
     */
    public static CryptoFactory getCryptoFactory(CryptoArchitecture arch) {
        CryptoFactory factory = null;
        switch (arch) {
            case SYMMETRIC : 
                factory = symmetricFactory;
                break;
            case ASYMMETRIC :
                factory = asymmetricFactory;
        }
        return factory;
    }
    
    /**
     * abstract method laying the foundation of the way a particular cryptographic 
     * arhcitecture based product is manufactured by the concrete factory.
     * 
     * @param CryptoChoices
     *          individual product-type as required from this factory
     * 
     * @return CryptoEngine
     *          the actual product instance based on the product-type supplied
     */
    public abstract CryptoEngine createCryptoEngine(CryptoChoices engineChoice); 
}