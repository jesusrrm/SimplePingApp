package com.recruitee.doclerholding.SimplePingApp.model;

import java.util.Date;

public class Config {
	String host="";
	long delayPingICMP;
	long delayPingTCP;
	long delayTrace;
	long pingTimeOutTCP;
	String lastPingICMP="";
	String lastPingTCP="";
	long pingResponseTime;

	Date dateLastPingTCP;
	String lastTrace="";

	String report_url="";
	String report_file="";
	private String trace_log_path;
	private String pingtcp_log_path;
	
	
	
	 

	public String getTrace_log_path() {
		return trace_log_path;
	}

	public void setTrace_log_path(String trace_log_path) {
		this.trace_log_path = trace_log_path;
	}

	public String getPingtcp_log_path() {
		return pingtcp_log_path;
	}

	public void setPingtcp_log_path(String pingtcp_log_path) {
		this.pingtcp_log_path = pingtcp_log_path;
	}

	public Date getDateLastPingTCP() {
		return dateLastPingTCP;
	}

	public void setDateLastPingTCP(Date dateLastPingTCP) {
		this.dateLastPingTCP = dateLastPingTCP;
	}

	public long getPingResponseTime() {
		return pingResponseTime;
	}

	public void setPingResponseTime(long pingResponseTime) {
		this.pingResponseTime = pingResponseTime;
	}

	public long getPingTimeOutTCP() {
		return pingTimeOutTCP;
	}

	public void setPingTimeOutTCP(long pingTimeOutTCP) {
		this.pingTimeOutTCP = pingTimeOutTCP;
	}

	public String getReport_url() {
		return report_url;
	}

	public void setReport_url(String report_url) {
		this.report_url = report_url;
	}

	public String getReport_file() {
		return report_file;
	}

	public void setReport_file(String report_file) {
		this.report_file = report_file;
	}

	public String getLastPingICMP() {
		return lastPingICMP;
	}

	public void setLastPingICMP(String lastPingICMP) {
		this.lastPingICMP = lastPingICMP;
	}

	public String getLastPingTCP() {
		return lastPingTCP;
	}

	public void setLastPingTCP(String lastPingTCP) {
		this.lastPingTCP = lastPingTCP;
	}

	public String getLastTrace() {
		return lastTrace;
	}

	public void setLastTrace(String lastTrace) {
		this.lastTrace = lastTrace;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public long getDelayPingICMP() {
		return delayPingICMP;
	}

	public void setDelayPingICMP(long delayPingICMP) {
		this.delayPingICMP = delayPingICMP;
	}

	public long getDelayPingTCP() {
		return delayPingTCP;
	}

	public void setDelayPingTCP(long delayPingTCP) {
		this.delayPingTCP = delayPingTCP;
	}

	public long getDelayTrace() {
		return delayTrace;
	}

	public void setDelayTrace(long delayTrace) {
		this.delayTrace = delayTrace;
	}

	public String toString() {
		return host + " - " + getReport_file();
	}
}
