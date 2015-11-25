package com.sujit.gsmail.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import com.gs.security.gsauthn.kerberos.Authenticator;

public class Check {

	public static void main(String[] args) {
		Authenticator authenticator = Authenticator.getCurrentUserAuthenticator("roysuj");  
		String cookies = authenticator.getToken();
		BasicCookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie gscookie = new BasicClientCookie("GSSSO",cookies);
		gscookie.setDomain(".gs.com");
		gscookie.setPath("/");
		cookieStore.addCookie(gscookie);
		HttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build(); 
		
		HttpResponse response2 = null;
		HttpGet getMethod = new HttpGet("http://imd.jenkins.csf.services.gs.com:44080/jenkins/view/DOCBUILDER/job/DOCBUILDER-periodicBuild/");
		try{
			response2 = httpclient.execute(getMethod);
			HttpEntity responseEntity = response2.getEntity();
			String responseContent = EntityUtils.toString(responseEntity);
			System.out.println(response2.getStatusLine().getStatusCode());
			System.out.println(responseContent);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
