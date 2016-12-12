package com.sujit.soundsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CDPlayerConfig {
	@Bean
	public CompactDisc adele21(){
		return new Adele21();
	}
	
	@Bean
	public CDPlayer cdPlayer(){
		return new CDPlayer(adele21());
	}
}
