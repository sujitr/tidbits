package com.sujit.gsmail.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import static org.quartz.JobBuilder.*;

import com.sujit.gsmail.reader.ReadInboxFolderMail;

public class InboxReaderScheduler {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		 SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory(); 
		  Scheduler sched = schedFact.getScheduler(); 
		  sched.start(); 
		  // define the job and tie it
		  JobDetail job = newJob(ReadInboxFolderMail.class).withIdentity("myJob2", "group2").build();
		  // Trigger the job to run now, and then every 40 seconds 
		  // Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger2", "group2").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(40).repeatForever()).build(); 
		  Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger2", "group2").startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 11-18 ? * MON-FRI")).build();
		  // Tell quartz to schedule the job using our trigger 
		  sched.scheduleJob(job, trigger);
		  
		  /*Thread.sleep(40000);
		  sched.shutdown();*/

	}

}
