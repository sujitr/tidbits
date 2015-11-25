package com.sujit.gsmail.utils;

import java.text.SimpleDateFormat;

import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;

public class MailMetaBox {
	private String fromNameForChain;
	private String sentStampForChain;
	private String toNamesForChain;
	private String ccNamesForChain;
	private String bodyDivForChain;
	private String toAddressesForEmail;
	private String ccAddressForEmail;
	private String subject;
	
	public MailMetaBox(EmailMessage message){
		super();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEEE, MMMM dd, yyyy hh:mm a");
		try {
			fromNameForChain = message.getSender().getName();
			sentStampForChain = sdf.format(message.getDateTimeSent());
			toNamesForChain = message.getDisplayTo();
			if(message.getDisplayCc()!=null){
				ccNamesForChain = message.getDisplayCc();
			}else{
				ccNamesForChain = null;
			}
			
			bodyDivForChain = ExtractBodyTextFromHTMLMail.getBodyDivHTMLAsString(message.getBody().toString());
			StringBuilder finalToFieldSb = new StringBuilder(message.getSender().getAddress());
			EmailAddressCollection toColl = message.getToRecipients();
			for(EmailAddress eaddr : toColl){
				finalToFieldSb.append(", ").append(eaddr.getAddress());
			}
			toAddressesForEmail = finalToFieldSb.toString();
			StringBuilder finalCcFieldSb = new StringBuilder();
			EmailAddressCollection ccColl = message.getCcRecipients();
			for(EmailAddress eaddr : ccColl){
				finalCcFieldSb.append(", ").append(eaddr.getAddress());
			}
			ccAddressForEmail = finalCcFieldSb.toString();
			subject = message.getSubject();
		} catch (ServiceLocalException e) {
			e.printStackTrace();
		}
		
	}

	public String getFromNameForChain() {
		return fromNameForChain;
	}

	public String getSentStampForChain() {
		return sentStampForChain;
	}

	public String getToNamesForChain() {
		return toNamesForChain;
	}

	public String getCcNamesForChain() {
		return ccNamesForChain;
	}

	public String getBodyDivForChain() {
		return bodyDivForChain;
	}

	public String getToAddressesForEmail() {
		return toAddressesForEmail;
	}

	public String getCcAddressForEmail() {
		return ccAddressForEmail;
	}
	
	public String getSubject(){
		return subject;
	}
	
}
