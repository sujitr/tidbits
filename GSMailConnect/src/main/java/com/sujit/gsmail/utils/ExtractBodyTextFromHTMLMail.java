package com.sujit.gsmail.utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractBodyTextFromHTMLMail {
	static final  Logger logger = LoggerFactory.getLogger(ExtractBodyTextFromHTMLMail.class);
	public static String getRecentBodyAsString(String bodyHTML){
		String temp = "";
		try{
			if(bodyHTML.startsWith("<html") || bodyHTML.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?><html")){
				temp = bodyHTML.substring(bodyHTML.indexOf("<body"), bodyHTML.indexOf("</body>")+7).replaceAll("\\<.*?>", " ").replaceAll("(\\t|\\n|\\r|\\r\\n)+", " ").replaceAll("\\s+"," ").trim();
				if(temp.contains("From:")){
					 return StringEscapeUtils.unescapeHtml4(temp.substring(0, temp.indexOf("From:")));
				}else{
					return StringEscapeUtils.unescapeHtml4(temp);
				}
			}else{
				return bodyHTML;
			}
		}catch(Exception e){
			System.out.println("[[Some exception happened while parsing. Can be checked later]]");
			return temp;
		}
	}
	
	/**
	 * Method to pick up the conversation part of the email message without the 
	 * additional header and tailer parts
	 * @param bodyHTML
	 * 		: Entire email body as HTML String
	 * @return
	 * 		HTML part of the email where only the conversation is present, as a String
	 */
	public static String getBodyDivHTMLAsString(String bodyHTML){
		Document doc = Jsoup.parse(bodyHTML, "UTF-8");
    	Elements divElements = doc.getElementsByClass("WordSection1");
    	if(divElements==null || divElements.size()==0){
    		logger.info("|-------------  looks like its not an HTML mail, more so like Rich Text formatted mail, which is converted to HTML...");
    		Elements bodyElements = doc.getElementsByTag("body");
    		Element bodyElement = bodyElements.first();
    		return bodyElement.html();
    	}else{
    		logger.info("|------------  its an HTML mail...");
    		Element divelement = divElements.get(0);
        	return divelement.html();
    	}
	}
}
