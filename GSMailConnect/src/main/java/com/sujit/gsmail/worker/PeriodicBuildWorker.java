package com.sujit.gsmail.worker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sujit.gsmail.utils.MailMetaBox;
import com.sujit.gsmail.utils.PeriodicBuildUtilities;
import com.sujit.gsmail.utils.ReplyFramework;
import com.sujit.sendmail.MailerApp;

public class PeriodicBuildWorker {
	static Logger logger = LoggerFactory.getLogger(PeriodicBuildWorker.class);
	public static void createPeriodicBuilds(ArrayList<String> jiraKeys){
		for(String key : jiraKeys){
			logger.info("|-- checking existence of periodic build with this key :"+key);
			if(PeriodicBuildUtilities.isPeriodBuildPresent(key)){
				logger.warn("|----- Periodic build already exists for "+key+". Nothing to be done here.");
			}else{
				logger.info("|----- Periodic build does NOT EXISTS. Attempting to create it...");
				String link = PeriodicBuildUtilities.createPeriodicBuild(key);
				// send response email
				logger.info("|-- Periodic Build for "+key+" has been created: "+link);
			}
		}
	}
	
	public static void sendUpdate(MailMetaBox emailMetaBox, String replyText){
		logger.info("|-------------------   sending update mail....");
		String replyMailHtml = ReplyFramework.getReplyFormattedMailStringForPeriodicBuild(replyText, emailMetaBox.getFromNameForChain(), emailMetaBox.getSentStampForChain(), emailMetaBox.getToNamesForChain(), emailMetaBox.getCcNamesForChain(), emailMetaBox.getSubject(), emailMetaBox.getBodyDivForChain());
		MailerApp app = new MailerApp();
		app.sendHTMLMail(emailMetaBox.getToAddressesForEmail(), emailMetaBox.getCcAddressForEmail(), "gs-imd-build-meisters@gs.com", emailMetaBox.getSubject().startsWith("RE: ")?emailMetaBox.getSubject():"RE: "+emailMetaBox.getSubject(), replyMailHtml, null);
		System.out.println("|-------------------   mail sent sucessfully");
	}
}
