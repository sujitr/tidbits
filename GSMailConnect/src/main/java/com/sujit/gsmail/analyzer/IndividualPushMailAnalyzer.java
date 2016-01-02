package com.sujit.gsmail.analyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is to analyze if an email is requesting for an Individual Push. This class
 * searches for the patterns which exposes as a request for push to some GS 
 * internal host.
 * 
 */
public class IndividualPushMailAnalyzer {
	
	final static Logger logger = LoggerFactory.getLogger(IndividualPushMailAnalyzer.class);
	
	
	private ArrayList<String> toAddresses;
	private ArrayList<String> ccAddresses;
	private String subject;
	private String body;
	private String fromAddress;
	
	public IndividualPushMailAnalyzer(ArrayList<String> toAddresses,
			ArrayList<String> ccAddresses, String subject, String body,
			String fromAddress) {
		super();
		this.toAddresses = toAddresses;
		this.ccAddresses = ccAddresses;
		this.subject = subject.toLowerCase();
		this.body = body.toLowerCase();
		this.fromAddress = fromAddress;
	}
	
	/**
	 * This method will inspect the various aspects of the email and then first determine if this is indeed a request.
	 * If so, it will extract the JIRA key of the product and return, else it will just return null.
	 */
	public String[] analyzeIndividualPushRequest(){
		String[] hosts = null;
		/*
		 * Check if the subject has any action clauses and mail sent to has build meisters id. Sometime the subject line
		 * may not have even a slightest hint of the fact that its a request for individual push. So a more fine grained 
		 * search is needed on body of the email to verify whether its a real individual push request. 
		 * The first if check may totally pass-off the word match (as regex is completely optional) and lets through solely based upon the presence
		 * of build meisters email address in To field. 
		 */
		if(toAddresses.contains("gs-imd-build-meisters@internal.email.gs.com") || toAddresses.contains("gs-pwm-build-dev@internal.email.gs.com") || ccAddresses.contains("gs-pwm-build-dev@internal.email.gs.com") || ccAddresses.contains("gs-imd-build-meisters@internal.email.gs.com")){
			logger.info("|---- checking this mail from individual push perspective..."); 
			if(subject.toLowerCase().contains("push")||subject.toLowerCase().contains("deploy")||subject.toLowerCase().contains("individual")||subject.toLowerCase().contains("to qa")||subject.toLowerCase().contains("to pqa")){
				logger.info("|---- This looks like a request for individual push...attempting to extract the hosts from body...");
				hosts = extractHostnamesFromSubjectBody(subject, body, true);
			}else{
				logger.info("|----- On close check of subject line it was deducted that this mail is not related to individual push, checking body...");
				if(bodySearchForIndividualPush(body)){
					/* sometimes email does not contains individual push related keywords 
					 * in subject line, but they do have them in the body. We should check for such words or sequences in body also  
					 */
					logger.info("|----- Although subject line of this mail did not have any individual push related stuffs, but mail body did have some reference for it...attempting host extraction...");
					hosts = extractHostnamesFromSubjectBody(subject, body, false);
				}else{
					logger.info("|---- This mail is not related to individual push requests. Ignoring.");
				}
			}
		}else{
			logger.info("|---- This mail is not related to individual push requests. Ignoring.");
		}
		return hosts;
	}
	
	/**
	 * Method to extract the host-names for push from subject and body
	 * @param subject
	 * 		: subject of the mail as string, converted to lower-case, as String
	 * @param body
	 * 		: plain text body of the mail, newlines stripped off and converted to lower-case, as String
	 * @param isPushPresentInSubject
	 * 		: boolean representing if "push" keyword is present in subject or not 
	 * @return
	 * 		String array having the host-names picked up from the input parameters. 
	 * No duplicate hosts will be added in this array as they will be filtered, even if mail mentions duplicate host names 
	 */
	private String[] extractHostnamesFromSubjectBody(String subject, String body, boolean isPushPresentInSubject){
		Set<String> hosts = new HashSet<String>();
		/*
		 * check subject for the presence of host-name
		 */
		String[] subjectTokens = subject.split("\\s");
		if(subjectTokens.length>0){
			for(String subjectToken : subjectTokens){
				subjectToken = subjectToken.replaceAll("\u00A0", "").replaceAll("\u2007", "").replaceAll("\u202f", "").trim();
				String subjectHost = fetchHostFromToken(subjectToken);
				if(null!=subjectHost){
					logger.info("|------- Found host in subject as :"+subjectHost);
					hosts.add(getFilteredHostName(subjectHost));
				}
			}
		}
		/*
		 * check body fore presence of host-name
		 */
		boolean isBodyHasHostOrBoxIndicators = body.toLowerCase().contains("qa") || body.toLowerCase().contains("pqa") || body.toLowerCase().contains("jhp") || body.toLowerCase().contains(" individual") || body.toLowerCase().contains(" dc") || body.toLowerCase().contains(" box");
		if(isPushPresentInSubject){
			logger.info("|------ keyword 'push' is present in subject line, checking body for hostnames only (not enforcing 'push' keyword check in body)...");
			if(isBodyHasHostOrBoxIndicators){
				String[] bodyTokens = body.split("\\s");
				if(bodyTokens.length>0){
					for(String bodyToken:bodyTokens){
						bodyToken = bodyToken.replaceAll("\u00A0", "").replaceAll("\u2007", "").replaceAll("\u202f", "").trim();
						String bodyHost = fetchHostFromToken(bodyToken);
						if(null!=bodyHost){
							logger.info("|------ Found host in body  as :"+bodyHost);
							hosts.add(getFilteredHostName(bodyHost));
						}
					}
				}
			}else{
				logger.info("|-------- No host found in the email body");
				logger.info("|----- This email body did not seem like a request to push individually, even if subject seemed so. Not attempting host extraction.");
			}
		}else{
			logger.info("|------ keyword 'push' is NOT present in subject line, checking body for that keyword & hostnames...");
			if(body.toLowerCase().contains("push") && isBodyHasHostOrBoxIndicators){
				String[] bodyTokens = body.split("\\s");
				if(bodyTokens.length>0){
					for(String bodyToken:bodyTokens){
						String bodyHost = fetchHostFromToken(bodyToken);
						if(null!=bodyHost){
							logger.info("|------ Found host in body  as :"+bodyHost);
							hosts.add(getFilteredHostName(bodyHost));
						}
					}
				}
			}else{
				logger.info("|-------- No host found in the email body");
				logger.info("|----- This email body did not seem like a request to push individually. Not attempting host extraction.");
			}
		}
		logger.info("|-----Total number of hosts found in this email :"+hosts.size());
		String[] res = hosts.toArray(new String[hosts.size()]);
		return res;
	}
	
