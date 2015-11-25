package com.sujit.gsmail.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import static org.quartz.JobBuilder.*;

import com.sujit.gsmail.reader.ReadICFolderMail;

public class ICReaderScheduler {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		 SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory(); 
		  Scheduler sched = schedFact.getScheduler(); 
		  sched.start(); 
		  // define the job and tie it
		  JobDetail job = newJob(ReadICFolderMail.class).withIdentity("myJob", "group1").build();
		  // Trigger the job to run now, and then every 40 seconds 
		  Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(40).repeatForever()).build(); 
		  // Tell quartz to schedule the job using our trigger 
		  sched.scheduleJob(job, trigger);
		  
		  Thread.sleep(40000);
		  
		  sched.shutdown();


	}

}
