package com.sujit.gsmail.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyJsonParser {

	public static void main(String[] args) {
		
	}
	
	public static String getQueueStatus(String jsonResponse){
    	int latestBuildNumber = -1;
    	int nextBuildNumber = -1;
    	String result = null;
    	JsonParser parser = new JsonParser();
    	JsonElement el = parser.parse(jsonResponse);
    	JsonObject jo = el.getAsJsonObject();
    	JsonArray ja = (JsonArray) jo.get("builds");
    	//System.out.println(">> Last build number: "+ja.get(0).getAsJsonObject().get("number").getAsString());
    	try{
    		latestBuildNumber = Integer.parseInt(ja.get(0).getAsJsonObject().get("number").getAsString());
    	}catch(NumberFormatException nex){
    		System.out.println(">> Last build number is weird.");
    	}
    	
	    JsonElement nextBuildNumberNode = jo.get("nextBuildNumber");
	    if(nextBuildNumberNode!=null){
	    	//System.out.println(">> Next Build Number :"+nextBuildNumberNode.getAsString());
	    	try{
	    		nextBuildNumber = Integer.parseInt(nextBuildNumberNode.getAsString());
	    	}catch(NumberFormatException nex){
	    		System.out.println(">> Next build number is weird.");
	    	}
	    }
	    if(jo.has("queueItem") && !jo.get("queueItem").isJsonNull()){
	    	JsonObject queueItemObject = jo.getAsJsonObject("queueItem");
		    if(queueItemObject!=null && queueItemObject.get("blocked").getAsBoolean()){
		    	if(latestBuildNumber!=-1 && nextBuildNumber!=-1 && latestBuildNumber==nextBuildNumber){
		    		result =  ">> The current push request has been queued as: #"+nextBuildNumber;
		    	}else if(latestBuildNumber!=-1 && nextBuildNumber!=-1 && latestBuildNumber!=nextBuildNumber){
		    		result =  ">> The current push request (#"+nextBuildNumber+") has been queued as "+queueItemObject.get("why").getAsString();
		    	}
		    }else{
		    	result =  ">> The current push request has been queued as: "+ja.get(0).getAsJsonObject().get("url").getAsString();
		    }
	    }else{
	    	result =  ">> The current push request has been queued as: "+ja.get(0).getAsJsonObject().get("url").getAsString();
	    }
	    return result;
    }

}
