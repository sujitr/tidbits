package com.sujit.gsmail.reader;

public class ReaderBase {
	private String userName;
	private String passWord;
	private String domain;
	
	public ReaderBase(String userName, String passWord, String domain) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.domain = domain;
	}

	protected String getUserName() {
		return userName;
	}

	protected String getPassWord() {
		return passWord;
	}

	protected String getDomain() {
		return domain;
	}
	
	
	
}
