package com.sujit.gsmail.reader;

import java.net.URL;
import java.text.SimpleDateFormat;

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
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.EmailMessageSchema;
import microsoft.exchange.webservices.data.core.service.schema.FolderSchema;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.property.complex.Mailbox;
import microsoft.exchange.webservices.data.property.definition.PropertyDefinitionBase;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;


public class CheckMailExchange {

	public static void main(String[] args) throws Exception {
		URL url = new URL("https://ews2007.ny.email.gs.com/ews/exchange.asmx");
		ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
		ExchangeCredentials credentials = new WebCredentials("roysuj", "","FIRMWIDE");
		service.setTraceEnabled(true);
		service.setCredentials(credentials);
		service.setUrl(url.toURI());
		service.setPreAuthenticate(true);
		//service.autodiscoverUrl("Sujit.Roy@ny.email.gs.com", new RedirectionUrlCallback());

		Mailbox mailbox = new Mailbox("Sujit.Roy@ny.email.gs.com");
		FolderId folder = new FolderId(WellKnownFolderName.Inbox, mailbox);
		PropertySet itempropertyset = new PropertySet(BasePropertySet.FirstClassProperties);
		itempropertyset.setRequestedBodyType(BodyType.Text);
		ItemView view = new ItemView(5);
		view.setPropertySet(itempropertyset);
		view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Descending);
		
		//SearchFilter sf = new SearchFilter.SearchFilterCollection(LogicalOperator.And, new SearchFilter.IsEqualTo(EmailMessageSchema.IsRead, false), new SearchFilter.);
		SearchFilter sf = new SearchFilter.ContainsSubstring(EmailMessageSchema.Categories, "IndiPushTag");
		
		FindItemsResults<Item> items = service.findItems(folder, sf ,view);
		for(Item i : items){
			//System.out.println(i.getSubject());		
			if(i instanceof EmailMessage){
				EmailMessage message = EmailMessage.bind(service, new ItemId(i.getId().toString()));
				message.load(itempropertyset); // (added for getting text only mail body, without the HTML. But it does not recognizes some MS characters. That is, its not UTF specific)
				SimpleDateFormat sdf = new SimpleDateFormat("EEEEE, MMMM dd, yyyy hh:mm a");
				System.out.println(message.getSender().getName()+"|--->|"+sdf.format(message.getDateTimeSent())+"|-------|"+message.getCategories().getString(0));
				message.getCategories().clearList();
				message.update(ConflictResolutionMode.NeverOverwrite);
			}
			
		}
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		/*FolderView fview = new FolderView(30);
		PropertySet ps = new PropertySet(BasePropertySet.IdOnly);
		ps.add(FolderSchema.DisplayName);
		fview.setPropertySet(ps);
		fview.setTraversal(FolderTraversal.Deep);
		FindFoldersResults res = service.findFolders(WellKnownFolderName.MsgFolderRoot, fview);
		for(Folder f: res.getFolders()){
			System.out.println(f.getDisplayName());
			if(f.getDisplayName().equals("IC")){
				Folder wmfolder = Folder.bind(service, f.getId());
				ItemView view = new ItemView(10);
				view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Descending);
				FindItemsResults<Item> items = service.findItems(wmfolder.getId(), view);
				for(Item i : items){
					//System.out.println(i.getSubject());		
					if(i instanceof EmailMessage){
						EmailMessage message = EmailMessage.bind(service, new ItemId(i.getId().toString()));
						System.out.println(message.getSender().getName() + "| ----- |"+ ExtractBodyTextFromHTMLMail.getRecentBodyAsString(message.getBody().toString()));
					}
					
				}
				break;
			}
		}*/
		service.close();
	}
}


