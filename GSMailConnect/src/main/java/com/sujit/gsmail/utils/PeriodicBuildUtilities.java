package com.sujit.gsmail.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.gs.security.gsauthn.kerberos.Authenticator;

public class PeriodicBuildUtilities {

	public static void main(String[] args) {
		//System.out.println("Value of the call is: "+PeriodicBuildUtilities.isPeriodBuildPresent("CONCRTSHEL"));
		/*HashMap<String, String> vals = PeriodicBuildUtilities.getContinuousBuildDetails("CONCRTSHEL");
		for(String key : vals.keySet()){
			System.out.println(key + " -> "+ vals.get(key));
		}*/
		
		createPeriodicBuild("CONCRLS");
	}
	
	/**
	 * Method to check if a periodic build exists given a JIRAKEY.
	 * @param jiraKey
	 * @return
	 */
	public static boolean isPeriodBuildPresent(String jiraKey){
		
		Authenticator authenticator = Authenticator.getCurrentUserAuthenticator("roysuj");  
		String cookies = authenticator.getToken();
		boolean result = false;
		try {
		    URL myURL = new URL("http://imd.jenkins.csf.services.gs.com:44080/jenkins/view/"+jiraKey+"/job/"+jiraKey+"-periodicBuild/");
		    HttpURLConnection con =  (HttpURLConnection)myURL.openConnection();
		    con.setRequestProperty("Cookie", "GSSSO=" + cookies);  
		    con.setRequestMethod("GET");
		    int responseCode = con.getResponseCode();
		    System.out.println("[[Response Code]]:"+responseCode);
		    if(responseCode==HttpURLConnection.HTTP_OK){
		    	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            if(response.toString().contains("<html><head><title>Redirection</title>")){
	            	result = true;
	            	System.out.println("Looks like periodic build is already present, but a gsauthnj failure. Response obtained:  -> "+response.toString());
	            }else if(response.toString().contains("HTTP Status 404") || response.toString().contains("The requested resource is not available.")){
					result = false;
				}else{
					result = true;
				}
		    }else{
		    	result = false;
		    }
		    if(con!=null){
            	con.disconnect();
            }
		} 
		catch (MalformedURLException e) { 
		    e.printStackTrace();
		} 
		catch (IOException e) {   
		    e.printStackTrace();
		}
		return result;
	}
	
