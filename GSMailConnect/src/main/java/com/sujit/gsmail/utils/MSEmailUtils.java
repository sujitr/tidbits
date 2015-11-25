package com.sujit.gsmail.utils;

import java.util.ArrayList;
import java.util.List;

import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;

public class MSEmailUtils {
	public static ArrayList<String> createEmailAddressList(EmailAddressCollection coll){
		ArrayList<String> result = null;
		if(coll!=null){
			result = new ArrayList<String>();
			List<EmailAddress> l = coll.getItems();
			for(EmailAddress a : l){
				result.add(a.getAddress());
			}
		}
		return result;
	}
}
