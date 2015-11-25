package com.sujit.gsmail.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gs.security.gsauthn.kerberos.Authenticator;

public class IndividualPushBuildUtilities {

	public static void main(String[] args) {
		
	}
	
	public static String pushToIndividualQABox(String hostname) throws UnsupportedEncodingException, InterruptedException{
		String finalResponse = null;
		String urlParameters ="QA_HOSTNAME=" + URLEncoder.encode(hostname, "UTF-8");
		Authenticator authenticator = Authenticator.getCurrentUserAuthenticator("roysuj");  
		String cookies = authenticator.getToken();
		try {
		    URL myURL = new URL("http://d119030-005.dc.gs.com:8061/jenkins/view/ReleaseMgmt/job/PushNimbleToIndividualBox/buildWithParameters");
		    HttpURLConnection con =  (HttpURLConnection)myURL.openConnection();
		    con.setRequestProperty("Cookie", "GSSSO=" + cookies);  
		    con.setRequestMethod("POST");
		    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    con.setRequestProperty("Content-Language", "en-US");  
		    con.setUseCaches (false);
	        con.setDoInput(true);
	        con.setDoOutput(true);
	        //Send request
	        DataOutputStream wr = new DataOutputStream (con.getOutputStream ());
	        wr.writeBytes (urlParameters);
	        wr.flush ();
	        wr.close ();
	        //Get Response	
	        /*InputStream is = con.getInputStream();
	        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	        String line;
	        StringBuffer response = new StringBuffer(); 
	        while((line = rd.readLine()) != null) {
	          response.append(line);
	          response.append('\r');
	        }
	        rd.close();
	        System.out.println("|------ post response is :"+response.toString());*/
	        if(con.getResponseCode()==HttpURLConnection.HTTP_OK || con.getResponseCode()==HttpURLConnection.HTTP_CREATED || con.getResponseCode()==HttpURLConnection.HTTP_MOVED_TEMP){
	        	Thread.sleep(8000);
	        	URL checkURL = new URL("http://d119030-005.dc.gs.com:8061/jenkins/view/ReleaseMgmt/job/PushNimbleToIndividualBox/api/json");
			    HttpURLConnection con2 =  (HttpURLConnection)checkURL.openConnection();
			    con2.setRequestProperty("Cookie", "GSSSO=" + cookies);  
			    con2.setRequestMethod("GET");
			    int responseCode = con2.getResponseCode();
			    if(responseCode==HttpURLConnection.HTTP_OK){
			    	BufferedReader in = new BufferedReader(new InputStreamReader(con2.getInputStream()));
					String inputLine;
					StringBuffer checkResponse = new StringBuffer();
					while ((inputLine = in.readLine()) != null) {
						checkResponse.append(inputLine);
					}
					in.close();
					String checkJsonResponse = checkResponse.toString();
					//System.out.println(checkResponse.toString());
					if(checkJsonResponse!=null && !checkJsonResponse.isEmpty()){
						finalResponse = MyJsonParser.getQueueStatus(checkJsonResponse); 
					}else{
						finalResponse = "Due to some system issues the link for ongoing push to '"+hostname+"' is not present as of now. Will post it as soon as it becomes available.";
						System.out.println("|-------------- There are some issues in getting the triggered individual push URL. Manual intervention required to check.");
					}
			    }
			    if(con2!=null){
	            	con2.disconnect();
	            }
	        }else{
	        	// something wrong happened at Jenkins server
	        	System.out.println("|-------- something wrong happened at Jenkins server, could not get a proper HTTP response after posting form data");
	        }
	        if(con!=null){
            	con.disconnect();
            }
		}catch (MalformedURLException e) { 
		    e.printStackTrace();
		} 
		catch (IOException e) {   
		    e.printStackTrace();
		}
		return finalResponse;
	}
	
