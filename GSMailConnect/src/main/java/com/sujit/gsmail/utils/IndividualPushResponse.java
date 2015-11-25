package com.sujit.gsmail.utils;

import java.util.ArrayList;

/**
 * Class to represent the reponse of an Individual Push request from a single mail.
 * One single mail can contain push request for many hosts. So the response can also contain 
 * links for all those push.
 * @author roysuj
 * 
 */
public class IndividualPushResponse {
	public enum ResponseMode {NORMAL, ERROR, NIMBLRUNNING, PQARESTRICTED}
	private ResponseMode mode;
	private boolean isAllPushDone;
	private ArrayList<String> links;
	public boolean isAllPushDone() {
		return isAllPushDone;
	}
	public void setAllPushDone(boolean isAllPushDone) {
		this.isAllPushDone = isAllPushDone;
	}
	public ArrayList<String> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<String> links) {
		this.links = links;
	}
	public ResponseMode getMode() {
		return mode;
	}
	public void setMode(ResponseMode mode) {
		this.mode = mode;
	}
	
	
}
