package com.sujit.scrambler.electives;

/**
 *  Enum to mark bit lengths of keys used in the crypto engines.
 *  Engines based on length of these keys could then be used
 *  as per the Java installation were specific jurisdiction files are enabled.
 *   
 * @author Sujit
 * @since 2018
 *
 */
public enum KeySizes {
	BIT_16 (128),
	BIT_32 (256);
	
	private final int bitLength;
	
	private KeySizes(int bitLength){
		this.bitLength = bitLength;
	}
	
	public int getBitLenth(){
		return this.bitLength;
	}
}
