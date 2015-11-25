package com.sujit.GSMailConnect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import com.sujit.gsmail.analyzer.IndividualPushMailAnalyzer;


public class IndividualPushAnalysisTest {
	
	IndividualPushMailAnalyzer testAnalyzer = null;
	
	ArrayList<String> toAddresses = null;
	ArrayList<String> ccAddresses = null;
	
	@Test
    public void testAnalysisOne() {
		String testSubject = "push mobile-sales to individual PQA box";
		String testBody = "Hi Build team, Could you please push MOBSALES_52_0_RC-1 to P QA box  qapwmcd21.ny.imd.gs.com. The Nimbl push to PQA did not run since yesterday. 	Regards, Savitha";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertEquals("qapwmcd21.ny.imd.gs.com",hosts[0]);
    }
	
	@Test
    public void testAnalysisTwo() {
		String testSubject = "Individual push request - latest CAIS-Legacy and Docbuilder RC to QA";
		String testBody = "Hi Build Team,   Please push the latest CAIS-Legacy and Docbuilder RC builds to all QA hosts mentioned below:   Cais-Legacy: http://conductor.services.gs.com/sdlc/productVersion/productVersionId/93219/productKey/CAISLGCY Caos-rep – qapwmcd21 .ny.imd.gs.com ads-tx-caos – qapwmcd21 .ny.imd.gs.com Cais-approval-qapwmcd22 .ny.imd.gs.com   DOCBUILDER : http://conductor.services.gs.com/sdlc/productVersion/productVersionId/93220/productKey/DOCBUILDER   qapwmcd21 .ny.imd.gs.com   Thanks, Sudeep Desai";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        Set<String> receivedSet = new HashSet<String>(Arrays.asList(hosts));
        Set<String> expectedSet = new HashSet<String>(); expectedSet.add("qapwmcd21.ny.imd.gs.com"); expectedSet.add("qapwmcd22.ny.imd.gs.com"); 
        assertTrue(expectedSet.equals(receivedSet));
    }
	
	@Test
    public void testAnalysisThree() {
		String testSubject = "Individual push request";
		String testBody = "Hi Team, Could you please push acct-ic-services build to QA box qapwmcd21 https://prod.deploy.nimbus.gs.com/ViewDistribution.action?distributionId=43319#placeholder;environmentTabs:QUALITY_ASSURANCE;installTabs71793:activities71793   Thanks, Gurveer";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertEquals("qapwmcd21.ny.imd.gs.com",hosts[0]);
    }
	
	@Test
    public void testAnalysisFour() {
		String testSubject = "Individual push request";
		String testBody = "Build Team, Can you please push market-data-platform build to qapwm-d134015-007.dc.gs.com host. Location: /home/jbuild/rsync_hudson_dist/market-data-platform/51.5 Thanks, Srinath";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertEquals("qapwm-d134015-007.dc.gs.com",hosts[0]);
    }
	
	/**
	 * Special test condition to check if a host can be extracted even if body does not contain any indication of push requests (and 
	 * subject did contain the push request indication). This test will fail currently as extraction logic still searches for push request related 
	 * keywords in body before searching for host-name. This logic needs to be updated a bit.
	 */
	@Test
    public void testAnalysisFive() {
		String testSubject = "RE: Push latest RC build for ciotools and ppttools in QA";
		String testBody = "Hi Sujit,   Please refer to hostname ” qapwmcd04.ny.imd.gs.com”.   Please do the needful, which is blocker for our regression testing.   Regards, Preetham";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertEquals("qapwmcd04.ny.imd.gs.com",hosts[0]);
    }
	
	@Test
    public void testAnalysisSix() {
		String testSubject = "RE: Push latest RC build for ciotools and ppttools in QA";
		String testBody = "Hi Sujit,   Please do an individual push for qa box ” qapwmcd04.ny.imd.gs.com”.      Regards, Preetham";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertEquals("qapwmcd04.ny.imd.gs.com",hosts[0]);
    }
	
	@Test
    public void testAnalysisSeven() {
		String testSubject = "Please run individual push job on Qapwmcd21";
		String testBody = "Build Team, Can you please run the individual push job on Qapwmcd21 Thanks, Doug Jaffa Goldman, Sachs & Co. Technology Division | PWM Account Opening  222 South Main, Floor 7 Salt Lake City, UT 84101 (801) 741-5545";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertEquals("qapwmcd21.ny.imd.gs.com",hosts[0]);
    }
	
