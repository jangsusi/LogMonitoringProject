package com.logmonitoring.model;

import com.logmonitoring.common.Util;

public class LogData {
	String[] logFactors;
	String time;
	String IP;
	String email;
	String method;
	String url;
	
	public LogData(String line) {
		this.logFactors = line.split(" ");
		if(logFactors.length != 5) {
			time = Util.getTime(line);
		}
	}
	
	public String getTime() {
		return time;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LogData) {
			if((this.IP.equals(((LogData) obj).IP))
				&& (this.email.equals(((LogData) obj).email))
				&& (this.method.equals(((LogData) obj).method))
				&& (this.url.equals(((LogData) obj).url))) {
				return true;
			}		
		}
		return false;	
	}

	@Override
	public int hashCode() {
		int result = 0;
		result += IP.hashCode();
		result += email.hashCode();
		result += method.hashCode();
		result += url.hashCode();
		return result;
	}
	
	public String getLogData() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(IP).append(" ").append(email).append(" ").append(method).append(" ").append(url);
		return strBuilder.toString();
	}

	private boolean isNotSupportedFormat() {
		if(!logFactors[1].equals("-") && !logFactors[2].contains("@")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean setLogData() {
		if(isNotSupportedFormat()) {
			return false;
		} else {
			IP = logFactors[0];
			email = logFactors[2];
			method = logFactors[5];
			url = logFactors[6];
		}
		return true;
	}

	public boolean isDifferentTime(String time) {
		if(!this.time.equals(time)) {
			return true;
		} else {
			return false;
		}
	}
		
}
