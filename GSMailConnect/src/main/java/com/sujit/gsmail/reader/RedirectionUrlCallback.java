package com.sujit.gsmail.reader;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.autodiscover.exception.AutodiscoverLocalException;

public class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {

	public boolean autodiscoverRedirectionUrlValidationCallback(String redirectionUrl) throws AutodiscoverLocalException {
		return redirectionUrl.toLowerCase().startsWith("https://");
	}

}