	@Test
    public void testAnalysisEight() {
		String testSubject = "RE: PQA Nimble to QA build push disabled?";
		String testBody = "Hi Nandini,   Think the PQA push was disabled by someone because there were some tests being run. However we will now go ahead with the individual push.   PQA hostname - pqapwmcd21   Rgds, Sujit ";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertEquals("pqapwmcd21.ny.imd.gs.com",hosts[0]);
    }
	
	/**
	 * This test also checks for the null value in cc list, this occurred when someone sent a mail with nothing in cc fields,
	 * and the earlier code spat out a null pointer exception
	 */
	@Test
    public void testAnalysisNine() {
		String testSubject = "Push Acct-Ic-Services to qapwmcd21";
		String testBody = "Please push the build for Acct-ic service to QA";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, null, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertEquals("qapwmcd21.ny.imd.gs.com",hosts[0]);
    }
	
	@Test
	public void testRCAndPush(){
		String testSubject = "RC build For Mobile-Imperonsation Issue";
		String testBody = "Build Team, Can you please create new RC build for PWM IC Core Platform Java Components (BIMBL) build and push it to qapwmcd21 host, we have Weyni?s approval for the below ticket. http://prod.csftools.services.gs.com/csf3jira/browse/PWMICCORE-583 http://conductor.services.gs.com/sdlc/productVersion/productVersionId/88424/productKey/PWMICCOREJ/selectTab/productDashboard/panel/Back%20to%20Product Thanks, Srinath ";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertTrue(hosts == null);
	}
	
	
	/**
	 * There was a mail from Yekaterina Bolotnaya requesting a QA push, but the analyzer failed to pick up the hostnames properly resulting in a failure of the build.
	 * This test was created in response of that to check and improve the hostname pickup process
	 * (The mail was sent on Monday, November 09, 2015 07:37 AM)
	 */
	@Test
    public void testAnalysisYekaterinaBolotnaya() {
		String testSubject = "RE: User Info Service | Push build to QA";
		String testBody = "Build team  could you please initiate the push to qa? ·          DID: 180014 ·          Boxes: qapwm-d180014-003.dc.gs.com  ,  qapwm-d180014-004.dc.gs.com     Thanks";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, null, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertEquals(hosts.length, 2);
        List<String> hostlist = Arrays.asList(hosts);
        assertTrue(hostlist.contains("qapwm-d180014-003.dc.gs.com")&&hostlist.contains("qapwm-d180014-004.dc.gs.com")&&hostlist.size()==2);
    }
	
	@Test
	public void testUnwantedQAHostNames(){
		String testSubject = "RE: TMT [COTR-873]: EAP6 Opusconfig changes not pushed to QA";
		String testBody = "Hi Savitha,   We have started tmt app on jboss6 by passing parameter and bounced with the help of eams team so ,please proceed with your testing in QA ,We will check with build team to push ticket changes to QA with new RC build.   Log Location and Log : /home/snoopy/rundir/qapwmcd24.ny.imd.gs.com_tmt_31718 /home/jbuild/curlinks/default/bin/startwl --no-infer-build --config-app=tmt --default-user-port=snoopy:31718 --verbose -M 200M -J -XX:PermSize=64M -J -XX:MaxPermSize=128M -M 500M -J -Xms500M --jboss-eap6 Creating config for JBoss EAP6 Use of uninitialized value in con   Thanks & Regards, Sandeep";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertTrue(hosts.length == 0);
	}
	
	
	
	@Test
    public void testNonIndividualPushMail() {
		String testSubject = "Hi Abra ka Dabra";
		String testBody = "Hi Build team, Could you please Let us know the status of GS. 	Regards, Savitha";
		
		testAnalyzer = new IndividualPushMailAnalyzer(toAddresses, ccAddresses, testSubject, testBody, "testCandidate@test.com");
        String[] hosts = testAnalyzer.analyzeIndividualPushRequest();
        assertTrue(hosts==null);
    }
	
	
	
	
	
	@Before
	public void initObjects(){
		toAddresses = new ArrayList<String>();
		toAddresses.add("gs-imd-build-meisters@internal.email.gs.com");
		ccAddresses = new ArrayList<String>();
		ccAddresses.add("a@b.com");
	}

}
