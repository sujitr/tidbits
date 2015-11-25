package com.sujit.gsmail.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
	private static Logger logger = LoggerFactory.getLogger(LogTest.class);
	public static void main(String[] args) {
		
        final String message = "Hello logging!";
        logger.trace(message);
        logger.debug(message);
        logger.info(message);
        logger.warn(message);
        logger.error(message);
	}

}
