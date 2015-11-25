package com.sujit.gsmail.analyzer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is to analyze if an email is requesting check autosys job failures
 * 
 */
public class AutosysJobFailureMailAnalyzer {
	
	
	final static Logger logger = LoggerFactory.getLogger(AutosysJobFailureMailAnalyzer.class);
	
	private ArrayList<String> toAddresses;
	private ArrayList<String> ccAddresses;
	private String subject;
	private String body;
	private String fromAddress;
	
	public AutosysJobFailureMailAnalyzer(ArrayList<String> toAddresses,
			ArrayList<String> ccAddresses, String subject, String body,
			String fromAddress) {
		super();
		this.toAddresses = toAddresses;
		this.ccAddresses = ccAddresses;
		this.subject = subject;
		this.body = body;
		this.fromAddress = fromAddress;
	}
	
	/**
	 * This method will inspect the various aspects of the email and then first determine if this is indeed a job failure notification mail.
	 * If so, it will extract the failed job names and return, else it will just return null.
	 */
	public ArrayList<String> analyzeAutosysFailedJobs(){
		ArrayList<String> jobNames = null;
		/*
		 * Check if the subject has any action clauses like 'JOBFAILURE' and mail sent to has wm-ct-oe id
		 */
		if((subject.toLowerCase().matches("^(.*?)job\\s?failure('s)?\\s?(.*?)$") || subject.toLowerCase().matches("^(.*?)autosys\\s?failure(s)?(.*?)$")) && toAddresses.contains("wm-ct-operational-engineering@ny.email.gs.com") && ccAddresses.contains("gs-pwm-prod-mgmt@ny.email.gs.com")){
			logger.info("|---- This subject is related to Job Failure");
			if(subject.toLowerCase().matches("^(.*?)job\\s?failure('s)?\\s?((clsvdcom|clsvpwm|imdfs)_)?(.*?)$") || subject.toLowerCase().matches("^(.*?)autosys\\s?failure(s)?(.*?)$")){
				logger.info("|--- definitely a case of Autosys Job Failure....analysing subject and body...");
				jobNames = extractJobNamesFromSubjectBody(subject, body);
			}else{
				logger.info("|--- Subject did not contain anything pointing to autosys job failures. Skipping this email.");
			}
		}else{
			logger.info("|---- This mail is not related to autosys job failure check. Ignoring.");
		}
		return jobNames;
	}
	
	private ArrayList<String> extractJobNamesFromSubjectBody(String subject, String body){
		ArrayList<String> jobNames = new ArrayList<String>();
		if(body.toLowerCase().matches("^(.*?)please\\s(check|take care of)\\s?(this|on\\sthe\\sbelow|these|the below)?\\s?(alert|job failure)?s?\\.?(.*?)\\s?(jobfailure)?\\s?(clsvdcom_|clsvpwm_|imdfs_)(.*?)$") || body.toLowerCase().matches("^(.*?)jobs\\sfailed(.*?)reboot(.*?)window(.*?)$")){
			String[] tokens = body.split("\\s");
			Pattern jobNamePattern = Pattern.compile("^(CLSVDCOM|CLSVPWM|IMDFS)_(.*?)$");
			if(tokens.length>0){
				for(String token:tokens){
					Matcher m1 = jobNamePattern.matcher(token.trim());
					while(m1.find()){
						logger.info("|------ Found job as :"+m1.group(0));
						jobNames.add(m1.group(0));
					}
				}
			}
		}else{
			logger.info("|----- This email body did not seem like a request to check for Autosys Job Failures, even if subject seemed so. Not attempting job name extraction.");
		}
		return jobNames;
	}
	
	
}
