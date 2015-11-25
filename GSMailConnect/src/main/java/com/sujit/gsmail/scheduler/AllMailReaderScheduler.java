package com.sujit.gsmail.scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.*;

import com.sujit.gsmail.reader.ReadMyMails;

public class AllMailReaderScheduler {
	
	static final Logger logger = LoggerFactory.getLogger(AllMailReaderScheduler.class);

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		// read the properties file for credentials
		Map<String, String> credentials = getUserCredentails();
		// schedule the job with a trigger
		 SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory(); 
		  Scheduler sched = schedFact.getScheduler(); 
		  sched.start(); 
		  JobDetail job = newJob(ReadMyMails.class).withIdentity("allMailJob", "allMailGroup").build();
		  job.getJobDataMap().put(ReadMyMails.userNameKey, credentials.get("userName"));
		  job.getJobDataMap().put(ReadMyMails.passWordKey, credentials.get("passWord"));
		  job.getJobDataMap().put(ReadMyMails.domainKey, credentials.get("domain"));
		  Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger3", "allMailGroup").startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 10-19 ? * MON-FRI")).build();
		  sched.scheduleJob(job, trigger);
	}
	
	public static Map<String, String> getUserCredentails(){
		Map<String, String> userCredentials = new HashMap<String, String>();
		Properties prop = new Properties();
		InputStream input = null;
		try{
			logger.info("|-- Attempting reading the startup configuraton property file..");
			String propertyFileName = "mailconn.properties";
			input = ReadMyMails.class.getClassLoader().getResourceAsStream(propertyFileName);
			if(input==null){
				logger.error("|--- Unable to read the property file :"+propertyFileName);
			}else{
				prop.load(input);
				logger.info("|--- Loaded the mail connection property file...");
				userCredentials.put("userName", prop.getProperty("userName"));
				userCredentials.put("passWord", prop.getProperty("passWord"));
				userCredentials.put("domain", prop.getProperty("domain"));
			}
		}catch(IOException ioex){
			logger.error("|---- Error encountered while loading the properties file", ioex);
		}finally{
			if(input!=null){
				try{
					input.close();
				}catch(IOException e){
					logger.error("|----- Error while closing the input stream for reading properties file", e);
				}
			}
		}
		if(userCredentials.size()==3){
			logger.info("|-----  successfully obtained the user credentals");
		}else{
			logger.info("|----- could not get the proper user credentials");
		}
		return userCredentials;
	}

}
