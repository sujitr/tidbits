package com.sujit.gsmail.utils;

public class AutosysJob {
	private String jobName;
	private String lastStartTime;
	private String lastEndTime;
	private String runLength;
	private String currentStatus;
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getLastStartTime() {
		return lastStartTime;
	}
	public void setLastStartTime(String lastStartTime) {
		this.lastStartTime = lastStartTime;
	}
	public String getLastEndTime() {
		return lastEndTime;
	}
	public void setLastEndTime(String lastEndTime) {
		this.lastEndTime = lastEndTime;
	}
	public String getRunLength() {
		return runLength;
	}
	public void setRunLength(String runLength) {
		this.runLength = runLength;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
}