	/**
	 * Method searches for possible case of request of individual push in body
	 * @param body
	 * @return
	 */
	private boolean bodySearchForIndividualPush(String body){
		boolean result = false;
		if(body.toLowerCase().matches("^(.*?)push(.*?)$") && (body.toLowerCase().contains(" qap")||body.toLowerCase().contains(" pqap")||body.toLowerCase().contains(" jh"))){
			if(body.toLowerCase().contains("please create new rc build")){
				logger.info("|---------  looks like its a request for push, but might be some other instructions also need to be followed before that. Manual check needed for now.");
			}else{
				result = true;
			}
		}else{
			logger.info("|--------- could not find any push related keyword or hostname reference in the mail. So classifying it as non-push related mail.");
		}
		return result;
	}
	
	private String fetchHostFromToken(String token){
		String hostFound = null;
		String freshToken = null;
		String candidate = null;
		final String QA_PREFIX = "qa";
		final String PQA_PREFIX = "pqa";
		final String ZU_PREFIX = "jhpc";
		if(token.length()>5 && !(token.contains("_")||token.contains("/"))){
			if(token.endsWith(".")||token.endsWith(",")){
				freshToken = token.substring(0, token.length()-1).trim();
			}else{
				freshToken = token.trim();
			}
			if(freshToken.contains("pqa")){
				candidate = freshToken.substring(token.indexOf(PQA_PREFIX)).replaceAll("(\"|'|`|”|“)", "");
				if(candidate.matches("^"+PQA_PREFIX+"(.*?)[0-9]+(.*?)(\\.gs\\.com)?(.*?)?$")){
					Pattern pqaPattern = Pattern.compile("^"+PQA_PREFIX+"(.*?)(\\.gs\\.com)?$");
					Matcher pqaMatcher = pqaPattern.matcher(candidate);
					while(pqaMatcher.find()){
						hostFound = pqaMatcher.group(0);
					}
				}
			}else if(freshToken.contains("qa")){
				candidate = freshToken.substring(token.indexOf(QA_PREFIX)).replaceAll("(\"|'|`|”|“)", "");
				if(candidate.matches("^"+QA_PREFIX+"(.*?)[0-9]+(.*?)(\\.gs\\.com)?(.*?)?$")){
					Pattern qaPattern = Pattern.compile("^"+QA_PREFIX+"(.*?)(\\.gs\\.com)?$");
					Matcher qaMatcher = qaPattern.matcher(candidate);
					while(qaMatcher.find()){
						hostFound = qaMatcher.group(0);
					}
				}
			}else if(freshToken.contains("jhpc")){
				candidate = freshToken.substring(token.indexOf(ZU_PREFIX)).replaceAll("(\"|'|`|”|“)", "");
				if(candidate.matches("^"+ZU_PREFIX+"(.*?)[0-9]+(.*?)(\\.gs\\.com)?(.*?)?$")){
					hostFound="jhpc_zurich_host";
				}
			}
		}
		return hostFound;
	}
	
	private String getFilteredHostName(String incomingHost){
		String fullHost = null;
		if(incomingHost.endsWith(".gs.com")){
			fullHost = incomingHost;
		}else{
			if(incomingHost.startsWith("jh")){
				fullHost = incomingHost+".ny.eq.gs.com";
			}else{
				fullHost = incomingHost+".ny.imd.gs.com";
			}
			logger.info("|----------  Updated to full hostname as :"+fullHost);
		}
		return fullHost;
	}
	
}
