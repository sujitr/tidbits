package com.sujit.gsmail.analyzer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is to analyze if an email is requesting to create a new periodic build.
 * 
 */
public class PeriodicBuildMailAnalyzer {
	private ArrayList<String> toAddresses;
	private ArrayList<String> ccAddresses;
	private String subject;
	private String body;
	private String fromAddress;
	
	public PeriodicBuildMailAnalyzer(ArrayList<String> toAddresses,
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
	 * This method will inspect the various aspects of the email and then first determine if this is indeed a request.
	 * If so, it will extract the JIRA key of the product and return, else it will just return null.
	 */
	public ArrayList<String> analyzeCreatePeriodicBuildRequest(){
		ArrayList<String> finalKeys = null;
		/*
		 * Check if the subject has any action clauses and mail sent to has build meistrs id
		 */
		if(subject.toLowerCase().matches("^(.*?)periodic\\s+build(.*?)$") && toAddresses.contains("gs-imd-build-meisters@internal.email.gs.com")){
			System.out.println("|-- This subject is related to periodic build");
			if(subject.toLowerCase().contains("enable")||subject.toLowerCase().contains("enabling")||subject.toLowerCase().contains("add")||subject.toLowerCase().contains("configure")||subject.toLowerCase().contains("setup")||subject.toLowerCase().contains("set up")){
				System.out.println("|-- This looks like a request for creating periodic build...attempting to extract the JIRA Key from subject...");
				finalKeys = extractJiraKeyFromSubjectOrBody(subject, body);
			}else if((body.toLowerCase().contains("enable")||body.toLowerCase().contains("add")||body.toLowerCase().contains("configure")) && body.toLowerCase().matches("^(.*?)periodic(\\s+)?build(.*?)$")){
				finalKeys = extractJiraKeyFromSubjectOrBody(subject, body);
			}
			if(finalKeys==null || finalKeys.isEmpty()){
				System.out.println("|-------- No JIRAKEY found in the mail");
			}
		}else{
			System.out.println("|---- This mail is not related to periodic build requests. Ignoring.");
		}
		return finalKeys;
	}
	
	private ArrayList<String> extractJiraKeyFromSubjectOrBody(String subject, String body){
		/*String subjectJKey = null;
		String bodyJKey = null;
		String finalKey = null;*/
		ArrayList<String> keys = new ArrayList<String>();
		if(subject.matches("^(.*?)\\b[A-Z]+\\b(.*?)")){
			Pattern jirakKeyPattern = Pattern.compile("^(.*?)(\\b[A-Z]+\\b)(.*?)");
			Matcher jiraMatcher = jirakKeyPattern.matcher(subject);
			while(jiraMatcher.find()){
				keys.add(jiraMatcher.group(2)); 
			}
		}else{
			System.out.println("|-- Subject line did not mention JIRA key..trying to extract from body, within links...");
			/* in future it might require going through all the links in the body, as the very first link might not contain it */
			Pattern jirakKeyPattern = Pattern.compile("http\\://(.*?)/(\\b[A-Z]+\\b)/?\\s?");
			Matcher jiraMatcher = jirakKeyPattern.matcher(body);
			while(jiraMatcher.find()){
				keys.add(jiraMatcher.group(2)); 
			}
		}
		/*if(subjectJKey!=null && bodyJKey!=null && subjectJKey.trim().equals(bodyJKey.trim())){
			System.out.println("|-- Found the Key as : "+subjectJKey);
			finalKey = subjectJKey;
		}else if(subjectJKey==null && bodyJKey!=null && !bodyJKey.isEmpty()){
			System.out.println("|-- Found the Key as : "+bodyJKey);
			finalKey = bodyJKey;
		}else if(subjectJKey!=null && bodyJKey==null && !subjectJKey.isEmpty()){
			System.out.println("|-- Found the Key as : "+subjectJKey);
			finalKey = subjectJKey;
		}else{
			System.out.println("|-- Unable to determine the JIRA Key from email");
		}*/
		return keys;
	}
}
