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

import com.sujit.gsmail.analyzer.IndividualPushMailAnalyzer;
import com.sujit.gsmail.analyzer.PeriodicBuildMailAnalyzer;
import com.sujit.gsmail.utils.ExtractBodyTextFromHTMLMail;
import com.sujit.gsmail.utils.IndividualPushResponse;
import com.sujit.gsmail.utils.MSEmailUtils;
import com.sujit.gsmail.utils.MailMetaBox;
import com.sujit.gsmail.utils.PeriodicBuildUtilities;
import com.sujit.gsmail.worker.IndividualPushWorker;
import com.sujit.gsmail.worker.PeriodicBuildWorker;

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
import microsoft.exchange.webservices.data.property.complex.StringList;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;


public class ReadICFolderMail extends ReaderBase implements Job{
	
	final static Logger logger = LoggerFactory.getLogger(ReadICFolderMail.class);
	
	public ReadICFolderMail(String userName, String passWord, String domain) {
		super(userName, passWord, domain);
	}
	
	/**
	 * This is the core method to read the unread messages in IC Folder, scans then for individual push OR periodic build requests
	 * and if yes, actions on them. Ideally this method should finish by 75 seconds for around 1/2 action items. Sometimes
	 * it takes more than that time as MS Exchange usually fails by socket timeout.  
	 * @return
	 * 	: Object denoting the state of completion
	 */
	protected Object readICMails() {
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
		// get hold of the IC folder
		Folder icfolder = null;
		for(Folder f: res.getFolders()){
			try {
				if(f.getDisplayName().equals("IC")){
					//icfolder = Folder.bind(service, f.getId());
					icfolder = f;
					break;
				}
			} catch (ServiceLocalException e) {
				e.printStackTrace();
			}
		}
		
		
		
		/**
		 * Block to just read the top 10 mails with subject and body
		 */
		/*ItemView view = new ItemView(10);
		FindItemsResults<Item> items = null;
		try {
			view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Descending);
			items = service.findItems(icfolder.getId(), view);
			for(Item i : items){
				if(i instanceof EmailMessage){
					EmailMessage message = EmailMessage.bind(service, new ItemId(i.getId().toString()));
					System.out.println(message.getSender().getName() + "| ----- |"+ ExtractBodyTextFromHTMLMail.getRecentBodyAsString(message.getBody().toString()));
				}
			}
		} catch (ServiceLocalException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		
		
		
		/**
		 * Block to read all the unread messages, check for mails which can be action'ed upon from periodic build point, action them and mark them as read.
		 * Leaves the other mails as it is.
		 */
		SearchFilter sf = new SearchFilter.SearchFilterCollection(LogicalOperator.And, new SearchFilter.IsEqualTo(EmailMessageSchema.IsRead, false));
		FindItemsResults<Item> items = null;
		try {
			logger.info("[---------------------------    Checking IC Folder for unread mail scan operation --------------------------------]");
			items = service.findItems(icfolder.getId(), sf, new ItemView(50));
			logger.info("| Total number of unread mails in IC Folder (as of "+new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(new Date())+") : "+items.getTotalCount());
			int noOfItems = items.getTotalCount();
			if(noOfItems > 0 && noOfItems < 100){
				for(Item i : items){
					if(i instanceof EmailMessage){
						EmailMessage message;
						message = EmailMessage.bind(service, new ItemId(i.getId().toString()));
						performIndividualPushSequence(message, true);
						/* Performing Periodic Build Analysis on this mail */
						logger.info("|-- performing periodic build analysis on this email...");
						PeriodicBuildMailAnalyzer pbma = new PeriodicBuildMailAnalyzer(MSEmailUtils.createEmailAddressList(message.getToRecipients()), MSEmailUtils.createEmailAddressList(message.getCcRecipients()), message.getSubject(), ExtractBodyTextFromHTMLMail.getRecentBodyAsString(message.getBody().toString()), message.getFrom().getName());
						ArrayList<String> jiraKeys = pbma.analyzeCreatePeriodicBuildRequest();
						if(jiraKeys!=null && !jiraKeys.isEmpty()){
							MailMetaBox emailMetaBox = new MailMetaBox(message);
							PeriodicBuildWorker.createPeriodicBuilds(jiraKeys);
							// mark the message as read
							message.setIsRead(true);
							message.update(ConflictResolutionMode.NeverOverwrite);
							PeriodicBuildWorker.sendUpdate(emailMetaBox, "Periodic build(s) have been created.");
						}else{
							logger.info("|-- No action taken on this email from periodic build perspective");
						}
						/* periodic build analysis complete */
					}
				}
			}else{
				logger.info("|-- IC folder unread message count is either 0 or has some anomaly. Not taking any actions.");
			}
			logger.info("[---------------------------    IC folder unread mail scan operation complete for this round  --------------------------------]");
			logger.info("{-----------------------------------------  Checking IC folder for earlier individual push requests kept on hold because of nimbl push...  --------------}");
			SearchFilter sfT = new SearchFilter.ContainsSubstring(EmailMessageSchema.Categories, "IndiPushTag");
			FindItemsResults<Item> indiPushTrackerItems = service.findItems(icfolder.getId(),sfT, new ItemView(30));
			logger.info("{------ Total number of tagged Individual push items in IC Folder : "+indiPushTrackerItems.getTotalCount());
			for(Item indiPushItem : indiPushTrackerItems){
				EmailMessage indiPushMessage = EmailMessage.bind(service, new ItemId(indiPushItem.getId().toString()));
				performIndividualPushSequence(indiPushMessage, false);
			}
			logger.info("{-----------------------------------------  earlier individual push requests on hold check complete  ----------------------------------------------}");
		} catch (ServiceLocalException e) {
			logger.error("|-- There is something wrong with the service call.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		service.close();
		return true;
	}
	
	/**
	 * This method is for calling the IC reader directly via quartz scheduler, scheduled to read only IC folder mails
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		readICMails();
	}
	
	/**
	 * This method is a timeout controlled call to read IC Folder functionality to address the issue
	 * of socket timeout exceptions which comes infrequently while accessing the MS Exchange.
	 * It waits for 75 seconds for response, else kills the last attempt and retries once more.
	 */
	public void scanICMails(){
		Callable<Object> callable = new Callable<Object>() {
            public Object call() throws Exception {
                return readICMails();
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
	
	private void performIndividualPushSequence(EmailMessage message, boolean isFreshMail) throws Exception {
		logger.info("[IC Mail] "+message.getSender().getName() + "| ----- |"+ message.getSubject()+" |-----| "+ ExtractBodyTextFromHTMLMail.getRecentBodyAsString(message.getBody().toString()));
		logger.info("|-- performing individual push analysis on this email...");
		IndividualPushMailAnalyzer ipma = new IndividualPushMailAnalyzer(MSEmailUtils.createEmailAddressList(message.getToRecipients()), MSEmailUtils.createEmailAddressList(message.getCcRecipients()), message.getSubject(), ExtractBodyTextFromHTMLMail.getRecentBodyAsString(message.getBody().toString()), message.getFrom().getName());
		String[] hostnames = ipma.analyzeIndividualPushRequest();
		if (null!=hostnames && hostnames.length>0) {
			MailMetaBox emailMetaBox = new MailMetaBox(message);
			IndividualPushResponse pushResponseData =  IndividualPushWorker.performPush(hostnames);
			if(pushResponseData.isAllPushDone()){
				logger.info("|-- Individual push related activity complete for this email successfully");
				if(isFreshMail){
					// mark the message as read
					message.setIsRead(true);
					message.update(ConflictResolutionMode.NeverOverwrite);
				}else{
					// just remove the individual push tagging from this message
					StringList sl = message.getCategories();
					if(sl.remove("IndiPushTag")){
						logger.info("|---- indipush tagging removed from this message");
					}else{
						logger.info("|---- unable to remove indipush tagging from this message. PLEASE REMOVE MANUALLY.");
					}
					message.setCategories(sl);
					message.update(ConflictResolutionMode.AlwaysOverwrite);
				}
				IndividualPushWorker.sendUpdate(emailMetaBox, pushResponseData);
			}else if(pushResponseData.getMode() == IndividualPushResponse.ResponseMode.NIMBLRUNNING){
				if (isFreshMail) {
					message.setIsRead(true);
					// setting the Individual Push tag so that it can be tracked later, when NIMBL push finishes
					StringList sl = new StringList();
					sl.add("IndiPushTag");
					message.setCategories(sl);
					message.update(ConflictResolutionMode.AlwaysOverwrite);
					IndividualPushWorker.sendUpdate(emailMetaBox, pushResponseData);
					logger.info("|---- NIMBL push is running. Sending update accordingly.");
				}else{
					// no need to do anything as the mail is already marked
				}
			}else{
				logger.info("|-- Individual push activity did not result in any final action on this email. Manual check needed.");
			}
		}else{
			logger.info("|-- No action taken on this email from individual push perspective");
		}
	}
	
}