	public static String createPeriodicBuild(String jiraKey){
		String buildLink = null;
		HashMap<String, String> contiBuildParams = PeriodicBuildUtilities.getContinuousBuildDetails(jiraKey);
		if(contiBuildParams!=null && contiBuildParams.size()==2){
			/*
			 * Hit the URL 'http://imd.jenkins.csf.services.gs.com:44080/jenkins/job/createPeriodicBuild/buildWithParameters' and post with the required parameters
			 */
			try {
				String urlParameters ="product.key=" + URLEncoder.encode(jiraKey, "UTF-8")+"&product.svn.url=" + URLEncoder.encode(contiBuildParams.get("svnlocation"), "UTF-8")+"&product.application.os="+URLEncoder.encode("linux", "UTF-8")+"&product.email.dl="+URLEncoder.encode(contiBuildParams.get("email"), "UTF-8");
				Authenticator authenticator = Authenticator.getCurrentUserAuthenticator("roysuj");  
				String cookies = authenticator.getToken();
				URL myURL = new URL("http://imd.jenkins.csf.services.gs.com:44080/jenkins/job/createPeriodicBuild/buildWithParameters");
				HttpURLConnection con =  (HttpURLConnection)myURL.openConnection();
				con.setRequestProperty("Cookie", "GSSSO=" + cookies);  
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				con.setRequestProperty("Content-Language", "en-US");  
				con.setUseCaches (false);
				con.setDoInput(true);
				con.setDoOutput(true);
				//Send request as POST
				DataOutputStream wr = new DataOutputStream (con.getOutputStream ());
				wr.writeBytes (urlParameters);
				wr.flush ();
				wr.close ();
				if(con.getResponseCode()==HttpURLConnection.HTTP_OK || con.getResponseCode()==HttpURLConnection.HTTP_CREATED || con.getResponseCode()==HttpURLConnection.HTTP_MOVED_TEMP){
					/* check for build URL, check if that's completed, then check if the periodic build is created by another method in this class, and if all looks good, then send back email
					 * to the senders in cc's with the periodic build link. Better if this happens in a separate thread.
					 */
					System.out.println("|-------- Periodic build created successfully. Enabling it....");
					enablePeriodicBuild(cookies, jiraKey);
					System.out.println("|-------- Periodic build created for '"+jiraKey+"' has been enabled.");
				}
				if(con!=null){
	            	con.disconnect();
	            }
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else{
			System.out.println("|---- Unable to queue the periodic build as all the required parameters cannot be obtained from continuous build");
		}
		return buildLink;
	}
	
	/**
	 * Method to get the parameters from continuous build, so that they can be passed onto periodic build creation method
	 * @param jiraKey
	 * @return
	 */
	public static HashMap<String, String> getContinuousBuildDetails(String jiraKey){
		HashMap<String, String> params = new HashMap<String, String>();
		Authenticator authenticator = Authenticator.getCurrentUserAuthenticator("roysuj");  
		String cookies = authenticator.getToken();
		try{
			URL myURL = new URL("http://imd.jenkins.csf.services.gs.com:44080/jenkins/view/"+jiraKey+"/job/"+jiraKey+"-continuousBuild/config.xml");
		    HttpURLConnection con =  (HttpURLConnection)myURL.openConnection();
		    con.setRequestProperty("Cookie", "GSSSO=" + cookies);  
		    con.setRequestMethod("GET");
		    int responseCode = con.getResponseCode();
		    System.out.println("[["+jiraKey+" Jenkins Continuous build configuration page response code]]:"+responseCode);
		    if(responseCode==HttpURLConnection.HTTP_OK){
		    	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            SAXReader reader = new SAXReader();
	            Document document = reader.read(in);
	            //System.out.println("Root element :" + document.getRootElement().getName());
	            Node scmLocation = document.selectSingleNode("/project/scm/locations/hudson.scm.SubversionSCM_-ModuleLocation/remote");
	            Node dlMail = document.selectSingleNode("/project/publishers/hudson.tasks.Mailer/recipients");
	            if(scmLocation!=null){
	            	params.put("svnlocation", scmLocation.getStringValue());
	            }
	            if(dlMail!=null){
	            	params.put("email", dlMail.getStringValue());
	            }
	            in.close();
	            if(con!=null){
	            	con.disconnect();
	            }
		    }
		}catch (MalformedURLException e) { 
		    e.printStackTrace();
		} 
		catch (IOException e) {   
		    e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return params;
	}
	
	public static void enablePeriodicBuild(String cookies, String jiraKey){
		try{
			URL pBuildEnableUrl = new URL("http://imd.jenkins.csf.services.gs.com:44080/jenkins/view/"+jiraKey+"/job/"+jiraKey+"-periodicBuild/enable");
			HttpURLConnection conn =  null;
			conn =  (HttpURLConnection)pBuildEnableUrl.openConnection();
		    conn.setRequestProperty("Cookie", "GSSSO=" + cookies);  
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    conn.setRequestProperty("Content-Language", "en-US");  
		    conn.setUseCaches (false);
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        //Send request
	        DataOutputStream wr = new DataOutputStream (conn.getOutputStream ());
	        // wr.writeBytes (urlParameters);
	        wr.flush ();
	        wr.close ();
	        if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
	        	System.out.println("|---------------- periodic build for "+jiraKey+" has been enabled");
	        }
	        if(conn!=null){
	        	conn.disconnect();
	        }
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
