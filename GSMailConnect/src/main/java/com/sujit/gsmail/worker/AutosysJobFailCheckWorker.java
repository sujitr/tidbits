package com.sujit.gsmail.worker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sujit.gsmail.utils.AutosysJob;
import com.sujit.gsmail.utils.AutosysJobStatusCheckUtility;
import com.sujit.gsmail.utils.ReplyFramework;
import com.sujit.sendmail.MailerApp;

public class AutosysJobFailCheckWorker {
	
	final static Logger logger = LoggerFactory.getLogger(AutosysJobFailCheckWorker.class);
	
	public static boolean performJobCheck(ArrayList<String> jobs, String fromName, String sentStamp, String toNames, String ccNames, String subject, String orgBody, String finalToFields, String finalCcFields){
		ArrayList<AutosysJob> failedJobs = new ArrayList<AutosysJob>();
		ArrayList<AutosysJob> runningJobs = new ArrayList<AutosysJob>();
		ArrayList<AutosysJob> successfulJobs = new ArrayList<AutosysJob>();
		ArrayList<AutosysJob> otherJobs = new ArrayList<AutosysJob>();
		ArrayList<String> nullJobs = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		sb.append("<table border='1' style='border-collapse: collapse;padding: 5px;font-family: calibri;'>");
		for(String job: jobs){
			AutosysJob ajob = AutosysJobStatusCheckUtility.getJobStatus(job);
			if(ajob!=null){
				if(ajob.getCurrentStatus().equalsIgnoreCase("failure")){
					failedJobs.add(ajob);
				} else if(ajob.getCurrentStatus().equalsIgnoreCase("running")){
					runningJobs.add(ajob);
				} else if(ajob.getCurrentStatus().equalsIgnoreCase("success")){
					successfulJobs.add(ajob);
				} else{
					otherJobs.add(ajob);
				}
				sb.append("<tr><td>"+ajob.getJobName()+"</td><td>"+ajob.getCurrentStatus()+"</td></tr>");
			}else{
				nullJobs.add(job);
				sb.append("<tr><td>"+job+"</td><td>No status found as of now</td></tr>");
			}
			
		}
		sb.append("</table>");
		logger.info("|------ Failed Jobs ------------------------------------------------------");
		for(AutosysJob fj : failedJobs){
			logger.info("|--------- "+fj.getJobName());
		}
		logger.info("|------ Running Jobs ------------------------------------------------------");
		for(AutosysJob rj : runningJobs){
			logger.info("|--------- "+rj.getJobName());
		}
		logger.info("|------ Successful Jobs ------------------------------------------------------");
		for(AutosysJob rj : successfulJobs){
			logger.info("|--------- "+rj.getJobName());
		}
		logger.info("|------ Other Jobs ------------------------------------------------------");
		for(AutosysJob rj : otherJobs){
			logger.info("|--------- "+rj.getJobName());
		}
		logger.info("|-------------------   sending update mail....");
		String replyMailHtml = ReplyFramework.getReplyFormattedMailStringForAutosys(sb.toString(), fromName, sentStamp, toNames, ccNames, subject, orgBody);
		MailerApp app = new MailerApp();
		app.sendHTMLMail(finalToFields, finalCcFields, "wm-ct-operational-engineering@ny.email.gs.com", subject.startsWith("RE: ")?subject:"RE: "+subject, replyMailHtml, null);
		logger.info("|-------------------   mail sent sucessfully");
		return true;
	}
}
