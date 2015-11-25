package com.sujit.gsmail.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gs.security.gsauthn.kerberos.Authenticator;

public class AutosysJobStatusCheckUtility {
	public static void main(String[] args) {
		AutosysJob aj = getJobStatus("CLSVDCOM_MARC_D_BS1_JB1");
		System.out.println(aj.getCurrentStatus());
	}
	
	public static AutosysJob getJobStatus(String jobName) {
		AutosysJob aj = new AutosysJob();
		try{
			Authenticator authenticator = Authenticator.getCurrentUserAuthenticator("roysuj");  
			String cookies = authenticator.getToken();
			URL myURL = new URL("http://autosys-ny.web.gs.com/scheduling/cgi-bin/autosys/web/user/jobconfiguration.cgi");
			StringBuilder urlParamBuilder = new StringBuilder();
			urlParamBuilder.append("jobconfigurationfunction=" + URLEncoder.encode("SUMMARY", "UTF-8"));
			urlParamBuilder.append("&").append("usecookies=" + URLEncoder.encode("Y", "UTF-8"));
			urlParamBuilder.append("&").append("envtype=" + URLEncoder.encode("Production", "UTF-8"));
			urlParamBuilder.append("&").append("region=" + URLEncoder.encode("NY", "UTF-8"));
			if(jobName.startsWith("CLSVDCOM_")|| jobName.startsWith("CLSVPWM_")){
				urlParamBuilder.append("&").append("instance=" + URLEncoder.encode("CT1", "UTF-8"));
			}else{
				urlParamBuilder.append("&").append("instance=" + URLEncoder.encode("IM1", "UTF-8"));
			}
			urlParamBuilder.append("&").append("batch=" + URLEncoder.encode(jobName.substring(0, jobName.indexOf("_")), "UTF-8"));
			urlParamBuilder.append("&").append("jobfilter=" + URLEncoder.encode("%", "UTF-8"));
			urlParamBuilder.append("&").append("selectjobfilter=" + URLEncoder.encode(jobName.substring(0, jobName.indexOf("_"))+"[-._]", "UTF-8"));
			urlParamBuilder.append("&").append("customjobfilter=" + URLEncoder.encode(jobName.substring(jobName.indexOf("_")+1,jobName.length()), "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("AC", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("FA", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("IN", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("OH", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("OI", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("QU", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("PE", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("RE", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("RU", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("ST", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("SU", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("TE", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("RW", "UTF-8"));
			urlParamBuilder.append("&").append("statusfilters=" + URLEncoder.encode("WA", "UTF-8"));
			
			urlParamBuilder.append("&").append("reportformat=" + URLEncoder.encode("Extended", "UTF-8"));
			urlParamBuilder.append("&").append("displayformat=" + URLEncoder.encode("Job Configuration First", "UTF-8"));
			urlParamBuilder.append("&").append("printlevel=" + URLEncoder.encode("ALL", "UTF-8"));
			urlParamBuilder.append("&").append("runnumber=" + URLEncoder.encode("ANY", "UTF-8"));
			urlParamBuilder.append("&").append("refreshcounter=" + URLEncoder.encode("0", "UTF-8"));
			
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
			wr.writeBytes (urlParamBuilder.toString());
			wr.flush ();
			wr.close ();
			if(con.getResponseCode()==HttpURLConnection.HTTP_OK){
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				String responseText = response.toString();
				if(responseText!=null && !responseText.isEmpty()){
					Document doc = Jsoup.parse(responseText, "UTF-8");
					Element table = doc.getElementById("instancebannerA");
					if(table!=null){
						Elements rows = table.getElementsByTag("tr");
						if(rows!=null && rows.size()>0){
							Element firstRow = rows.first();
							Elements internalTables = firstRow.getElementsByTag("table");
							Element summaryTable = internalTables.first();
							Iterator<Element> ite = summaryTable.select("td").iterator();
							for(int i = 0; i < 8; i++){
								ite.next();
							}
							aj.setJobName(ite.next().text());
							aj.setLastStartTime(ite.next().text());
							aj.setLastEndTime(ite.next().text());
							aj.setRunLength(ite.next().text());
							aj.setCurrentStatus(ite.next().text());
							System.out.println("|------ Extracted job details for :"+jobName);
						}else{
							System.out.println("|------ No summary details found on this job : "+jobName);
							aj = null;
						}
					}else{
						System.out.println("|------ No extended details found on this job : "+jobName);
						aj = null;
					}
				}else{
					System.out.println("|------ could not get any response from autosys system");
					aj = null;
				}
			}
			if(con!=null){
            	con.disconnect();
            }
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aj;
	}
}
