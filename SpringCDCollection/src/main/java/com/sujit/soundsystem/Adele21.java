package com.sujit.soundsystem;

import org.springframework.stereotype.Component;

@Component
public class Adele21 implements CompactDisc {
	
	private String title = "Rolling in the deep";
	private String artist = "Adele";

	@Override
	public void play() {
		System.out.println("Playing "+title+" by "+artist);
	}

}
