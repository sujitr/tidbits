package com.sujit.gsmail.reader;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.ietf.jgss.MessageProp;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sujit.gsmail.analyzer.AutosysJobFailureMailAnalyzer;
import com.sujit.gsmail.analyzer.IndividualPushMailAnalyzer;
import com.sujit.gsmail.analyzer.PeriodicBuildMailAnalyzer;
import com.sujit.gsmail.utils.ExtractBodyTextFromHTMLMail;
import com.sujit.gsmail.utils.MSEmailUtils;
import com.sujit.gsmail.utils.PeriodicBuildUtilities;
import com.sujit.gsmail.worker.AutosysJobFailCheckWorker;
import com.sujit.gsmail.worker.IndividualPushWorker;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.FolderTraversal;
import microsoft.exchange.webservices.data.core.enumeration.search.LogicalOperator;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.enumeration.service.ConflictResolutionMode;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.EmailMessageSchema;
import microsoft.exchange.webservices.data.core.service.schema.FolderSchema;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.property.complex.Mailbox;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;


public class ReadWMCTOEFolderMail extends ReaderBase implements Job{

	final static Logger logger = LoggerFactory.getLogger(ReadWMCTOEFolderMail.class);
	
	public ReadWMCTOEFolderMail(String userName, String passWord, String domain) {
		super(userName, passWord, domain);
	}
	
	protected Object readWMCTOEMails() {
		URL url = null;
		try {
			url = new URL("https://ews2007.ny.email.gs.com/ews/exchange.asmx");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
		ExchangeCredentials credentials = new WebCredentials(getUserName(), getPassWord(), getDomain());
		service.setTraceEnabled(true);
		service.setCredentials(credentials);
		try {
			service.setUrl(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		service.setPreAuthenticate(true);
		//service.autodiscoverUrl("Sujit.Roy@ny.email.gs.com", new RedirectionUrlCallback());

		
		FolderView fview = new FolderView(30);
		PropertySet ps = new PropertySet(BasePropertySet.IdOnly);
		try {
			ps.add(FolderSchema.DisplayName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fview.setPropertySet(ps);
		fview.setTraversal(FolderTraversal.Deep);
		FindFoldersResults res = null;
		try {
			res = service.findFolders(WellKnownFolderName.MsgFolderRoot, fview);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// get hold of the WM-CT-OE folder
		Folder wmctoeFolder = null;
		for(Folder f: res.getFolders()){
			try {
				if(f.getDisplayName().equals("WM-CT-Operational-Engineering")){
					wmctoeFolder = f;
					break;
				}
			} catch (ServiceLocalException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Block to read all the unread messages, check for mails which can be action'ed upon from autosys job failure, action them and mark them as read.
		 * Leaves the other mails as it is.
		 */
		SearchFilter sf = new SearchFilter.SearchFilterCollection(LogicalOperator.And, new SearchFilter.IsEqualTo(EmailMessageSchema.IsRead, false));
		FindItemsResults<Item> items = null;
		try {
			logger.info("[---------------------------    Scanning WM-CT-OE Folder --------------------------------]");
			items = service.findItems(wmctoeFolder.getId(), sf, new ItemView(50));
			logger.info("| Total number of unread mails in WM-CT-OE Folder (as of "+new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(new Date())+") : "+items.getTotalCount());
			int noOfItems = items.getTotalCount();
			if(noOfItems > 0 && noOfItems < 100){
				for(Item i : items){
					if(i instanceof EmailMessage){
						EmailMessage message;
						message = EmailMessage.bind(service, new ItemId(i.getId().toString()));
						logger.info("[WM-CT-OE Mail] "+message.getSender().getName() + "| ----- |"+ message.getSubject());//)+" |-----| "+ ExtractBodyTextFromHTMLMail.getRecentBodyAsString(message.getBody().toString()));
						/* Performing Autosys Job Failure Analysis on this mail */
						logger.info("|-- performing autosys job failure analysis on this email...");
						AutosysJobFailureMailAnalyzer ajfma = new AutosysJobFailureMailAnalyzer(MSEmailUtils.createEmailAddressList(message.getToRecipients()), MSEmailUtils.createEmailAddressList(message.getCcRecipients()), message.getSubject(), ExtractBodyTextFromHTMLMail.getRecentBodyAsString(message.getBody().toString()), message.getFrom().getName());
						ArrayList<String> failedJobs = ajfma.analyzeAutosysFailedJobs();
						if (null!=failedJobs && failedJobs.size()>0) {
							String fromName = message.getSender().getName();
							SimpleDateFormat sdf = new SimpleDateFormat("EEEEE, MMMM dd, yyyy hh:mm a");
							String sentStamp = sdf.format(message.getDateTimeSent());
							String toNames = message.getDisplayTo().replaceAll(";", ", ");
							String ccNames = message.getDisplayCc().replaceAll(";", ", ");
							String bodyDiv = ExtractBodyTextFromHTMLMail.getBodyDivHTMLAsString(message.getBody().toString());
							StringBuilder finalToFieldSb = new StringBuilder(message.getSender().getAddress());
							EmailAddressCollection toColl = message.getToRecipients();
							for(EmailAddress eaddr : toColl){
								finalToFieldSb.append(", ").append(eaddr.getAddress());
							}
							StringBuilder finalCcFieldSb = new StringBuilder(message.getSender().getAddress());
							EmailAddressCollection ccColl = message.getCcRecipients();
							for(EmailAddress eaddr : ccColl){
								finalCcFieldSb.append(", ").append(eaddr.getAddress());
							}
							boolean isCheckDoneForThisMessage = AutosysJobFailCheckWorker.performJobCheck(failedJobs, fromName, sentStamp, toNames, ccNames, message.getSubject(), bodyDiv, finalToFieldSb.toString(), finalCcFieldSb.toString());
							if(isCheckDoneForThisMessage){
								logger.info("|-- autosys job failure related activity completed for this email successfully");
								// mark the message as read
								message.setIsRead(true);
								message.update(ConflictResolutionMode.NeverOverwrite);
							}else{
								logger.info("|-- autosys job failure activity did not resulted in any final action on this email");
							}
						}else{
							logger.info("|-- No action taken on this email from autosys job failure perspective");
						}
					}
				}
			}else{
				logger.info("|-- WM-CT-OE folder unread message count is either 0 or has some anomaly. Not taking any actions.");
			}
			logger.info("[---------------------------    WM-CT-OE Folder scan complete for this round  --------------------------------]");
		} catch (ServiceLocalException e) {
			logger.error("|-- There is something wrong with the service call.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		service.close();
		return true;
	}
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		readWMCTOEMails();
	}
	
	public void scanWMCTOEMails(){
		Callable<Object> callable = new Callable<Object>() {
            public Object call() throws Exception {
                return readWMCTOEMails();
            }
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        int attempt = 0;
        while(attempt < 2){
        	Future<Object> task = executorService.submit(callable);
        	try {
                // ok, wait for 75 seconds max
                Object result = task.get(75, TimeUnit.SECONDS);
                System.out.println("|---- ************* Finished with result: " + result);
                attempt=2;
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
            	task.cancel(true);
            	if(attempt == 0){
            		 System.out.println("|----************* timeout...killing it, and retrying...");
            	}else{
            		System.out.println("|----************* timeout...killing it, and stopping for this round.");
            	}
            	attempt++;
            } catch (InterruptedException e) {
                System.out.println("|----************* interrupted");
                attempt = 2;
            }
        }
	}
	
}