	public static String pushToIndividualZurichQABox(String hostname) throws UnsupportedEncodingException{
		String finalResponse = null;
		Authenticator authenticator = Authenticator.getCurrentUserAuthenticator("roysuj");  
		String cookies = authenticator.getToken();
		/* check if the ZEL push job is disabled or not 
	     * if enabled then push it, else first enable , then push, and then disable  */
	    if(isZELBuildDisabled(cookies)){
		    if(toggleZELBuild(cookies, true)){
		    	finalResponse = pushZELBuild(cookies);
		        toggleZELBuild(cookies, false);
		    }else{
		    	System.out.println("|-------- Unable to enable the ZEL push build. Something wrong with Jenkins configuration of the build.");
		    }
	    }else{
	    	finalResponse = pushZELBuild(cookies);
	    }
		return finalResponse;
	}
	
	/**
	 * Method to check if ZEL Push build is disabled or not
	 * @param cookies
	 * @return
	 */
	private static boolean isZELBuildDisabled(String cookies){
		boolean result = false;
		try {
			URL checkURL = new URL("http://d119030-005.dc.gs.com:8061/jenkins/view/ReleaseMgmt/job/PushNimbleBuildsToZEL/config.xml");
			HttpURLConnection checkConnection =  (HttpURLConnection)checkURL.openConnection();
			checkConnection.setRequestProperty("Cookie", "GSSSO=" + cookies);  
			checkConnection.setRequestMethod("GET");
			int responseCode = checkConnection.getResponseCode();
			if(responseCode==HttpURLConnection.HTTP_OK){
				BufferedReader in = new BufferedReader(new InputStreamReader(checkConnection.getInputStream()));
			    SAXReader reader = new SAXReader();
			    Document document = reader.read(in);
			    Node disabledNode = document.selectSingleNode("/project/disabled");
				if(disabledNode!=null){
					result = Boolean.parseBoolean(disabledNode.getStringValue());
					System.out.println("|----- PushNimbleBuildsToZEL disabled flag is : "+result);
				}
				in.close();
			}else{
				System.out.println("|----- Unable to read ZEL push config page to check for it disabled/enabled status. Please check the Jenkins page for proper functioning.");
			}
			if(checkConnection!=null){
				checkConnection.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Method to enable or disable the ZEL build job. 
	 * @param cookies
	 * @param enable (true for enabling job, false for disabling)
	 * @return
	 */
	private static boolean toggleZELBuild(String cookies, boolean enable){
		boolean result = false;
		try{
			URL zelBuildDisableUrl = new URL("http://d119030-005.dc.gs.com:8061/jenkins/view/ReleaseMgmt/job/PushNimbleBuildsToZEL/disable");
			URL zelBuildEnableUrl = new URL("http://d119030-005.dc.gs.com:8061/jenkins/view/ReleaseMgmt/job/PushNimbleBuildsToZEL/enable");
			HttpURLConnection conn =  null;
			if(enable){
				conn =  (HttpURLConnection)zelBuildEnableUrl.openConnection();
			}else{
				conn =  (HttpURLConnection)zelBuildDisableUrl.openConnection();
			}
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
	        	result = true;
	        	if(enable){
	        		System.out.println("|------- ZEL push  build has been enabled");
	        	}else{
	        		System.out.println("|------- ZEL push  build has been disabled");
	        	}
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
		return result;
	}
	
	
	/**
	 * Method to send the POST request for initiating a build and return the link
	 * @param cookies
	 * @return
	 */
	private static String pushZELBuild(String cookies){
		String buildLink = null;
		try{
			URL zelBuildUrl = new URL("http://d119030-005.dc.gs.com:8061/jenkins/view/ReleaseMgmt/job/PushNimbleBuildsToZEL/build");
			HttpURLConnection buildConn =  (HttpURLConnection)zelBuildUrl.openConnection();
		    buildConn.setRequestProperty("Cookie", "GSSSO=" + cookies);  
		    buildConn.setRequestMethod("POST");
		    buildConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    buildConn.setRequestProperty("Content-Language", "en-US");  
		    buildConn.setUseCaches (false);
	        buildConn.setDoInput(true);
	        buildConn.setDoOutput(true);
	        //Send request
	        DataOutputStream wrb = new DataOutputStream (buildConn.getOutputStream ());
	        // wr.writeBytes (urlParameters);
	        wrb.flush ();
	        wrb.close ();
	        if(buildConn.getResponseCode()==HttpURLConnection.HTTP_OK || buildConn.getResponseCode()==HttpURLConnection.HTTP_CREATED || buildConn.getResponseCode()==HttpURLConnection.HTTP_MOVED_TEMP){
	        	/* read the response and publish the build link */
	        	Thread.sleep(10000);
	        	URL checkURL = new URL("http://d119030-005.dc.gs.com:8061/jenkins/view/ReleaseMgmt/job/PushNimbleBuildsToZEL/api/json");
			    HttpURLConnection con2 =  (HttpURLConnection)checkURL.openConnection();
			    con2.setRequestProperty("Cookie", "GSSSO=" + cookies);  
			    con2.setRequestMethod("GET");
			    int responseCode = con2.getResponseCode();
			    if(responseCode==HttpURLConnection.HTTP_OK){
			    	BufferedReader in = new BufferedReader(new InputStreamReader(con2.getInputStream()));
					String inputLine;
					StringBuffer checkResponse = new StringBuffer();
					while ((inputLine = in.readLine()) != null) {
						checkResponse.append(inputLine);
					}
					in.close();
					String checkJsonResponse = checkResponse.toString();
					//System.out.println(checkResponse.toString());
					if(checkJsonResponse!=null && !checkJsonResponse.isEmpty()){
						buildLink = MyJsonParser.getQueueStatus(checkJsonResponse); 
					}else{
						buildLink = "Due to some system issues the link for ongoing push to Zurich host is not present as of now. Will share it as soon as it becomes available.";
						System.out.println("|-------------- There are some issues in getting the triggered individual ZEL push URL. Manual intervention required to check.");
					}
			    }
	        }else{
	        	System.out.println("|-------- ZEL Push build attempt has failed. Could not get proper HTTP response after posting form data.");
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		return buildLink;
	}
	
	/**
	 * Method to check if the scheduled nimbl push is running.
	 * @return
	 * 		true if Job is running, false otherwise
	 */
	public static boolean isNimblPushRunning(){
		boolean result = false;
		Authenticator authenticator = Authenticator.getCurrentUserAuthenticator("roysuj");  
		String cookies = authenticator.getToken();
		try{
			URL checkURL = new URL("http://d119030-005.dc.gs.com:8061/jenkins/view/ReleaseMgmt/job/PushNimbleBuildsToQa/lastBuild/api/json");
		    HttpURLConnection con2 =  (HttpURLConnection)checkURL.openConnection();
		    con2.setRequestProperty("Cookie", "GSSSO=" + cookies);  
		    con2.setRequestMethod("GET");
		    int responseCode = con2.getResponseCode();
		    if(responseCode==HttpURLConnection.HTTP_OK){
		    	BufferedReader in = new BufferedReader(new InputStreamReader(con2.getInputStream()));
				String inputLine;
				StringBuffer checkResponse = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					checkResponse.append(inputLine);
				}
				in.close();
				String checkJsonResponse = checkResponse.toString();
				if(checkJsonResponse!=null && !checkJsonResponse.isEmpty()){
					JsonParser parser = new JsonParser();
					JsonElement el = parser.parse(checkJsonResponse);
					JsonObject jo = el.getAsJsonObject();
					JsonElement resultElement = jo.get("result");
					if(resultElement!=null && resultElement.isJsonNull()){
						System.out.println("|----- Nimbl push is running as of now");
						result = true;
					}
				}else{
					System.out.println("|------------ No Json response obtained for NIMBL build. Unable to determine its state. Assuming its running.");
					result = true;
				}
		    }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
