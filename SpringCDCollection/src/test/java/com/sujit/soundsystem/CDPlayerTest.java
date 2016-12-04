package com.sujit.soundsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CDPlayerConfig.class)
public class CDPlayerTest {
	
	@Rule
	public final StandardOutputStreamLog log = new StandardOutputStreamLog();
	
	@Autowired
	private CompactDisc cd;
	
	@Autowired
	private MediaPlayer mp;

	@Test
	public void testCDShouldNotBeNull() {
		assertNotNull(cd);
	}

	@Test
	public void testPlay(){
		mp.play();
		assert(log.getLog().contains("Playing Rolling in the deep by Adele"));
	}
	
}
