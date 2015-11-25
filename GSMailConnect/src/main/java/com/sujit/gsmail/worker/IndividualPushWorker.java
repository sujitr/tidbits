package com.sujit.gsmail.worker;

import java.io.UnsupportedEncodingException;

import com.sujit.gsmail.utils.IndividualPushBuildUtilities;
import com.sujit.gsmail.utils.IndividualPushResponse;
import com.sujit.gsmail.utils.IndividualPushResponse.ResponseMode;
import com.sujit.gsmail.utils.MailMetaBox;
import com.sujit.gsmail.utils.ReplyFramework;
import com.sujit.sendmail.MailerApp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndividualPushWorker {
	
	final static Logger logger = LoggerFactory.getLogger(IndividualPushWorker.class);
	/**
	 * Method to initiate push for all hosts via individual Push
	 * @param hostNames
	 * 					: String array containing host-names
	 * @return
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException
	 */
	public static IndividualPushResponse performPush(String[] hostNames) throws InterruptedException, UnsupportedEncodingException{
		IndividualPushResponse pushResponse = new IndividualPushResponse();
		boolean result = false;
		if(IndividualPushBuildUtilities.isNimblPushRunning()){
			logger.warn("|-----  NIMBL Push is running. Cannot initiate individual push now");
			pushResponse.setMode(ResponseMode.NIMBLRUNNING);
		}else{
			if (null!=hostNames) {
				// switch for pqa push prohibition
				boolean isPQA_Push_Restricted = false;
				boolean isCurrentPQAPushRequestBlockedBecauseOfRestriction = false;
				if (hostNames.length > 0) {
					ArrayList<String> links = new ArrayList<String>();
					for(String hostname : hostNames){
						String pushLink = null;
						if(hostname.endsWith(".gs.com")){
							if(hostname.startsWith("jh")){
								// zurich push call
								pushLink = IndividualPushBuildUtilities.pushToIndividualZurichQABox(hostname);
								Thread.sleep(5000);
							}else{
								 if(hostname.startsWith("pqa") && isPQA_Push_Restricted){
									pushLink = "PQA push is temporarily prohibited.";
									isCurrentPQAPushRequestBlockedBecauseOfRestriction = true;
								}else{
									pushLink = IndividualPushBuildUtilities.pushToIndividualQABox(hostname);
									Thread.sleep(5000);
								}
							}
						}else{
							if(hostname.startsWith("jh")){
								// zurich push call after adding suffix
								pushLink = IndividualPushBuildUtilities.pushToIndividualZurichQABox(hostname+".ny.eq.gs.com");
								Thread.sleep(5000);
							}else{
								 if(hostname.startsWith("pqa") && isPQA_Push_Restricted){
									pushLink = "PQA push is temporarily prohibited.";
									isCurrentPQAPushRequestBlockedBecauseOfRestriction = true;
								}else{
									pushLink = IndividualPushBuildUtilities.pushToIndividualQABox(hostname+".ny.imd.gs.com");
									Thread.sleep(5000);
								}
							}
						}
						logger.info("|-------- Individual push to host '"+hostname+"' status is: "+pushLink);
						if(null!=pushLink){
							links.add(pushLink);
						}
					}
					result = true;
					pushResponse.setAllPushDone(result);
					pushResponse.setLinks(links);
					if(isCurrentPQAPushRequestBlockedBecauseOfRestriction){
						pushResponse.setMode(ResponseMode.PQARESTRICTED);
					}else{
						pushResponse.setMode(ResponseMode.NORMAL);
					}
				} else {
					logger.info("|-- This mail seems related to individual push, but no hosts found to action upon.");
				}
			}
		}
		return pushResponse;
	}
	
	public static void sendUpdate(MailMetaBox emailMetaBox, IndividualPushResponse pushResp){
		logger.info("|-------------------   sending update mail....");
		StringBuilder sb = new StringBuilder();
		if(pushResp.getMode()==ResponseMode.NIMBLRUNNING){
			sb.append("The scheduled <a href='http://d119030-005.dc.gs.com:8061/jenkins/view/ReleaseMgmt/job/PushNimbleBuildsToQa/'>nimbl push</a> is going on as of now. "
					+ "Most likely it would push your build to the hosts if your changes were deployed prior to the start of the push.");
			sb.append("<br>");
			sb.append("Anyway, we will initiate the individual push for the mentioned hosts after the scheduled nimbl push completes.");
		}else{
			Set<String> linkSet = new HashSet<String>(pushResp.getLinks());
			if(linkSet.size()==1 && linkSet.contains("PQA push is temporarily prohibited.")){
				// it means all the hosts provided were PQA hosts and all of them were blocked
				sb.append("Individual push for the mentioned host(s) are on hold as instructed by QA team. Please get approval from Ajay Raman for push.");
			}else{
				sb.append("Individual push for the mentioned host(s) have been initiated. Details are - <br><br>");
				sb.append("<table border='0' style='border-collapse: collapse;padding: 5px;font-family: calibri;'>");
				for(String link : pushResp.getLinks()){
					sb.append("<tr><td>"+link+"</td></tr>");
				}
				sb.append("</table>");
				sb.append("<br>You can check status of all these builds at this <a href='http://home.ic.gs.com/jenkins/view/ReleaseMgmt/job/PushNimbleToIndividualBox/'>location</a>.<br>");
			}
			if(pushResp.getMode()==ResponseMode.PQARESTRICTED){
				sb.append("<br><span style='text-decoration: underline;'>For the PQA hosts, push activity was not triggered, as PQA push is on hold as of now. Please check with Ajay Raman for approval with regard to PQA push.</span>");
			}
		}
		String replyMailHtml = ReplyFramework.getReplyFormattedMailStringForIndividualPush(sb.toString(), emailMetaBox.getFromNameForChain(), emailMetaBox.getSentStampForChain(), emailMetaBox.getToNamesForChain(), emailMetaBox.getCcNamesForChain(), emailMetaBox.getSubject(), emailMetaBox.getBodyDivForChain());
		MailerApp app = new MailerApp();
		app.sendHTMLMail(emailMetaBox.getToAddressesForEmail(), emailMetaBox.getCcAddressForEmail(), "gs-imd-build-meisters@gs.com", emailMetaBox.getSubject().startsWith("RE: ")?emailMetaBox.getSubject():"RE: "+emailMetaBox.getSubject(), replyMailHtml, null);
		logger.info("|-------------------   mail sent sucessfully");
	}
	
}
