package com.sujit.gsmail.reader;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Main job class to be called from scheduler for doing all analysis for 
 * creating periodic builds, pushing to individual boxes and autosys jobs.
 * This class handles 'unread mails' from Inbox, IC and 'wm-ct-oe' folders.
 * @author roysuj
 *
 */

@PersistJobDataAfterExecution
public class ReadMyMails implements Job{
	static final Logger logger = LoggerFactory.getLogger(ReadMyMails.class);
	public static final String userNameKey = "userName";
	public static final String passWordKey = "passWord";
	public static final String domainKey = "domain";
	public void execute(JobExecutionContext jeContext) throws JobExecutionException {
		JobDataMap dataMap = jeContext.getJobDetail().getJobDataMap();
		String uName = dataMap.getString(userNameKey);
		String pWord = dataMap.getString(passWordKey);
		String domain = dataMap.getString(domainKey);
		ReadInboxFolderMail inbox = new ReadInboxFolderMail(uName, pWord, domain);
		ReadICFolderMail ic = new ReadICFolderMail(uName, pWord, domain);
		ReadWMCTOEFolderMail wm = new ReadWMCTOEFolderMail(uName, pWord, domain);
		logger.info("|-- Starting scan operation on emails for auto response...");
		/*inbox.scanInboxMails();
		ic.scanICMails();
		wm.scanWMCTOEMails();*/
		inbox.readInboxMails();
		ic.readICMails();
		wm.readWMCTOEMails();
		logger.info("|-- Auto response scan finished");
		inbox = null;
		ic = null;
		wm = null;
	}
}


