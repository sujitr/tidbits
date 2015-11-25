package com.sujit.GSMailConnect;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sujit.gsmail.analyzer.AutosysJobFailureMailAnalyzer;

public class AutosysJobFailureAnalysisTest {
	
	AutosysJobFailureMailAnalyzer analyzer = null;
	
	ArrayList<String> toAddresses = null;
	ArrayList<String> ccAddresses = null;
	
	@Before
	public void initObjects(){
		toAddresses = new ArrayList<String>();
		toAddresses.add("wm-ct-operational-engineering@ny.email.gs.com");
		ccAddresses = new ArrayList<String>();
		ccAddresses.add("gs-pwm-prod-mgmt@ny.email.gs.com");
	}

	@Test
	public void testWeekendJobFailures() {
		//fail("Not yet implemented");
		
		String testSubject = "AMS/R Reboot autosys failures | OE";
		String testBody = "Team,  Below jobs failed during AMS/R Reboot window, please take care.  Job Name	Start_Date	Start_Time	End_Date	End_Time	Status CLSVPWM_AP_INTERACT_BOX	9/14/2015	0:06:04	9/19/2015	15:33:13	FA CLSVPWM_AP_INTER_JB1	9/14/2015	0:06:10	9/19/2015	15:33:09	FA CLSVPWM_EODTC_BOX	9/18/2015	18:00:16	9/19/2015	15:13:17	FA CLSVPWM_EODTC_JB1	9/18/2015	18:00:54	9/19/2015	15:13:13	FA CLSVPWM_GWC_SYNCHER_BOX	9/14/2015	0:00:04	9/19/2015	15:33:33	FA CLSVPWM_GWC_SYNC_JB1	9/14/2015	0:00:13	9/19/2015	15:33:29	FA CLSVPWM_PET_ALSYDAEMON_BOX	9/14/2015	0:06:04	9/19/2015	15:32:52	FA CLSVPWM_PET_ALSY_JB1	9/14/2015	0:06:10	9/19/2015	15:32:48	FA CLSVPWM_WC_SYNCHER_BOX	9/14/2015	0:06:04	9/19/2015	15:33:03	FA CLSVPWM_WC_SYNC_JB1	9/14/2015	0:06:10	9/19/2015	15:32:59	FA CLSVPWM_GWC_DG1002_BOX	9/17/2015	6:05:04	9/19/2015	15:32:32	FA CLSVPWM_GWC_1002_JB1	9/17/2015	6:05:10	9/19/2015	15:32:28	FA  Best Regards, Satheesh PWM Production Management Work : +1 (212) 934-7741  Prod Mgmt. Hotline: 1(917)-343-4739 | Mail to: PWM Prod Management ";
		
		analyzer = new AutosysJobFailureMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.gs.com");
		ArrayList<String> jobs = analyzer.analyzeAutosysFailedJobs();
		assertEquals(12, jobs.size());
		
	}

}
