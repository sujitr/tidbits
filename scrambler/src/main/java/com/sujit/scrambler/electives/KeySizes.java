package com.sujit.scrambler.electives;

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
